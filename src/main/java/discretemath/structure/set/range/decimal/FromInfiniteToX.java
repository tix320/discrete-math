package discretemath.structure.set.range.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import discretemath.common.NumberUtils;
import discretemath.structure.set.range.Range;

public final class FromInfiniteToX implements Range<BigDecimal> {

	private final BigDecimal to;

	private final boolean inclusive;

	public FromInfiniteToX(BigDecimal to, boolean inclusive) {
		this.to = to;
		this.inclusive = inclusive;
	}

	@Override
	public BigInteger size() {
		return NumberUtils.BIGINTEGER_POSITIVE_INFINITY;
	}

	@Override
	public boolean contains(BigDecimal item) {
		int compare = item.compareTo(to);
		if (compare == 0) {
			return inclusive;
		} else {
			return compare < 0;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FromInfiniteToX that = (FromInfiniteToX) o;
		return inclusive == that.inclusive && to.equals(that.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(to, inclusive);
	}
}