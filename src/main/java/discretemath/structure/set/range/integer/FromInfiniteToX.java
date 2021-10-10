package discretemath.structure.set.range.integer;

import java.math.BigInteger;
import java.util.Objects;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.Range;

public final class FromInfiniteToX implements Range<BigInteger> {

	private final BigInteger to;

	public FromInfiniteToX(BigInteger to) {
		this.to = to;
	}

	@Override
	public BigInteger size() {
		return NumberUtils.BIGINTEGER_POSITIVE_INFINITY;
	}

	@Override
	public boolean contains(BigInteger item) {
		return item.compareTo(to) <= 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FromInfiniteToX that = (FromInfiniteToX) o;
		return to.equals(that.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(to);
	}
}