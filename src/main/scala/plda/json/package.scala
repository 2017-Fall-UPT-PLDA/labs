package plda

/**
  * This is a package object. All definitions we put here are available inside
  * this entire package, and, any imports:
  * {{{
  *   import plda.json._
  * }}}
  *
  * Will import the public definitions in here as well.
  *
  * More information about package objects:
  * - http://www.scala-lang.org/docu/files/packageobjects/packageobjects.html
  */
package object json {
  /**
    * This is a type alias. It is mostly used for syntactic purposes, or
    * giving more semantically meaningful names to the same types.
    *
    * e.g:
    * {{{
    *   object Credentials {
    *     type PlainTextPassword = String
    *     type Username = String
    *   }
    *   //....
    *
    *   def hashPassword(plainText: PlainTextPassword, salt: Array[Byte]) //...
    * }}}
    *
    * They can also be left abstract, more info on that here:
    * - https://typelevel.org/blog/2015/07/13/type-members-parameters.html
    *
    */
  type Json = JsonValue

}
