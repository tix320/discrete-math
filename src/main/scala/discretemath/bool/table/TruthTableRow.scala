package discretemath.bool.table;

import discretemath.common.BitString;

trait TruthTableRow {

  def index: Int

  def arguments: IndexedSeq[Boolean]

  def functionsCount: Int

  def getArgumentsBitString: BitString

  def getArgument(index: Int): Boolean

  def getFunctionValue(index: Int): FunctionValue
}