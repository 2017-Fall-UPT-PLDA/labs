package plda.derivation

import plda.json.DefaultJsonEncoders
import shapeless.{::, Generic, HList, HNil, LabelledGeneric, Lazy, Witness}
import shapeless.labelled.FieldType

import scala.util.Random

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 16 Nov 2017
  *
  */
object ReinventingTheWheel extends DefaultJsonEncoders with App {

  import plda.json._
  import plda.json.syntax._

  //  sealed trait HList

  /**
    * Error:(14, 16) type mismatch;
    * found   : String
    * required: Int
    * val x: Int = s
    */

  /**
    * Error:(20, 30) type mismatch;
    * found   : String("42")
    * required: H
    * val hl = Values(42, Values("42", Values(1, End)))
    *
    */
  //  case class Values[+H, +T <: HList](
  //    head: H,
  //    tail: T
  //  ) extends HList
  //
  //  trait End extends HList
  //
  //  case object End extends End


  //  implicit val endEncoder: JsonEncoder[End] = JsonEncoder(_ => JsonObject.empty)

  //  implicit def summonListEncoder[T](implicit enc: JsonEncoder[T]): JsonEncoder[List[T]] = {
  //    JsonEncoder { list: List[T] =>
  //      JsonArray(list map enc.encode)
  //    }
  //  }

  /**
    * implicit def variousTypesEncoder[H, T <: VariousTypes](
    * hEncoder: JsonEncoder[H],
    * restEncoder: JsonObjectEncoder[T]
    * ): JsonEncoder[Types[H, T]] = ???
    *
    */

  //    implicit def valuesEncoder[H, T <: HList](implicit
  //      firstElemEncoder: JsonEncoder[H],
  //      valuesEncoder: JsonEncoder[T]
  //    ): JsonEncoder[Values[H, T]] = ???

  /**
    * Error:(20, 30) type mismatch;
    * found   : String("42")
    * required: H
    * val hl = Values(42, Values("42", Values(1, End)))
    *
    */

  //  (List[(Int, Int)]((1, 2), (1,2))).toJson
  //  import scala.reflect.runtime.universe._

  //  val h1: Values[Int, Values[String, Values[Int, End]]] =
  //    Values(42, Values("42", Values(1, End)))
  /**
    * {{{
    *
    *
    * Expr[plda.json.Json](
    * syntax.JsonEncoderOperations(ReinventingTheWheel.this.h1).toJson(
    * ReinventingTheWheel.this.valuesEncoder(
    *   ReinventingTheWheel.this.intJsonEncoder,
    *   ReinventingTheWheel.this.valuesEncoder(
    *     ReinventingTheWheel.this.stringJsonEncoder,
    *     ReinventingTheWheel.this.valuesEncoder(
    *       ReinventingTheWheel.this.intJsonEncoder,
    *       ReinventingTheWheel.this.endEncoder)))))
    * }}}
    */

  //  println {
  //    reify(h1.toJson)
  //  }

  type Value = Nothing // Int | Function
  type Expr = Nothing // BinaryOperation | Lambda

  def interpret(expr: Expr, context: Map[String, Value]): Int = {
    val x = 1 + 3
    val y = 1 + 42
    x + y
    // case Let("x", valueOfX, body) =>
    // interpret(body, context + (newVarName -> interpret(valueOfX, context))
  }


  //  implicit def valuesEncoder[H, T <: HList](implicit
  //    firstElemEncoder: JsonEncoder[H],
  //    valuesEncoder: JsonEncoder[T]
  //  ): JsonEncoder[Values[H, T]] = ???

  //  Int :: Int :: HNil

  implicit lazy val hnil: JsonObjectEncoder[HNil] =
    JsonObjectEncoder { _ => JsonObject.empty }


  implicit def hlistJsonEncoder[K <: Symbol, H, T <: HList](
    implicit
    fieldNameSingleType: Witness.Aux[K],
    hEncoder: JsonEncoder[H],
    tEncoder: JsonObjectEncoder[T]
  ): JsonObjectEncoder[FieldType[K, H] :: T] = {
    JsonObjectEncoder { hlist =>
      val fieldName: String = fieldNameSingleType.value.name
      val head = hEncoder.encode(hlist.head)
      val tail = tEncoder.encode(hlist.tail)
      tail + (fieldName -> head)
    }
  }

  //FIXME — — final version, w/ field names
  implicit def labelledGenericEncoder[A, R](
    implicit
    gen: LabelledGeneric.Aux[A, R],
    enc: Lazy[JsonEncoder[R]]
  ): JsonEncoder[A] = JsonEncoder { a => enc.value.encode(gen.to(a)) }


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
  //  }

//  implicit def genericEncoder[Type, ShapelessRepr](implicit
//    generic: Generic.Aux[Type, ShapelessRepr],
//    enc: Lazy[JsonEncoder[ShapelessRepr]]
//  ): JsonEncoder[Type] = ???

  //  implicit def genericEncoder[A, R](
  //    implicit
  //    gen: Generic.Aux[A, R],
  //    enc: Lazy[JsonEncoder[R]]
  //  ): JsonEncoder[A] =
  //    JsonEncoder { a => enc.value.encode(gen.to(a)) }


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

  val f: (Int => Int) = { x => x + x }
  f(42)


}
