package discretemath.structure.function.concrete

import discretemath.structure.exception.NotInvertibleException
import discretemath.structure.function
import discretemath.structure.function.CalculableFunction
import discretemath.structure.set.Sets

import scala.math.BigDecimal.RoundingMode

class CompoundInterest(val interestRate: BigDecimal, val compoundNumbersPerTime: Int, val time: Int) extends CalculableFunction[BigDecimal, BigDecimal](Sets.R_NON_NEG, Sets.R_NON_NEG) {

  override def calculate(principal: BigDecimal): BigDecimal = {

    val rateDivideNumbers = interestRate / compoundNumbersPerTime
    val onePlusDivision = 1 + rateDivideNumbers
    val exponent = compoundNumbersPerTime * time
    var power = onePlusDivision.pow(exponent)
    power = power.setScale(10, RoundingMode.HALF_UP)

    principal * power
  }

  @throws[NotInvertibleException]
  override def getInverse: function.Function[BigDecimal, BigDecimal] = { // TODO
    throw new UnsupportedOperationException
  }
}