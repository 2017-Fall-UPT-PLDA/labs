package plda.json

/**
  * JSON is a data markup language. And this is our structured
  * representation of it.
  *
  * See more information on JSON here:
  * - https://www.w3schools.com/js/js_json_datatypes.asp
  *
  * To learn more about sealed traits and case classes see here:
  * - https://underscore.io/blog/posts/2015/06/02/everything-about-sealed.html
  * - https://docs.scala-lang.org/tour/case-classes.html
  */
sealed trait JsonValue

final case class JsonBoolean(boolean: Boolean) extends JsonValue

final case class JsonString(value: String) extends JsonValue

final case class JsonNumber(value: BigDecimal) extends JsonValue

final case class JsonArray(jss: List[JsonValue]) extends JsonValue

final case class JsonObject(properties: Map[String, JsonValue]) extends JsonValue

/**
  * Not implemented on purpose. We will see why in our next lab :)
  */
//final case object JsonNull extends JsonValue

/**
  * Convenience methods to help construct these JsonValues, we could have
  * grouped just as easily in their respective companion objects as well
  */
object JsonValue {
  def apply(s: Boolean): JsonBoolean = JsonBoolean(s)

  def apply(s: String): JsonString = JsonString(s)

  def apply(b: BigDecimal): JsonNumber = JsonNumber(b)

  def apply(jss: JsonValue*): JsonArray = JsonArray(jss.toList)

  def apply(props: (String, JsonValue)*): JsonObject = JsonObject(props.toMap)
}



