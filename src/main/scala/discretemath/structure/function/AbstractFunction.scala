package discretemath.structure.function

import discretemath.structure.exception.{DomainIsInfiniteException, InputNotFromDomainException, NotInvertibleException}
import discretemath.structure.set.main._
import discretemath.structure.set.multi.{MultiSet, SimpleMultiSet}
import discretemath.utils.CacheValue


abstract class AbstractFunction[T, R] extends Function[T, R] {
  protected val ontoCache = new CacheValue[Boolean]
  protected val oneToOneCache = new CacheValue[Boolean]

  @throws[InputNotFromDomainException]
  @throws[IllegalStateException]
  final override def apply(input: T): R = {
    if (!domain.contains(input)) {
      throw new InputNotFromDomainException
    }

    applyWithoutCheckInput(input)
  }

  override def range: Set[R] = {
    if (domain.isInfinite) {
      throw new DomainIsInfiniteException
    }

    SimpleSet(domain.view.map(applyWithoutCheckInput).toSet)
  }

  override def multiSetRange: MultiSet[R] = {
    if (domain.isInfinite) {
      throw new DomainIsInfiniteException
    }

    val rangeSet: Map[R, Int] = domain.view.map(applyWithoutCheckInput).groupMapReduce(identity)(_ => 1)((v1, v2) => v1 + v2)

    SimpleMultiSet(rangeSet)
  }

  override def graph: Set[(T, R)] = {
    if (domain.isInfinite) {
      throw new DomainIsInfiniteException
    }

    val graph = domain.view.map(input => (input, applyWithoutCheckInput(input))).toSet

    SimpleSet(graph)
  }

  override def isOnto: Boolean = {
    ontoCache synchronized {
      if (ontoCache.isSet) return ontoCache.get

      val isOnto = range.size == codomain.size

      ontoCache.set(isOnto)

      return isOnto
    }

  }

  override def isOneToOne: Boolean = {
    oneToOneCache synchronized {
      if (oneToOneCache.isSet) return oneToOneCache.get

      val range = multiSetRange
      val mode: Option[R] = range.mode

      val isOneToOne = mode match {
        case Some(value) => range.itemCount(value) == 1 // no duplicates
        case None => true
      }

      oneToOneCache.set(isOneToOne)

      return isOneToOne
    }

  }

  @throws[NotInvertibleException]
  override def getInverse: Function[R, T] = {
    if (!isOnto || !isOneToOne) throw new NotInvertibleException
    if (domain.isInfinite) throw new DomainIsInfiniteException

    val reverseMapping = domain.view.map(input => (applyWithoutCheckInput(input), input)).toMap

    StaticFunction(reverseMapping)
  }

  protected def applyWithoutCheckInput(input: T): R
}