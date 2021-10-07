package discretemath.bool.expression.forms;

import java.util.Iterator;
import java.util.List;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;
import discretemath.common.BitString;

public sealed interface Minterm extends BooleanExpression permits SimpleMinterm, SubMinterm {

	List<Literal> literals();

	@Override
	default BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	default BooleanExpression normalize() {
		return this;
	}

	default Minterm subMinterm(int fromIndex, int toIndex) {
		return new SubMinterm(this, fromIndex, toIndex);
	}

	default BitString toBitString() {
		List<Literal> literals = literals();
		Iterator<Literal> literalsIterator = literals.iterator();

		BitString bitString = BitString.forN(literals.size());
		for (int i = 0; i < literals.size(); i++) {
			Literal literal = literalsIterator.next();
			bitString.set(i, literal.isPositive());
		}

		return bitString;
	}
}
