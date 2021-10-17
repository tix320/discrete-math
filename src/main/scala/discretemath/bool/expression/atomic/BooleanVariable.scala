package discretemath.bool.expression.atomic;

import discretemath.bool.expression.exception.VariableValueNotSpecifiedException
import discretemath.common.VariableSymbols;

case class BooleanVariable(symbol: Char) extends AtomicBooleanExpression {

  override def getVariables: Set[BooleanVariable] = Set(this)

  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    return arguments.getOrElse(this, throw new VariableValueNotSpecifiedException(this))
  }

  override def isSatisfiable: Boolean = true

  override def toString: String = String.valueOf(symbol)
}

object BooleanVariable {
  val X: BooleanVariable = BooleanVariable('x')
  val Y: BooleanVariable = BooleanVariable('y')
  val Z: BooleanVariable = BooleanVariable('z')

  def getN(variablesCount: Int): IndexedSeq[BooleanVariable] = {
    return VariableSymbols.view.take(variablesCount).map(symbol => BooleanVariable(symbol)).toIndexedSeq
  }
}