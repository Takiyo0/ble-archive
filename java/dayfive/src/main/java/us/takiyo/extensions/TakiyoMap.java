package us.takiyo.extensions;

import java.util.HashMap;

public class TakiyoMap<K, V> extends HashMap<K, V> {
    public TakiyoMap() {
        super();
    }

    public V getKey(K key) {
        return super.get(key);
    }
}
