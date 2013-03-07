package example 

import unfiltered.request._
import unfiltered.response._
import unfiltered.Async


import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._


import org.slf4j.LoggerFactory;



class ExampleServer
  extends unfiltered.filter.async.Plan {

  import WorkQueue.Msg

  private val log = LoggerFactory.getLogger(getClass)

  def intent = {

    // Handles the index|home page
    case rq @ Path("/") =>  rq.respond( Ok ~> IndexPage() ~> HtmlContent )

    // The /touppercase resource
    case rq @ Path("/touppercase") => rq match {

        case POST(Params(Payload(p))) =>

        async(rq) {
          WorkQueue.put( Msg.UpperCase(p) ).mapTo[String].map({ res =>
            Ok ~> UpperCasePage(p,res) ~> HtmlContent
          })
        }

        case _ => rq.respond( Ok ~> UpperCasePage() ~> HtmlContent)

    }

    // Otherwise log and fail
    case rq @ Path(path) & Method(meth) & Params(ps) & RemoteAddr(remoteAddr) =>

      val msg = "NOT FOUND => "   + meth + " " + path +
                " with params '"  + ps.toSeq.reverse.toMap      + "'" +
                " and body '"     + new String(Body.bytes(rq))  + "'"

      log.error( msg )
      rq.respond( NotFound ~> NotFoundPage() ~> HtmlContent )

  }


  /** 
    TODO move to utils package 
    TODO second param list should accept onError handler.
    TODO make generic error handler to pass as default parameter for the error handler.
    */
  def async[A](responder: Async.Responder[A])(body: => Future[ResponseFunction[A]]) {
    body onComplete {
      case Success(rf)  => responder.respond(rf)
      case Failure(x)    => 
        log.error("Async Task completed with error "+x.toString)        
        responder.respond(RequestTimeout ~> ResponseString(x.toString) )
    }
  }

}

/** Matcher for the payload params */
object Payload extends Params.Extract( "payload", Params.first ~> Params.nonempty )

/** Matcher to get the request method */
object Method {
  def unapply[T](req: HttpRequest[T]) = Some(req.method.toUpperCase)
}


