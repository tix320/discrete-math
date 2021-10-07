package discretemath.bool.expression.forms;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import discretemath.bool.expression.BooleanExpression;
import discretemath.bool.expression.atomic.BooleanVariable;
import discretemath.bool.expression.atomic.BooleanVariables;
import discretemath.bool.expression.exception.VariableValueNotSpecifiedException;
import discretemath.bool.operator.FunctionallyCompleteOperatorsSet;
import discretemath.bool.operator.Operators;
import discretemath.bool.table.FunctionValue;
import discretemath.bool.table.TruthTable;
import discretemath.bool.table.TruthTableRow;

public class ProductOfSumExpression implements BooleanExpression {

	private final List<BooleanVariable> variables;

	private final LinkedHashSet<Maxterm> maxterms;

	private ProductOfSumExpression(List<BooleanVariable> variables, LinkedHashSet<Maxterm> maxterms) {
		this.variables = variables;
		this.maxterms = maxterms;
	}

	public static ProductOfSumExpression fromTruthTable(TruthTable truthTable) {
		int functionsCount = truthTable.getFunctionsCount();
		if (functionsCount != 1) {
			throw new IllegalArgumentException("Truth table must be contain exactly one function");
		}

		int variablesCount = truthTable.getVariablesCount();

		List<BooleanVariable> variables = BooleanVariables.getVariables(variablesCount);

		LinkedHashSet<Maxterm> maxterms = new LinkedHashSet<>();
		for (TruthTableRow truthTableRow : truthTable) {
			if (truthTableRow.getFunctionValue(0) == FunctionValue.FALSE) {

				LinkedHashSet<Literal> literals = new LinkedHashSet<>();
				for (int i = 0; i < truthTableRow.argumentsCount(); i++) {
					boolean value = truthTableRow.getArgument(i);
					BooleanVariable variable = variables.get(i);
					literals.add(new SimpleLiteral(variable, !value));
				}

				maxterms.add(new SimpleMaxterm(literals));
			}
		}

		return new ProductOfSumExpression(variables, maxterms);
	}

	public List<BooleanVariable> getVariables() {
		return variables;
	}

	public LinkedHashSet<Maxterm> getMaxterms() {
		return maxterms;
	}

	@Override
	public boolean evaluate(Map<BooleanVariable, Boolean> arguments) throws VariableValueNotSpecifiedException {
		boolean[] values = new boolean[maxterms.size()];

		Iterator<Maxterm> maxtermIterator = maxterms.iterator();

		for (int i = 0; i < maxterms.size(); i++) {
			Maxterm maxterm = maxtermIterator.next();

			values[i] = maxterm.evaluate(arguments);
		}

		return Operators.AND.evaluate(values);
	}

	@Override
	public BooleanExpression expressViaOperators(FunctionallyCompleteOperatorsSet operatorsSet) {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override
	public BooleanExpression normalize() {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Maxterm maxterm : maxterms) {
			builder.append("(").append(maxterm).append(")");
		}

		return builder.toString();
	}
}
