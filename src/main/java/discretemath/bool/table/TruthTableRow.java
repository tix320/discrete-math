package discretemath.bool.table;

import discretemath.common.BitString;

public interface TruthTableRow {

	int index();

	int argumentsCount();

	int functionsCount();

	BitString getArgumentsBitString();

	boolean getArgument(int index);

	FunctionValue getFunctionValue(int index);
}
