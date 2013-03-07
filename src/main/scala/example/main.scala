package example

import scala.util.control.Exception._
import org.slf4j.LoggerFactory

object ExampleMain {

  private val log = LoggerFactory.getLogger(getClass)

  final val DefaultPort = 8888

  def main(args:Array[String]) {

    log.info( <div>Example Server Version {example.BuildInfo.version}</div>.text )

    val port = 
      ( allCatch opt args.lift(0).getOrElse("").toInt )
        .filter( _ > 1024  )
        .getOrElse(DefaultPort)

    unfiltered.jetty.Http(port).plan(new ExampleServer()).run(
      { s =>
        log.info( "Unfiltered Server started in port %s".format(s.port))
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
      setName( "IBListenerShutdownHook" )
      println( "ShutdownHook Invoked" )
      WorkQueue.shutdown
      log.debug( "Shutdown Hook: Akka System stopped" )
    }
  }


}
  
