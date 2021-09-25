package discretemath.bool.table;

public interface TruthTableRow {

	int argumentsCount();

	int functionsCount();

	boolean getArgument(int index);

	boolean getValue(int index);
}
