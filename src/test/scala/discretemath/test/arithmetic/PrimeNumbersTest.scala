package discretemath.test.arithmetic

import discretemath.arithmetic.PrimeNumbers
import org.scalatest.funsuite.AnyFunSuite

class PrimeNumbersTest extends AnyFunSuite {

  test("isPrime") {
    assertThrows[IllegalArgumentException] {
      PrimeNumbers.isPrime(-4)
    }

    assert(!PrimeNumbers.isPrime(0))
    assert(!PrimeNumbers.isPrime(1))
    assert(!PrimeNumbers.isPrime(2))
    assert(PrimeNumbers.isPrime(3))
    assert(!PrimeNumbers.isPrime(4))
    assert(PrimeNumbers.isPrime(5))
    assert(!PrimeNumbers.isPrime(6))
    assert(PrimeNumbers.isPrime(7))
    assert(!PrimeNumbers.isPrime(8))
    assert(!PrimeNumbers.isPrime(9))
    assert(!PrimeNumbers.isPrime(10))
    assert(PrimeNumbers.isPrime(11))
    assert(!PrimeNumbers.isPrime(12))
    assert(PrimeNumbers.isPrime(17))
    assert(!PrimeNumbers.isPrime(39))
    assert(!PrimeNumbers.isPrime(48))
    assert(PrimeNumbers.isPrime(7919))
    assert(PrimeNumbers.isPrime(87178291199L))
  }

  test("factorsOf") {
    assertThrows[IllegalArgumentException] {
      PrimeNumbers.factorsOf(-4)
    }

    assertThrows[IllegalArgumentException] {
      PrimeNumbers.factorsOf(0)
    }

    assertThrows[IllegalArgumentException] {
      PrimeNumbers.factorsOf(1)
    }


    assert(PrimeNumbers.factorsOf(2) == Seq(2))
    assert(PrimeNumbers.factorsOf(3) == Seq(3))
    assert(PrimeNumbers.factorsOf(4) == Seq(2, 2))
    assert(PrimeNumbers.factorsOf(5) == Seq(5))
    assert(PrimeNumbers.factorsOf(6) == Seq(2, 3))
    assert(PrimeNumbers.factorsOf(7) == Seq(7))
    assert(PrimeNumbers.factorsOf(8) == Seq(2, 2, 2))
    assert(PrimeNumbers.factorsOf(9) == Seq(3, 3))
    assert(PrimeNumbers.factorsOf(10) == Seq(2, 5))
    assert(PrimeNumbers.factorsOf(11) == Seq(11))
    assert(PrimeNumbers.factorsOf(12) == Seq(2, 2, 3))
    assert(PrimeNumbers.factorsOf(13) == Seq(13))
    assert(PrimeNumbers.factorsOf(14) == Seq(2, 7))
    assert(PrimeNumbers.factorsOf(15) == Seq(3, 5))
    assert(PrimeNumbers.factorsOf(16) == Seq(2, 2, 2, 2))
    assert(PrimeNumbers.factorsOf(17) == Seq(17))
    assert(PrimeNumbers.factorsOf(18) == Seq(2, 3, 3))
    assert(PrimeNumbers.factorsOf(19) == Seq(19))
    assert(PrimeNumbers.factorsOf(20) == Seq(2, 2, 5))
    assert(PrimeNumbers.factorsOf(39) == Seq(3, 13))
    assert(PrimeNumbers.factorsOf(48) == Seq(2, 2, 2, 2, 3))
    assert(PrimeNumbers.factorsOf(7919) == Seq(7919))


    assert(PrimeNumbers.factorsOf(432_423) == Seq(3, 3, 23, 2089))
    assert(PrimeNumbers.factorsOf(432_424) == Seq(2, 2, 2, 191, 283))
    assert(PrimeNumbers.factorsOf(54_376_543) == Seq(13, 223, 18757))
    assert(PrimeNumbers.factorsOf(921_318_273) == Seq(3, 3, 3, 67, 509297))
    assert(PrimeNumbers.factorsOf(402_938_777) == Seq(17, 379, 62539))
  }
}
