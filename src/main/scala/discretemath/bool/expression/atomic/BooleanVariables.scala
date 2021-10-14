package discretemath.bool.expression.atomic;

import discretemath.common.VariableSymbols;

object BooleanVariables {

  val X: BooleanVariable = BooleanVariable('x')
  val Y: BooleanVariable = BooleanVariable('y')
  val Z: BooleanVariable = BooleanVariable('z')

  def getVariables(variablesCount: Int): IndexedSeq[BooleanVariable] = {
    return VariableSymbols.view.take(variablesCount).map(symbol => BooleanVariable(symbol)).toIndexedSeq
  }
}
