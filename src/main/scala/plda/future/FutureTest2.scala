package plda.future

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 07 Dec 2017
  *
  */
object FutureTest2 extends App {

  import scala.concurrent._
  import scala.concurrent.duration._

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  var x: String = "initial value"

  val finalValue = for {
    v1 <- Future {
      Thread.sleep(scala.util.Random.nextInt(100))
      x = "F1 IS THE LAST"
      println(s"${Util.pid()} — f1 — 42")
      42
    }
    v2 <- Future {
      Thread.sleep(scala.util.Random.nextInt(100))
      x = "F2 IS THE LAST"
      println(s"${Util.pid()} — f2 — 11")
      11
    }
  } yield v1 + v2

  println {
    Await.result(finalValue, 1 second)
  }

  println {
    x
  }

}

object Util {
  def pid(): Long = Thread.currentThread().getId
}
