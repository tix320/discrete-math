package discretemath.bool;

import java.util.Arrays;
import java.util.Objects;

public final class BooleanTable {

	private final boolean[][] table;

	private BooleanTable(boolean[][] table) {
		this.table = table;
	}

	public static BooleanTable from(int[][] table) {
		Objects.requireNonNull(table[0], "Boolean table's row must not be null");

		int firstRowLength = table[0].length;

		boolean[][] booleanTable = new boolean[table.length][firstRowLength];


		for (int i = 0, tableLength = table.length; i < tableLength; i++) {
			int[] row = table[i];
			Objects.requireNonNull(row, "Boolean table's row must not be null");
			if (row.length != firstRowLength) {
				throw new IllegalArgumentException("Boolean table rows must be same size");
			}

			for (int j = 0, rowLength = row.length; j < rowLength; j++) {
				int value = row[j];

				booleanTable[i][j] = value != 0;
			}
		}

		return new BooleanTable(booleanTable);
	}

	public static BooleanTable from(boolean[][] table) {
		Objects.requireNonNull(table[0], "Boolean table's row must not be null");

		int firstRowLength = table[0].length;

		for (boolean[] row : table) {
			Objects.requireNonNull(row, "Boolean table's row must not be null");
			if (row.length != firstRowLength) {
				throw new IllegalArgumentException("Boolean table rows must be same size");
			}
		}

		return new BooleanTable(table);
	}

	public static BooleanTable create(int n, int m) {
		boolean[][] table = new boolean[n][m];
		return new BooleanTable(table);
	}

	public int rowsCount() {
		return table.length;
	}

	public int columnsCount() {
		return table[0].length;
	}

	public boolean get(int i, int j) {
		return table[i][j];
	}

	public void set(int i, int j, boolean value) {
		table[i][j] = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BooleanTable that = (BooleanTable) o;
		return Arrays.deepEquals(table, that.table);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(table);
	}
}
