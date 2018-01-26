package plda.monad

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 14 Dec 2017
  *
  */
object MonadApp extends App {
  val o1 = Option(1)
  val o2 = Option(2)

  val x: Option[Int] = o1.flatMap { one =>
    o2.map { two =>
      two + 1
    }
  }

  for {
    one <- o1
    two <- o2
  } yield one + two

}
