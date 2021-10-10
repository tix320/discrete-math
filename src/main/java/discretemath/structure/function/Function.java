package discretemath.structure.function;

import discretemath.structure.exception.InputNotFromDomainException;
import discretemath.structure.exception.NotInvertibleException;
import discretemath.structure.set.Set;
import discretemath.structure.set.multi.MultiSet;
import discretemath.structure.tuple.Pair;

public interface Function<T, R> {

	Set<T> domain();

	Set<R> codomain();

	Set<R> range();

	MultiSet<R> multiSetRange();

	boolean isOnto();

	boolean isOneToOne();

	Function<R, T> getInverse() throws NotInvertibleException;

	Set<Pair<T, R>> graph();

	/**
	 * @throws InputNotFromDomainException when given input not from function domain
	 * @throws IllegalStateException       when result for given input is not defined
	 * @throws IllegalStateException       when output does not exist in the codomain
	 */
	R apply(T input) throws InputNotFromDomainException, IllegalStateException;
}
