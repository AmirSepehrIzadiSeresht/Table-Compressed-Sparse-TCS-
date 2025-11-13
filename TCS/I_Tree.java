    import java.util.Iterator;
    public interface I_Tree<T> extends Iterable<T> {
        Position<T> getRoot();

        Position<T> getParent(Position<T> pos) 
            throws IllegalArgumentException;

        Iterable<Position<T>> children(Position<T> pos); // AbsteractBinaryTree

        int numChildren(Position<T> pos) // AbsteractBinaryTree
            throws IllegalArgumentException;

        boolean isInternal(Position<T> pos)
            throws IllegalArgumentException;

        boolean isExternal(Position<T> pos)
            throws IllegalArgumentException;

        boolean isRoot(Position<T> pos) 
            throws IllegalArgumentException;

        int size();

        boolean isEmpty();

        Iterator<T> iterator();

        Iterable<Position<T>> positions();
    } 