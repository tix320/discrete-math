package discretemath.structure.matrix;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class ArrayMatrix<T> implements Matrix<T> {

	private final T[][] table;

	protected ArrayMatrix(Object[][] table) {
		//noinspection unchecked
		this.table = (T[][]) table;
	}

	public static <T> ArrayMatrix<T> from(T[][] table) {
		Objects.requireNonNull(table[0], "Matrix table's row must not be null");

		int firstRowLength = table[0].length;

		for (T[] row : table) {
			Objects.requireNonNull(row, "Matrix table's row must not be null");
			if (row.length != firstRowLength) {
				throw new IllegalArgumentException("Matrix table's rows must be same size");
			}
		}

		return new ArrayMatrix<>(table);
	}

	public static <T> ArrayMatrix<T> create(Class<T> type, int n, int m) {
		@SuppressWarnings("unchecked")
		T[][] table = (T[][]) Array.newInstance(type, n, m);
		return new ArrayMatrix<>(table);
	}

	@Override
	public int rowsCount() {
		return table.length;
	}

	@Override
	public int columnsCount() {
		return table[0].length;
	}

	@Override
	public T get(int i, int j) {
		return table[i][j];
	}

	@Override
	public void set(int i, int j, T value) {
		table[i][j] = value;
	}

	@Override
	public Matrix<T> add(Matrix<T> matrix) {
		//TODO
		return null;
	}

	@Override
	public Matrix<T> multiply(Matrix<T> matrix) {
		//TODO
		return null;
	}

	@Override
	public Matrix<T> transpose() {
		//TODO
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ArrayMatrix<?> arrayMatrix = (ArrayMatrix<?>) o;
		return Arrays.deepEquals(table, arrayMatrix.table);
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(table);
	}
}
