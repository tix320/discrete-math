package discretemath.structure.set.multi;

import java.util.Iterator;

import discretemath.structure.set.Set;
import discretemath.structure.tuple.Pair;

public abstract class AbstractMultiSet<T> implements MultiSet<T> {

	@Override
	public final int hashCode() {
		return iteratorHashcode(withCountIterator());
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Set otherSet)) {
			return false;
		}

		if (otherSet.isInfinite()) {
			return false;
		} else {
			if (this.size() != otherSet.size()) {
				return false;
			}

			if (otherSet instanceof MultiSet multiSet) {
				Iterator<Pair<T, Integer>> iterator = multiSet.withCountIterator();

				while (iterator.hasNext()) {
					Pair<T, Integer> pair = iterator.next();
					T item = pair.first();
					int count = pair.second();

					if (this.itemCount(item) != count) {
						return false;
					}
				}
			} else {
				for (Object item : otherSet) {
					@SuppressWarnings("unchecked")
					T casted = (T) item;
					if (this.itemCount(casted) != 1) {
						return false;
					}
				}
			}

			return true;
		}
	}

	private static int iteratorHashcode(Iterator<?> iterator) {
		int result = 1;

		while (iterator.hasNext()) {
			Object element = iterator.next();

			result = 31 * result + (element == null ? 0 : element.hashCode());
		}

		return result;
	}
}
