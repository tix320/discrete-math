package discretemath.structure.sequence

import discretemath.structure.exception.TermNotDefinedException

import java.util.concurrent.ConcurrentHashMap

class CacheableSequence[T](protected override val originSequence: Sequence[T]) extends SequenceDecorator[T](originSequence) {

  private val cache = new ConcurrentHashMap[BigInt, T]

  @throws[TermNotDefinedException]
  override def apply(n: BigInt): T = cache.computeIfAbsent(n, originSequence.apply)


}