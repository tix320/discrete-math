package extension

import discretemath.common.{Adder, Multiplier}
import discretemath.utils.Characters.EXPONENT_CHARACTERS

import scala.collection.mutable.ArrayBuffer

object Extensions {

  implicit class ArrayImprovements(array: Array[Int]) {
    def allMax: collection.IndexedSeq[Int] = {
      val indexes = new ArrayBuffer[Int]
      var max = array(0)
      indexes.addOne(0)
      for (i <- 1 until array.length) {
        if (array(i) == max) indexes.addOne(i)
        else if (array(i) > max) {
          indexes.clear()
          indexes.addOne(i)
          max = array(i)
        }
      }
      indexes
    }
  }

  implicit class IntImprovements(exponent: Int) {

    def toExponentString: String = {
      var number = exponent

      val digitsCount = if (exponent == 0) 1 else math.log10(number).toInt + 1

      val array = new Array[Char](digitsCount)

      var index = array.length - 1
      while (number > 0) {
        val digit = number % 10
        number = number / 10

        val exponentChar = EXPONENT_CHARACTERS(digit)

        array(index) = exponentChar
        index -= 1
      }

      return new String(array)
    }
  }

  implicit object IntAdder extends Adder[Int] {

    override def add(item1: Int, item2: Int): Int = item1 + item2
  }

  implicit object IntMultiplier extends Multiplier[Int] {

    override def multiply(item1: Int, item2: Int): Int = item1 * item2
  }

}