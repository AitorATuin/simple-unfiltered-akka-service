package example


import org.slf4j.LoggerFactory;

object ExampleMain {

  private val log = LoggerFactory.getLogger(getClass)

  final val DefaultPort = 8088


  Runtime.getRuntime.addShutdownHook(ShutdownHook)  

  def main(args:Array[String]) {

    log.info( <div>Example Server Version {example.BuildInfo.version}</div>.text )


    unfiltered.jetty.Http(8888).plan(new ExampleServer()).run(
      { s => 
        log.info( "Unfiltered Server started in port %s".format(s.port))
      }, 
      { s => 
        log.info( "Unfiltered Server stopped" )
        log.info( "Starting " )
        WorkQueue.system.shutdown
      }
    )

  }



  object ShutdownHook extends Thread {
    override def run =  {
      setName( "IBListenerShutdownHook" )
      println( "ShutdownHook Invoked" )
      WorkQueue.system.shutdown
      log.debug( "Shutdown Hook: Akka System stopped" )
    }
  }


}
  
