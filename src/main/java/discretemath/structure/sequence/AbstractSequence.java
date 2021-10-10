package discretemath.structure.sequence;

import discretemath.structure.exception.NotSummableException;
import discretemath.structure.exception.TermNotDefinedException;
import discretemath.structure.function.AbstractFunction;
import discretemath.structure.set.Set;
import discretemath.structure.set.Sets;

public abstract class AbstractSequence<T> extends AbstractFunction<Integer, T> implements Sequence<T> {

	private final Set<T> codomain;

	protected AbstractSequence(Set<T> codomain) {
		this.codomain = codomain;
	}

	@Override
	public final Set<Integer> domain() {
		return Sets.N_INT;
	}

	@Override
	public final Set<T> codomain() {
		return codomain;
	}

	@Override
	public T sum(int from, int to) throws NotSummableException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public final T term(int n) throws TermNotDefinedException {
		return apply(n);
	}

	@Override
	protected final T applyWithoutCheckInput(Integer input) {
		if (input < 0) {
			throw new IllegalArgumentException(String.valueOf(input));
		}

		return null;
	}

	protected abstract T getTerm(int n);
}
