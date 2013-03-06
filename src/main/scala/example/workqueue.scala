package example

import akka.actor.{ Actor, ActorSystem, Props }
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._

import org.slf4j.LoggerFactory

object WorkQueue {

  trait Msg

  object Msg {
    case class UpperCase(s:String) extends Msg
    case class Count(s:String)     extends Msg
  }

  def put(m: Msg) = worker ? m

  def shutdown() = system.shutdown()

  private implicit val  timeout = Timeout(200 millis)
  private val           system  = ActorSystem("WorkQueue")
  private[this] val     worker  = system.actorOf(Props[WorkQueue], name = "WorkQueue")

}

class WorkQueue extends Actor {

  private val log = LoggerFactory.getLogger(getClass)

  import WorkQueue._

  def receive:Receive = {
    // FIXME make a random pause before replying
    case Msg.UpperCase(s) => sender ! s.toUpperCase
  }

}

