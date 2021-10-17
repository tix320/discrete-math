package discretemath.bool.expression.atomic;

sealed trait ConstantBooleanValue extends AtomicBooleanExpression {

}

object T extends ConstantBooleanValue {

  override def getVariables: Set[BooleanVariable] = Set.empty

  override def evaluate(arguments: Map[BooleanVariable, Boolean]) = true

  override def isSatisfiable: Boolean = true

  override def toString = "1"
}

object F extends ConstantBooleanValue {

  override def getVariables: Set[BooleanVariable] = Set.empty

  override def evaluate(arguments: Map[BooleanVariable, Boolean]) = false

  override def isSatisfiable: Boolean = false

  override def toString = "0"
}
