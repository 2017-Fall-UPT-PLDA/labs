package plda.json

/**
  * The important helpers are defined in this trait,
  * but they are also exposed via the singleton
  * object [[syntax]] for easy importing. So that
  * any scope where the following is imported
  * can easily do this ``value.toJson``:
  *
  * {{{
  *   import plda.json.syntax._
  *   val x: PLDA = ???
  *   x.toJson
  *   x.toJson.noSpaces
  * }}}
  *
  * Alternatively you can always extend (mixin) this
  * trait into your own classes to achieve the same
  * effect. Just be careful that you don't mixin, and
  * import the same definitions because then it would fail.
  *
  * {{{
  *   class MyClass extends JsonSyntax {
  *     //... somewhere where you define an encoder for the type of x
  *     x.toJson
  *   }
  *
  *   //bad—will wind up in a compiler error
  *   class MyClass extends JsonSyntax {
  *     import plda.json.syntax._
  *     //...
  *     x.toJson
  *   }
  * }}}
  *
  */
trait JsonSyntax {

  /**
    * Implicit classes are a convenient way to seamlessly "add"
    * domain specific methods to existing types.
    *
    * More on this here:
    * - https://docs.scala-lang.org/overviews/core/implicit-classes.html
    *
    * Important!
    * You should avoid putting too much login in these things, as it
    * can get pretty "magical" pretty quickly.
    */
  implicit class JsonEncoderOperations[T](val t: T) {
    def toJson(implicit enc: JsonEncoder[T]): Json = {
      enc.encode(t)
    }
  }

  implicit class JsonValueOperations(val json: Json) {
    def noSpaces: String = JsonPrettyPrinterImplementations.compact(json)

    def twoSpaces: String = JsonPrettyPrinter(2)(json)
  }

}



