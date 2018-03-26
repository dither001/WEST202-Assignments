import java.util.Iterator;

/* @Author(s): Nick Foster & Sarah Watterson
 * @Date: September 2017
 * 
 * Class represents a set of objects using an array,
 * implementing the Set interface provided for lab. 
 */

public class ArraySet<T> implements Set<T> {
	// fields
	public static final int CAPACITY_MULTIPLIER = 2;
	public static final int DEFAULT_CAPACITY = 10; 
	
	private int capacity = 0;
	private int numberOfElements = 0;
	private T[] elements;
	
	// constructors
	public ArraySet() {
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public ArraySet(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity must be >= 0");
		}
		
		this.capacity = capacity;
		elements = (T[])new Object[capacity];
	}
	
	// methods
	@Override
	public void add(T element) {
		if (!contains(element)) {
			ensureCapacity();
			elements[numberOfElements] = element;
			++numberOfElements;
		}
	}

	@Override
	public void addAll(T[] elements) {
		for (int i = 0; i < elements.length; ++i) {
			add(elements[i]);
		}
	}

	@Override
	public boolean contains(T element) {
		for (int i = 0; i < this.numberOfElements; ++i) {
			if (this.elements[i].equals(element)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getSize() {
		return this.numberOfElements;
	}

	@Override
	public void remove(T element) {
		int index = -1;
		for (int i = 0; i < this.numberOfElements; ++i) {
			if (this.elements[i].equals(element)) {
				index = i;
				break;
			}
		}
		
		if (index > -1 ) {
			--this.numberOfElements;
			this.elements[index] = this.elements[this.numberOfElements];
		}
	}

	private void ensureCapacity() {
		if (numberOfElements == capacity) {
			T[] newArray = (T[])new Object[(numberOfElements + 1) * CAPACITY_MULTIPLIER];
			System.arraycopy(elements, 0, newArray, 0, numberOfElements);
			elements = newArray;
		}
	}

	@Override
	public Set union(Set anotherSet) {
		for (int i = 0; i < this.getSize(); ++i) {
			anotherSet.add(this.elements[i]);
		}
		
		return anotherSet;
	}

	@Override
	public Set intersection(Set anotherSet) {
		Set s = new ArraySet();
		
		for (int i = 0; i <= this.getSize(); ++i) {
			if (anotherSet.contains(this.elements[i])) {
				s.add(this.elements[i]);
			}
		}
		
		return s;
	}

	@Override
	public Set difference(Set anotherSet) {
		Set s = new ArraySet();
		
		for (int i = 0; i <= anotherSet.getSize(); ++i) {
			if (!(anotherSet.contains(this.elements[i]))) {
				s.add(this.elements[i]);
			}
		}
		
		return s;
	}

	@Override
	public Iterator iterator() {
		return new ArraySetIterator();
	}
	
	private class ArraySetIterator implements Iterator<T> {
		private int index = 0;
		
		public boolean hasNext() {
			if (index < numberOfElements)
				return true;
			else
				return false;
		}

		public T next() {
			if (hasNext()) {
				T nextItem = elements[index++];
				
				return nextItem;
			}
			else
				throw new java.util.NoSuchElementException("No items remaining in the iteration.");
			
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}		
	}

}
