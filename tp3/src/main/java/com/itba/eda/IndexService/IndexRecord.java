package com.itba.eda.IndexService;

public record IndexRecord<K extends Comparable<? super K>, V>(K key, V value) implements Comparable<IndexRecord<K, V>> {
    public IndexRecord(K key) {
        this(key, null);
    }

    public int compareTo(IndexRecord<K, V> o) {
        return key.compareTo(o.key());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof IndexRecord rec))
            return false;

        return compareTo((IndexRecord<K, V>) rec) == 0;
    }

    @Override
    public String toString() {
        return String.format("Record <%s, %s>", key, value);
    }
}
