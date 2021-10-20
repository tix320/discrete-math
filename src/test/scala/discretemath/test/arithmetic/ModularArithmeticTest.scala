package discretemath.test.arithmetic

import discretemath.arithmetic.ModularArithmetic
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

    assert(ModularArithmetic.gcd(1,1) == 1)
    assert(ModularArithmetic.gcd(2,2) == 2)
    assert(ModularArithmetic.gcd(5,5) == 5)
    assert(ModularArithmetic.gcd(1,4) == 1)
    assert(ModularArithmetic.gcd(8,1) == 1)


    assert(ModularArithmetic.gcd(4,7) == 1)
    assert(ModularArithmetic.gcd(4,8) == 4)
    assert(ModularArithmetic.gcd(12,14) == 2)
    assert(ModularArithmetic.gcd(15,18) == 3)
    assert(ModularArithmetic.gcd(19,24) == 1)
    assert(ModularArithmetic.gcd(52,31) == 1)
    assert(ModularArithmetic.gcd(52,32) == 4)

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

    assert(ModularArithmetic.lcm(1,1) == 1)
    assert(ModularArithmetic.lcm(2,2) == 2)
    assert(ModularArithmetic.lcm(5,5) == 5)
    assert(ModularArithmetic.lcm(1,4) == 4)
    assert(ModularArithmetic.lcm(8,1) == 8)


    assert(ModularArithmetic.lcm(4,7) == 28)
    assert(ModularArithmetic.lcm(4,8) == 8)
    assert(ModularArithmetic.lcm(12,14) == 84)
    assert(ModularArithmetic.lcm(12,18) == 36)
    assert(ModularArithmetic.lcm(15,18) == 90)
    assert(ModularArithmetic.lcm(19,24) == 456)
    assert(ModularArithmetic.lcm(50,30) == 150)
    assert(ModularArithmetic.lcm(52,32) == 416)

  }
}
