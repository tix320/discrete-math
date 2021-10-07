package discretemath.bool.expression.atomic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import discretemath.common.VariableSymbols;

public class BooleanVariables {

	public static final BooleanVariable X = new BooleanVariable('x');
	public static final BooleanVariable Y = new BooleanVariable('y');
	public static final BooleanVariable Z = new BooleanVariable('z');

	public static List<BooleanVariable> getVariables(int variablesCount) {
		return StreamSupport.stream(VariableSymbols.getIterator(variablesCount).spliterator(), false)
				.map(BooleanVariable::new)
				.collect(Collectors.toList());
	}
}
