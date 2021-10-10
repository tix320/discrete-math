package discretemath.structure.sequence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import discretemath.structure.exception.InputNotFromDomainException;
import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.exception.NotSummableException;
import discretemath.structure.exception.TermNotDefinedException;
import discretemath.structure.function.Function;
import discretemath.structure.set.Set;
import discretemath.structure.set.multi.MultiSet;
import discretemath.structure.tuple.Pair;

public class CacheableSequence<T> implements Sequence<T> { // TODO delegate all calls

	private final Sequence<T> originSequence;

	private final Map<Integer, T> cache;

	public CacheableSequence(Sequence<T> originSequence) {
		this.originSequence = originSequence;
		this.cache = new ConcurrentHashMap<>();
	}

	@Override
	public T term(int n) throws TermNotDefinedException {
		return cache.computeIfAbsent(n, originSequence::term);
	}

	@Override
	public T sum(int from, int to) throws NotSummableException {
		return null;
	}

	@Override
	public Set<Integer> domain() {
		return null;
	}

	@Override
	public Set<T> codomain() {
		return null;
	}

	@Override
	public Set<T> range() {
		return null;
	}

	@Override
	public MultiSet<T> multiSetRange() {
		return null;
	}

	@Override
	public boolean isOnto() {
		return false;
	}

	@Override
	public boolean isOneToOne() {
		return false;
	}

	@Override
	public Function<T, Integer> getInverse() throws NotInvertibleException {
		return null;
	}

	@Override
	public Set<Pair<Integer, T>> graph() {
		return null;
	}

	@Override
	public T apply(Integer input) throws InputNotFromDomainException, IllegalStateException {
		return null;
	}
}
