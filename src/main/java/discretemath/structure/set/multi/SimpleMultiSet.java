package discretemath.structure.set.multi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import discretemath.structure.set.Set;
import discretemath.structure.tuple.Pair;
import discretemath.structure.tuple.SimplePair;
import discretemath.utils.CacheValue;

public class SimpleMultiSet<T> extends AbstractMultiSet<T> {

	private final Map<T, Integer> countMap;

	private final int size;

	private final CacheValue<T> modeCache = new CacheValue<>();

	private SimpleMultiSet(Map<T, Integer> countMap) {
		this.countMap = countMap;
		this.size = countMap.values().stream().mapToInt(value -> value).sum();
	}

	public static <T> MultiSet<T> fromCountMap(Map<T, Integer> map) {
		return new SimpleMultiSet<>(Map.copyOf(map));
	}

	public static <T> MultiSet<T> fromCollection(Collection<T> collection) {
		Map<T, Integer> countMap = new HashMap<>();
		for (T item : collection) {
			countMap.merge(item, 1, (oldValue, newValue) -> oldValue + 1);
		}

		return new SimpleMultiSet<>(countMap);
	}

	@Override
	public T mode() {
		synchronized (modeCache) {
			if (modeCache.isSet()) {
				return modeCache.get();
			}

			Iterator<Map.Entry<T, Integer>> iterator = countMap.entrySet().iterator();
			Map.Entry<T, Integer> modeEntry = iterator.next();

			while (iterator.hasNext()) {
				Map.Entry<T, Integer> entry = iterator.next();
				if (entry.getValue() > modeEntry.getValue()) {
					modeEntry = entry;
				}
			}

			T mode = modeEntry.getKey();

			modeCache.set(mode);

			return mode;
		}
	}

	@Override
	public int itemCount(T item) {
		Integer count = countMap.get(item);
		if (count == null) {
			return 0;
		}

		return count;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(T item) {
		return countMap.getOrDefault(item, 0) != 0;
	}

	@Override
	public Set<T> unionWith(Set<T> set) {
		Map<T, Integer> resultCountMap = new HashMap<>(countMap);

		if (set instanceof MultiSet<T> multiSet) {
			for (T item : multiSet) {
				int count = multiSet.itemCount(item);
				resultCountMap.merge(item, count, Math::max);
			}
		} else {
			for (T item : set) {
				resultCountMap.merge(item, 1, Math::max);
			}
		}

		return new SimpleMultiSet<>(resultCountMap);
	}

	@Override
	public Set<T> intersectWith(Set<T> set) {
		Map<T, Integer> resultCountMap = new HashMap<>(countMap);

		if (set instanceof MultiSet<T> multiSet) {
			for (Map.Entry<T, Integer> entry : resultCountMap.entrySet()) {
				T item = entry.getKey();
				Integer count = entry.getValue();
				int countInOther = multiSet.itemCount(item);
				entry.setValue(Math.min(count, countInOther));
			}
		} else {
			Iterator<Map.Entry<T, Integer>> iterator = resultCountMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<T, Integer> entry = iterator.next();
				T item = entry.getKey();
				if (set.contains(item)) {
					entry.setValue(1);
				} else {
					iterator.remove();
				}
			}
		}

		return new SimpleMultiSet<>(resultCountMap);
	}

	@Override
	public Set<T> differenceWith(Set<T> set) {
		Map<T, Integer> resultCountMap = new HashMap<>(countMap);

		if (set instanceof MultiSet<T> multiSet) {
			for (T item : multiSet) {
				int count = multiSet.itemCount(item);
				resultCountMap.computeIfPresent(item, (key, value) -> Math.max(0, value - count));
			}
		} else {
			for (T item : set) {
				resultCountMap.computeIfPresent(item, (key, value) -> Math.max(0, value - 1));
			}
		}

		return new SimpleMultiSet<>(resultCountMap);
	}

	@Override
	public MultiSet<T> sumWith(MultiSet<T> set) {
		Map<T, Integer> resultCountMap = new HashMap<>(countMap);

		for (T item : set) {
			int count = set.itemCount(item);
			resultCountMap.merge(item, count, Integer::sum);
		}

		return new SimpleMultiSet<>(resultCountMap);
	}

	@Override
	public Set<T> symmetricDifferenceWith(Set<T> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public <P> Set<Pair<T, P>> cartesianProductWith(Set<P> set) {
		throw new UnsupportedOperationException("Not implemented yet"); // TODO
	}

	@Override
	public Iterator<T> iterator() {
		return countMap.keySet().iterator();
	}

	@Override
	public Iterator<Pair<T, Integer>> withCountIterator() {
		Iterator<Map.Entry<T, Integer>> iterator = countMap.entrySet().iterator();
		return new Iterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Pair<T, Integer> next() {
				Map.Entry<T, Integer> entry = iterator.next();
				return new SimplePair<>(entry.getKey(), entry.getValue());
			}
		};
	}


	@Override
	public String toString() {
		return countMap.toString();
	}
}
