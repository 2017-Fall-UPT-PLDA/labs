package plda

final case class PLDA(
  courses: List[PLDACourse],
  labs: List[PLDALab]
)

/**
  * As you can see, I have no idea what I'm doing,
  * and like a good programmer I reflect that in my types
  *
  * More on [[Option]]:
  * - http://danielwestheide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html
  *
  */
final case class PLDALab(
  number: Int,
  instructor: Instructor,
  possibleTopic: Option[String],
  actualTopic: Option[String]
)

final case class PLDACourse(
  number: Int,
  instructor: Instructor,
  topic: String
)

final case class Instructor(
  name: String
)

object PLDASamples {

  trait WeeklySample {
    def course: PLDACourse

    def lab: PLDALab

    def number: Int
  }

  private lazy val `Lorand Szakacs` = Instructor("Lorand Szakacs")
  private lazy val `Marius Minea` = Instructor("Marius Minea")

  object week1 extends WeeklySample {
    override lazy val number: Int = 1

    override lazy val course: PLDACourse = PLDACourse(
      number = number,
      instructor = `Marius Minea`,
      topic = "Introduction to PLDA + Functional Programming"
    )

    override lazy val lab: PLDALab = PLDALab(
      number = number,
      instructor = `Lorand Szakacs`,
      possibleTopic = None,
      actualTopic = None
    )
  }

  object week2 extends WeeklySample {
    override lazy val number: Int = 2

    override lazy val course: PLDACourse = PLDACourse(
      number = number,
      instructor = `Marius Minea`,
      topic = "Lambda Calculus"
    )

    override lazy val lab: PLDALab = PLDALab(
      number = number,
      instructor = `Lorand Szakacs`,
      possibleTopic = Option("Intro to Functional Programming"),
      actualTopic = Option("Intro to Functional Programming")
    )
  }

  object week3 extends WeeklySample {
    override lazy val number: Int = 3

    override lazy val course: PLDACourse = PLDACourse(
      number = number,
      instructor = `Marius Minea`,
      topic = "Types"
    )

    override lazy val lab: PLDALab = PLDALab(
      number = number,
      instructor = `Lorand Szakacs`,
      possibleTopic = Option("Intro to types"),
      actualTopic = Option("Types + Scala Syntax Magic")
    )
  }

  object week4 extends WeeklySample {
    override lazy val number: Int = 4

    override lazy val course: PLDACourse = PLDACourse(
      number = number,
      instructor = `Marius Minea`,
      topic = "Continuations"
    )

    override lazy val lab: PLDALab = PLDALab(
      number = number,
      instructor = `Lorand Szakacs`,
      possibleTopic = Option("looking at the JSON Pretty Printer -> Building an interpreter"),
      actualTopic = None
    )
  }

  lazy val classSoFar: PLDA = PLDA(
    courses = List(
      week1.course,
      week2.course,
      week3.course,
      week4.course
    ),
    labs = List(
      week1.lab,
      week2.lab,
      week3.lab,
      week4.lab
    )
  )
}
