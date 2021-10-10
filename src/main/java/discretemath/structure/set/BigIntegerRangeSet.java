package discretemath.structure.set;

import java.math.BigInteger;
import java.util.Iterator;

import discretemath.common.NumberUtils;
import discretemath.structure.exception.TooLargeSetException;
import discretemath.structure.set.range.Range;
import discretemath.structure.tuple.Pair;

public class BigIntegerRangeSet extends AbstractSet<BigInteger> implements RangeSet<BigInteger> {

	private final Range<BigInteger> range;

	public BigIntegerRangeSet(Range<BigInteger> range) {
		this.range = range;
	}

	@Override
	public int size() {
		if (NumberUtils.GT(range.size(), Integer.MAX_VALUE)) {
			throw new TooLargeSetException();
		}

		return range.size().intValue();
	}

	@Override
	public boolean contains(BigInteger item) {
		return range.contains(item);
	}

	@Override
	public Set<BigInteger> unionWith(Set<BigInteger> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<BigInteger> intersectWith(Set<BigInteger> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<BigInteger> differenceWith(Set<BigInteger> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<BigInteger> symmetricDifferenceWith(Set<BigInteger> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public <P> Set<Pair<BigInteger, P>> cartesianProductWith(Set<P> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Iterator<BigInteger> iterator() {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Range<BigInteger> range() {
		return range;
	}
}
