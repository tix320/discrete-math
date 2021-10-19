package discretemath.arithmetic

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.{break, breakable}

object PrimeNumbers {

  def isPrime(number: Long): Boolean = {
    if (number < 0) throw new IllegalArgumentException

    if (number == 0 || number == 1 || number % 2 == 0) {
      return false
    }

    val sqRoot = math.sqrt(number).toLong

    for (i <- 3L to sqRoot by 2L) {
      if (number % i == 0) {
        return false
      }
    }

    return true
  }

  def factorsOf(number: Long): collection.Seq[Long] = {
    if (number <= 1) throw new IllegalArgumentException

    val factors = new ArrayBuffer[Long]()

    var n = number

    while ((n & 1) == 0) { // is even
      factors.addOne(2)
      n = n / 2
    }

    breakable {
      for (factor <- 3L to (number / 3).floor.toLong by 2L) {
        while (n % factor == 0) {
          factors.addOne(factor)
          n = n / factor
        }

        if (n == 1) {
          break
        }
      }

      if (n != 1) {
        factors.addOne(n)
      }
    }

    return factors
  }
}
