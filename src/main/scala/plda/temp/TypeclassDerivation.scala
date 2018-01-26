package plda.temp

import plda.json._
import plda.json.syntax._

import scala.language.implicitConversions
import scala.util.Random

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 24 Nov 2017
  *
  */
object TypeclassDerivation extends App with DefaultJsonEncoders {

  //  sealed trait HList
  //
  //  case class ::[+Head, +Tail <: HList](
  //    h: Head,
  //    t: Tail
  //  ) extends HList
  //
  //  sealed trait HNil extends HList
  //
  //  case object HNil extends HNil

  import shapeless._
  import shapeless.labelled._

  //=====================================

  final case class PLDACourse(
    number: Int,
    instructor: Instructor,
    topic: String
  )

  final case class Instructor(
    name: String
  )

  val plda: PLDACourse = PLDACourse(
    number = 9,
    instructor = Instructor("blas"),
    topic = "shapeless"
  )

  val pldaHlist: Int :: (String :: HNil) :: String :: HNil = ::(
    plda.number,
    ::(
      ::(plda.instructor.name, HNil),
      ::(
        plda.topic,
        HNil
      )
    )
  )

  implicit val endEncoder: JsonObjectEncoder[HNil] =
    JsonObjectEncoder { _: HNil => JsonObject.empty }

  //  implicit def hlistEncoder[Head, Tail <: HList](
  //    implicit
  //    headEncoder: JsonEncoder[Head],
  //    tailEncoder: JsonObjectEncoder[Tail]
  //  ): JsonObjectEncoder[Head :: Tail] = {
  //
  //    JsonObjectEncoder[Head :: Tail] { (hlist: Head :: Tail) =>
  //      val headJson = headEncoder.encode(hlist.head)
  //      val tailJson = tailEncoder.encode(hlist.tail)
  //      tailJson.+(s"field-${Random.nextInt()}" -> headJson)
  //    }
  //  }

  implicit def hlistEncoder[K <: Symbol, Head, Tail <: HList](
    implicit
    fieldName: Witness.Aux[K],
    headEncoder: JsonEncoder[Head],
    tailEncoder: JsonObjectEncoder[Tail]
  ): JsonObjectEncoder[FieldType[K, Head] :: Tail] = {

    JsonObjectEncoder[FieldType[K, Head] :: Tail] { (hlist: FieldType[K, Head] :: Tail) =>
      val headJson = headEncoder.encode(hlist.head)
      val tailJson = tailEncoder.encode(hlist.tail)
      tailJson.+(fieldName.value.name -> headJson)
    }
  }

  implicit def shapelessRepresentationOfTToJson[ObjectType, ShapelessRepr](
    implicit
    repr: LabelledGeneric.Aux[ObjectType, ShapelessRepr],
    jsonEnc: Lazy[JsonObjectEncoder[ShapelessRepr]]
  ): JsonObjectEncoder[ObjectType] = {
    JsonObjectEncoder[ObjectType] { o =>
      jsonEnc.value.encode(repr.to(o))
    }
  }


  println {
    plda.toJson.noSpaces
  }

  //  println {
  //    pldaHlist.toJson.noSpaces
  //  }

  /**
    * Expr[plda.json.Json](syntax.JsonEncoderOperations(TypeclassDerivation.this.pldaHlist).toJson(
    *   TypeclassDerivation.this.hlistEncoder(
    *     TypeclassDerivation.this.intJsonEncoder,
    *     TypeclassDerivation.this.hlistEncoder(
    *       TypeclassDerivation.this.hlistEncoder(
    *         TypeclassDerivation.this.stringJsonEncoder,
    *         TypeclassDerivation.this.endEncoder),
    *       TypeclassDerivation.this.hlistEncoder(
    *       TypeclassDerivation.this.stringJsonEncoder,
    *       TypeclassDerivation.this.endEncoder)))))
    */

  //  import scala.reflect.runtime.universe._

  //  println {
  //    reify(
  //      pldaHlist.toJson
  //    )
  //  }


}
