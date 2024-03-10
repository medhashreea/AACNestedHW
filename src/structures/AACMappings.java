package structures;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Medhashree Adhikari
 * 
 * Purpose:
 *   The AACMappings class keeps track of the complete set of AAC 
 *   mappings (multiple ACCCategories). It storea the mapping of the 
 *   images on the home page to the AACCategories. The class keeps 
 *   track of the current category that is being displayed.
 */

public class AACMappings {
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
  
 // AssociativeArray<String, String> imageArr = new AssociativeArray<>();

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * Creates a new empty filename with the given filename
   */
  public AACMappings(String filename) {
    

    try {
      File file = new File(filename);
      this.homeCategory = new AACCategory("");
      curCategory = homeCategory;
      this.categories = new AssociativeArray<String, AACCategory>();
      // organizing the home category to add things 
      //categories.set("", curCategory);
      Scanner input = new Scanner(file); // create a Scanner object
      while (input.hasNext()) {
        String _line_ = input.nextLine();
        // split the line by the space into 2 parts
        String[] stringSplit = _line_.split(" ", 2);
        
        // check if it is a category or item
        // if the line doesn't start with ">", the line is a category
        if (_line_.charAt(0) != '>') {
          // set current category as the given category naem
          this.curCategory = new AACCategory(stringSplit[1]);
          categories.set(stringSplit[0], curCategory);
          homeCategory.addItem​(stringSplit[0], stringSplit[1]);
        // if not a category, it is a inner image
        } else {
          // reset the first string to the string without the ">"
          stringSplit[0] = stringSplit[0].substring(1);
          // create current
          curCategory.addItem​(stringSplit[0], stringSplit[1]);
        }
      } // while loop
      input.close();
    } catch (FileNotFoundException e) {
    } catch (NullKeyException e) { }
  } // AACMappings(String filename)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+
  /**
   * Adds the mapping to the current category (or the default 
   * category if that is the current category)
   */
  public void add(String imageLoc, String text) {
    try {
      if (curCategory == null) {
        this.categories.set(imageLoc, new AACCategory(text));
      } else {
        curCategory.addItem​(imageLoc, text);
      }
    } catch (NullKeyException e) {
      e.printStackTrace();
    }
  } // add​(String imageLoc, String text)

  /**
   * Gets the current category
   */
  public String getCurrentCategory() {
    if (curCategory == null) {
      return null;
    }
    return curCategory.getCategory();
  } //getCurrentCategory()

  /**
   * Provides an array of all the images in the current category
   */
  public String[] getImageLocs() {
    if(curCategory != null) {
      return curCategory.getImages();
    }
    return null;
  } // getImageLocs()

  /**
   * Given the image location selected, it determines the associated 
   * text with the image. If the image provided is a category, it also 
   * updates the AAC's current category to be the category associated 
   * with that image
   */
  public String getText(String imageLoc) {
    // check if in home page usin comparisun to null
    if (isCategory​(imageLoc)) {
      try {
        this.curCategory = categories.get(imageLoc);
        return this.curCategory.getCategory();
      } catch (KeyNotFoundException e) {
      }
    }
    // else, return the text to be spoken that is associated with that image
    return curCategory.getText(imageLoc);
  } // getImageLocs()

  /**
   * Determines if the image represents a category or text to speak
   */
  public boolean isCategory​(String imageLoc) {
    return categories.hasKey(imageLoc);
  } // isCategory​(String imageLoc)

  /**
   * Resets the current category of the AAC back to the default category
   */
  public void reset() {
    this.curCategory = homeCategory;
  } // reset()

  /**
   * Writes the ACC mappings stored to a file.
   */
  public void writeToFile(String filename) {
    try 
    (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      for(int i = 0; i < categories.size(); i++) {
        String category = categories.pairs[i].key;
        for (String locate : this.categories.get(category).getImages()) {
          writer.write(locate + this.categories.get(category).getText(locate) + '\n');
        }
      }
    } catch (KeyNotFoundException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  } // writeToFile(filename)
} // AACMappings Class

// for (String category : categories.getAllKeys()) {
//   for (String locate : this.categories.get(category).getImages()) {
//     writer.write(locate + this.categories.get(category).getText(locate) + '\n');
//   }
// }