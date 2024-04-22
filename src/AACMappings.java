import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
  public AACMappings(String filename) {
    try {
      // organizing the home category to add things
      Scanner input = new Scanner(new File(filename)); // create a Scanner object
      this.homeCategory = new AACCategory("");
      this.categories = new AssociativeArray<String, AACCategory>();
      this.curCategory = this.homeCategory;
      categories.set("", curCategory);

      while (input.hasNext()) {
        String _line = input.nextLine();
        // split the line by the space into 2 parts
        String[] stringSplit = _line.split(" ", 2);

        // check if it is a category or item
        // if the line doesn't start with ">", the line is a category
        if (_line.charAt(0) != '>') {
          // set current category as the given category naem
          curCategory = new AACCategory(stringSplit[1]);
          categories.set(stringSplit[0], curCategory);
          this.homeCategory.addItem(stringSplit[0], stringSplit[1]);
          // if not a category, it is a inner image
        } else {
          this.curCategory.addItem(stringSplit[0].substring(1), stringSplit[1]);
          // reset the first string to the string without the ">"
        } // if/else
      } // while loop
      input.close();
    } catch (FileNotFoundException | NullKeyException e) {
      System.err.println(e.toString());
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
   * @throws NullKeyException
   */
  public void add​(String imageLoc, String text) {
    try {
      if (this.curCategory == null) {
        this.categories.set(imageLoc, new AACCategory(text));
        // this.curCategory.addItem(imageLoc, text);
      } else {
        this.curCategory.addItem(imageLoc, text);
      }
    } catch (NullKeyException e) {
    }
  } // add(String, String)

  /**
   * Gets the current category
   * 
   * @return category
   */
  public String getCurrentCategory() {
    if (this.curCategory == null) {
      return null;
    }
    return this.curCategory.getCategory();
  } // getCurrentCategory()

  /**
   * Provides an array of all the images in the current category
   * 
   * @return
   */
  public String[] getImageLocs() {
    if (this.curCategory != null) {
      return this.curCategory.getImages();
    }
    return null;
  } // getImageLocs()

  /**
   * Given the image location selected, it determines the associated text with the
   * image
   * 
   * @param imageLoc
   * @return
   * @throws KeyNotFoundException
   */
  public String getText​(String imageLoc) {
    if (isCategory​(imageLoc)) {
      try {
        this.curCategory = this.categories.get(imageLoc);
        return this.curCategory.getCategory();
      } catch (KeyNotFoundException e) {
      }
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
    return this.categories.hasKey(imageLoc);
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
    try {
      PrintWriter pen = new PrintWriter(new FileWriter(filename));
      // loop between categories
      for (String catName : categories.getAllKeys()) {
        // loop inside a cateory
        AACCategory catHold = this.categories.get(catName);
        pen.println(catName + " " + catHold.getCategory());
        for (String items : catHold.getImages()) {
          if (catHold != homeCategory) {
            pen.println(">" + items + " " + catHold.getText(items));
          }
        }
      }
      pen.close();
    } catch (KeyNotFoundException e) {
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }
  } // writeToFile​(String)

} // class AACMappings