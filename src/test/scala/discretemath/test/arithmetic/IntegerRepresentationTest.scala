package discretemath.test.arithmetic

import discretemath.arithmetic.IntegerRepresentation
import org.scalatest.funsuite.AnyFunSuite

class IntegerRepresentationTest extends AnyFunSuite {

  test("toBaseExpansionSpecialCases") {
    assertThrows[IllegalArgumentException] {
      IntegerRepresentation.toBaseExpansion(32, -1)
    }

    assertThrows[IllegalArgumentException] {
      IntegerRepresentation.toBaseExpansion(32, 0)
    }

    assertThrows[IllegalArgumentException] {
      IntegerRepresentation.toBaseExpansion(32, 1)
    }

    assert(IntegerRepresentation.toBaseExpansion(0, 8) == "0")
    assert(IntegerRepresentation.toBaseExpansion(1, 6) == "1")
  }

  test("toBaseExpansion") {
    assert(IntegerRepresentation.toBaseExpansion(1442, 2) == "1 ⋅ 2¹⁰ + 1 ⋅ 2⁸ + 1 ⋅ 2⁷ + 1 ⋅ 2⁵ + 1 ⋅ 2")
    assert(IntegerRepresentation.toBaseExpansion(3210, 3) == "1 ⋅ 3⁷ + 1 ⋅ 3⁶ + 1 ⋅ 3⁵ + 1 ⋅ 3³ + 2 ⋅ 3² + 2 ⋅ 3")
    assert(IntegerRepresentation.toBaseExpansion(2103, 4) == "2 ⋅ 4⁵ + 3 ⋅ 4² + 1 ⋅ 4 + 3")
    assert(IntegerRepresentation.toBaseExpansion(2453, 5) == "3 ⋅ 5⁴ + 4 ⋅ 5³ + 3 ⋅ 5² + 3")
    assert(IntegerRepresentation.toBaseExpansion(9173, 6) == "1 ⋅ 6⁵ + 1 ⋅ 6⁴ + 2 ⋅ 6² + 4 ⋅ 6 + 5")
    assert(IntegerRepresentation.toBaseExpansion(7145, 7) == "2 ⋅ 7⁴ + 6 ⋅ 7³ + 5 ⋅ 7² + 5 ⋅ 7 + 5")
    assert(IntegerRepresentation.toBaseExpansion(7680, 8) == "1 ⋅ 8⁴ + 7 ⋅ 8³")
    assert(IntegerRepresentation.toBaseExpansion(-7680, 8) == "-1 ⋅ 8⁴ - 7 ⋅ 8³")
  }

  test("toCantorExpansion") {
    assert(IntegerRepresentation.toCantorExpansion(0) == "0 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(1) == "1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(-1) == "-1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(2) == "1 ⋅ 2!")
    assert(IntegerRepresentation.toCantorExpansion(-2) == "-1 ⋅ 2!")
    assert(IntegerRepresentation.toCantorExpansion(3) == "1 ⋅ 2! + 1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(-3) == "-1 ⋅ 2! - 1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(5) == "2 ⋅ 2! + 1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(-5) == "-2 ⋅ 2! - 1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(10) == "1 ⋅ 3! + 2 ⋅ 2!")
    assert(IntegerRepresentation.toCantorExpansion(-10) == "-1 ⋅ 3! - 2 ⋅ 2!")
    assert(IntegerRepresentation.toCantorExpansion(25) == "1 ⋅ 4! + 1 ⋅ 1!")
    assert(IntegerRepresentation.toCantorExpansion(-30) == "-1 ⋅ 4! - 1 ⋅ 3!")
  }

}
