package discretemath.structure.set.range;

case object EmptyRange extends Range[Nothing] {

	override def size = 0

	override def contains(item: Nothing): Boolean = false
}