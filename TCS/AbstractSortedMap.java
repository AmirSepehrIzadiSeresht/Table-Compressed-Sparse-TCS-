import java.util.Comparator;
public abstract class AbstractSortedMap<K,V> extends AbstractMap<K,V> implements I_SortedMap<K,V> {
    private Comparator<K> comp;

    protected AbstractSortedMap(Comparator<K> c){
        comp = c;
    }
    protected AbstractSortedMap(){
            this(new DefaultComparator<K>());
    }
    
    protected int compare(I_Entry<K,V> a, I_Entry<K,V> b){
        return comp.compare(a.getKey(), b.getKey());
    }
    protected int compare(K a, K b){
        return comp.compare(a, b);
    }

    /** Determines whether key obeys total ordering property*/
    protected boolean checkKey(K key) throws IllegalArgumentException{
        try{
            return (comp.compare(key,key) == 0);
        } catch(ClassCastException e){
            throw new IllegalArgumentException("Incompatible key");
        }
    }
}
