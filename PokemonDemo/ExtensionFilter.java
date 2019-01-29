package PokemonDemo;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A special subclass of FileFilter to make sure that when the user
 * tries to save or load a file, only special "Pokemon Save Files" are
 * visible in the parent folder.
 * 
 * @author mreiss
 */
public class ExtensionFilter extends FileFilter {
    
	private String _extension;
    private String _description;

    /**
     * Initializes the appropriate file description ("Pokemon Save File") and
     * extension (".pkmn") for a new ExtensionFilter
     * 
     * @param description
     * @param extensions
     */
    public ExtensionFilter(String description, String extensions) {
      _description = description;
      _extension = extensions;
    }

    /**
     * A method from the interface FileFilter that is called by the 
     * program when selecting which files to display in a folder.
     */
    public boolean accept(File file) {
      if (file.isDirectory()) {
        return true;
      }
      String path = file.getAbsolutePath();
      String ext = _extension;
      if (path.endsWith(ext) && (path.charAt(path.length() - ext.length()) == '.')){
    	  return true;
      }
      return false;
    }

    /**
     * Accessor for the description of a specific ExtensionFilter.
     */
    public String getDescription() {
      return _description;
    }
  }