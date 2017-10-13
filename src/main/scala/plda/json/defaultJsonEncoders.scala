package plda.json

/**
  * Both in Scala, and Haskell, when we define type-classes (see docs for [[JsonEncoder]]),
  * we provide default implementations for existing types. It can be very cumbersome,
  * but for a library designer and implementer it is an absolutely necessary step.
  *
  * Luckily, Haskell has pioneered methods of automatically deriving many type-classes,
  * and those methods have been adapted for scala. But this is a very advanced topic
  * that we might touch upon later. For now I'll just paste some documentation here:
  * - https://github.com/milessabin/kittens
  *
  */
trait DefaultJsonEncoders
  extends NumericJsonEncoders
    with MiscJsonEncoders
    with CollectionJsonEncoders

trait NumericJsonEncoders {
  implicit val byteJsonEncoder: JsonEncoder[Byte] = JsonEncoder { b => JsonNumber(BigDecimal(b)) }
  implicit val intJsonEncoder: JsonEncoder[Int] = JsonEncoder { i => JsonNumber(i) }
  implicit val longJsonEncoder: JsonEncoder[Long] = JsonEncoder { l => JsonNumber(l) }
  implicit val floatJsonEncoder: JsonEncoder[Float] = JsonEncoder { f => JsonNumber(f.toDouble) }
  implicit val doubleDecimalJsonEncoder: JsonEncoder[Double] = JsonEncoder { d => JsonNumber(d) }
  implicit val bigDecimalJsonEncoder: JsonEncoder[BigDecimal] = JsonEncoder { bd => JsonNumber(bd) }

  /**
    * Now, you surely noticed that in some cases I write:
    * {{{
    *   JsonEncoder { f => JsonNumber(BigDecimal(f)) }
    * }}}
    * and in other cases:
    * {{{
    *   JsonEncoder { i => JsonNumber(i) }
    * }}}
    *
    * That is because [[JsonNumber]] has a [[BigDecimal]] fields, and scala [[Predef]],
    * the default imports available to every scala program, define implicit conversion
    * from some numberic types, to [[BigDecimal]]. An example of such a conversion is this one
    * [[scala.math.BigDecimal#double2bigDecimal]].
    *
    * This process is called "implicit conversion" and you can define such methods that do
    * these conversions as such:
    * {{{
    *     /** Implicit conversion from `Int` to `BigDecimal`. */
    *     implicit def int2bigDecimal(i: Int): BigDecimal = apply(i)
    * }}}
    *
    * One should be careful when defining these, as it can lead to unexpected results,
    * but the safest way to use these is automatically deriving type-classes from other
    * type-classes as you can see in the [[CollectionJsonEncoders]] trait.
    *
    * More on implicit conversions:
    * - https://docs.scala-lang.org/tour/implicit-conversions.html
    *
    */
}

trait MiscJsonEncoders {
  implicit val stringJsonEncoder: JsonEncoder[String] = JsonEncoder(JsonString.apply)
  implicit val booleanJsonEncoder: JsonEncoder[Boolean] = JsonEncoder(JsonBoolean.apply)
}

trait CollectionJsonEncoders {
  
  /**
    * This is where we start writing Prolog-like code to be executed by the scala type-inference :)
    *
    * In essence every one of these implicit defs says something of the following:
    * "if I have an [[JsonEncoder]] for some type T, then I can create one for [[List]] of T".
    *
    * Which is quite easy to see why, since the rules for encoding List[Int], or List[String]
    * will only vary because of [[Int]] or [[String]], the logic for serializing [[List]] stay
    * the same — namely, create a [[JsonArray]] containing the [[Json]] corresponding to the
    * type of the elements of the [[List]].
    *
    * We can go even further than this, but this is abstract enough for now :)
    */
  implicit def summonListEncoder[T](implicit enc: JsonEncoder[T]): JsonEncoder[List[T]] = {
    JsonEncoder { list: List[T] =>
      JsonArray(list map enc.encode)
    }
  }

  //FIXME: for now we represent this as an array, but JsonNull is better suited
  implicit def summonOptionEncoder[T](implicit enc: JsonEncoder[T]): JsonEncoder[Option[T]] = {
    JsonEncoder { opt: Option[T] =>
      JsonArray((opt map enc.encode).toList)
    }
  }
}
