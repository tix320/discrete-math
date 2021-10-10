package discretemath.bool.expression.forms;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.atomic.BooleanVariables;
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;
import discretemath.bool.minimization.McCluskey;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;
import discretemath.bool.operator.Operators;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;
import discretemath.bool.table.TruthTableRow;

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

		List<BooleanVariable> variables = BooleanVariables.getVariables(variablesCount);

		LinkedHashSet<Minterm> minterms = new LinkedHashSet<>();
		for (TruthTableRow truthTableRow : truthTable) {
			if (truthTableRow.getFunctionValue(0) == FunctionValue.TRUE) {

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
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		boolean[] values = new boolean[minterms.size()];

		Iterator<Minterm> mintermIterator = minterms.iterator();

		for (int i = 0; i < minterms.size(); i++) {
			Minterm minterm = mintermIterator.next();

			values[i] = minterm.evaluate(arguments);
		}

		return Operators.OR.evaluate(values);
	}

	@Override
	public BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override
	public BooleanExpression normalize() {
		return new McCluskey().minimizeSOPExpression(this);
	}

	@Override
	public String toString() {
		return minterms.stream().map(Object::toString).collect(Collectors.joining(" + "));
	}
}
