package discretemath.structure.sequence;

import discretemath.structure.set.Set;

public abstract class RecurrentSequence<T> extends AbstractSequence<T> {

	public RecurrentSequence(Set<T> codomain) {
		super(codomain);
	}

	@Override
	protected final T getTerm(int n) {
		return getNextRecurrentValue(n);
	}

	protected abstract T getNextRecurrentValue(int n);
}
