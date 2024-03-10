package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Medhashree Adhikari
 * @author Samuel A. Rebelsky
 * 
 * Acknowledgement:
 *   Ishitha
 *   Boston
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    // create new array
    AssociativeArray<K, V> clonedArr = new AssociativeArray<K, V>();
    try {
      // loop to iterate
      for (int i = 0; i < this.size; i++) {
        // set each thing in null to the key and value
        clonedArr.set(pairs[i].key, pairs[i].value);
      }
    } catch (NullKeyException e) {}
    return clonedArr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String stringHolder = "";

    // if array is empty, do
    if(pairs.length == 0) {
      return "{}";
      // else, do for loop which goes until last key
    } else {
      for (int i = 0; i < this.size; i++) {
        // if it is the first thing in the array
        if (i == 0) {
          // replace the empty str with the statement
          stringHolder = pairs[0].key + ": " + pairs[0].value;
        } else {
          // after the 0
          stringHolder = stringHolder + ", " + pairs[i].key + ": " + pairs[i].value;
        }
      } // for loop
    } // condition check

    return "{ " + stringHolder + " }";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Checks if the array is full
   */
  public boolean isFull() {
    if(this.size == pairs.length) {
      return true;
    }
    return false;
  }

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    // check for null case
    if (key == null) {
      throw new NullKeyException ("Can't set null value");
    }
    
    for (int i = 0; i < this.size; i++) {
      if (pairs[i].key.equals(key)) {
        pairs[i].value = value;
        // check if array is full
      } else if (this.isFull()) {
        this.expand();
      }
    } // for loop
    pairs[size++] = new KVPair<K,V>(key, value); // increases the size;
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    if (key == null) {
      throw new KeyNotFoundException("Key not available.");
    }
    // for loop to check if & where it exists
    for(int i = 0; i < this.size; i++) {
      // it current pair key is what we are looking for
      if (hasKey(key)) {
        // return the value
        return pairs[i].value;
      } // condition check
    } // for loop
    return null;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    if (key == null) {
      // throw new KeyNotFoundException("Key not available.");
      return false;
    }
    
    for (int i = 0; i < this.size; i++) {
      if (pairs[i].key.equals(key)) {
        return true;
      } // condition check
    } // loop
    // return false, if not found
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   * @throws NullKeyException 
   */
  public void remove(K key) throws KeyNotFoundException {
    int shift = 0;
    if (hasKey(key)) {
      for(int i = 0; i < this.size; i++) {
        if (pairs[i].key.equals(key)) {
          pairs[i] = null;
          // pairs[i].key = null;
          // pairs[i].value = null;

          shift = i;

          for (int s = shift + 1; s < this.size; s++) {
            pairs[s - 1] = pairs[s];
          } // for loop
          this.size--;
        }      
      } // for loop
    } // condition check
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  /**
   * Creates an array of all the keys in the KeyValue pair, 
   * and returns the array
   */
  public String[] getAllKeys() {
    String[] keyArr = new String[this.size];

    for (int i = 0; i < this.size; i++) {
      keyArr[i] = pairs[i].key.toString();
    }
    return keyArr;
  }

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    if (hasKey(key)) {
      for (int i = 0; i < this.size; i++) {
        if (pairs[i].key.equals(key)) {
          return i;
        } // return key in current index
      } // for loop
      // condition check - does the arr even have the key
    } else { 
      throw new KeyNotFoundException();
    }
    return -1;
  } // find(K)
} // class AssociativeArray