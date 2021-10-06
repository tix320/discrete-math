package discretemath;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class Matrix<T> {

	private final T[][] table;

	protected Matrix(Object[][] table) {
		//noinspection unchecked
		this.table = (T[][]) table;
	}

	public static <T> Matrix<T> from(T[][] table) {
		Objects.requireNonNull(table[0], "Boolean table's row must not be null");

		int firstRowLength = table[0].length;

		for (T[] row : table) {
			Objects.requireNonNull(row, "Boolean table's row must not be null");
			if (row.length != firstRowLength) {
				throw new IllegalArgumentException("Boolean table rows must be same size");
			}
		}

		return new Matrix<>(table);
	}

	public static <T> Matrix<T> create(Class<T> type, int n, int m) {
		@SuppressWarnings("unchecked")
		T[][] table = (T[][]) Array.newInstance(type, n, m);
		return new Matrix<>(table);
	}

	public int columnsCount() {
		return table[0].length;
	}

	public T get(int i, int j) {
		return table[i][j];
	}

	public void set(int i, int j, T value) {
		table[i][j] = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Matrix<?> matrix = (Matrix<?>) o;
		return Arrays.deepEquals(table, matrix.table);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(table);
	}
}
