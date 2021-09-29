package discretemath.bool;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.BooleanVariable;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;
import discretemath.bool.table.TruthTable;
import discretemath.bool.table.TruthTableRow;
import discretemath.common.VariableSymbols;

public class SumOfProductExpression implements BooleanExpression {

	private final List<BooleanVariable> variables;

	private final LinkedHashSet<Minterm> minterms;

	private SumOfProductExpression(List<BooleanVariable> variables, LinkedHashSet<Minterm> minterms) {
		this.variables = variables;
		this.minterms = minterms;
	}

	public static SumOfProductExpression fromTruthTable(TruthTable truthTable) {
		int functionsCount = truthTable.getFunctionsCount();
		if (functionsCount != 1) {
			throw new IllegalArgumentException("Truth table must be contain exactly one function");
		}

		int variablesCount = truthTable.getVariablesCount();

		List<BooleanVariable> variables = new ArrayList<>(variablesCount);
		for (Character symbol : VariableSymbols.symbolsFor(variablesCount)) {
			variables.add(new BooleanVariable(symbol));
		}

		LinkedHashSet<Minterm> minterms = new LinkedHashSet<>();
		for (TruthTableRow truthTableRow : truthTable) {
			if (truthTableRow.getValue(0)) {

				LinkedHashSet<Literal> literals = new LinkedHashSet<>();
				for (int i = 0; i < truthTableRow.argumentsCount(); i++) {
					boolean value = truthTableRow.getArgument(i);
					BooleanVariable variable = variables.get(i);
					literals.add(new SimpleLiteral(variable, value));
				}

				minterms.add(new SimpleMinterm(literals));
			}
		}

		return new SumOfProductExpression(variables, minterms);
	}

	public List<BooleanVariable> getVariables() {
		return variables;
	}

	public LinkedHashSet<Minterm> getMinterms() {
		return minterms;
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
