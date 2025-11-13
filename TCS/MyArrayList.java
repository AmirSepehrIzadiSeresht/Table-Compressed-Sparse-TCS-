import java.util.Iterator;
import java.util.NoSuchElementException;
public class MyArrayList<E> implements List<E>{
    private int capacity; //default capacity, can be any arbitray number

    private E[] data; //generic array to store elements
    private int f = 0; //index to front of circular array
    private int sz = 0; // number of elements currently stored in array;
    private static final int b = 2; // base of geometric growth of dynamic array

    public MyArrayList(int cap) throws IllegalArgumentException{
        if (cap <= 0)
            throw new IllegalArgumentException("Invalid size.");

        capacity = cap;

        data = (E[]) new Object[capacity];
    }

    public MyArrayList(){
        this(2);
    }

    public int size(){return sz;};
    public boolean isEmpty(){return 0 == sz;};

    public E get(int i) throws IndexOutOfBoundsException{

        return data[indx(i)];
    }

    public void insert(int i, E ele) throws IndexOutOfBoundsException{
        checkIndx(i, sz);

        /*
         * this insertion algorithm takes at most O(n/2)
         * insertions at either end of the array list
         * takes O(1) because of the circular nature of
         * the implementation
         * 
         * space complexity is O(n) for n being the 
         * number of current elements as 
         * data.length <= 2 * sz
         */

        int j;
        if(i <= sz/2) {
            // it is closer to front of the array
            j = f;
            while(j != indx(i)) {
                data[backward(j)] = data[j];
                j = forward(j);
            }
            data[backward(j)] = ele;

            f = backward(f); //updata f
        } else {
            j = indx(sz - 1); // gives index of the last element of array
            while(j != indx(i - 1)){
                data[forward(j)] = data[j];
                j = backward(j);
            }

            data[forward(j)] = ele;
        }

        sz++; // increase size of array

        if(sz == data.length) { // if array full then resize
            resize(data.length * b);
        }
    }

    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndx(i, sz - 1); // throws exception if list is already empty
        E temp;
        int j;
        if(i <= sz/2){
            temp = get(i);
            j = indx(i);
            while(j != f){
                data[j] = data[backward(j)];
                j = backward(j);
            }
            data[j] = null;
            f = forward(f); // update front of array
        } else {
            temp = get(i);
            j = indx(i);
            while(j != indx(sz - 1)){
                data[j] = data[forward(j)];
                j = forward(j);
            }
            data[j] = null;
        }

        sz--; // update current size

        /*
         * if arrayList is sparse, that is for b = 2:
         * sz <= data.length / 4
         * shrink array to half size
         * this ensures that capacity of array is at most 4 * sz
         * which maintains space complexity of O(n) for n = sz
         */

        if(data.length != capacity && sz <= data.length / (b * b)){
            resize(data.length / b);
        }

        return temp;
    }
    public void addLast(E ele){
        insert(sz, ele);
    }

    public E getLast() {
        if(isEmpty())
            return null;
        return data[indx(sz-1)];
    }

    public E removeLast() throws IllegalStateException{
        try{
            return remove(sz - 1);
        } catch(IndexOutOfBoundsException e){
            throw new IllegalStateException("List is Empty");
        }
    }

    public E replace(int i, E ele) throws IndexOutOfBoundsException{
        checkIndx(i, sz - 1);
        E temp = get(i);
        data[indx(i)] = ele;
        return temp;
    }

    private void resize(int newSize){
        E[] temp = (E[]) new Object[newSize];

        int j = 0;
        while(j != sz){
            temp[j] = data[indx(j)];
            j++;
        }

        f = 0; // reset front
        data = temp;
    }

    private void checkIndx(int i, int upLimit) throws IndexOutOfBoundsException{
        if(i < 0 || i > upLimit)
            throw new IndexOutOfBoundsException();
    }

    private int backward(int i){
        return (i - 1 + data.length) % data.length;
    }

    private int forward(int i){
        return (i + 1) % data.length;
    }

    private int indx(int i) {
        return (f + i) % data.length;
    }

    private class IteratorElems implements Iterator<E> {
        private int i = 0;
        public boolean hasNext() {return i < sz;}
        public E next(){
            if(i == sz) {
                throw new NoSuchElementException();
            }
            return get(i++);
        }
        // can be implemented later
        public void remove(){throw new UnsupportedOperationException();}
    }

    private class IterableElems implements Iterable<E> {
        public Iterator<E> iterator(){
            return new IteratorElems();
        }
    }

    public Iterable<E> elements(){
        return new IterableElems();
    }


    public void showList(){
        System.out.println("\nList: ");
        for(int i = 0; i < sz; ++i){

                System.out.print(" " + data[indx(i)]);
        }
    }


}
/* DEBUG
        public void main(String[] args) {
        MyArrayList<Integer> alist = new MyArrayList<>();

        alist.insert(0,7);
        alist.showList();
        alist.insert(0,8); // insertion at front
        alist.showList();
        alist.insert(2,4); // insertion at end
        alist.showList();
        
        alist.remove(2); 
        alist.showList();


     
        System.out.println("Size: " + alist.size());
        System.out.println("First ele: " + alist.get(0));
    }


    -----

    public void showList(){
        System.out.println("\nList: ");
        for(int i = 0; i < data.length; ++i){
            if(data[indx(i)] == null)
                System.out.print(" *");
            else
                System.out.print(" " + data[indx(i)]);
        }
    }

 */
