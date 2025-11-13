import java.util.Comparator;

public abstract class SortedTreeMap<K,V> extends AbstractSortedMap<K,V> {
    // a specialized version of the linked binary tree with
    //support for balancing
 
       
       // underlying tree structure
       protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();
        protected Position<I_Entry<K,V>> maxEntPos = null;
       //constructs sorted map using the natural ordering of keys
       public SortedTreeMap(){
        super(); //AbstractSoretedMap constructor
        tree.addRoot(null); //creates a sentinel leaf as root
        maxEntPos = tree.getRoot();
       }

       public SortedTreeMap(Comparator<K> comp){
        super(comp);
        tree.addRoot(null);
       }

       public int size() {
        return (tree.size() - 1)/2; //only internal nodes contain entries
       }

       protected void expandExternal(Position<I_Entry<K,V>> p, I_Entry<K,V> ent){
        tree.replaceEle(p, ent);
        tree.addLeftChild(p,null); // add new sentinel leaves as children
        tree.addRightChild(p,null);
       }
       // returns position in subtree rooted at p having the given key
       //(or else the terminal leaf)
       private Position<I_Entry<K,V>> treeSearch(Position<I_Entry<K,V>>  p , K key){
        if(tree.isExternal(p))
            return p; // return terminal p

        int comp = compare(key, p.getEle().getKey());
       if(comp == 0){
        return p; // exact match is found
    
       } else if(comp > 0){
        return treeSearch(tree.getRightChild(p), key);
       } else {
        return treeSearch(tree.getLeftChild(p), key);
       }
       
       
       }


       public V get(K key) throws IllegalStateException{
        checkKey(key); //key type obeys natural ordering - may throw exception
        Position<I_Entry<K,V>> p = treeSearch(tree.getRoot(), key);
        
        if(tree.isExternal(p)){
            return null;
        } else {
            return p.getEle().getVal();
        }
       }

       public V put(K key, V val) throws IllegalArgumentException{
        checkKey(key); //may throw exception
        I_Entry<K,V> ent = new MapEnt<>(key, val);
        Position<I_Entry<K,V>> p = treeSearch(tree.getRoot(), key);
        if(tree.isExternal(p)) {
            expandExternal(p, ent);
            rebalanceInsert(p); // hook 
            return null;
        } else {
            V old =p.getEle().getVal();
            tree.replaceEle(p, ent);
            
            return old;
        }

       }

       public void putMaxEnt(K key, V val) throws IllegalArgumentException{
        checkKey(key); //may throw exception
        I_Entry<K,V> ent = new MapEnt<>(key, val);
        
            expandExternal(maxEntPos, ent);
            rebalanceInsert(maxEntPos); //O(log n)
            maxEntPos = tree.getRightChild(maxEntPos);
            return;


       }

       public V remove(K key) throws IllegalArgumentException{
        checkKey(key);
        Position<I_Entry<K,V>> p = treeSearch(tree.getRoot(), key);
        if(tree.isExternal(p)) {
            
            return null;
        } else {
            V old = p.getEle().getVal();
            // if the node to be removed has tow internal children
            //we need to find key strictly less than that of p's
            if(tree.isInternal(tree.getRightChild(p)) && tree.isInternal(tree.getLeftChild(p))) {
                Position<I_Entry<K,V>> replacement = treeMax(tree.getLeftChild(p));
                tree.replaceEle(p, replacement.getEle());
                p = replacement;
            }
            Position<I_Entry<K,V>> leaf = (tree.isExternal(tree.getLeftChild(p)) ? tree.getLeftChild(p) : tree.getRightChild(p));
            Position<I_Entry<K,V>> sib = tree.sibling(leaf); // could be internal or external
            tree.remove(leaf);
            tree.remove(p);
            rebalanceDelete(sib); // hook
            return old;
        }
       }

       //returns position with maximum key rooted at position 
       // which is located at rightmost internal node
       protected Position<I_Entry<K,V>> treeMax(Position<I_Entry<K,V>> p){
        Position<I_Entry<K,V>> walk = p;
        while(tree.isInternal(walk)){
            walk = tree.getRightChild(walk);

        }
        return tree.getParent(walk);
       }
       //return entry having the greatest key or null is tree is not empty
       public I_Entry<K,V> maxEnt(){
        if(tree.isEmpty()){
            return null;
        }
        return treeMax(tree.getRoot()).getEle();
       }
     

        /*ADDING SYMMETRY METHODS */
  
       protected Position<I_Entry<K,V>> treeMin(Position<I_Entry<K,V>> p){
        Position<I_Entry<K,V>> walk = p;
        while(tree.isInternal(walk)){
            walk = tree.getLeftChild(walk);

        }
        return tree.getParent(walk);
       }

