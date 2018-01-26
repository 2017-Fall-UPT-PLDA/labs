package plda.monad

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 14 Dec 2017
  *
  */
trait Monad[+T] {
//  val f: Int => Option[Int] = ???
//  val g: Int => Option[Int] = ???

  // f(g(x))
//  val x = f compose g
  def flatMap[B](f: T => Monad[B]): Monad[B]
}

//sealed abstract class MyList[+A]

sealed abstract class MyOption[+A]

sealed abstract class MyTry[+T]

trait MyFuture[+T]
