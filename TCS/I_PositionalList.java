public interface I_PositionalList<T> {
            int size();
        boolean isEmpty();

        Position<T> first();
        Position<T> last();

        Position<T> before(Position<T> pos) throws IllegalArgumentException;
        Position<T> after(Position<T> pos) throws IllegalArgumentException;

        Position<T> addFirst(T ele);
        Position<T> addLast(T ele);

        Position<T> addAfter(Position<T> pos, T ele) throws IllegalArgumentException;
        Position<T> addBefore(Position<T> pos, T ele) throws IllegalArgumentException;

        T Replace(Position<T> pos , T ele) throws IllegalArgumentException;
        
        T remove(Position<T> pos) throws IllegalArgumentException;
}
