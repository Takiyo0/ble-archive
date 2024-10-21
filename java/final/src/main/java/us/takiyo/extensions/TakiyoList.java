package us.takiyo.extensions;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class TakiyoList<V> extends ArrayList<V>{
	public TakiyoList() {
		super();
	}

	public V get(Function<V, String> fun, String value) {
		for (V d : this)
			if (Objects.equals(fun.apply(d), value)) return d;
		return null;
	}
}
