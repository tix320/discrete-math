package discretemath.structure.set;

import java.util.Iterator;

import discretemath.common.NumberUtils;
import discretemath.structure.exception.TooLargeSetException;
import discretemath.structure.set.range.Range;
import discretemath.structure.tuple.Pair;

public class IntRangeSet extends AbstractSet<Integer> implements RangeSet<Integer> {

	private final Range<Integer> range;

	public IntRangeSet(Range<Integer> range) {
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
	public boolean contains(Integer item) {
		return range.contains(item);
	}

	@Override
	public Set<Integer> unionWith(Set<Integer> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<Integer> intersectWith(Set<Integer> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<Integer> differenceWith(Set<Integer> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Set<Integer> symmetricDifferenceWith(Set<Integer> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public <P> Set<Pair<Integer, P>> cartesianProductWith(Set<P> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Iterator<Integer> iterator() {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Range<Integer> range() {
		return range;
	}
}
