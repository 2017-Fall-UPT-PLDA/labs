package plda.derivation

import plda.json._


/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 15 Nov 2017
  *
  */
object ActualJsonDerivation {

  sealed trait VariousTypes extends Product with Serializable

  final case class Types[+H, +T <: VariousTypes](head: H, tail: T) extends VariousTypes

  sealed trait End extends VariousTypes

  case object End extends End

  val exampleOfSingletonTypes: Types[String, Types[Int, Types[Boolean, End]]] =
    Types(
      "12344",
      Types(
        42,
        Types(
          true,
          End
        )
      )
    )

  trait General[T] extends Serializable {
    type Shape

    def to(t: T): Shape

    def from(r: Shape): T
  }

  object General {
    type For[Type, ShapeOfType] = General[Type] {type Shape = ShapeOfType}

    def apply[T](implicit gen: General[T]): For[T, gen.Shape] = gen
  }

  implicit val endJsonEncoder: JsonObjectEncoder[End] =
    JsonObjectEncoder.apply(_ => JsonObject.empty)

  implicit def variousTypesEncoder[H, T <: VariousTypes](
    hEncoder: JsonEncoder[H],
    restEncoder: JsonObjectEncoder[T]
  ): JsonEncoder[Types[H, T]] = ???

  //  implicit def generalEncoder[Type, Shape](
  //    implicit
  //    gen: General.For[Type, Shape],
  //    enc: Lazy[JsonEncoder[Shape]]
  //  ): JsonEncoder[Type] = ???

  import shapeless._
  import shapeless.labelled._

  implicit val hnilJsonEncoder: JsonObjectEncoder[HNil] =
    JsonObjectEncoder.apply(_ => JsonObject.empty)

  //FIXME: no field names
  //  implicit def hlistJsonEncoder[H, T <: HList](
  //    implicit
  //    hEncoder: JsonEncoder[H],
  //    tEncoder: JsonObjectEncoder[T]
  //  ): JsonObjectEncoder[H :: T] = {
  //    JsonObjectEncoder { hlist =>
  //      val fieldName: String = s"?${scala.util.Random.nextInt(100)}"
  //      val head = hEncoder.encode(hlist.head)
  //      val tail = tEncoder.encode(hlist.tail)
  //      tail + (fieldName -> head)
  //    }
  //  }

  //FIXME — — almost final version, no Lazy
  //  implicit def hlistJsonEncoder[K <: Symbol, H, T <: HList](
  //    implicit
  //    fieldNameSingleType: Witness.Aux[K],
  //    hEncoder: JsonEncoder[H],
  //    tEncoder: JsonObjectEncoder[T]
  //  ): JsonObjectEncoder[FieldType[K, H] :: T] = {
  //    JsonObjectEncoder { hlist =>
  //      val fieldName: String = fieldNameSingleType.value.name
  //      val head = hEncoder.encode(hlist.head)
  //      val tail = tEncoder.encode(hlist.tail)
  //      tail + (fieldName -> head)
  //    }
  //  }

  //FIXME —  — final version with Lazy
  implicit def hlistJsonEncoder[K <: Symbol, H, T <: HList](
    implicit
    fieldNameSingleType: Witness.Aux[K],
    hEncoder: Lazy[JsonEncoder[H]],
    tEncoder: JsonObjectEncoder[T]
  ): JsonObjectEncoder[FieldType[K, H] :: T] = {
    JsonObjectEncoder { hlist =>
      val fieldName: String = fieldNameSingleType.value.name
      val head = hEncoder.value.encode(hlist.head)
      val tail = tEncoder.encode(hlist.tail)
      tail + (fieldName -> head)
    }
  }

  implicit def genericEncoder[A, R](
    implicit
    gen: Generic.Aux[A, R],
    enc: Lazy[JsonEncoder[R]]
  ): JsonEncoder[A] = JsonEncoder { a => enc.value.encode(gen.to(a)) }

  //FIXME — — final version, w/ field names
  implicit def labelledGenericEncoder[A, R](
    implicit
    gen: LabelledGeneric.Aux[A, R],
    enc: Lazy[JsonEncoder[R]]
  ): JsonEncoder[A] = JsonEncoder { a => enc.value.encode(gen.to(a)) }


}
