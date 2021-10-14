package discretemath.structure.set.multi

import discretemath.structure.set.BaseSet

trait MultiSet[T] extends BaseSet[T, MultiSet, MultiSet[T]] {

  def sumWith(set: MultiSet[T]): MultiSet[T]

  def mode: Option[T]

  def itemCount(item: T): Int

  def withCountIterator: Iterator[(T, Int)]
}