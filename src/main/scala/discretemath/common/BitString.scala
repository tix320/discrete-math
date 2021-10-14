package discretemath.common

import java.util

class BitString private(private val bitSet: util.BitSet, val length: Int) extends Iterable[Boolean] {

  def set(index: Int, value: Boolean): Unit = {
    if (index >= length) throw new IndexOutOfBoundsException(index)
    bitSet.set(index, value)
  }

  def get(index: Int): Boolean = {
    if (index >= length) throw new IndexOutOfBoundsException(index)
    bitSet.get(index)
  }

  def bitCount: Int = bitSet.cardinality

  def asLong: Long = {
    if (length > 64) throw new UnsupportedOperationException

    var value = 0L
    for (i <- length - 1 to 0 by -1) {
      val shiftCount = length - 1 - i
      value += (if (bitSet.get(i)) 1L << shiftCount else 0L)
    }

    value
  }

  def differInOneBit(other: BitString): Boolean = {
    try {
      getDifferBitIndex(other)
      true
    } catch {
      case _: MoreThanOneBitDifferenceException =>
        false
    }
  }

  @throws[MoreThanOneBitDifferenceException]
  def getDifferBitIndex(other: BitString): Int = {
    val clone = this.bitSet.clone.asInstanceOf[util.BitSet]

    clone.xor(other.bitSet)
    if (clone.cardinality != 1) throw new MoreThanOneBitDifferenceException(Integer.toString(clone.cardinality))

    clone.length - 1
  }

  override def iterator = new Itr

  def canEqual(other: Any): Boolean = other.isInstanceOf[BitString]

  override def equals(other: Any): Boolean = other match {
    case that: BitString =>
      (that canEqual this) &&
        bitSet == that.bitSet &&
        length == that.length
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(bitSet, length)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String = {
    val builder = new StringBuilder
    for (i <- 0 until length) {
      val bit = bitSet.get(i)
      builder.append(if (bit) '1'
      else '0')
    }
    builder.toString
  }

  final class Itr extends Iterator[Boolean] {
    private var index = 0

    override def hasNext: Boolean = index < BitString.this.length

    override def next: Boolean = {
      try {
        val value = bitSet.get(index)
        index += 1
        value
      }
      catch {
        case _: ArrayIndexOutOfBoundsException =>
          throw new NoSuchElementException
      }
    }
  }
}

object BitString {
  def forN(n: Int) = new BitString(new util.BitSet(n), n)
}