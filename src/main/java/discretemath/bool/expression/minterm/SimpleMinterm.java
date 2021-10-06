package discretemath.bool.expression.minterm;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class SimpleMinterm implements Minterm {

	private final List<Literal> literals;

	public SimpleMinterm(Set<Literal> literals) {
		this.literals = literals.stream().toList();
	}

	@Override
	public List<Literal> literals() {
		return literals;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SimpleMinterm that = (SimpleMinterm) o;
		return Objects.equals(literals, that.literals);
	}

	@Override
	public int hashCode() {
		return Objects.hash(literals);
	}

	@Override
	public String toString() {
		return literals.stream().map(Object::toString).collect(Collectors.joining(""));
	}
}
