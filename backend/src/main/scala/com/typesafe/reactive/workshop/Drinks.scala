package com.typesafe.reactive.workshop

import scala.util.Random

sealed trait Drink

case class Akkarita(extraShot: Boolean = false) extends Drink

case class MaiPlay() extends Drink

case class PinaScalada() extends Drink


object Drinks {

  val drinks: Set[Drink] =
    Set(Akkarita(), MaiPlay(), PinaScalada())

  def order(code: String): Drink =
    code.toLowerCase match {
      case "a" => Akkarita()
      case "m" => MaiPlay()
      case "p" => PinaScalada()
      case _   => throw new IllegalArgumentException("""Unknown drink code "$code"!""")
    }

  def anyOther(drink: Drink): Drink = {
    val others = drinks - drink
    others.toVector(Random.nextInt(others.size))
  }
}
