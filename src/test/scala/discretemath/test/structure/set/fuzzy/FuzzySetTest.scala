package discretemath.test.structure.set.fuzzy

import discretemath.structure.set.fuzzy.StaticFuzzySet
import org.scalatest.funsuite.AnyFunSuite

class FuzzySetTest extends AnyFunSuite {

  test("union") {
    val fuzzySet1 = StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.7, 8 -> 0))
    val fuzzySet2 = StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.4, 5 -> 0.1))

    val resultSet = fuzzySet1.unionWith(fuzzySet2)

    assert(resultSet == StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.7, 5 -> 0.1, 8 -> 0)))
  }

  test("intersect") {
    val fuzzySet1 = StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.7, 8 -> 0, 5 -> 0.05))
    val fuzzySet2 = StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.4, 5 -> 0.1))

    val resultSet = fuzzySet1.intersectWith(fuzzySet2)

    assert(resultSet == StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.4, 5 -> 0.05)))
  }

  test("complement") {
    val fuzzySet = StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.7, 8 -> 0, 5 -> 0.05))

    val resultSet = fuzzySet.complement

    assert(resultSet == StaticFuzzySet(Map(3 -> 0.5, 4 -> 0.3, 5 -> 0.95, 8 -> 1)))
  }
}
