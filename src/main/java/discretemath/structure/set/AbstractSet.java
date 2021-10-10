package discretemath.structure.set;

public abstract class AbstractSet<T> implements Set<T> {

	@Override
	public final int hashCode() {
		if (isInfinite()) {
			RangeSet<?> rangeSet = (RangeSet<?>) this;
			return rangeSet.hashCode();
		} else {
			return iterableHashcode(this);
		}
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;

		if (!(o instanceof Set otherSet)) {
			return false;
		}

		boolean thisIsInfinite = this.isInfinite();
		boolean otherIsInfinite = otherSet.isInfinite();

		if (thisIsInfinite != otherIsInfinite) { // if one of them is infinite, but second not
			return false;
		}

		if (thisIsInfinite) { // both infinite
			RangeSet<?> thisRangeSet = (RangeSet<?>) this;
			RangeSet<?> otherRangeSet = (RangeSet<?>) this;

			return thisRangeSet.range().equals(otherRangeSet.range());
		} else {
			if (this.size() != otherSet.size()) {
				return false;
			}

			for (Object item : otherSet) {
				@SuppressWarnings("unchecked")
				T casted = (T) item;
				if (!this.contains(casted)) {
					return false;
				}
			}


			return true;
		}
	}

	private static int iterableHashcode(Iterable<?> iterable) {
		int result = 1;

		for (Object element : iterable)
			result = 31 * result + (element == null ? 0 : element.hashCode());

		return result;
	}
}
