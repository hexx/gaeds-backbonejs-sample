package com.github.hexx.messageboard

import java.util.Date
import unfiltered.request._
import unfiltered.response._
import com.github.hexx.gaeds.{ Datastore, Mapper, Property }
import com.github.hexx.gaeds.Property._
import net.liftweb.json._

class Message(
    val name: Property[String],
    val message: Property[String],
    val date: Property[Date])
  extends Mapper[Message] {
  def this() = this(mock, mock, mock)
}

object Message extends Message

class MessageBoard extends unfiltered.filter.Plan {
  val messageLimit = 100

  def intent = {
    case GET(Path("/messages")) => {
      val json = JArray(Message.query.asIterator.map(_.toJObject).toList)
      Ok ~> ResponseString(compact(render(json)))
    }
    case req @ POST(Path("/messages")) => {
      if (req.underlying.getCharacterEncoding == null) {
        req.underlying.setCharacterEncoding("UTF-8")
      }
      val jobject = JsonBody(req).get.asInstanceOf[JObject]
      val m = Message.fromJObject(jobject)
      if (Message.query.count >= messageLimit) {
        Datastore.delete(Message.query.sort(_.date asc).asSingleKey)
      }
      m.put()
      Ok
    }
  }
}
