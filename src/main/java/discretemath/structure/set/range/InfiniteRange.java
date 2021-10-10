package discretemath.structure.set.range;

import java.math.BigInteger;

import discretemath.common.NumberUtils;

public final class InfiniteRange<T> implements Range<T> {

	private static final InfiniteRange<?> INFINITE_RANGE = new InfiniteRange<>();

	private InfiniteRange() {
	}

	public static <T> InfiniteRange<T> get() {
		//noinspection unchecked
		return (InfiniteRange<T>) INFINITE_RANGE;
	}

	@Override
	public BigInteger size() {
		return NumberUtils.BIGINTEGER_POSITIVE_INFINITY;
	}

	@Override
	public boolean contains(T item) {
		return true;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == InfiniteRange.class;
	}
}