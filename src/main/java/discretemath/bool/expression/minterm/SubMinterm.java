package discretemath.bool.expression.minterm;

import java.util.List;

public final class SubMinterm implements Minterm {

	private final List<Literal> subList;

	public SubMinterm(Minterm minterm, int fromIndex, int toIndex) {
		this.subList = minterm.literals().subList(fromIndex, toIndex);
	}

	@Override
	public List<Literal> literals() {
		return subList;
	}
}
