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

}
