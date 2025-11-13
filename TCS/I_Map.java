

public interface I_Map<K,V> {
    int size();
    boolean isEmpty();
    /**
     * 
     * @param key 
     * @param val
     * @return null if no matching key is found. Returns a value when 
     * substituting the previous value associated with the passed key
     */
    V put(K key, V val);
    /**
     * 
     * @param key
     * @return null if no matching key found
     */
    V get(K key);
    /**
     * 
     * @param key
     * @return null if no matching key found
     */
    V remove(K key);
    /**
     *  
     * @return Returns true if a matching key is found
     */
    //boolean containsKey();

    Iterable<K> keySet();
    Iterable<V> valSet();
    Iterable<I_Entry<K,V>> entSet();
}