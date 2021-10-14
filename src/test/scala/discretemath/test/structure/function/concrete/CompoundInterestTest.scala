package discretemath.test.structure.function.concrete

import discretemath.structure.function.concrete.CompoundInterest
import org.scalatest.funsuite.AnyFunSuite

import java.math.BigDecimal;

class CompoundInterestTest extends AnyFunSuite {

  test("calculate") {
    val compoundInterest = new CompoundInterest(BigDecimal.valueOf(0.05), 12, 10);

    assert(compoundInterest.calculate(BigDecimal.valueOf(5000)) == 8235.0474885);
  }
}