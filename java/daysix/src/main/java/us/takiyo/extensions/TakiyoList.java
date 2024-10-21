package us.takiyo.extensions;

import java.util.ArrayList;
import java.util.function.Function;

public class TakiyoList<V> extends ArrayList<V> {

    public TakiyoList() {
        super();
    }

    public V get(Function<V, String> func, String query) {
        for (V v : this)
            if (func.apply(v).equals(query))
                return v;

        return null;
    }
}
