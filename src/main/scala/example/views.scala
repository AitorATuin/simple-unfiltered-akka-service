package example

import unfiltered.response._
import scala.xml._

import java.util.Date

trait View {

  type Params = Map[String,String]

  protected def content(params:Params = Map()): NodeSeq

  def render(params:Params = Map()) = ResponseString(layout(params).toString)

  def layout(params:Params) =
    <html>
      <head>
      </head>
      <body>
        <h1>Example Service Test Page</h1>
        <span><a href="/">Home</a></span> |
        <span><a href="/touppercase">Turn Text Into Uppercase</a></span>
        <hr/>
        <div id="content" height="550px" >
          {content(params)}
        </div>
        <div>Rendered at {new Date}. Example Service Version {example.BuildInfo.version} </div>
      </body>
    </html>

}

object Index extends View {
  def apply() = render()
  def content(params:Params = Map()) = 
    <div id="index">
      <p>I am a work in progress and a sample, please do not expect much from me!</p>
    </div>
}

object UpperCasePage extends View {

  def apply() = render()

  def apply(payload:String, response:String) =
    render(Map(
      "response"  -> response,
      "payload"   -> payload
    ))

  def content(params:Params = Map()) = {
    val payload   = params.get("payload")
    val response  = params.get("response")
    <div id="result">
    { 
      if (response.isDefined)
        renderPayload(response.get)
      else <div>Ohai, plzzz send some dataz!"</div><br/>
    }
    </div>
    <div id="pushform">
      <form action="touppercase" method="post" >
        <label for="payload">Payload</label><br/>
        <textarea name="payload" id="payload" cols="80" rows="10">{payload getOrElse ""}</textarea><br/>
        <input type="submit" name="push" value="Push" />
      </form>
    </div>
  }

  def renderPayload(response:String):NodeSeq = {
    <div id="pushdata">
      <p>Uppercased!:</p>
      <p><b><pre>{response}</pre></b></p>
    </div>
  }
    
}

object NotFoundPage extends View {
  def apply() = render()
  def content(params:Params = Map()) = 
    <div id="NotFound">
      <pre>The page you are looking for does not exist</pre>
    </div>
}

object ErrorPage extends View {
  def apply(error:String) = render( Map( "error" -> error ) )
  def content(params:Params = Map()) = 
    <div id="Error">
      <p>The uri you just used caused an error: </p>
      <pre>{params.getOrElse("error", "Unknown Error")}</pre>
    </div>
}



