package discretemath.common

object VariableSymbols extends Iterable[Char] {
  private val symbols = Array('x', 'y', 'z', 'w', 'a', 'b')

  override def iterator: Iterator[Char] = {
    return new Itr()
  }

  final private class Itr extends Iterator[Char] {
    private var index = 0

    override def hasNext: Boolean = index < symbols.length

    override def next: Char = {
      try {
        val next = symbols(index)
        index += 1
        return next
      }
      catch {
        case _: ArrayIndexOutOfBoundsException =>
          throw new NoSuchElementException
      }
    }
  }
}