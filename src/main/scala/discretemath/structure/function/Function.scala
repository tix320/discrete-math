package discretemath.structure.function

import discretemath.structure.exception.{InputNotFromDomainException, NotInvertibleException}
import discretemath.structure.set.main.Set
import discretemath.structure.set.multi.MultiSet

trait Function[T, R] {

  def domain: Set[T]

  def codomain: Set[R]

  def range: Set[R]

  def multiSetRange: MultiSet[R]

  def isOnto: Boolean

  def isOneToOne: Boolean

  @throws[NotInvertibleException]
  def getInverse: Function[R, T]

  def graph: Set[(T, R)]

  /**
   * @throws InputNotFromDomainException when given input not from function domain
   * @throws IllegalStateException       when result for given input is not defined
   * @throws IllegalStateException       when output does not exist in the codomain
   */
  @throws[InputNotFromDomainException]
  @throws[IllegalStateException]
  def apply(input: T): R
}