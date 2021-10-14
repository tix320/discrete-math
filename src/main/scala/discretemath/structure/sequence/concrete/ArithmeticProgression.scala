package discretemath.structure.sequence.concrete

import discretemath.structure.sequence.SimpleSequence
import discretemath.structure.set.Sets

final class ArithmeticProgression(val initialTerm: BigDecimal, val difference: BigDecimal) extends SimpleSequence[BigDecimal](Sets.R) {

  override protected def applyWithoutCheckInput(n: BigInt): BigDecimal = initialTerm + (difference * BigDecimal(n))

  override def isOneToOne = true
}