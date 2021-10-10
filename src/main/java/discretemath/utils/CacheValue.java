package discretemath.utils;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

public class CacheValue<T> {

	private final AtomicReference<T> value = new AtomicReference<>();

	public boolean isSet() {
		return value.get() != null;
	}

	public T get() {
		T value = this.value.get();
		if (value == null) {
			throw new NoSuchElementException();
		}

		return value;
	}

	public void set(T value) {
		boolean changed = this.value.compareAndSet(null, value);

		if (!changed) {
			throw new IllegalStateException();
		}
	}
}
