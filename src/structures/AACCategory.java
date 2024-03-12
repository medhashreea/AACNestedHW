package structures;
/**
 * @author Medhashree Adhikari
 * 
 * Purpose:
 *   The AACCategory represents a single category and its items. It 
 *   works to store the mapping between the image location and the text 
 *   that should be spoken and the name of the category.
 */

public class AACCategory {
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
  //AssociativeArray<String, String> txtArr;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Creates a new empty category with the given name
   */
  public AACCategory(String category) {
    this.category = category;
    this.txtArr = new AssociativeArray<String, String>();
  } // AACCategory(String name)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+
  /**
   * Adds the mapping of the imageLoc to the text to the category.
   * @throws NullKeyException 
   */
  public void addItem​(String imageLoc, String text) throws NullKeyException {
      this.txtArr.set(imageLoc, text);
  } // addItem​(String imageLoc, String text)                                                  

  /**
   * Returns the name of the category
   */
  public String getCategory() {
    return this.category;
  } // getCategory()

  /**
   * Returns an array of all the images in the category
   */
  public String[] getImages() {
    return this.txtArr.getAllKeys();
  } // getImageLocs()

  /**
   * Returns the text associated with the given image loc in this category
   */
  public String getText(String imageLoc) {
    String txt = "";
    try {
      for (int i = 0; i < txtArr.size(); i++) {
        if(txtArr.pairs[i].thisKey() == imageLoc) {
          txt = txtArr.get(txtArr.pairs[i].thisKey());
          return txt;
        }
      } // for loop
    } catch (KeyNotFoundException e) {}
    return txt;
    // try {
    //   this.txtArr.get(imageLoc);
    // } catch (KeyNotFoundException e) {
    // }
    // return txt;
  } // getText(String imageLoc)
  
  /**
   * Determines if the provided images is stored in the category
   */
  public boolean hasImage​(String imageLoc) {
    return txtArr.hasKey(imageLoc);
  } // hasImage​(String imageLoc)
} // AACCategory Class