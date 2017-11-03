package plda.parser

import plda.json._

import scala.util.parsing.combinator.JavaTokenParsers

object JsonParser extends JavaTokenParsers {
  override val skipWhitespace: Boolean = true
  val openBrace: Parser[String] = literal("{")
  val closedBrace: Parser[String] = literal("}")
  val quoteParser: Parser[Unit] = literal(""""""").map(_ => ())
  val field: Parser[String] = {
    for {
      _ <- quoteParser
      content <- ident
      _ <- quoteParser
    } yield content
  }

  val jsonNumber: Parser[JsonNumber] =
    wholeNumber.map(s => JsonValue(s.toInt))

  val jsonString: Parser[JsonString] =
    stringLiteral.map(s => JsonValue(s))

  //  "field" : 42
  val property: Parser[(String, JsonValue)] = {
    for {
      fieldName: String <- field
      _: String <- literal(":")
      n: Json <- jsonNumber | jsonString | jsonObject
    } yield (fieldName, n)
  }

  lazy val jsonObject: Parser[JsonObject] = {
    for {
      _ <- openBrace
      properties <- repsep(property, literal(","))
      _ <- closedBrace
    } yield JsonValue(properties: _*)
  }

  val jsonArray: Parser[JsonArray] = {
    for {
      _ <- literal("[")
      content <- repsep(jsonParser | jsonString | jsonNumber, literal(","))
      _ <- literal("]")
    } yield JsonArray(content)
  }

  lazy val jsonParser: Parser[JsonValue] = {
    jsonObject | jsonArray
  }
}



