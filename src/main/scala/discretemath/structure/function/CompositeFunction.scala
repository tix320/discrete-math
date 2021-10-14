package discretemath.structure.function

import discretemath.structure.exception.NotInvertibleException
import discretemath.structure.set.main._

final class CompositeFunction[T, M, R] private(val g: Function[T, M], val f: Function[M, R]) extends AbstractFunction[T, R] {
  override def domain: Set[T] = g.domain

  override def codomain: Set[R] = f.codomain

  override def isOnto: Boolean = {
    ontoCache synchronized {
      if (ontoCache.isSet) return ontoCache.get

      if (g.isOnto && f.isOnto) {
        ontoCache.set(true)
        return true
      }

      if (!f.isOnto) {
        ontoCache.set(false)
        return false
      }

      return super.isOnto
    }
  }

  override def isOneToOne: Boolean = {
    oneToOneCache synchronized {
      if (oneToOneCache.isSet) return oneToOneCache.get

      if (g.isOneToOne && f.isOneToOne) {
        oneToOneCache.set(true)
        return true
      }

      if (!g.isOneToOne) {
        oneToOneCache.set(false)
        return false
      }

      return super.isOneToOne
    }
  }

  @throws[NotInvertibleException]
  override def getInverse: CompositeFunction[R, M, T] = {
    if (!isOnto || !isOneToOne) throw new NotInvertibleException

    val fInverse = f.getInverse
    val gInverse = g.getInverse
    new CompositeFunction[R, M, T](fInverse, gInverse)
  }

  override protected def applyWithoutCheckInput(input: T): R = {
    val intermediateValue = g.apply(input)
    if (intermediateValue == null) throw new IllegalStateException("Intermediate value for input [%s] is not defined".format(input))

    val result = f.apply(intermediateValue)
    if (result == null) throw new IllegalStateException("Result for input [%s] is not defined".format(input))

    if (!codomain.contains(result)) throw new IllegalStateException("Output [%s] does not exist in the codomain".format(result))

    return result
  }
}

object CompositeFunction {
  def apply[T, M, R](function1: Function[T, M], function2: Function[M, R]) = new CompositeFunction[T, M, R](function1, function2)
}