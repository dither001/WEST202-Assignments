/**
 * Implementation of DictionaryInterface using Linked List
 * 
 * @author Destin West & Nick Foster - November 2017
 */

public class HashDictionaryChained<K, V> implements DictionaryInterface<K, V> {
	// fields
	private static int DEFAULT_SIZE = 97;
	private List<TableElement<K, V>>[] masterList;

	// constructors
	public HashDictionaryChained() {
		this(DEFAULT_SIZE);
	}

	@SuppressWarnings("unchecked")
	public HashDictionaryChained(int size) {
		if (size < 0)
			throw new IllegalArgumentException();
		masterList = (List<TableElement<K, V>>[]) new List[size];
	}

	// methods
	@Override
	public V put(K key, V value) {
		TableElement<K, V> element = new TableElement<K, V>(key, value);
		int hash = hashValue(key);

		// create LinkedList if bucket is empty
		if (masterList[hash] == null) {
			masterList[hash] = new LinkedList<TableElement<K, V>>();
		}
		
		// update value if key already exists; break and return early if does
		if (contains(key)) {
			for (int i = 0; i < masterList[hash].getLength(); ++i) {
				// if key is found in bucket, return value of key
				if (masterList[hash].get(i).getKey().equals(key)) {
					masterList[hash].get(i).setValue(value);
					return value;
				}
			}
		}
		
		// if the key isn't there, add the element
		masterList[hashValue(key)].add(element);
		return value;
	}

	private int hashValue(K key) {
		return (Math.abs(key.hashCode()) % masterList.length);
	}

	@Override
	public V get(K key) {
		if (contains(key)) {
			int hash = hashValue(key);
			// iterate through elements of bucket
			for (int i = 0; i < masterList[hash].getLength(); ++i) {
				// if key is found in bucket, return value of key
				if (masterList[hash].get(i).getKey().equals(key)) {
					return masterList[hash].get(i).getValue();
				}
			}
		}

		return null;
	}

	@Override
	public boolean contains(K key) {
		boolean contains = false;
		int hash = hashValue(key);
		
		// check to see if bucket exists and/or is empty
		if (masterList[hash] != null && masterList[hash].isEmpty() != true) {
			// iterate through elements of bucket
			for (int i = 0; i < masterList[hash].getLength(); ++i) {
				// if key is found in bucket, toggle contains and break
				if (masterList[hash].get(i).getKey().equals(key)) {
					contains = true;
					break;
				}
			}
		}
		
		return contains;
	}

	@Override
	public V remove(K key) {
		if (contains(key)) {
			int hash = hashValue(key);
			for (int i = 0; i < masterList[hash].getLength(); ++i) {
				// remove element, return value of removed element
				if (masterList[hash].get(i).getKey().equals(key)) {
					return masterList[hash].remove(i).getValue();
				}
			}
		}
		
		return null;
	}

	@Override
	public int size() {
		int size = 0;
		for (List<TableElement<K, V>> el : masterList) {
			size += (el != null) ? el.getLength() : 0;
		}

		return size;
	}

	@Override
	public Set<K> keySet() {
		Set<K> newSet = new ArraySet<K>();
		
		for (int i = 0; i < masterList.length; ++i) {
			// if bucket is not null, search bucket
			if (masterList[i] != null) {
				// add the key of each element in bucket
				for (int j = 0; j < masterList[i].getLength(); ++j) {
					newSet.add(masterList[i].get(j).getKey());
				}
			}
		}
		
		return newSet;
	}

	@Override
	public Set<V> valueSet() {
		Set<V> newSet = new ArraySet<V>();
		
		for (int i = 0; i < masterList.length; ++i) {
			// if bucket is not null, search bucket
			if (masterList[i] != null) {
				// add the key of each element in bucket
				for (int j = 0; j < masterList[i].getLength(); ++j) {
					newSet.add(masterList[i].get(j).getValue());
				}
			}
		}
		
		return newSet;
	}

	// private classes
	@SuppressWarnings("hiding")
	private class TableElement<K, V> {
		// fields
		private K key;
		private V value;

		// constructors
		private TableElement(K key, V value) {
			this.key = key;
			this.value = value;
		}

		// methods
		private K getKey() {
			return key;
		}

		private V getValue() {
			return value;
		}

		private void setKey(K key) {
			this.key = key;
		}

		private void setValue(V value) {
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		public boolean equals(Object other) {
			boolean flag = false;

			if (other instanceof TableElement) {
				TableElement<K, V> candidate = (TableElement<K, V>) other;

				if ((this.getKey()).equals(candidate.getKey()))
					flag = true;
			}

			return flag;
		}
	}

	// END OF CODE
}