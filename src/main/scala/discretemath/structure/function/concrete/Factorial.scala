package discretemath.structure.function.concrete

import discretemath.structure.sequence.RecurrentSequence
import discretemath.structure.set.Sets

import scala.math.BigInt

final class Factorial extends RecurrentSequence[BigInt](Sets.Z_PLUS) {

  override def isOnto = false

  override def isOneToOne = false

  override protected def getNextRecurrentValue(n: BigInt): BigInt = {
    if ((n == 0) || (n == 1)) return 1

    getNextRecurrentValue(n - 1) * n
  }
}