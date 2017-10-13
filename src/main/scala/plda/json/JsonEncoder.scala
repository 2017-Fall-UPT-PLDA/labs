package plda.json

/**
  * Traits with one type parameter are usually candidates for what is called
  * the "type-class" pattern
  *
  * See more about this here:
  * - http://danielwestheide.com/blog/2013/02/06/the-neophytes-guide-to-scala-part-12-type-classes.html
  * - https://typelevel.org/cats/typeclasses.html
  *
  */
trait JsonEncoder[T] {
  def encode(t: T): Json
}

/**
  * We provide a convenient constructor so that instead of writing:
  * {{{
  *   implicit val intEncoder: JsonEncoder[Int] = new JsonEncoder[Int] {
  *     override def encode(t: Int): Json = JsNumber(t)
  *   }
  * }}}
  *
  * we can write:
  * {{{
  *   implicit val intEncoder: JsonEncoder[Int] = JsonEncoder { i => JsonNumber(i) }
  * }}}
  *
  * More on Function values:
  * - https://alvinalexander.com/scala/how-to-use-functions-as-variables-values-in-scala-fp
  *
  */
object JsonEncoder {
  def apply[T](f: T => Json): JsonEncoder[T] = new JsonEncoderImpl[T](f)

  private class JsonEncoderImpl[T](f: T => Json) extends JsonEncoder[T] {
    override def encode(t: T): Json = f(t)
  }

}
