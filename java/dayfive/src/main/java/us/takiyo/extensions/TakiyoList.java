package us.takiyo.extensions;

import java.util.ArrayList;
import java.util.function.Function;

public class TakiyoList<E> extends ArrayList<E> {
    public TakiyoList() {
        super();
    }

    public E get(Function<E, String> map, String value) {
        for (E data : this) {
            if (map.apply(data).equals(value)) return data;
        }
        return null;
    }
}
