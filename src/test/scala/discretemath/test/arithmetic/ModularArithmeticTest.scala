package discretemath.test.arithmetic

import discretemath.arithmetic.ModularArithmetic
import discretemath.arithmetic.exception.NotRelativelyPrimeException
import discretemath.arithmetic.modular.congruence.LinearCongruence
import org.scalatest.funsuite.AnyFunSuite

class ModularArithmeticTest extends AnyFunSuite {

  test("exponentModuloSpecialCases") {
    assertThrows[IllegalArgumentException] {
      ModularArithmetic.exponentModulo(3, 2, -1)
    }

    assert(ModularArithmetic.exponentModulo(4, 8, 1) == 0)

    assert(ModularArithmetic.exponentModulo(4, 0, 3) == 1)

    assertThrows[UnsupportedOperationException] {
      ModularArithmetic.exponentModulo(11, -4, 3)
    }
  }

  test("exponentModulo") {
    assert(ModularArithmetic.exponentModulo(11, 13, 19) == 11)
    assert(ModularArithmetic.exponentModulo(7, 4, 3) == 1)
    assert(ModularArithmetic.exponentModulo(8, 1, 6) == 2)
    assert(ModularArithmetic.exponentModulo(5, 5, 5) == 0)
    assert(ModularArithmetic.exponentModulo(1, 2, 2) == 1)
    assert(ModularArithmetic.exponentModulo(2, 2, 2) == 0)
    assert(ModularArithmetic.exponentModulo(32, 13, 67) == 34)
    assert(ModularArithmetic.exponentModulo(78, 123, 50) == 2)

  }

  test("gcd") {
    assertThrows[AssertionError] {
      ModularArithmetic.gcd(-1, 2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.gcd(4, -2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.gcd(0, 5)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.gcd(6, 0)
    }

    assert(ModularArithmetic.gcd(1, 1) == 1)
    assert(ModularArithmetic.gcd(2, 2) == 2)
    assert(ModularArithmetic.gcd(5, 5) == 5)
    assert(ModularArithmetic.gcd(1, 4) == 1)
    assert(ModularArithmetic.gcd(8, 1) == 1)


    assert(ModularArithmetic.gcd(4, 7) == 1)
    assert(ModularArithmetic.gcd(4, 8) == 4)
    assert(ModularArithmetic.gcd(12, 14) == 2)
    assert(ModularArithmetic.gcd(15, 18) == 3)
    assert(ModularArithmetic.gcd(19, 24) == 1)
    assert(ModularArithmetic.gcd(52, 31) == 1)
    assert(ModularArithmetic.gcd(52, 32) == 4)

  }

  test("lcm") {
    assertThrows[AssertionError] {
      ModularArithmetic.lcm(-1, 2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.lcm(4, -2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.lcm(0, 5)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.lcm(6, 0)
    }

    assert(ModularArithmetic.lcm(1, 1) == 1)
    assert(ModularArithmetic.lcm(2, 2) == 2)
    assert(ModularArithmetic.lcm(5, 5) == 5)
    assert(ModularArithmetic.lcm(1, 4) == 4)
    assert(ModularArithmetic.lcm(8, 1) == 8)


    assert(ModularArithmetic.lcm(4, 7) == 28)
    assert(ModularArithmetic.lcm(4, 8) == 8)
    assert(ModularArithmetic.lcm(12, 14) == 84)
    assert(ModularArithmetic.lcm(12, 18) == 36)
    assert(ModularArithmetic.lcm(15, 18) == 90)
    assert(ModularArithmetic.lcm(19, 24) == 456)
    assert(ModularArithmetic.lcm(50, 30) == 150)
    assert(ModularArithmetic.lcm(52, 32) == 416)

  }

  test("bezoutCoefficients") {
    assertThrows[AssertionError] {
      ModularArithmetic.bezoutCoefficients(-1, 2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.bezoutCoefficients(4, -2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.bezoutCoefficients(0, 5)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.bezoutCoefficients(6, 0)
    }

    assert(ModularArithmetic.bezoutCoefficients(1, 1) == (1, 0, 1))
    assert(ModularArithmetic.bezoutCoefficients(2, 2) == (2, 0, 1))
    assert(ModularArithmetic.bezoutCoefficients(5, 5) == (5, 0, 1))
    assert(ModularArithmetic.bezoutCoefficients(1, 4) == (1, 1, 0))
    assert(ModularArithmetic.bezoutCoefficients(8, 1) == (1, 0, 1))

    assert(ModularArithmetic.bezoutCoefficients(4, 7) == (1, 2, -1))
    assert(ModularArithmetic.bezoutCoefficients(4, 8) == (4, 1, 0))
    assert(ModularArithmetic.bezoutCoefficients(12, 14) == (2, -1, 1))
    assert(ModularArithmetic.bezoutCoefficients(15, 18) == (3, -1, 1))
    assert(ModularArithmetic.bezoutCoefficients(19, 24) == (1, -5, 4))
    assert(ModularArithmetic.bezoutCoefficients(52, 31) == (1, 3, -5))
    assert(ModularArithmetic.bezoutCoefficients(32, 52) == (4, 5, -3))

    assert(ModularArithmetic.bezoutCoefficients(240, 46) == (2, -9, 47))
    assert(ModularArithmetic.bezoutCoefficients(46, 240) == (2, 47, -9))
  }

  test("inverseModulo") {
    assertThrows[AssertionError] {
      ModularArithmetic.inverseModulo(-1, 2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.inverseModulo(4, -2)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.inverseModulo(0, 5)
    }

    assertThrows[AssertionError] {
      ModularArithmetic.inverseModulo(6, 0)
    }

    assertThrows[NotRelativelyPrimeException] {
      assert(ModularArithmetic.inverseModulo(5, 5) == (5, 0, 1))
    }

    assertThrows[NotRelativelyPrimeException] {
      assert(ModularArithmetic.inverseModulo(15, 18) == (3, -1, 1))
    }

    assert(ModularArithmetic.inverseModulo(1, 1) == 0)
    assert(ModularArithmetic.inverseModulo(1, 4) == 1)
    assert(ModularArithmetic.inverseModulo(8, 1) == 0)
    assert(ModularArithmetic.inverseModulo(4, 7) == 2)
    assert(ModularArithmetic.inverseModulo(19, 24) == 19)
    assert(ModularArithmetic.inverseModulo(52, 31) == 3)
    assert(ModularArithmetic.inverseModulo(240, 41) == 34)
    assert(ModularArithmetic.inverseModulo(25, 56) == 9)
    assert(ModularArithmetic.inverseModulo(25, 102) == 49)
  }

  test("solveCongruencesSystem") {
    assert(ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(2, 3), LinearCongruence(3, 5), LinearCongruence(2, 7))) == 23)
    assert(ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(5, 3), LinearCongruence(8, 5), LinearCongruence(9, 7))) == 23)
    assert(ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(2, 3), LinearCongruence(1, 4), LinearCongruence(3, 5))) == 53)
    assert(ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(1, 2), LinearCongruence(2, 3), LinearCongruence(3, 5), LinearCongruence(4, 11))) == 323)

    assertThrows[NotRelativelyPrimeException] {
      ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(5, 6), LinearCongruence(3, 10), LinearCongruence(8, 15)))
    }

    assert(ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(5, 6), LinearCongruence(3, 10), LinearCongruence(8, 15)), notRelativelyPrime = true) == 23)
    assert(ModularArithmetic.solveCongruencesSystem(IndexedSeq(LinearCongruence(7, 9), LinearCongruence(4, 12), LinearCongruence(16, 21)), notRelativelyPrime = true) == 16)
  }
}
