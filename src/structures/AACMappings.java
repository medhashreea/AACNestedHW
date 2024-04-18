import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The AACMappings class keeps track of the complete set of AAC
 * mappings (multiple ACCCategories). It storea the mapping of the
 * images on the home page to the AACCategories. The class keeps
 * track of the current category that is being displayed.
 * 
 * @author Medhashree Adhikari
 */

public class AACMappings extends Object {
  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The home category
   */
  AACCategory homeCategory;

  /**
   * The current category
   */
  AACCategory curCategory;

  /**
   * Creates a new associative array
   */
  AssociativeArray<String, AACCategory> categories;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a new empty filename with the given filename
   */
  public AACMappings(String filename) throws FileNotFoundException {
    File file = new File(filename);
    this.homeCategory = new AACCategory("");
    this.categories = new AssociativeArray<String, AACCategory>();

    try {
      // organizing the home category to add things
      Scanner input = new Scanner(file); // create a Scanner object

      while (input.hasNext()) {
        String _line = input.nextLine();
        // split the line by the space into 2 parts
        String[] stringSplit = _line.split(" ", 2);

        // check if it is a category or item
        // if the line doesn't start with ">", the line is a category
        if (_line.charAt(0) != '>') {
          // set current category as the given category naem
          curCategory = this.homeCategory;
          this.add​(stringSplit[0], stringSplit[1]);
          curCategory = this.categories.get(stringSplit[0]);
          // if not a category, it is a inner image
        } else {
          this.add​(stringSplit[0].substring(1), stringSplit[1]);
          // reset the first string to the string without the ">"
        } // if/else

      } // while loop

      input.close();
    } catch (FileNotFoundException | KeyNotFoundException e) {
    }

    reset();
  } // AACMappings(String filename)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Adds the mapping to the current category (or the default category if that is
   * the current category)
   * 
   * @param imageLoc
   * @param text
   * @throws KeyNotFoundException
   * @throws NullKeyException
   */
  public void add​(String imageLoc, String text) {
    try {
      if (this.curCategory == this.homeCategory) {
        this.categories.set(text, new AACCategory(text));
        this.curCategory.addItem(imageLoc, text);
      } else {
        this.curCategory.addItem(imageLoc, text);
      }
    } catch (Exception e) {
    }
  } // add(String, String)

  /**
   * Gets the current category
   * 
   * @return category
   */
  public String getCurrentCategory() {
    return this.curCategory.getCategory();
  } // getCurrentCategory()

  /**
   * Provides an array of all the images in the current category
   * 
   * @return
   */
  public String[] getImageLocs() {
    return this.curCategory.getImages();
  } // getImageLocs()

  /**
   * Given the image location selected, it determines the associated text with the
   * image
   * 
   * @param imageLoc
   * @return
   * @throws KeyNotFoundException
   */
  public String getText​(String imageLoc) throws KeyNotFoundException {
    // check if in the home page
    if (this.getCurrentCategory() == this.homeCategory.getCategory()) {
      // if in homepage, get the categories associated text
      String txt = this.curCategory.getText(imageLoc);
      try {
        this.curCategory = this.categories.get(imageLoc);
      } catch (KeyNotFoundException e) {
        e.printStackTrace();
      }
      return txt;
    }

    return this.curCategory.getText(imageLoc);
  } // getText​(String)

  /**
   * Determines if the image represents a category or text to speak
   * 
   * @param imageLoc
   * @return
   */
  public boolean isCategory​(String imageLoc) {
    return this.homeCategory.hasImage​(imageLoc);
  } // isCategory​(String)

  /**
   * Resets the current category of the AAC back to the default category
   */
  public void reset() {
    this.curCategory = this.homeCategory;
  } // reset()

  /**
   * Writes the ACC mappings stored to a file
   * 
   * @param filename
   * @throws KeyNotFoundException
   * @throws IOException
   */
  public void writeToFile​(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      for (int i = 0; i < this.categories.size(); i++) {
        String category = categories.pairs[i].key;
        for (String locate : this.categories.get(category).getImages()) {
          writer.write(">" + locate + " " +
              this.categories.get(category).getText(locate) + '\n');
        }
      }
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  } // writeToFile​(String)

} // class AACMappings

// -----------------------------------------------------
// if (isCategory​(imageLoc)) {
// String txt = this.curCategory.getText(imageLoc);
// this.curCategory = this.categories.get(txt);
// return txt;
// } else {
// String txt = this.curCategory.getText(imageLoc);
// return txt;
// } // gettext