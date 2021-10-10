package discretemath.structure.function;

import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.set.Set;

public final class CompositeFunction<T, M, R> extends AbstractFunction<T, R> {

	private final Function<T, M> g;

	private final Function<M, R> f;

	private CompositeFunction(Function<T, M> g, Function<M, R> f) {
		this.g = g;
		this.f = f;
	}

	public static <T, M, R> Function<T, R> compose(Function<T, M> function1, Function<M, R> function2) {
		return new CompositeFunction<>(function1, function2);
	}

	@Override
	public Set<T> domain() {
		return g.domain();
	}

	@Override
	public Set<R> codomain() {
		return f.codomain();
	}

	@Override
	public boolean isOnto() {
		synchronized (ontoCache) {
			if (ontoCache.isSet()) {
				return ontoCache.get();
			}

			if (g.isOnto() && f.isOnto()) {
				ontoCache.set(true);
				return true;
			}

			if (!f.isOnto()) {
				ontoCache.set(false);
				return false;
			}

			return super.isOnto();
		}
	}

	@Override
	public boolean isOneToOne() {
		synchronized (oneToOneCache) {
			if (oneToOneCache.isSet()) {
				return oneToOneCache.get();
			}

			if (g.isOneToOne() && f.isOneToOne()) {
				oneToOneCache.set(true);
				return true;
			}

			if (!g.isOneToOne()) {
				oneToOneCache.set(false);
				return false;
			}

			return super.isOneToOne();
		}
	}

	@Override
	public Function<R, T> getInverse() throws NotInvertibleException {
		if (!isOnto() || !isOneToOne()) {
			throw new NotInvertibleException();
		}

		Function<R, M> fInverse = f.getInverse();
		Function<M, T> gInverse = g.getInverse();

		return new CompositeFunction<>(fInverse, gInverse);
	}

	@Override
	protected R applyWithoutCheckInput(T input) {
		M intermediateValue = g.apply(input);

		if (intermediateValue == null) {
			throw new IllegalStateException("Intermediate value for input [%s] is not defined".formatted(input));
		}

		R result = f.apply(intermediateValue);

		if (result == null) {
			throw new IllegalStateException("Result for input [%s] is not defined".formatted(input));
		}

		if (!codomain().contains(result)) {
			throw new IllegalStateException("Output [%s] does not exist in the codomain".formatted(result));
		}

		return result;
	}
}
