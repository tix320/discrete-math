package discretemath.bool.table;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import discretemath.Matrix;
import discretemath.bool.BooleanTable;
import discretemath.bool.expression.exception.InvalidArgumentCountException;
import discretemath.combination.BitCombinations;
import discretemath.common.BitString;

public class TruthTable implements Iterable<TruthTableRow> {

	private final BooleanTable inputTable;

	private final Matrix<FunctionValue> functionValuesTable;

	private TruthTable(BooleanTable inputTable, Matrix<FunctionValue> functionValuesTable) {
		this.inputTable = inputTable;
		this.functionValuesTable = functionValuesTable;
	}

	public static TruthTable forNDegree(int n) {
		return forNDegree(n, 0);
	}

	public static TruthTable forNDegree(int n, int spaceForFunctions) {
		int rowsCount = 1 << n;
		BooleanTable table = BooleanTable.create(rowsCount, n);
		Matrix<FunctionValue> functionValueTable = Matrix.create(FunctionValue.class, rowsCount, spaceForFunctions);

		BitCombinations.nBitCombinations(n, table);

		return new TruthTable(table, functionValueTable);
	}

	public static TruthTable forNDegreeWithAllFunctions(int n) {
		int rowsCount = 1 << n;
		int functionsCount = 1 << rowsCount;
		BooleanTable table = BooleanTable.create(rowsCount, n);
		Matrix<FunctionValue> functionValueTable = Matrix.create(FunctionValue.class, rowsCount, functionsCount);

		BitCombinations.nBitCombinations(n, table);
		//BitCombinations.nBitCombinations(rowsCount, functionValueTable, 0, n, Direction.FROM_LEFT_TO_RIGHT); //TODO

		return new TruthTable(table, functionValueTable);
	}

	public void setFunctionValue(int functionIndex, int row, FunctionValue value) {
		functionValuesTable.set(row, functionIndex, value);
	}

	public void setFunctionValue(int functionIndex, FunctionValue[] values) {
		int functionValuesCount = 1 << getVariablesCount();
		InvalidArgumentCountException.checkExact(functionValuesCount, values.length);

		for (int i = 0; i < values.length; i++) {
			FunctionValue value = values[i];
			functionValuesTable.set(i, functionIndex, value);
		}

	}

	public int getVariablesCount() {
		return inputTable.columnsCount();
	}

	public int getFunctionsCount() {
		return functionValuesTable.columnsCount();
	}

	@Override
	public Iterator<TruthTableRow> iterator() {
		return new Iter();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TruthTable that = (TruthTable) o;
		return inputTable.equals(that.inputTable) && functionValuesTable.equals(that.functionValuesTable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputTable, functionValuesTable);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < inputTable.rowsCount(); i++) {
			for (int j = 0; j < inputTable.columnsCount(); j++) {
				boolean b = inputTable.get(i, j);
				builder.append(b ? 1 : 0).append("\t");
			}
			builder.append("\n");
		}

		return builder.toString();
	}

	private final class Iter implements Iterator<TruthTableRow> {

		private int rowIndex = 0;

		@Override
		public boolean hasNext() {
			return rowIndex < inputTable.rowsCount();
		}

		@Override
		public TruthTableRow next() {
			if (rowIndex >= inputTable.rowsCount()) {
				throw new NoSuchElementException();
			}

			int currentRowIndex = rowIndex;
			rowIndex++;

			return new TruthTableRow() {
				@Override
				public int index() {
					return currentRowIndex;
				}

				@Override
				public int argumentsCount() {
					return getVariablesCount();
				}

				@Override
				public int functionsCount() {
					return getFunctionsCount();
				}

				@Override
				public BitString getArgumentsBitString() {
					BitString bitString = BitString.forN(inputTable.columnsCount());

					for (int j = 0; j < inputTable.columnsCount(); j++) {
						bitString.set(j, inputTable.get(currentRowIndex, j));
					}

					return bitString;
				}

				@Override
				public boolean getArgument(int index) {
					if (index > argumentsCount()) {
						throw new IndexOutOfBoundsException(index);
					}

					return inputTable.get(currentRowIndex, index);
				}

				@Override
				public FunctionValue getFunctionValue(int index) {
					if (index > functionsCount()) {
						throw new IndexOutOfBoundsException(index);
					}

					return functionValuesTable.get(currentRowIndex, index);
				}
			};
		}
	}
}
