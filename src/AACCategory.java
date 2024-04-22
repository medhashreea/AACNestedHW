/**
 * The AACCategory represents a single category and its items. It
 * works to store the mapping between the image location and the text
 * that should be spoken and the name of the category.
 * 
 * @author Medhashree Adhikari
 * 
 */
public class AACCategory extends Object {
  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The name of the category
   */
  String category;

  /**
   * Creates a new associative array
   */
  AssociativeArray<String, String> txtArr;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a new empty category with the given name
   */
  public AACCategory(String category) {
    this.category = category;
    this.txtArr = new AssociativeArray<String, String>();
  } // AACCategory(String)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Adds the mapping of the imageLoc to the text to the category
   * 
   * @param imageLoc
   * @param text
   * @throws NullKeyException
   */
  public void addItem(String imageLoc, String text) {
    try {
      this.txtArr.set(imageLoc, text);
    } catch (NullKeyException e) {
      e.printStackTrace();
    }
  } // addItem(String, String)

  /**
   * Returns the name of the category
   * 
   * @return category
   */
  public String getCategory() {
    if(this.category == null) {
      return null;
    }
    return this.category;
  } // getCategory()

  /**
   * Returns an array of all the images in the category
   * 
   * @return keyArr
   */
  public String[] getImages() {
    try {
      return this.txtArr.getAllKeys();
    } catch (KeyNotFoundException e) {
      return new String[] {};
    }
  } // getImages()

  /**
   * Returns the text associated with the given image loc in this category
   * 
   * @param imageLoc
   * @return
   * @throws KeyNotFoundException
   */
  public String getText(String imageLoc) {
    try {
      return this.txtArr.get(imageLoc);
    } catch (Exception e) {
      return "";
    }
  } // getText(String)

  /**
   * Determines if the provided images is stored in the category
   * 
   * @param imageLoc
   * @return boolean
   */
  public boolean hasImage​(String imageLoc) {
    return this.txtArr.hasKey(imageLoc);
  } // hasImage​(String)

} // class AACCategory