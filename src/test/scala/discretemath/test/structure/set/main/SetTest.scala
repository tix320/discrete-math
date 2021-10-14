package discretemath.test.structure.set.main

import discretemath.structure.set.main.SimpleSet
import org.scalatest.funsuite.AnyFunSuite;


class SetTest extends AnyFunSuite {

  test("union") {
    val set1 = SimpleSet(1, 2, 3);
    val set2 = SimpleSet(3, 4, 5);

    assert(set1.unionWith(set2) == SimpleSet(1, 2, 3, 4, 5));
  }

  test("intersect") {
    val set1 = SimpleSet(1, 2, 3, 4);
    val set2 = SimpleSet(3, 4, 5);

    assert(set1.intersectWith(set2) == SimpleSet(3, 4));
  }

  test("difference") {
    val set1 = SimpleSet(1, 2, 3, 4);
    val set2 = SimpleSet(3, 4, 5);

    assert(set1.differenceWith(set2) == SimpleSet(1, 2));
  }

  test("symmetricDifference") {
    val set1 = SimpleSet(1, 2, 3, 4);
    val set2 = SimpleSet(3, 4, 5);

    assert(set1.symmetricDifferenceWith(set2) == SimpleSet(1, 2, 5));
  }
}