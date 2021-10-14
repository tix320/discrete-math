package discretemath.structure.function.concrete


import discretemath.structure.function.CalculableFunction
import discretemath.structure.set.Sets

import scala.math.{BigDecimal, BigInt}

final class CeilFunction() extends CalculableFunction[BigDecimal, BigInt](Sets.R, Sets.Z) {

  override def isOnto = true

  override def isOneToOne = false

  override def calculate(value: BigDecimal): BigInt = value.setScale(0, BigDecimal.RoundingMode.CEILING).toBigInt
}