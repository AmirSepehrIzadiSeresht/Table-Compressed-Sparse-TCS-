/** Abstract base class providing some functionality of the tree base class */
    import java.util.ArrayList;
    import java.util.Iterator;
import java.util.Queue;
import java.util.ArrayDeque;
    public  abstract class AbstractTree<T> implements I_Tree<T> {
        public boolean isInternal(Position<T> pos) {
            return numChildren(pos) > 0;
        }

        public boolean isExternal(Position<T> pos){
            return numChildren(pos) == 0;
        }

        public boolean isRoot(Position<T> pos){
            return pos == getRoot();
        }

        public boolean isEmpty(){
            return size() == 0;
        }

        protected void preorderSubtree(Position<T> pos, ArrayList<Position<T>> snapshot) {
            snapshot.add(pos);
            for(Position<T> c : children(pos)){
                preorderSubtree(c, snapshot);
            }
        }

        public Iterable<Position<T>> preorder() {
            ArrayList<Position<T>> snapshot = new ArrayList<>();
            if(!isEmpty()){
                preorderSubtree(getRoot(), snapshot);
            }
            return snapshot;
        }

        public Iterable<Position<T>> positions(){
            return preorder();
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


        public Iterable<Position<T>> breadthFirst(){
            ArrayList<Position<T>> snapshot = new ArrayList<>();
            if(!isEmpty()){
                Queue<Position<T>> fringe = new ArrayDeque<>();
                fringe.add(getRoot());

                while(!fringe.isEmpty()){
                    Position<T> p = fringe.poll();
                    snapshot.add(p);
                    for(Position<T> c : children(p)){
                        fringe.add(c);
                    }
                }
            }

            return snapshot;
        }



    }// --- end of abstract tree ---