import java.util.Iterator;
import java.util.NoSuchElementException;


    // start of class LinkedPositionalList
    public class PositionalList<T> implements I_PositionalList<T> {
        private static class Node<T> implements Position<T> {
            private T ele;
            private Node<T> next;
            private Node<T> prev;

            public Node(T ele, Node<T> prev, Node<T> next) {
                this.ele = ele;
                this.prev = prev;
                this.next = next;
            }

            public T getEle() throws IllegalStateException {
                if(next == null) throw new IllegalStateException("Pos is removed from list.");
                return ele;
            }

            public void setEle(T ele){
                    this.ele = ele;
            }

            public Node<T> getNext(){
                return next;
            }

            public void setNext(Node<T> next){
                this.next = next;
            }
        
            public Node<T> getPrev(){
                return prev;
            }

            public void setPrev(Node<T> prev){
                this.prev = prev;
            }

            public void nullify(){
                next = prev = null;
                ele = null;
            }
        }

        private Node<T> headerSentinel;
        private Node<T> trailerSentinel;
        int size = 0;

        public PositionalList() {
            headerSentinel = new Node<>(null, null, null);
            trailerSentinel = new Node<>(null, headerSentinel, null);
            headerSentinel.setNext(trailerSentinel);
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return 0 == size;
        }

        public Position<T> first() {
            return position(headerSentinel.getNext());
        }

        public Position<T> last(){
             return position(trailerSentinel.getPrev());
        }


        private Node<T> validate(Position<T> pos) throws IllegalArgumentException{
            if(!(pos instanceof Node)) throw new IllegalArgumentException("Position is invalid.");
            Node<T> node = (Node<T>) pos;
            if(node.getNext() == null) throw new IllegalArgumentException("Position is already removed from the list");
            return node;
        }

        private Position<T> position(Node<T> node){
            if(node == headerSentinel || node == trailerSentinel){
                return null;
            }

            return (Position<T>) node;
        }

        private Position<T> addBetween(T ele, Node<T> predecessor, Node<T> successor) {
            Node<T> newest= new Node<>(ele, predecessor, successor);
            successor.setPrev(newest);
            predecessor.setNext(newest);
            ++size;
            return (Position<T>) newest;
        }

        public Position<T> before(Position<T> pos) throws IllegalArgumentException {
            Node<T> node = validate(pos);
            return position(node.getPrev());
        }

        public Position<T> after(Position<T> pos) throws IllegalArgumentException {
            Node<T> node = validate(pos);
            return position(node.getNext());
        }

        public Position<T> addFirst(T ele) {
            return addBetween(ele, headerSentinel, headerSentinel.getNext());
        }
        
        public Position<T> addLast(T ele) {
            return addBetween(ele, trailerSentinel.getPrev(), trailerSentinel);
        }

        public Position<T> addAfter(Position<T> pos, T ele) throws IllegalArgumentException {
            Node<T> node = validate(pos);
            return addBetween(ele, node, node.getNext());
        }


        public Position<T> addBefore(Position<T> pos, T ele) throws IllegalArgumentException {
            Node<T> node = validate(pos);
            return addBetween(ele, node.getPrev(), node);
        }


        public T Replace(Position<T> pos, T ele) throws IllegalArgumentException {
            Node<T> node = validate(pos);
            T retVal = node.getEle();
            node.setEle(ele);
            return retVal;
        }
        
        public T remove(Position<T> pos) throws IllegalArgumentException {
            Node<T> node = validate(pos);

            Node<T> predeccessor = node.getPrev();
            Node<T> successor = node.getNext();

            predeccessor.setNext(successor);
            successor.setPrev(predeccessor);

            T retVal = node.getEle();

            node.nullify();
            --size;

            return retVal;
        }

        /**adding iterator functionality to data structure */
        private class PositionIterator implements Iterator<Position<T>> {
            private Position<T> cursor = first();
            private Position<T> recent = null;

            public boolean hasNext(){
                return (cursor != null);
            }

            public Position<T> next() throws NoSuchElementException {
                if(cursor == null) throw new NoSuchElementException("no entity available");
                recent = cursor;
                cursor = after(cursor);
                return cursor;
            }

            public void remove() throws IllegalArgumentException{
                if(recent == null) throw new IllegalStateException("nothing to remove");
                PositionalList.this.remove(recent);
                recent = null;
            }
        }
        private class PositionIterable implements Iterable<Position<T>> {
            public Iterator<Position<T>> iterator(){
                return new PositionIterator();
            }
        }

        public Iterable<Position<T>> positions(){
            return new PositionIterable();
        }

        private class ElementIterator implements Iterator<T> {
            // adapter design pattern
            Iterator<Position<T>> positionIterator = positions().iterator();
            public boolean hasNext(){return positionIterator.hasNext();}
            public T next(){return positionIterator.next().getEle();}
            public void remove(){positionIterator.remove();}
        }

        public Iterator<T> iterator() {
            return new ElementIterator();
        }
        // end of iterator functionality
    }
    // end of class LinkedPositionalList


    //** insertion sort for positional list for integer*/
    // public static <T> void insertionSort(LinkedPositionalList<T> list){
    //     Position<T> marker = list.first();
    //     Position<T> pivot = null;
    //     Position<T> walk = null;

    //     //ensures list contains at least two elements

    //     while(marker != list.last()) {
    //         pivot = list.after(marker);
    //         T pivotVal = pivot.getEle();

    //         // if already sorted
    //         if(pivotVal > marker.getEle()){
    //             marker = pivot;
    //         } else {
    //             walk = list.before(marker);
    //             pivot = list.addBefore(walk, list.remove(pivot));
            
    //             while(list != null && walk.getEle() > pivotVal) {
    //                 walk = list.before(walk);
    //             }
    //             list.addAfter(walk, list.remove(pivot));
    //         }
    //     }   
    // }
    // end of insertion sort
