package discretemath.structure.set;

import java.math.BigDecimal;
import java.util.Iterator;

import discretemath.structure.exception.UncountableSetException;
import discretemath.structure.set.range.Range;
import discretemath.structure.tuple.Pair;

public class BigDecimalRangeSet extends AbstractSet<BigDecimal> implements RangeSet<BigDecimal> {

	private final Range<BigDecimal> range;

	public BigDecimalRangeSet(Range<BigDecimal> range) {
		this.range = range;
	}

	@Override
	public int size() {
		return -1;
	}

	@Override
	public boolean contains(BigDecimal item) {
		return range.contains(item);
	}

	@Override
	public Set<BigDecimal> unionWith(Set<BigDecimal> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<BigDecimal> intersectWith(Set<BigDecimal> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<BigDecimal> differenceWith(Set<BigDecimal> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<BigDecimal> symmetricDifferenceWith(Set<BigDecimal> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public <P> Set<Pair<BigDecimal, P>> cartesianProductWith(Set<P> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Iterator<BigDecimal> iterator() {
		throw new UncountableSetException();
	}

	@Override
	public Range<BigDecimal> range() {
		return range;
	}
}
