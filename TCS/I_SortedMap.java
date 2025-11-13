public interface I_SortedMap<K,V> extends I_Map<K,V>{
    I_Entry<K,V> minEnt();
    I_Entry<K,V> maxEnt();

    I_Entry<K,V> ceilingEnt(K key) throws IllegalArgumentException;
    I_Entry<K,V> floorEnt(K key) throws IllegalArgumentException;

    I_Entry<K,V> aboveEnt(K key) throws IllegalArgumentException;
    I_Entry<K,V> belowEnt(K key) throws IllegalArgumentException;

    /**
     * 
     * @param k1
     * @param k2
     * @return returns a subset from k1 inclusive to k2 exclusive
     */
    Iterable<I_Entry<K,V>> subMap(K k1, K k2) throws IllegalArgumentException;
}
