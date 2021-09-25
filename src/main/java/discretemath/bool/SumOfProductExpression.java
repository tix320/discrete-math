package discretemath.bool;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;
import discretemath.bool.table.TruthTable;
import discretemath.bool.table.TruthTableRow;
import discretemath.common.VariableSymbols;

public class SumOfProductExpression implements BooleanExpression {

	private final int variablesCount;

	private final LinkedHashSet<Minterm> minterms;

	private SumOfProductExpression(LinkedHashSet<Minterm> minterms) {
		this.minterms = minterms;
		this.variablesCount = minterms.iterator().next().literals().size();
	}

	public static SumOfProductExpression fromTruthTable(TruthTable truthTable) {
		int functionsCount = truthTable.getFunctionsCount();
		if (functionsCount != 1) {
			throw new IllegalArgumentException("Truth table must be contain exactly one function");
		}

		int variablesCount = truthTable.getVariablesCount();
		LinkedHashSet<Minterm> minterms = new LinkedHashSet<>();
		for (TruthTableRow truthTableRow : truthTable) {
			if (truthTableRow.getValue(0)) {
				Iterator<Character> symbols = VariableSymbols.symbolsFor(variablesCount);
				LinkedHashSet<Literal> literals = new LinkedHashSet<>();
				for (int i = 0; i < truthTableRow.argumentsCount(); i++) {
					boolean value = truthTableRow.getArgument(i);
					literals.add(new SimpleLiteral(symbols.next(), value));
				}

				minterms.add(new SimpleMinterm(literals));
			}
		}

		return new SumOfProductExpression(minterms);
	}

	@Override
	public BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override
	public BooleanExpression normalize() {
		throw new UnsupportedOperationException(); //TODO normalize with McCluskey
	}

	@Override
	public String toString() {
		return minterms.stream().map(Object::toString).collect(Collectors.joining(" + "));
	}
}
