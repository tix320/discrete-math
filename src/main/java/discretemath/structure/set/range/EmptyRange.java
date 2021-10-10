package discretemath.structure.set.range;

import java.math.BigInteger;

public final class EmptyRange<T> implements Range<T> {

	private static final EmptyRange<?> EMPTY_RANGE = new EmptyRange<>();

	private EmptyRange() {
	}

	public static <T> EmptyRange<T> get() {
		//noinspection unchecked
		return (EmptyRange<T>) EMPTY_RANGE;
	}

	@Override
	public BigInteger size() {
		return BigInteger.ZERO;
	}

	@Override
	public boolean contains(T item) {
		return false;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == EmptyRange.class;
	}
}