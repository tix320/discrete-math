package discretemath.structure.sequence.concrete

import discretemath.structure.sequence.SimpleSequence
import discretemath.structure.set.Sets

final class GeometricProgression(val initialTerm: BigDecimal, val ratio: BigDecimal) extends SimpleSequence[BigDecimal](Sets.R) {

  override protected def applyWithoutCheckInput(n: BigInt): BigDecimal = initialTerm * ratio.pow(n.intValue)

  override def isOneToOne = true
}