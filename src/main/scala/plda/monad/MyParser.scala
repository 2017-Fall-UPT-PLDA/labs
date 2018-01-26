package plda.monad

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 14 Dec 2017
  *
  */
import cats.implicits._
case class ParseResult[T](
  nextInput: String,
  output:    T
)

trait MyParser[Output] {
  type Result = ParseResult[Output]

  def parse(string: String): Result

  def flatMap[NewOutput](f: Output => MyParser[NewOutput]): MyParser[NewOutput] = {
    new MyParser[NewOutput] {
      override type Result = ParseResult[NewOutput]

      override def parse(string: String): Result = {
        val ParseResult(next, output) = MyParser.this.parse(string)
        val r: MyParser[NewOutput] = f(output)
        r.parse(next)
      }
    }
  }

  def map[NewOutput](f: Output => NewOutput): MyParser[NewOutput] = ???
  /*
      for {
      _ <- quoteParser
      content <- ident
      _ <- quoteParser
    } yield content
  }
 */
}

object Test {
  val intParser:  MyParser[Int]    = ???
  val wordParser: MyParser[String] = ???

  val x: MyParser[(Int, String)] = for {
    i <- intParser
    w <- wordParser
  } yield (i, w)
}
