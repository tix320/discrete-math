package discretemath.structure.set

import discretemath.structure.set.range._

import discretemath.structure.set.main.Set

trait RangeSet[T]  {
  this: Set[T] =>

  def range: Range[T]

}