package discretemath.utils

import java.util.NoSuchElementException
import java.util.concurrent.atomic.AtomicReference

class CacheValue[T] {
	private val value = new AtomicReference[T]

	def isSet: Boolean = value.get != null

	def get: T = {
		val value = this.value.get
		if (value == null) throw new NoSuchElementException
		value
	}

	def set(value: T): Unit = {
		val changed = this.value.compareAndSet(null.asInstanceOf[T], value)
		if (!changed) throw new IllegalStateException
	}
}