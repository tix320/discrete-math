package discretemath.structure.set.multi

trait AbstractMultiSet[T] extends MultiSet[T] {

  final def canEqual(a: Any): Boolean = a.isInstanceOf[MultiSet[_]]

  final override def equals(that: Any): Boolean = {
    that match {
      case otherSet: MultiSet[T] => {
        if (this.cardinality != otherSet.cardinality) return false

        return this.forall(item => this.itemCount(item) == otherSet.itemCount(item))
      }
      case _ => false
    }
  }

  final override def hashCode: Int = AbstractMultiSet.iteratorHashcode(withCountIterator)
}

object AbstractMultiSet {
  private def iteratorHashcode(iterator: Iterator[_]) = {
    var result = 1
    while (iterator.hasNext) {
      val element = iterator.next
      result = 31 * result + (if (element == null) 0
      else element.hashCode)
    }
    result
  }
}