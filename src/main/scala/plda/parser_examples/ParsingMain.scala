package plda.parser_examples

import plda.parser._
import plda.json._

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com
  * @since 02 Nov 2017
  *
  */
object ParsingMain extends App {
  val printer = JsonPrettyPrinter()

  import JsonParser._

  parseAll(jsonParser,
    """  {"question":{"part1":"The answer to life?","part2":"The Universe?","part3":"And Everything else?"},"answer":42}""") match {
    case Success(result, _) => println(result)
    case s: NoSuccess => throw new RuntimeException(s.msg)
  }

  parseAll(jsonParser,
    """  [{}, "string", 42, [1, 2]]""") match {
    case Success(result, _) => println(result)
    case s: NoSuccess => throw new RuntimeException(s.msg)
  }


}
