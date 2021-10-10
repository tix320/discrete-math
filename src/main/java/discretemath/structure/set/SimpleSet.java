package discretemath.structure.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

import discretemath.structure.tuple.Pair;
import discretemath.structure.tuple.SimplePair;

public class SimpleSet<T> extends AbstractSet<T> {

	private final java.util.Set<T> set;

	private SimpleSet(java.util.Set<T> set) {
		this.set = set;
	}

	public static <T> Set<T> from(java.util.Set<T> set) {
		return new SimpleSet<>(java.util.Set.copyOf(set));
	}

	@SafeVarargs
	public static <T> Set<T> of(T... items) {
		return new SimpleSet<>(Arrays.stream(items).collect(Collectors.toUnmodifiableSet()));
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean contains(T item) {
		return set.contains(item);
	}

	@Override
	public Set<T> unionWith(Set<T> set) {
		java.util.Set<T> newSet = new HashSet<>(this.set);

		if (set instanceof SimpleSet<T> simpleSet) {
			newSet.addAll(simpleSet.set);
		} else {
			set.forEach(newSet::add);
		}

		return new SimpleSet<>(newSet);
	}

	@Override
	public Set<T> intersectWith(Set<T> set) {
		java.util.Set<T> newSet = new HashSet<>(this.set);

		if (set instanceof SimpleSet<T> simpleSet) {
			newSet.retainAll(simpleSet.set);
		} else {
			newSet.removeIf(t -> !set.contains(t));
		}

		return new SimpleSet<>(newSet);
	}

	@Override
	public Set<T> differenceWith(Set<T> set) {
		java.util.Set<T> newSet = new HashSet<>(this.set);

		if (set instanceof SimpleSet<T> simpleSet) {
			newSet.removeAll(simpleSet.set);
		} else {
			newSet.removeIf(set::contains);
		}

		return new SimpleSet<>(newSet);
	}

	@Override
	public Set<T> symmetricDifferenceWith(Set<T> set) {
		return unionWith(set).differenceWith(intersectWith(set));
	}

	@Override
	public <P> Set<Pair<T, P>> cartesianProductWith(Set<P> set) {
		java.util.Set<Pair<T, P>> pairsSet = new HashSet<>(size() * set.size());

		for (T firstItem : this.set) {
			for (P secondItem : set) {
				Pair<T, P> pair = new SimplePair<>(firstItem, secondItem);
				pairsSet.add(pair);
			}
		}

		return new SimpleSet<>(pairsSet);
	}

	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public String toString() {
		return set.toString();
	}
}
