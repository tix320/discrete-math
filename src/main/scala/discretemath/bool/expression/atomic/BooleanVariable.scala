package discretemath.bool.expression.atomic;

import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;

case class BooleanVariable(symbol: Char) extends AtomicBooleanExpression {

  @throws[VariableValueNotSpecifiedException]
  def evaluate(arguments: Map[BooleanVariable, Boolean]): Boolean = {
    return arguments.getOrElse(this, throw new VariableValueNotSpecifiedException(this))
  }

  override def toString: String = String.valueOf(symbol)
}
