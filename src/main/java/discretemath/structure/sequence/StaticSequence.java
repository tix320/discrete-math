package discretemath.structure.sequence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.exception.TermNotDefinedException;
import discretemath.structure.function.Function;
import discretemath.structure.function.StaticFunction;
import discretemath.structure.set.Set;
import discretemath.structure.set.SimpleSet;
import discretemath.structure.set.multi.MultiSet;
import discretemath.structure.set.multi.SimpleMultiSet;
import discretemath.structure.tuple.Pair;
import discretemath.structure.tuple.SimplePair;

public final class StaticSequence<T> extends AbstractSequence<T> {

	private final List<T> terms;

	public StaticSequence(List<T> terms) {
		super(SimpleSet.from(new HashSet<>(terms)));
		this.terms = List.copyOf(terms);
	}

	@Override
	public Set<T> range() {
		return SimpleSet.from(new HashSet<>(terms));
	}

	@Override
	public MultiSet<T> multiSetRange() {
		return SimpleMultiSet.fromCollection(terms);
	}

	@Override
	public Set<Pair<Integer, T>> graph() {
		java.util.Set<Pair<Integer, T>> pairSet = new HashSet<>();
		for (int i = 0; i < terms.size(); i++) {
			pairSet.add(new SimplePair<>(i, terms.get(i)));
		}

		return SimpleSet.from(pairSet);
	}

	@Override
	public boolean isOnto() {
		return true;
	}

	@Override
	public Function<T, Integer> getInverse() throws NotInvertibleException {
		if (!isOnto() || !isOneToOne()) {
			throw new NotInvertibleException();
		}

		Map<T, Integer> reverseMapping = new HashMap<>();

		for (int i = 0; i < terms.size(); i++) {
			T term = terms.get(i);
			reverseMapping.put(term, i);
		}

		return StaticFunction.from(reverseMapping);
	}

	@Override
	protected T getTerm(int n) {
		try {
			return terms.get(n);
		} catch (IndexOutOfBoundsException ignored) {
			throw new TermNotDefinedException(String.valueOf(n));
		}
	}
}
