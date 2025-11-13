public interface List<E> {
    /** Retrieves element with index i within the array */
    E get(int i) throws IndexOutOfBoundsException;

    /** replaces element currently at index i and returns the replaced element */
    E replace(int i, E ele) throws IndexOutOfBoundsException;

    void insert(int i, E ele) throws IndexOutOfBoundsException;

    E remove(int i)throws IndexOutOfBoundsException;

    boolean isEmpty();
    
    /** returns number of elements currently stored within the array list */
    int size();
    E removeLast() throws IllegalStateException;
    public E getLast();
    public void addLast(E ele);
}
