package discretemath.structure.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import discretemath.structure.exception.DomainIsInfiniteException;
import discretemath.structure.exception.InputNotFromDomainException;
import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.set.Set;
import discretemath.structure.set.SimpleSet;
import discretemath.structure.set.multi.MultiSet;
import discretemath.structure.set.multi.SimpleMultiSet;
import discretemath.structure.tuple.Pair;
import discretemath.structure.tuple.SimplePair;
import discretemath.utils.CacheValue;

public abstract class AbstractFunction<T, R> implements Function<T, R> {

	protected final CacheValue<Boolean> ontoCache = new CacheValue<>();
	protected final CacheValue<Boolean> oneToOneCache = new CacheValue<>();

	@Override
	public final R apply(T input) throws InputNotFromDomainException, IllegalStateException {
		if (!domain().contains(input)) {
			throw new InputNotFromDomainException();
		}

		return applyWithoutCheckInput(input);
	}

	@Override
	public Set<R> range() {
		if (domain().isInfinite()) {
			throw new DomainIsInfiniteException();
		}

		java.util.Set<R> resultSet = new HashSet<>();

		for (T item : domain()) {
			R result = applyWithoutCheckInput(item);
			resultSet.add(result);
		}

		return SimpleSet.from(resultSet);
	}

	@Override
	public MultiSet<R> multiSetRange() {
		if (domain().isInfinite()) {
			throw new DomainIsInfiniteException();
		}

		Map<R, Integer> rangeSet = new HashMap<>();

		for (T item : domain()) {
			R result = applyWithoutCheckInput(item);

			rangeSet.merge(result, 1, (oldValue, newValue) -> oldValue + 1);
		}

		return SimpleMultiSet.fromCountMap(rangeSet);
	}

	@Override
	public Set<Pair<T, R>> graph() {
		if (domain().isInfinite()) {
			throw new DomainIsInfiniteException();
		}

		java.util.Set<Pair<T, R>> resultSet = new HashSet<>();

		for (T item : domain()) {
			R result = applyWithoutCheckInput(item);
			resultSet.add(new SimplePair<>(item, result));
		}

		return SimpleSet.from(resultSet);
	}

	@Override
	public boolean isOnto() {
		synchronized (ontoCache) {
			if (ontoCache.isSet()) {
				return ontoCache.get();
			}

			boolean isOnto = range().size() == codomain().size();

			ontoCache.set(isOnto);

			return isOnto;
		}
	}

	@Override
	public boolean isOneToOne() {
		synchronized (oneToOneCache) {
			if (oneToOneCache.isSet()) {
				return oneToOneCache.get();
			}

			MultiSet<R> range = multiSetRange();
			R mode = range.mode();
			int modeCount = range.itemCount(mode);

			boolean isOneToOne = modeCount == 1; // no duplicates

			oneToOneCache.set(isOneToOne);

			return isOneToOne;
		}
	}

	@Override
	public Function<R, T> getInverse() throws NotInvertibleException {
		if (!isOnto() || !isOneToOne()) {
			throw new NotInvertibleException();
		}

		if (domain().isInfinite()) {
			throw new DomainIsInfiniteException();
		}

		Map<R, T> reverseMapping = new HashMap<>();

		for (T input : domain()) {
			R result = applyWithoutCheckInput(input);

			reverseMapping.put(result, input);
		}

		return StaticFunction.from(reverseMapping);
	}

	protected abstract R applyWithoutCheckInput(T input);
}
