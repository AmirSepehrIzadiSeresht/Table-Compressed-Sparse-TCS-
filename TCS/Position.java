public interface Position<T> {
        /**
         * Returns element currently stored at this position.
         * 
         * @return Element stored at this position
         * @throws IllegalStateException if position argument is previously deleted from the list
         */
        T getEle() throws IllegalStateException;
    
} 