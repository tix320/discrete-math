package discretemath.combination

import discretemath.common.BitString

class GrayCode private(val table: Array[BitString]) {
  def get(index: Int): BitString = table(index)

  def find(bitString: BitString): Int = {
    if (bitString.length != getVariablesCount) throw new IllegalArgumentException(bitString.toString)
    for (i <- table.indices) {
      val string = table(i)
      if (bitString == string) return i
    }
    throw new IllegalStateException
  }

  def getCombinationsCount: Int = table.length

  def getVariablesCount: Int = table(0).length

  override def toString: String = {
    val builder = new StringBuilder
    for (bitString <- table) {
      for (i <- 0 until bitString.length) {
        val b = bitString.get(i)
        builder.append(if (b) 1
        else 0).append("\t")
      }
      builder.append("\n")
    }
    builder.toString
  }
}

object GrayCode {
  def forN(n: Int): GrayCode = {
    val bitSetTable = createBitStringTable(n)
    new GrayCode(bitSetTable)
  }

  private def createBitStringTable(n: Int) = {
    val rowsCount = 1 << n
    val table = new Array[BitString](rowsCount)
    for (i <- table.indices) {
      table(i) = BitString.forN(n)
    }
    for (i <- 0 until rowsCount) {
      val grayCode = i ^ (i >> 1)
      for (j <- 0 until n) {
        val value = ((grayCode >> j) & 1) == 1
        table(i).set(n - 1 - j, value)
      }
    }
    table
  }
}
