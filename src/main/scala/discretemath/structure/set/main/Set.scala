package discretemath.structure.set.main

import discretemath.structure.exception.{TooLargeSetException, UncountableSetException}
import discretemath.structure.set.BaseSet

/**
 * If the set is infinite, then it must also implement `RangeSet`
 */
trait Set[T] extends BaseSet[T, Set, Set[T]] {

  def powerSet: Set[Set[T]]

  /**
   * @return -1 when infinity
   * @throws TooLargeSetException when size is greater than `Integer`
   */
  @throws[UncountableSetException]
  override def iterator: Iterator[T]
}
