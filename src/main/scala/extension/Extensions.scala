package extension

import scala.collection.mutable.ArrayBuffer

object Extensions {

  implicit class ArrayImprovements(array: Array[Int]) {
    def allMax: collection.IndexedSeq[Int] = {
      val indexes = new ArrayBuffer[Int]
      var max = array(0)
      indexes.addOne(0)
      for (i <- 1 until array.length) {
        if (array(i) == max) indexes.addOne(i)
        else if (array(i) > max) {
          indexes.clear()
          indexes.addOne(i)
          max = array(i)
        }
      }
      indexes
    }
  }

}