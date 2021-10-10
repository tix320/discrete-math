package discretemath.structure.function;

import discretemath.structure.set.Set;

public abstract class CalculableFunction<T, R> extends AbstractFunction<T, R> {

	private final Set<T> domain;

	private final Set<R> codomain;

	public CalculableFunction(Set<T> domain, Set<R> codomain) {
		this.domain = domain;
		this.codomain = codomain;
	}

	@Override
	public final Set<T> domain() {
		return domain;
	}

	@Override
	public final Set<R> codomain() {
		return codomain;
	}

	@Override
	protected final R applyWithoutCheckInput(T input) {
		R result = calculate(input);

		if (result == null) {
			throw new IllegalStateException("Result for input [%s] is not defined".formatted(input));
		}

		if (!codomain().contains(result)) {
			throw new IllegalStateException("Output [%s] does not exist in the codomain".formatted(result));
		}

		return result;
	}

	protected abstract R calculate(T input);
}
