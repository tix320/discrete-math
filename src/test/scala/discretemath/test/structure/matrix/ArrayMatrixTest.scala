package discretemath.test.structure.matrix

import discretemath.structure.matrix.ArrayMatrix
import extension.Extensions.{IntAdder, IntMultiplier}
import org.scalatest.funsuite.AnyFunSuite

class ArrayMatrixTest extends AnyFunSuite {

  test("multiply") {
    val matrix1: ArrayMatrix[Int] = ArrayMatrix(Array(
      Array(1, 0, 5, 6),
      Array(0, 2, 4, 7),
      Array(7, 8, 0, 8),
    ))

    val matrix2: ArrayMatrix[Int] = ArrayMatrix(Array(
      Array(2, 3, 9, 8, 7),
      Array(5, 3, 4, 5, 6),
      Array(9, 4, 0, 2, 4),
      Array(8, 6, 7, 5, 3),
    ))

    val productMatrix: ArrayMatrix[Int] = ArrayMatrix(Array(
      Array(95, 59, 51, 48, 45),
      Array(102, 64, 57, 53, 49),
      Array(118, 93, 151, 136, 121),
    ))

    assert(matrix1.multiply(matrix2) == productMatrix)
  }

  test("addition") {
    val matrix1: ArrayMatrix[Int] = ArrayMatrix(Array(
      Array(1, 0, 5, 6),
      Array(0, 2, 4, 7),
      Array(7, 8, 0, 8),
    ))

    val matrix2: ArrayMatrix[Int] = ArrayMatrix(Array(
      Array(2, 3, 9, 8),
      Array(5, 3, 4, 5),
      Array(9, 4, 0, 2),
    ))

    val additionMatrix: ArrayMatrix[Int] = ArrayMatrix(Array(
      Array(3, 3, 14, 14),
      Array(5, 5, 8, 12),
      Array(16, 12, 0, 10),
    ))

    assert(matrix1.add(matrix2) == additionMatrix)
  }

}
