package discretemath.test.structure.set.multi

import discretemath.structure.set.multi.SimpleMultiSet
import org.scalatest.funsuite.AnyFunSuite


class MultiSetTest extends AnyFunSuite {

  test("union") {
    val set1 = SimpleMultiSet(Map(1 -> 2, 2 -> 3, 4 -> 1))
    val set2 = SimpleMultiSet(Map(1 -> 1, 3 -> 2, 4 -> 2))

    assert(set1.unionWith(set2) == SimpleMultiSet(Map(1 -> 2, 2 -> 3, 3 -> 2, 4 -> 2)))

  }

  test("intersect") {
    val set1 = SimpleMultiSet(Map(1 -> 2, 2 -> 3, 4 -> 3, 5 -> 0))
    val set2 = SimpleMultiSet(Map(1 -> 1, 3 -> 2, 4 -> 2, 6 -> 1))

    assert(set1.intersectWith(set2) == SimpleMultiSet(Map(1 -> 1, 4 -> 2)))
  }

  test("difference") {
    val set1 = SimpleMultiSet(Map(1 -> 2, 2 -> 3, 4 -> 1))
    val set2 = SimpleMultiSet(Map(1 -> 1, 3 -> 2, 4 -> 2))

    assert(set1.differenceWith(set2) == SimpleMultiSet(Map(1 -> 1, 2 -> 3)))
  }

  test("sum") {
    val set1 = SimpleMultiSet(Map(1 -> 2, 2 -> 3, 4 -> 1))
    val set2 = SimpleMultiSet(Map(1 -> 1, 3 -> 2, 4 -> 2))

    assert(set1.sumWith(set2) == SimpleMultiSet(Map(1 -> 3, 2 -> 3, 3 -> 2, 4 -> 3)))
  }
}