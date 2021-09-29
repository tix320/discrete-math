package discretemath.bool;

import discretemath.bool.expression.BooleanVariable;

public record SimpleLiteral(BooleanVariable variable, boolean isPositive) implements Literal {

	@Override
	public String toString() {
		return isPositive ? variable.toString() : variable + "′";
	}
}
