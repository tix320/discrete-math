package discretemath.test.structure.function

import discretemath.structure.function.StaticFunction
import org.scalatest.funsuite.AnyFunSuite

class StaticFunctionTest extends AnyFunSuite {

  test("isOneToOne") {
    val function = StaticFunction(Map(1 -> 2, 4 -> 5, 6 -> 7));

    assert(function.isOneToOne);

    val function2 = StaticFunction(Map(1 -> 2, 4 -> 2, 6 -> 7));

    assert(!function2.isOneToOne);
  }

  test("getInverse") {
    val function = StaticFunction(Map(1 -> 5, 2 -> 8, 3 -> 10));

    val inverseFunction = function.getInverse;

    assert(inverseFunction.apply(5) == 1);
    assert(inverseFunction.apply(8) == 2);
    assert(inverseFunction.apply(10) == 3);
  }
}