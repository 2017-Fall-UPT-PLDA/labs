package plda.json

import org.scalatest.FunSpec

/**
  *
  * In a great feat of meta-programming, Scala allows us to also write tests
  * about whether or not our code compiles! This is very useful when you
  * want to build a domain specific language that disallows writing
  * certain things.
  *
  * More on the various scalatest DSL-s here:
  * - http://www.scalatest.org/user_guide/selecting_a_style
  *
  * And on assertions:
  * - http://www.scalatest.org/user_guide/using_assertions
  *
  */
class JsonSyntaxTest extends FunSpec {

  /**
    * This is a type that is only relevant to this test. We don't want
    * it leaking outside of this class
    *
    * More on access qualifiers in general:
    * - http://www.jesperdj.com/2016/01/08/scala-access-modifiers-and-qualifiers-in-detail/
    */
  private case class PLDALab(
    labNumber: Int,
    topic: String
  )

  describe("encoder syntax") {

    describe("with implicit Encoder in scope") {

      implicit val PLDALabEncoder: JsonEncoder[PLDALab] = new JsonEncoder[PLDALab] {
        override def encode(t: PLDALab): Json = JsonObject(
          Map(
            "labNumber" -> JsonNumber(t.labNumber),
            "topic" -> JsonString("topic")
          )
        )
      }

      it("should compile by finding the encoder in scope") {
        assertCompiles(
          """
            |import plda.json.syntax._
            |
            |val p = PLDALab(3, "scala syntax magic")
            |
            |val j: Json = p.toJson
            |
        """.stripMargin
        )
      }

      it("should fail to compile if we don't import syntax") {
        assertDoesNotCompile(
          """
            |
            |val p = PLDALab(3, "scala syntax magic")
            |
            |val j: Json = p.toJson
            |
        """.stripMargin
        )
      }
    }

    describe("without implicit Encoder in scope") {
      it("should fail to compile") {
        assertDoesNotCompile(
          """
            |import plda.json.syntax._
            |
            |val p = PLDALab(3, "scala syntax magic")
            |
            |val j: Json = p.toJson
            |
        """.stripMargin
        )
      }
    }
  }

  describe("pretty printing syntax") {
    it("add noSpacesMethod") {
      assertCompiles(
        """
          |import plda.json.syntax._
          |
          |val j: Json = ???
          |
          |j.noSpaces
          |
        """.stripMargin
      )
    }

    it("add twoSpacesMethod") {
      assertCompiles(
        """
          |import plda.json.syntax._
          |
          |val j: Json = ???
          |
          |j.twoSpaces
          |
        """.stripMargin
      )
    }
  }

  describe("encoder + pretty printing syntax") {
    implicit val PLDALabEncoder: JsonEncoder[PLDALab] = new JsonEncoder[PLDALab] {
      override def encode(t: PLDALab): Json = JsonObject(
        Map(
          "labNumber" -> JsonNumber(t.labNumber),
          "topic" -> JsonString("topic")
        )
      )
    }
    it("should allow transforming to json then prettyPrinting") {
      assertCompiles(
        """
          |import plda.json.syntax._
          |
          |val p = PLDALab(3, "scala syntax magic")
          |
          |val result: String = p.toJson.noSpaces
          |
        """.stripMargin
      )
    }
  }
}
