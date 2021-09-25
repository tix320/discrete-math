package discretemath.bool.operator;

import java.util.Set;

import static discretemath.bool.operator.Operators.*;


public enum FunctionallyCompleteOperatorsSet {

	AND_OR_NOT(AND, OR, NOT), AND_NOT(AND, NOT), OR_NOT(OR, NOT), SINGLE_NAND(NAND), SINGLE_NOR(NOR);

	private final Set<Operator> operators;

	FunctionallyCompleteOperatorsSet(Operator... operators) {
		this.operators = Set.of(operators);
	}

	public boolean contains(Operator operator) {
		return operators.contains(operator);
	}
}
