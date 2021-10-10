package discretemath.structure.matrix;

public interface Matrix<T> {

	int rowsCount();

	int columnsCount();

	T get(int i, int j);

	void set(int i, int j, T value);

	Matrix<T> add(Matrix<T> matrix);

	Matrix<T> multiply(Matrix<T> matrix);

	Matrix<T> transpose();

}
