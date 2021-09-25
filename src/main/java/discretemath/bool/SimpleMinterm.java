package discretemath.bool;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public record SimpleMinterm(LinkedHashSet<Literal> literals) implements Minterm {

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
