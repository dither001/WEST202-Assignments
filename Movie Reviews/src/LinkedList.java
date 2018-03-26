/**
 * Implementation of the List interface.
 * 
 * This implementation involves a single-linked list.
 * 
 * @author Greg Gagne - February 2017
 * Completed by Destin West & Shannon Mellin Sept 2017
 *
 */
public class LinkedList<T> implements List<T> {
	// reference to the head of the linked list
	private Node head;

	// number of elements in the list
	private int numberOfElements;

	public LinkedList() {
		head = null;
	}

	/** 
	 * Inner class representing a node in the linked list
	 */

	private class Node
	{
		private T data;
		private Node next;

		private Node(T data) {
			this(data,null);
		}

		private Node (T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	// Methods

	@Override
	public void add(T item) {

		// adds (appends) an item to the rear of the list

		Node newNode = new Node(item);
		Node current = head;

		if (isEmpty()) {
			// special case - first element being added to the list
			head = newNode;
		}
		else {
			while (current.next != null) {
				current = current.next;
			}

			// current now references the last item in the list
			current.next = newNode;
		}

		newNode.next = null;
		numberOfElements++;
	}

	//adds an item at the specific index by adjusting the linkage of the nodes
	@Override
	public boolean add(T item, int index) {
		Node newNode = new Node(item);
		Node current = head;

		//if linked list is empty and the user wants to add to the beginning of the list
		if (isEmpty() && index == 0) {
			head = newNode;
			newNode.next = null;
			++numberOfElements;
			return true;
		}
		//if linked list is empty and user enters an index # > 0 inform the user of the error
		else if (isEmpty() && index != 0) {
			System.out.println("The list is empty, put your item in the first spot!");
			return false;
		}
		//if the user enters an index # greater than the list size inform the user of the error
		else if (numberOfElements < index && index < 0) {
			System.out.println("Your index must be between 0 and " + numberOfElements + ".");
			return false;
		}
		//if the list isn't empty and the index # is zero, new object becomes the head of the list
		else if (index == 0) {
			newNode.next = current;
			head = newNode;
			++numberOfElements;
			return true;
		}
		//if the list isn't empty and the index # is anything other than 0 the object is inserted at the index number
		else {
			for (int i = 0; i < index-1; i++) {
				current = current.next;
			}
			newNode.next = current.next;
			current.next = newNode;
			numberOfElements++;
		}
		//the object has been added successfully
		return true;
	}
	//boolean found = true if the item is in the list
	@Override
	public boolean contains(T item) {
		Node current = head;
		boolean found = false;

		while (current != null && !found) {
			if (current.data.equals(item)) {
				found = true;
			}

			current = current.next;
		}

		return found;

	}
	//gets the data for the item of the list at a specific index
	@Override
	public T get(int index) {
		Node current = head;

		if (isEmpty() || index >= numberOfElements) {
			return null;
		}
		else {
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			return current.data;
		}
	}

	//removes an object from the list
	@Override
	public boolean remove(T item) {

		Node current = head;
		int index = 0;
		// if list is empty there is nothing to remove
		if (isEmpty()) {
			return false;
		}
		// if the list contains the item use index to state the items place
		else if (contains(item)){
			while (!current.data.equals(item)) {
				current = current.next;
				index++;
			}
			//if item is at the beginning of the list
			if (current == head) {
				head = head.next;
				numberOfElements--;
				return true;
			}
			else {
				//reset current to the beginning of the list
				current = head;
				//cycle to the element prior to the item to be removed on the list
				for (int i = 0; i < index-1; i++) {
					current = current.next;
				}

				current.next = current.next.next;
				numberOfElements--;
				return true;
			}
		}
		return false;
	}

	@Override
	public T remove(int index) {
		T rv = null;

		if (isEmpty() || index >= numberOfElements) {
			rv = null;
		}
		else if (index == 0) {
			// special case - first element in the list
			rv = head.data;
			head = head.next;
			numberOfElements--;
		}
		else {
			int currentIndex = 0;
			Node current = head;

			while (currentIndex < (index - 1)) {
				current = current.next;
				currentIndex++;
			}

			// current references the node we want to remove
			rv = current.next.data;
			current.next = current.next.next;
			numberOfElements--;
		}

		return rv;
	}
	//the length of the list
	@Override
	public int getLength() {
		return numberOfElements;
	}
	//if head == null the list is empty
	@Override
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		else
			return false;
	}
	//increase counter by 1 every time the item is found in the list
	@Override
	public int getFrequency(T item) {

		Node current = head;
		int counter = 0;

		if (isEmpty()) {
			return counter;
		}
		else {
			//cycles through the list
			while (current.next != null) {
				if (current.data.equals(item)) {
					counter++;
				}
				current = current.next;
				//makes sure the last node is counted if equal to item
				if (current.next == null && current.data.equals(item)) {
					counter++;
				}
			}
		}
		return counter;
	}
	//sets head to null, leaving the rest of the list unreachable (empty)
	@Override
	public void clear() {
		head = null;
	}

}
