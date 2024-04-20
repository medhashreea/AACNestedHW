import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Medhashree Adhikari
 * @author Samuel A. Rebelsky
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
    // loop to iterate
    for (int i = 0; i < this.size; i++) {
      // set each thing in null to the key and value
      clonedArr.pairs[i] = new KVPair<>(this.pairs[i].key, this.pairs[i].value);
    }
    clonedArr.size = this.size;
    return clonedArr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String stringHolder = "";

    // if array is empty, do
    if (this.size == 0) {
      return "{}";
      // else, do for loop which goes until last key
    } else {
      for (int i = 0; i < this.size; i++) {
        // if it is the first thing in the array
        if (i == 0) {
          // replace the empty str with the statement
          stringHolder = this.pairs[0].key + ": " + this.pairs[0].value;
        } else {
          // after the 0
          stringHolder = stringHolder + ", " + this.pairs[i].key + ": "
              + this.pairs[i].value;
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
    // if size and length are equal, true
    // else, false
    return (this.size == this.pairs.length);
  } // isFull()

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   * 
   * @throws NullKeyException
   */
  public void set(K key, V value) throws NullKeyException {
    // check for null case
    if (key == null) {
      throw new NullKeyException();
    }

    try {
      if (hasKey(key)) {
        // if it exists already, reset value to new value
        this.pairs[find(key)].value = value;
        return;
      }
    } catch (Exception e) {
    } // try catch

    // if arr is full,
    if (this.isFull()) {
      this.expand(); // expand
      // create, increase the size
      this.pairs[size++] = new KVPair<K, V>(key, value);
    } else {
      // create, increase the size
      this.pairs[size++] = new KVPair<K, V>(key, value);
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    return this.pairs[find(key)].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   * 
   */
  public boolean hasKey(K key) {
    try {
      if (find(key) >= 0) {
        return true;
      }
    } catch (KeyNotFoundException e) {
    }
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int shift = 0;
      // if key exists
      if (hasKey(key)) {
        shift = find(key);
        this.pairs[find(key)] = null; // remove

        // shift array over
        for (int s = shift + 1; s < this.size; s++) {
          this.pairs[s - 1] = this.pairs[s];
        } // for loop

        // decrease or set size to zero
        if (this.size > 0) {
          this.size--;
        } else {
          this.size = 0;
        }
      } // condition check
    } catch (KeyNotFoundException e) {
    }
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
   * 
   * @throws KeyNotFoundException
   */
  public String[] getAllKeys() throws KeyNotFoundException {
    String[] keyArr = new String[this.size];

    for (int i = 0; i < this.size; i++) {
      keyArr[i] = this.pairs[i].key.toString();
    }
    return keyArr;
  } // getAllKeys()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  private void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  private int find(K key) throws KeyNotFoundException {
    if (key == null) {
      throw new KeyNotFoundException();
    }

    for (int i = 0; i < this.size; i++) {
      if ((this.pairs[i] != null) && (this.pairs[i].key.equals(key))) {
        return i;
      } // return key in current index
    } // for loop
    // condition check - does the arr even have the key
    throw new KeyNotFoundException();
  } // find(K)
} // class AssociativeArray