        public I_Entry<K,V> minEnt(){
        if(tree.isEmpty()){
            return null;
        }
        return treeMin(tree.getRoot()).getEle();
       }


       public I_Entry<K,V> ceilingEnt(K key) throws IllegalArgumentException{
            checkKey(key);
            Position<I_Entry<K,V>> p =treeSearch(tree.getRoot(), key);
            if(tree.isInternal(p)){ //exact match
                return p.getEle();
            } else {
                while(!tree.isRoot(p)) {
                    if(p == tree.getLeftChild(tree.getParent(p))){
                        return (tree.getParent(p)).getEle();
                    } else {
                        p = tree.getParent(p);
                    }
                }    
                return null; //no such floor exists
            }
       }

        public I_Entry<K,V> aboveEnt(K key) throws IllegalArgumentException{
            checkKey(key);
            Position<I_Entry<K,V>> p =treeSearch(tree.getRoot(), key);
            if(tree.isInternal(p) && tree.isInternal(tree.getRightChild(p))) {
                return treeMin(tree.getRightChild(p)).getEle();
            }
           // otherwise we had failed search or p doesn't have internal right child
           while(!tree.isRoot(p)) {
                    if(p == tree.getLeftChild(tree.getParent(p))){
                        return (tree.getParent(p)).getEle();
                    } else {
                        p = tree.getParent(p);
                    }
                }    
                return null; //no such floor exists
       }

       /*END OF SYMMETRY METHODS */


    //returns ent having greatest key less / equal than given(if any)
       public I_Entry<K,V> floorEnt(K key) throws IllegalArgumentException{
            checkKey(key);
            Position<I_Entry<K,V>> p =treeSearch(tree.getRoot(), key);
            if(tree.isInternal(p)){ //exact match
                return p.getEle();
            } else {
                while(!tree.isRoot(p)) {
                    if(p == tree.getRightChild(tree.getParent(p))){
                        return (tree.getParent(p)).getEle();
                    } else {
                        p = tree.getParent(p);
                    }
                }    
                return null; //no such floor exists
            }
       }

       //returns ent with key strictly less than given
       public I_Entry<K,V> belowEnt(K key) throws IllegalArgumentException{
            checkKey(key);
            Position<I_Entry<K,V>> p =treeSearch(tree.getRoot(), key);
            if(tree.isInternal(p) && tree.isInternal(tree.getLeftChild(p))) {
                return treeMax(tree.getLeftChild(p)).getEle();
            }
           // otherwise we had failed search or p doesn't have internal left child
          while(!tree.isRoot(p)) {
                    if(p == tree.getRightChild(tree.getParent(p))){
                        return (tree.getParent(p)).getEle();
                    } else {
                        p = tree.getParent(p);
                    }
                }    
                return null; //no such floor exists
       }


       //returns an iterable collection of all key value pairs of the tree
       public Iterable<I_Entry<K,V>> entSet(){
        MyArrayList<I_Entry<K,V>> buff = new MyArrayList<>(tree.size());
        for(Position<I_Entry<K,V>> p: tree.inorder()) {
            if(tree.isInternal(p)) {
                buff.addLast(p.getEle());
            }
        }
        return (Iterable<I_Entry<K,V>>)buff;
       }


       //returns iterable of entries in range [k1, k2)
      public Iterable<I_Entry<K,V>> subMap(K k1, K k2) throws IllegalArgumentException {
 MyArrayList<I_Entry<K,V>> buff = new MyArrayList<>(tree.size());
        if(compare(k1, k2) < 0){
            subMapRecurse(k1, k2, tree.getRoot(), buff);
        }
        return (Iterable<I_Entry<K,V>>)buff;
      }


      private void subMapRecurse(K k1, K k2, Position<I_Entry<K,V>> p, MyArrayList<I_Entry<K,V>> buff){
            if(tree.isInternal(p)){
                if(compare(p.getEle().getKey(), k1) < 0) {
                    subMapRecurse(k1, k2, tree.getRightChild(p), buff);
                }
            } else {
                subMapRecurse(k1, k2, tree.getLeftChild(p), buff);
                //p.getele.get key >= k1
                if(compare(p.getEle().getKey(), k2) < 0) {
                    buff.addLast(p.getEle());
                    subMapRecurse(k1, k2, tree.getRightChild(p), buff);
                }
            }
      }

      // hooks to be implemented
      
      protected abstract void rebalanceInsert(Position<I_Entry<K,V>> p);
      protected abstract void rebalanceDelete(Position<I_Entry<K,V>> p);
}     // -- end of sorted tree map --