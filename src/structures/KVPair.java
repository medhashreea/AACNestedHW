/**
 * An easy way to store key/value pairs. We assume that other
 * classes will access fields directly.
 * 
 * @author Samuel A. Rebelsky
 */
class KVPair<K, V> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key.
   */
  private K key;

  /**
   * The value.
   */
  private V value;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty key/value pair.
   */
  KVPair() {
    this(null, null);
  } // KVPair()

  /**
   * Create a new key/value pair.
   */
  KVPair(K key, V value) {
    this.key = key;
    this.value = value;
  } // KVPair(K,V)

  // +------------------+--------------------------------------------
  // | Standard methods |
  // +------------------+

  public KVPair<K, V> clone() {
    return new KVPair<K, V>(this.key, this.value);
  } // clone()

  public String toString() {
    return "{ " + this.key.toString() + " : " + this.value.toString() + " }";
  } // toString()

  public K getK() {
    return this.key;
  } // getK() 

  public V getV() {
    return this.value;
  } // getV()

  public V setV(V value) {
    return this.value = value;
  } // getV()
} // class KVPair
