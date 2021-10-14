package discretemath.common

sealed trait Direction {
}

object Direction {

  case object FROM_UP_TO_DOWN extends Direction
  case object FROM_LEFT_TO_RIGHT extends Direction
}