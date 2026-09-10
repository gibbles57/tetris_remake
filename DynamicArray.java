/** 
 * A dynamic array used for storing items.
 * @param <T> type of elements
 */
public class DynamicArray<T>
{
    /**
     * array of elements to hold.
     */
    private T[] data;
    /**
     * Makes an array with a fixed size.
     * @param size size of the array.
     * @throws RuntimeException if size < 0
     */
    @SuppressWarnings("unchecked")
    public DynamicArray(int size) {
        if (size < 1) {
            throw new RuntimeException("Invalid size.");
        }
        this.data = (T[]) new Object[size];
    } // O(1)

    /**
     * Sets the element at the index given.
     * @param index target index.
     * @param obj object to store.
     * @throws IndexOutOfBoundsException if index is not valid.
     */
    public void set(int index, T obj) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = obj;
    } // O(1)

    /**
     * Returns the element at the index.
     * @param index tyarget index.
     * @return object at index.
     * @throws IndexOutOfBoundsException if index is not valid.
     */
    public T get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    } // O(1)

    /**
     * Returns the size of the array.
     * @return size of array.
     */
    public int size() {
        return data.length;
    } // O(1)
}
