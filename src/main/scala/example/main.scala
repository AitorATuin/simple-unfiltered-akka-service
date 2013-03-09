package example

import scala.util.control.Exception._
import org.slf4j.LoggerFactory

object ExampleMain {

  private val log = LoggerFactory.getLogger(getClass)

  final val DefaultPort = 8888

  def main(args:Array[String]) {

    log.info( s"Example Server Version ${example.BuildInfo.version}" )

    val port = 
      ( allCatch opt args.lift(0).getOrElse("").toInt )
        .filter( _ > 1024  )
        .getOrElse(DefaultPort)

    unfiltered.jetty.Http(port).plan(new ExampleServer()).run(
      { s =>
        log.info( s"Unfiltered Server started in port $s.port")
        Runtime.getRuntime.addShutdownHook(ShutdownHook)
      },
      { s =>
        log.info( "Unfiltered Server stopped" )
        log.info( "Stopping WorkQueue" )
        WorkQueue.shutdown
      }
    )


  }

  object ShutdownHook extends Thread {
    override def run =  {
      setName( "ExampleServiceShutdownHook" )
      println( "ShutdownHook Invoked" )
      WorkQueue.shutdown
      log.debug( "Shutdown Hook: Akka System stopped" )
    }
  }


}
  
