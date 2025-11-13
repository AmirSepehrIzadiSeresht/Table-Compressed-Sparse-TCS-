import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnsortedListMap<K,V> extends AbstractMap<K,V> {
    /*
     * it is a concrete implementation which implements the following:
     * (n is the nuber of entries present in the list)
     * - size() [O(1)]
     * - put() [O(n)]
     * - get() [O(n)]
     * - remove() [O(n)]
     * - entSet
     */

    private MyArrayList<MapEnt<K,V>> map = new MyArrayList<>();
    private boolean keyMatched = false;
    public UnsortedListMap(){};
    public int size(){
        return map.size();
    }

    /**
     * 
     * @param key  
     * @return if key not found returns -1
     */
    private int findIndx(K key){
        int n = map.size();
        for(int i = 0; i < n; ++i){
            if(map.get(i).getKey().equals(key)){
                //keyMatched = true;
                return i;
            }
        }

        return -1;
    }

    //doesnt perform linear search
    public void putUnique(K key, V val) {
        map.addLast(new MapEnt<>(key, val));
    }

    public V put(K key, V val){

        int i = findIndx(key);

        if(i == -1){
            map.addLast(new MapEnt<>(key, val));
            return null;
        } 
        
        return map.get(i).setVal(val);
    }

    public K getKey(int indx){
        return map.get(indx).getKey();
    }
    public V getVal(int indx){
        return map.get(indx).getVal();
    }

    public V get(K key){
        int i = findIndx(key);

        if(i == -1)
            return null;
   
        return  map.get(i).getVal();

    }
    /*
     * when an element is removed there is no need to shift elements backward or forward
     *  (as si done in map.remove())
     * - delete the element
     * - replace with last element (if not last element itself)
     * - nullify the last element (remove it)
     */
    public V remove(K key){
        int i = findIndx(key);
        int n = map.size();
        if(-1 == i){
            return null;
        }
        V ret = map.get(i).getVal();
        if(i != n -1) {
             map.replace(i, map.getLast());
        }
        map.removeLast();
        return ret;
    }

     public V remove(int indx){
        int n = map.size();
        V ret = map.get(indx).getVal();
        if(indx != n -1) {
             map.replace(indx, map.getLast());
        }
        map.removeLast();
        return ret;
    }

    private class IteratorEnt implements Iterator<I_Entry<K,V>> {
        private int i = 0;
        private int n = map.size();
        public boolean hasNext(){
            return i < n;
        }
        public I_Entry<K,V> next(){
            if(i == n){
                throw new NoSuchElementException();
            }
            return map.get(i++);
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    
    private class IterableEnt implements Iterable<I_Entry<K,V>> {
        public Iterator<I_Entry<K,V>> iterator(){
            return new IteratorEnt();
        }
    }
    public Iterable<I_Entry<K,V>> entSet(){
        return new IterableEnt();
    }
}
