package plda.derivation

import plda._
import plda.json._
import plda.json.encoders.stringJsonEncoder
import plda.json.syntax._
import shapeless._


/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 15 Nov 2017
  *
  */
object JsonDerivationExample extends App with DefaultJsonEncoders {

  case class House(
    name: String,
    location: GeoLocation
  )

  case class School(
    name: String,
    location: GeoLocation
  )

  val p: PLDA = PLDASamples.classSoFar
  val location = GeoLocation(
    45, 45
  )
  val sc = School(
    name = "UPT",
    location
  )

  import scala.reflect.runtime.universe._



  //  implicit def hlistJsonEncoder[Head, Tail <: HList](
  //    implicit
  //    hEncoder: JsonEncoder[Head],
  //    tEncoder: JsonObjectEncoder[Tail]
  //  ): JsonObjectEncoder[Head :: Tail] = {
  //    JsonObjectEncoder { hlist =>
  //      val fieldName: String = s"?${Random.nextInt(100)}"
  //      val head = hEncoder.encode(hlist.head)
  //      val tail = tEncoder.encode(hlist.tail)
  //      tail + (fieldName -> head)
  //    }
  /**
    * Expr[plda.json.Json](syntax.JsonEncoderOperations(JsonDerivationExample.this.cheating).toJson(
    * ReinventingTheWheel.hlistJsonEncoder(
    *   JsonDerivationExample.this.intJsonEncoder,
    *   ReinventingTheWheel.hlistJsonEncoder(
    *     JsonDerivationExample.this.intJsonEncoder,
    *     ReinventingTheWheel.hnil))))
    *
    *
    */

  case class GeoLocation(
    lat: Int,
    lng: Int,
  )

  val x = 42

  /**
    * Error:(69, 7) type mismatch;
    * found   : String("aaaa")
    * required: Int
    * x = "aaaa"
    */

  import shapeless.labelled._

//    val cheating = 42 :: 42 :: HNil

  val cheating = GeoLocation(42, 42)

  import ReinventingTheWheel._

  println {
    reify(
      PLDASamples.classSoFar.toJson.twoSpaces
    )
  }

  println {
    PLDASamples.classSoFar.toJson.twoSpaces
  }

}
