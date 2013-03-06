package example

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props


import scala.concurrent.duration._
import akka.util.Timeout

import akka.pattern.ask

import org.slf4j.LoggerFactory


object WorkQueue {

  trait Msg
  object Msg {
    case class UpperCase(s:String) extends Msg
    case class Count(s:String)     extends Msg
  }

  implicit val timeout = Timeout(200 millis)

  val system = ActorSystem("IBListener")

  def put(m: Msg) = worker ? m

  private[this] val worker = system.actorOf(Props[WorkQueue], name = "WorkQueue")

}

class WorkQueue extends Actor {

  private val log = LoggerFactory.getLogger(getClass)

  import WorkQueue._

  def receive:Receive = {
    // FIXME make a random pause before replying
    case Msg.UpperCase(s) => sender ! s.toUpperCase
  }

}

