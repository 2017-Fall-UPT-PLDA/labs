package plda.json_examples

import plda._

object PLDATypesJsonEncoders extends PLDATypesJsonEncoders

/**
  * This is an example of how you can use what we created in [[plda.json]].
  *
  * In time, we will be reducing all this code to nothing, quite literally —
  * with the added benefit that you won't be able to  accidentally give a
  * wrong name to a JSON property field.
  */
trait PLDATypesJsonEncoders {

  /**
    * alternatively, we could have extended the traits:
    * [[plda.json.JsonSyntax]], and [[plda.json.DefaultJsonEncoders]]
    * respectively
    */

  import plda.json._
  import plda.json.syntax._
  import plda.json.encoders._

  implicit val pldaInstructorJsonEncoder: JsonEncoder[Instructor] = JsonEncoder { i => i.name.toJson }

  implicit val pldaCourseJsonEncoder: JsonEncoder[PLDACourse] = JsonEncoder { p =>
    JsonValue(
      "number" -> p.number.toJson,
      "instructor" -> p.instructor.toJson,
      "topic" -> p.topic.toJson,
    )
  }

  implicit val pldaLabJsonEncoder: JsonEncoder[PLDALab] = JsonEncoder { p =>
    JsonValue(
      "number" -> p.number.toJson,
      "instructor" -> p.instructor.toJson,
      "possibleTopic" -> p.possibleTopic.toJson,
      "actualTopic" -> p.actualTopic.toJson
    )
  }

  /**
    * As you can see, we have eased our burden of defining
    * json format as compared with what you see the explicit
    * methods used in the tests — and at little to no runtime
    * cost.
    * The bit of code
    * {{{
    *   p.courses.toJson
    * }}}
    * uses the implicit [[pldaCourseJsonEncoder]] which in turn
    * uses [[pldaInstructorJsonEncoder]] which in turn [[stringJsonEncoder]]
    */
  implicit val pldaJsonEncoder: JsonEncoder[PLDA] = JsonEncoder { p =>
    JsonValue(
      "courses" -> p.courses.toJson,
      "labs" -> p.labs.toJson
    )
  }

}
