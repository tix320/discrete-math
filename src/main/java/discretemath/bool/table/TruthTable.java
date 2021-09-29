package discretemath.bool.table;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import discretemath.bool.BooleanTable;
import discretemath.combination.BitCombinations;
import discretemath.common.Direction;

public class TruthTable implements Iterable<TruthTableRow> {

	private final int variablesCount;
	private final int functionsCount;

	private final BooleanTable table;

	private TruthTable(BooleanTable table, int variablesCount) {
		this.table = table;
		this.variablesCount = variablesCount;
		this.functionsCount = table.columnsCount() - variablesCount;
	}

	public static TruthTable forNDegree(int n) {
		return forNDegree(n, 0);
	}

	public static TruthTable forNDegree(int n, int spaceForFunctions) {
		int rowsCount = 1 << n;
		BooleanTable table = BooleanTable.create(rowsCount, n + spaceForFunctions);

		BitCombinations.nBitCombinations(n, table);

		return new TruthTable(table, n);
	}

	public static TruthTable forNDegreeWithAllFunctions(int n) {
		int rowsCount = 1 << n;
		int functionsCount = 1 << rowsCount;
		int columnsCount = n + functionsCount;
		BooleanTable table = BooleanTable.create(rowsCount, columnsCount);

		BitCombinations.nBitCombinations(n, table);
		BitCombinations.nBitCombinations(rowsCount, table, 0, n, Direction.FROM_LEFT_TO_RIGHT);

		return new TruthTable(table, n);
	}

	public void setFunctionValue(int functionIndex, int row, boolean value) {
		table.set(row, variablesCount + functionIndex, value);
	}

	public void setFunctionValue(int functionIndex, boolean[] values) {
		for (int i = 0; i < values.length; i++) {
			boolean value = values[i];
			table.set(i, variablesCount + functionIndex, value);
		}

	}

	public int getVariablesCount() {
		return variablesCount;
	}

	public int getFunctionsCount() {
		return functionsCount;
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
		return variablesCount == that.variablesCount && functionsCount == that.functionsCount && Objects.equals(table,
				that.table);
	}

	@Override
	public int hashCode() {
		return Objects.hash(variablesCount, functionsCount, table);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < table.rowsCount(); i++) {
			for (int j = 0; j < table.columnsCount(); j++) {
				boolean b = table.get(i, j);
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
			return rowIndex < table.rowsCount();
		}

		@Override
		public TruthTableRow next() {
			if (rowIndex >= table.rowsCount()) {
				throw new NoSuchElementException();
			}

			int currentRowIndex = rowIndex;
			rowIndex++;

			return new TruthTableRow() {
				@Override
				public int argumentsCount() {
					return variablesCount;
				}

				@Override
				public int functionsCount() {
					return functionsCount;
				}

				@Override
				public boolean getArgument(int index) {
					if (index > argumentsCount()) {
						throw new IndexOutOfBoundsException(index);
					}

					return table.get(currentRowIndex, index);
				}

				@Override
				public boolean getValue(int index) {
					if (index > functionsCount()) {
						throw new IndexOutOfBoundsException(index);
					}

					index = index + argumentsCount();


					return table.get(currentRowIndex, index);
				}
			};
		}
	}
}
