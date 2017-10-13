package plda.json

import org.scalatest.FunSpec

/**
  * This is a DSL (domain specific language) for writing tests that simply
  * leverages scala's extensible syntax. It has no additional compiler support
  * other than what is provided by the standard language.
  *
  * More on the various scalatest DSL-s here:
  * - http://www.scalatest.org/user_guide/selecting_a_style
  *
  * And on assertions:
  * - http://www.scalatest.org/user_guide/using_assertions
  */
class JsonPrettyPrintingTest extends FunSpec {

  describe("No spaces pretty printing") {
    val noSpaces: JsonPrettyPrinter = JsonPrettyPrinter(0)

    describe("behavior of flat object stringification") {
      it("simple flat object") {
        val expected = """{"answer_to_life_universe_and_everything_else":42,"question":"What do you get if you multiply six by nine?"}"""
        val obj = JsonObject(
          Map(
            "answer_to_life_universe_and_everything_else" -> JsonNumber(42),
            "question" -> JsonString("What do you get if you multiply six by nine?")
          )
        )
        val result = noSpaces(obj)
        println(result)
        assert(expected == result)
      }

      it("flat object with an array") {
        val expected = """{"otheranswers":[54,33],"answer_to_life_universe_and_everything_else":42,"question":"What do you get if you multiply six by nine?"}"""
        val obj = JsonObject(
          Map(
            "otheranswers" -> JsonArray(List(JsonNumber(54), JsonNumber(33))),
            "answer_to_life_universe_and_everything_else" -> JsonNumber(42),
            "question" -> JsonString("What do you get if you multiply six by nine?"),
          )
        )
        val result = noSpaces(obj)
        println(result)
        assert(expected == result)
      }
    }

    describe("nested objects") {
      it("stringify nested object") {
        val expected = """{"question":{"part1":"The answer to life?","part2":"The Universe?","part3":"And Everything else?"},"answer":42}"""
        val obj = JsonObject(
          Map[String, Json](
            "question" -> JsonObject {
              Map(
                "part1" -> JsonString("The answer to life?"),
                "part2" -> JsonString("The Universe?"),
                "part3" -> JsonString("And Everything else?"),
              )
            },
            "answer" -> JsonNumber(42),
          )
        )
        val result = noSpaces(obj)
        println(result)
        assert(expected == result)
      }
    }


  }


}
