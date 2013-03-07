package example

import akka.actor.{ Actor, ActorSystem, Props }
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._

import org.slf4j.LoggerFactory

object WorkQueue {

  sealed trait Msg // Can't extend outside file (sealed).

  object Msg {
    case class UpperCase(s: String) extends Msg
    case class Count(s: String)     extends Msg
  }

  def put(m: Msg) = worker ? m

  def shutdown() = system.shutdown()

  private implicit  val timeout = Timeout(1 second)
  private[this]     val system  = ActorSystem("WorkQueue")
  private[this]     val worker  = system.actorOf(Props[WorkQueue], name = "WorkQueue")

}

class WorkQueue extends Actor {

  private val log = LoggerFactory.getLogger(getClass)

  import WorkQueue._

  def receive:Receive = {
    case Msg.UpperCase(s) => 
      // Sleep to give impression of long and costly operation
      val sleep = scala.util.Random.nextInt(999)+1
      Thread.sleep(sleep)
      sender ! s.toUpperCase
  }

}

