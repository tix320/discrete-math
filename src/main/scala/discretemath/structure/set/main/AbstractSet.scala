package discretemath.structure.set.main

import discretemath.structure.set.RangeSet

trait AbstractSet[T] extends Set[T] {

  final def canEqual(a: Any): Boolean = a.isInstanceOf[Set[_]]

  final override def equals(that: Any): Boolean = {
    that match {
      case otherSet: Set[T] => {
        val thisIsInfinite = this.isInfinite
        val otherIsInfinite = otherSet.isInfinite

        if (thisIsInfinite != otherIsInfinite) { // if one of them is infinite, but second not
          return false
        }

        if (thisIsInfinite) { // both infinite
          val thisRangeSet = this.asInstanceOf[RangeSet[_]]
          val otherRangeSet = this.asInstanceOf[RangeSet[_]]
          return thisRangeSet.range == otherRangeSet.range
        }
        else {
          if (this.cardinality != otherSet.cardinality) {
            return false
          }

          return this.forall(item => otherSet.contains(item))
        }
      }
      case _ => false
    }
  }

  final override def hashCode: Int = AbstractSet.iterableHashcode(this)
}

object AbstractSet {
  private def iterableHashcode(iterable: Iterable[_]) = {
    var result = 1
    for (element <- iterable) {
      result = 31 * result + (if (element == null) 0
      else element.hashCode)
    }
    result
  }
}