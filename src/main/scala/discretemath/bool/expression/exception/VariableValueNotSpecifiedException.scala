package discretemath.bool.expression.exception;

import discretemath.bool.expression.atomic.BooleanVariable;

class VariableValueNotSpecifiedException(val variable: BooleanVariable) extends RuntimeException("Variable[%s]".format(variable)) {
}