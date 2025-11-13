import java.util.Iterator;

public abstract class AbstractMap<K,V> implements I_Map<K,V>{
    /*
     * what this abstract map imlements?
     * - isEmpty()
     * - MapEnt which implements Entry
     * - two interables based on adaptation of concrete implementation
     *      of EntSet() which give set of all entries
     *      keySet() & ValSet()
     * 
     */

    protected static class MapEnt<K,V> implements I_Entry<K,V> {
        private K key;
        private V val;

        public MapEnt(K k, V v){
            key = k;
            val = v;
        }

        public K getKey(){
            return key;
        }
        public V getVal(){
            return val;
        }

        public void setKey(K k){
            key = k;
        }

        public V setVal(V v){
            V old = val;
            val = v;
            return old;
        }
    } // -- end of MapEnt --

    public boolean isEmpty() {return size() == 0;};

    private class KeyIterator implements Iterator<K> {
       private Iterator<I_Entry<K,V>> ent = entSet().iterator();
        public boolean hasNext() {return ent.hasNext();}
        public K next() {return ent.next().getKey();}
        public void remove(){throw new UnsupportedOperationException();}

    }

    private class keyIterable implements Iterable<K> {
        public Iterator<K> iterator(){
            return new KeyIterator();
        }
    }
    public Iterable<K> keySet(){
        return new keyIterable();
    }


        private class ValIterator implements Iterator<V> {
       private Iterator<I_Entry<K,V>> ent = entSet().iterator();
        public boolean hasNext() {return ent.hasNext();}
        public V next() {return ent.next().getVal();}
        public void remove(){throw new UnsupportedOperationException();}

    }

    private class valIterable implements Iterable<V> {
        public Iterator<V> iterator(){
            return new ValIterator();
        }
    }

    public Iterable<V> valSet(){
        return new valIterable();
    }
}
