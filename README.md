# PLDA - labs
-----
This repository contains the result of what we code during the lab with added references for further reading — and a bit beautified.  

The purpose of this code is to be explored, both in its current state, and historically. I strive to ensure that
the explanations found in the code are as concise as possible, while giving enough further reading for you to learn [Scala]().

## Scala

We will be using [Scala] in this class. Some quick (woefully incomplete) resources:
- [Tour of Scala](https://docs.scala-lang.org/tour/tour-of-scala.html)
- [Cheatsheet](https://docs.scala-lang.org/cheatsheets/)
- [Methods are not Functions](http://tpolecat.github.io/2014/06/09/methods-functions.html)
- Programming in Scala 2nd edition — as a reference book. Not freely available, but you can find it :)

Mine the code for more contextualized references :)

## Domain

We build our code-base centered around manipulating [JSON](http://www.json.org/). From representation, encoding, pretty-printing, decoding, and boiler-plate scrapping of all the before. This will help us understand pretty much all relevant concepts in the Scala language. While at the same time revealing concepts that are relevant to the building of language interpreters — both stand-alone, and embedded.

## Running and exploring the code

The most recommended environment comprises of the following:
* _the runtime_ — install the [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) — the runtime
* _the development environment_ — download [IntelliJ](https://www.jetbrains.com/idea/download/) — on first start you can download the scala plugin through the wizard. If that doesn't work, then go ahead and [install it later](https://plugins.jetbrains.com/plugin/1347-scala)
* _the build tool_ — download and/or install [SBT](https://www.scala-lang.org/documentation/getting-started-sbt-track/getting-started-with-scala-and-sbt-on-the-command-line.html)

#### Importing into IntelliJ

It is highly recommended that you use _IntelliJ_ because then the documents become navigable. Whenever you see a a type written like `[[TypeName]]` in a scaladoc comment, IntelliJ allows you to go to its definition, making the code read like a wiki :)

See [how to import SBT project into IntelliJ](https://www.jetbrains.com/help/idea/getting-started-with-sbt.html#import_project).

Since our project is already defined in _SBT_, we will be simply importing the project into _IntelliJ_. Never modify the project from the _IntelliJ_ gui settings. Everything can be done in the `build.sbt` file, and then reloaded into _IntelliJ_.

#### Using SBT

After installing SBT, you should go to the folder where you cloned this git repo, and [start the SBT repl](http://www.scala-sbt.org/0.13/docs/Running.html). From the repl you can `compile`, `run`, and `test` the application.

## Starting point

You can start navigating in the main class found in `src/main/scala/plda/json_examples/MainJson`. From IntelliJ it is trivially runnable, you'll figure it out :). See how to run the [application from SBT](https://alvinalexander.com/scala/sbt-how-specify-main-method-class-to-run-in-project).  

Then, you should also check out the unit tests found under `src/test/scala/...`. They can be run either from

## Contributing

There are plenty of things left to be done. Both in terms of organizing the documentation of the code, and suggesting ways of improvement. You can easily do so by using the [fork + pull request model](https://help.github.com/articles/creating-a-pull-request-from-a-fork/).

Suggested improvements:
- [ ] the code itself is quite concise, but can be rendered hard to read because of all the scaladoc. You could write a [scalafix](https://scalacenter.github.io/scalafix/) script to help remove only the scaladoc, and keep the rest of the code intact. This may or may not help your colleagues understand the code better. This topic falls under "meta-programming" and will be covered towards the end of the class. But that's no reason to scare the determined student!

- [ ] a better explanation of implicit resolution, conversion, and type-level programming is in order. We will be exploring this topic quite a lot in our implementation. But a centralized —down to earth— view on it would be welcome. Starting points to help synthesize an explanation:
  - ["The Dialectics of Type-Level Programming by Aaron Levin"](https://www.youtube.com/watch?v=0wxGrf8toWk)
  - [Implicit classes](https://docs.scala-lang.org/overviews/core/implicit-classes.html)
  - [Implicit Conversions](https://docs.scala-lang.org/tour/implicit-conversions.html)
  - [Implicit Parameter Precedence, again](http://eed3si9n.com/implicit-parameter-precedence-again)

- [ ] all reading materials are scattered throughout the code in scaladoc. And sometimes even duplicated. If anyone can come up with a scheme (that necessarily involves code) and renders our code-base more readable, and at the same time making it more obvious what concepts are involved in a language construct — while making it trivial to read up on these constructs, would be greatly appreciated. Essentially, build a code-like index.
