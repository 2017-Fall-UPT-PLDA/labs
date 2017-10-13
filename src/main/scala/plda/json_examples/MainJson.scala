package plda.json_examples

import plda.PLDASamples

/**
  * extending [[App]] helps us get rid of the  burden of having
  * to explicitly define a main method:
  * {{{
  *   def main(args: Array[String]): Unit
  * }}}
  *
  * More on the main method and App:
  * - https://alvinalexander.com/scala/how-to-launch-scala-application-with-object-main-method-trait
  */
object MainJson extends App with PLDATypesJsonEncoders {

  import plda.json.syntax._

  val soFar = PLDASamples.classSoFar

  /**
    * Two successive additions
    *
    * ``toJson: [[plda.json.Json]]`` can be called on any object of type T that
    * has an implicit type-class [[plda.json.JsonEncoder]] parameterized on T.
    *
    * ``noSpaces: [[String]]`` can be called on any object of type [[plda.json.Json]]
    */
  println("-------")
  println(soFar.toJson.noSpaces)
  println("-------")
  println(soFar.toJson.twoSpaces) //FIXME: unfortunately this is not implemented properly yet

}
