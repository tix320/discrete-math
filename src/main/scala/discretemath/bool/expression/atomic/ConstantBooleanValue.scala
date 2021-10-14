package discretemath.bool.expression.atomic;

sealed trait ConstantBooleanValue extends AtomicBooleanExpression {

  object TRUE extends ConstantBooleanValue {

    override def evaluate(arguments: Map[BooleanVariable, Boolean]) = true

    override def toString = "1"
  }

  object FALSE extends ConstantBooleanValue {

    override def evaluate(arguments: Map[BooleanVariable, Boolean]) = false

    override def toString = "0"
  }

}