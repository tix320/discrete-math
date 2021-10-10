package discretemath.structure.sequence;

import discretemath.structure.exception.NotSummableException;
import discretemath.structure.exception.TermNotDefinedException;
import discretemath.structure.function.Function;

public interface Sequence<T> extends Function<Integer, T> {

	T term(int n) throws TermNotDefinedException;

	T sum(int from, int to) throws NotSummableException;
}
