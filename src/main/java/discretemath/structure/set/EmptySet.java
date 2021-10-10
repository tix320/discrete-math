package discretemath.structure.set;

import java.util.Collections;
import java.util.Iterator;

import discretemath.structure.tuple.Pair;

public class EmptySet<T> extends AbstractSet<T> {

	private static final EmptySet<?> INSTANCE = new EmptySet<>();

	private EmptySet() {
	}

	public static <T> EmptySet<T> get() {
		//noinspection unchecked
		return (EmptySet<T>) INSTANCE;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean contains(T item) {
		return false;
	}

	@Override
	public Set<T> unionWith(Set<T> set) {
		return set;
	}

	@Override
	public Set<T> intersectWith(Set<T> set) {
		return this;
	}

	@Override
	public Set<T> differenceWith(Set<T> set) {
		return this;
	}

	@Override
	public Set<T> symmetricDifferenceWith(Set<T> set) {
		return set;
	}

	@Override
	public <P> Set<Pair<T, P>> cartesianProductWith(Set<P> set) {
		return get();
	}

	@Override
	public Iterator<T> iterator() {
		return Collections.emptyIterator();
	}
}
