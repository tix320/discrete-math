package discretemath.structure.set.multi

import discretemath.utils.CacheValue

import scala.collection.immutable

class SimpleMultiSet[T] private(val countMap: Map[T, Int]) extends AbstractMultiSet[T] {

  val cardinality: Int = countMap.values.sum
  private val modeCache = new CacheValue[T]

  override def mode: Option[T] = {
    modeCache.synchronized {
      if (modeCache.isSet) return Option(modeCache.get)

      val mode = countMap.maxBy(_._2)._1

      modeCache.set(mode)

      return Option(mode)
    }

  }

  override def itemCount(item: T): Int = countMap.getOrElse(item, 0)

  override def contains(item: T): Boolean = countMap.getOrElse(item, 0) != 0

  override def unionWith[I <: MultiSet[T], R >: MultiSet[T]](set: I): R = {
    val mergerFunction = set match {
      case multiSet: MultiSet[T] => (k: T) => (k, math.max(this.itemCount(k), multiSet.itemCount(k)))
      case _ => (k: T) =>
        (k, math.max(this.itemCount(k), {
          if (set.contains(k)) 1 else 0
        }))
    }

    val resultCountMap = countMap.keySet.view.concat(set.iterator)
      .map(mergerFunction)
      .toMap

    new SimpleMultiSet[T](resultCountMap)
  }

  override def intersectWith[I <: MultiSet[T], R >: MultiSet[T]](set: I): R = {
    val mergerFunction = set match {
      case multiSet: MultiSet[T] => (k: T) => (k, math.min(this.itemCount(k), multiSet.itemCount(k)))
      case _ => (k: T) =>
        (k, math.min(this.itemCount(k), {
          if (set.contains(k)) 1 else 0
        }))
    }

    val resultCountMap = countMap.keySet.view.concat(set.iterator)
      .map(mergerFunction)
      .toMap

    new SimpleMultiSet[T](resultCountMap)
  }

  override def differenceWith[I <: MultiSet[T], R >: MultiSet[T]](set: I): R = {
    val mergerFunction = set match {
      case multiSet: MultiSet[T] => (k: T) => (k, math.max(0, this.itemCount(k) - multiSet.itemCount(k)))
      case _ => (k: T) =>
        (k, math.max(0, this.itemCount(k) - {
          if (set.contains(k)) 1 else 0
        }))
    }

    val resultCountMap = countMap.keySet.view.concat(set.iterator)
      .map(mergerFunction)
      .toMap

    new SimpleMultiSet[T](resultCountMap)
  }

  override def sumWith(set: MultiSet[T]): MultiSet[T] = {
    val resultCountMap = countMap.keySet.view.concat(set.iterator)
      .map((k: T) => (k, this.itemCount(k) + set.itemCount(k)))
      .toMap

    new SimpleMultiSet[T](resultCountMap)
  }

  override def symmetricDifferenceWith[I <: MultiSet[T], R >: MultiSet[T]](set: I) = throw new UnsupportedOperationException("Not implemented yet") // TODO

  override def cartesianProductWith[P](set: MultiSet[P]) = throw new UnsupportedOperationException("Not implemented yet")

  override def iterator: Iterator[T] = countMap.view.flatMap(tuple => Seq.fill(tuple._2)(tuple._1)).iterator

  override def withCountIterator: Iterator[(T, Int)] = countMap.iterator

  override def toString: String = countMap.toString
}

object SimpleMultiSet {
  def apply[T](map: immutable.Map[T, Int]) = new SimpleMultiSet[T](map)

  def apply[T](collection: Iterable[T]): SimpleMultiSet[T] = {
    val countMap = collection.groupMapReduce(identity)(i => 1)((v1, v2) => v1 + v2)
    new SimpleMultiSet[T](countMap)
  }
}