package discretemath.structure.set;

import java.util.Iterator;

import discretemath.structure.exception.TooLargeSetException;
import discretemath.structure.exception.UncountableSetException;
import discretemath.structure.tuple.Pair;

/**
 * If the set is infinite, then it must also implement {@link RangeSet}
 */
public interface Set<T> extends Iterable<T> {

	/**
	 * @return -1 when infinity
	 * @throws TooLargeSetException when size is greater than {@link Integer#MAX_VALUE}
	 */
	int size();

	default boolean isInfinite() {
		return size() == -1;
	}

	boolean contains(T item);

	Set<T> unionWith(Set<T> set);

	Set<T> intersectWith(Set<T> set);

	Set<T> differenceWith(Set<T> set);

	Set<T> symmetricDifferenceWith(Set<T> set);

	<P> Set<Pair<T, P>> cartesianProductWith(Set<P> set);

	@Override
	Iterator<T> iterator() throws UncountableSetException;
}
