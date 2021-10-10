package discretemath.structure.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.set.Set;
import discretemath.structure.set.SimpleSet;
import discretemath.structure.set.multi.MultiSet;
import discretemath.structure.set.multi.SimpleMultiSet;
import discretemath.structure.tuple.Pair;
import discretemath.structure.tuple.SimplePair;

public class StaticFunction<T, R> extends AbstractFunction<T, R> {

	private final Map<T, R> mapping;

	private final Set<T> domain;

	private final Set<R> codomain;

	private StaticFunction(Map<T, R> mapping) {
		this.mapping = mapping;
		this.domain = SimpleSet.from(mapping.keySet());
		this.codomain = SimpleSet.from(new HashSet<>(mapping.values()));
	}

	public static <T, R> StaticFunction<T, R> from(Map<T, R> mapping) {
		return new StaticFunction<>(Map.copyOf(mapping));
	}

	@Override
	public Set<T> domain() {
		return domain;
	}

	@Override
	public Set<R> codomain() {
		return codomain;
	}

	@Override
	public Set<R> range() {
		return codomain();
	}

	@Override
	public MultiSet<R> multiSetRange() {
		Map<R, Integer> rangeSet = new HashMap<>();
		mapping.values().forEach(value -> rangeSet.merge(value, 1, (oldValue, newValue) -> oldValue + 1));

		return SimpleMultiSet.fromCountMap(rangeSet);
	}

	@Override
	public Set<Pair<T, R>> graph() {
		java.util.Set<Pair<T, R>> pairSet = mapping.entrySet()
				.stream()
				.map(entry -> new SimplePair<>(entry.getKey(), entry.getValue()))
				.collect(Collectors.toSet());

		return SimpleSet.from(pairSet);
	}

	@Override
	public boolean isOnto() {
		return true;
	}

	@Override
	public Function<R, T> getInverse() throws NotInvertibleException {
		if (!isOnto() || !isOneToOne()) {
			throw new NotInvertibleException();
		}

		HashMap<R, T> reverseMapping = new HashMap<>();

		for (Map.Entry<T, R> entry : mapping.entrySet()) {
			reverseMapping.put(entry.getValue(), entry.getKey());
		}

		return new StaticFunction<>(reverseMapping);
	}

	@Override
	protected R applyWithoutCheckInput(T input) {
		R result = mapping.get(input);

		if (result == null) {
			throw new IllegalStateException("Result for input [%s] is not defined".formatted(input));
		}

		return result;
	}


}
