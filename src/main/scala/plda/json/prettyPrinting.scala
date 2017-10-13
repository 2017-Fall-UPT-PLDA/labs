package plda.json

import scala.collection.immutable

trait JsonPrettyPrinter {
  def apply(json: Json): String

  /**
    * If zero, then no spaces will be added to our JSON,
    * if greater than zero then indentation will also be added.
    */
  def spaces: Int
}

/**
  * This is a companion object, if you come from  the Java world you can
  * think of it as holding all static fields and methods of a class — since
  * scala classes can't have static fields. But it is more than that,
  * it is also a value that you can pass around, and it has a unique type.
  *
  * Every class/trait is allowed to have a "companion" object with
  * the same name. But objects can also have unique names. For the
  * latter scroll down to [[JsonPrettyPrinterImplementations]].
  *
  * More on companion objects:
  *  - https://docs.scala-lang.org/tour/singleton-objects.html
  *
  * And, a useful tip of software engineering, you see that the apply
  * method is essentially a "factory" method that gives us different
  * implementations depending on what we want. Thus, neatly
  * hiding all implementation details of these printers, while
  * exposing a clean interface.
  */
object JsonPrettyPrinter {

  /**
    * Methods named ``apply`` have a special status in scala. Any object
    * that has an ``apply`` method can be used with function call syntax.
    *
    * e.g.
    * {{{
    *   PrettyPrinter(42)
    *   // is semantically equivalent to:
    *   PrettyPrinter.apply(42)
    * }}}
    *
    * More on this here:
    * - https://twitter.github.io/scala_school/basics2.html
    */
  def apply(spaces: Int = 0): JsonPrettyPrinter = {
    if (spaces < 0)
      throw new IllegalArgumentException("cannot have negative number of spaces")
    else if (spaces == 0) {
      JsonPrettyPrinterImplementations.compact
    } else {
      new JsonPrettyPrinterImplementations.SpacedJsonPrettyPrinter(spaces)
    }
  }
}

/**
  * In here we keep two implementations for PrettyPrinters. And we want to keep
  * this as an implementation details of the package [[plda.json]] package.
  *
  * The implementation [[JsonPrettyPrinterImplementations.compact]] is optimized for
  * using absolutely no spaces in the string representation of JSON,
  * while [[JsonPrettyPrinterImplementations.SpacedJsonPrettyPrinter]] does indentation,
  * adds spaces between commas, and so forth.
  *
  * More on access qualifiers in general:
  * - http://www.jesperdj.com/2016/01/08/scala-access-modifiers-and-qualifiers-in-detail/
  */
private[json] object JsonPrettyPrinterImplementations {

  object compact extends JsonPrettyPrinter {

    override val spaces: Int = 0

    /**
      * ``match`` this is pattern matching. It is
      * best used in conjunction with sealed trait
      * hierarchies like [[Json]].
      *
      * More on pattern matching:
      *  - https://docs.scala-lang.org/tour/pattern-matching.html
      *
      * Although more verbose in scala than in other functional languages,
      * this is the way we define "Algebraic Data Types" — also referred to
      * as "Sum Types".
      *
      * More on the concept of algebraic data types here:
      *  - http://tpolecat.github.io/presentations/algebraic_types.html#1
      *  - http://learnyouahaskell.com/making-our-own-types-and-typeclasses
      *
      * ----
      * Now, details about the actual implementation of this thing.
      *
      * As you will notice, it is a recursive function.
      * We have one ``case`` for each variant of our [[Json]] datatype.
      * The simple cases of [[JsonString]], [[JsonNumber]], [[JsonBoolean]]
      * are not recursive, while the other two cases are.
      */
    def apply(json: Json): String = json match {
      case JsonBoolean(bool) =>
        bool.toString

      case JsonString(value) =>
        surroundWithQuotes(value)

      case JsonNumber(value) =>
        value.toString

      /**
        * As you can see we used a very simple way of transforming
        * the list `jss` into a list of strings.
        *
        * A simple one-liner implementation of this would be:
        * {{{
        *     jss.map(this.apply).mkString("[", ",", "]")
        * }}}
        *
        * The ``map`` function is one of the most used
        * functional combinators in any functional language.
        *
        * More about these here:
        *  - https://twitter.github.io/scala_school/collections.html
        *
        * The ``mkString`` function this simply concatenates the string
        * representations of all of the elements of the list using ',' as
        * separators.
        *
        * Note that this method is polymorphic in a very bad way
        * because it depends on Java's Object.toString method.
        * Luckily we are dealing with Strings, so it is acceptable
        * in this case. But in the general case this is quite ugly
        */
      case JsonArray(jss) =>
        val jsonsAsStrings: List[String] = jss.map(this.apply)
        val content = jsonsAsStrings.mkString(",")
        s"[$content]"


      /**
        * When we map [[Map]]s we map a 2-tuple of key value pair to a new value,
        * if the resulting type we map to is a 2-tuple as well, then the resulting
        * collection is still a [[Map]], otherwise it becomes a collection, like in
        * this case where we transform a pair into a single [[String]]
        */
      case JsonObject(properties) =>
        val strings: immutable.Iterable[String] = properties.map { p: (String, Json) =>
          val (key, value) = p
          val jsonString = this.apply(value)
          s"${surroundWithQuotes(key)}:$jsonString"
        }
        val content = strings.mkString(",")
        s"{$content}"
    }
  }


  /**
    * Here we made the constructor of the class private. And the instantiation
    * of this class can be done only in its companion object [[JsonPrettyPrinter]].
    *
    * And defined a public field named `spaces`; and overrode the superclass method at the same time.
    * More on that here:
    * - https://www.safaribooksonline.com/library/view/scala-cookbook/9781449340292/ch04s03.html
    *
    */
  final class SpacedJsonPrettyPrinter private[json](override val spaces: Int) extends JsonPrettyPrinter {
    /**
      * This will be executed when instantiating the object, and it will throw an
      * IllegalArgumentException. But because we do not expose this class outside
      * of the json package, this implementation ought to be fairly robust.
      */
    require(spaces > 1, "you should never instantiate a non-compact pretty printer with zero or less")

    override def apply(json: Json): String = {
      //FIXME: actually write a correct implementation for this pretty printer
      JsonPrettyPrinterImplementations.compact(json)
    }
  }

  /**
    * JsonStrings are always escaped by quotes, notice we used the
    * triple quote literal string, and inserted a 4th quote :)
    *
    * More on triple quotes here:
    *  - http://allaboutscala.com/tutorials/chapter-2-learning-basics-scala-programming/scala-escape-characters-create-multi-line-string/
    *
    */
  private def surroundWithQuotes(s: String): String = {
    s""""$s""""
  }

}

