package discretemath.bool.table;

import java.util.Iterator;
import java.util.NoSuchElementException;

import discretemath.combination.BitCombinations;
import discretemath.common.Direction;

public class TruthTable implements Iterable<TruthTableRow> {

	private final int variablesCount;
	private final int functionsCount;

	private final boolean[][] table;

	private TruthTable(int variablesCount, boolean[][] table) {
		this.variablesCount = variablesCount;
		this.functionsCount = table[0].length - variablesCount;
		this.table = table;
	}

	public static TruthTable of(int variablesCount, boolean[][] table) {
		return new TruthTable(variablesCount, table);
	}

	public static TruthTable forNDegree(int n) {
		int rowsCount = 1 << n;
		boolean[][] table = new boolean[rowsCount][n];

		BitCombinations.nBitCombinations(n, table);

		return new TruthTable(n, table);
	}

	public static TruthTable forNDegreeWithAllFunctions(int n) {
		int rowsCount = 1 << n;
		int functionsCount = 1 << rowsCount;
		int columnsCount = n + functionsCount;
		boolean[][] table = new boolean[rowsCount][columnsCount];

		BitCombinations.nBitCombinations(n, table);
		BitCombinations.nBitCombinations(rowsCount, table, 0, n, Direction.FROM_LEFT_TO_RIGHT);

		return new TruthTable(n, table);
	}

	public void print() {
		for (boolean[] row : table) {
			for (boolean b : row) {
				System.out.print((b ? 1 : 0) + "\t");
			}
			System.out.println();
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

	private final class Iter implements Iterator<TruthTableRow> {

		private int rowIndex = 0;

		@Override
		public boolean hasNext() {
			return rowIndex < table.length;
		}

		@Override
		public TruthTableRow next() {
			if (rowIndex >= table.length) {
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

					return table[currentRowIndex][index];
				}

				@Override
				public boolean getValue(int index) {
					if (index > functionsCount()) {
						throw new IndexOutOfBoundsException(index);
					}

					index = index + argumentsCount();


					return table[currentRowIndex][index];
				}
			};
		}
	}
}
