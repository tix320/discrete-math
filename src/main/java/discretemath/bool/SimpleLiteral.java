package discretemath.bool;

public record SimpleLiteral(char symbol, boolean isPositive) implements Literal {

	@Override
	public String toString() {
		return isPositive ? String.valueOf(symbol) : symbol + "′";
	}
}
