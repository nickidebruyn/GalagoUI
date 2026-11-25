package com.galago.ui.saves;

import com.jme3.system.JmeSystem;

import java.io.*;
import java.util.logging.Logger;

/**
 * This class will handle all player data. Save user settings with this class.
 *
 * @author NideBruyn
 */
public class DesktopGameSave extends GameSave {

  public DesktopGameSave(String fileName) {
    super(fileName);
  }

  /**
   * Read any saved data from the file system.
   */
  public void read() {
    File folder = JmeSystem.getStorageFolder();

    if (folder != null && folder.exists()) {
      try {
        file = new File(folder.getAbsolutePath() + File.separator + fileName);
        if (file.exists()) {
          FileInputStream fileIn = new FileInputStream(file);
          ObjectInputStream in = new ObjectInputStream(fileIn);
          try {
            gameData = (GameData) in.readObject();

          } catch (ClassNotFoundException ex) {
            Logger.getLogger(DesktopGameSave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
          }

        } else {
          file.createNewFile();
          gameData = new GameData();
          save();
        }

      } catch (IOException ex) {
        Logger.getLogger(DesktopGameSave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
    } else {
    }
  }

  /**
   * Write game data to the file system.
   */
  public void save() {
    File folder = JmeSystem.getStorageFolder();

    if (folder != null && folder.exists()) {
      if (file != null) {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {
          fileOut = new FileOutputStream(file);
          out = new ObjectOutputStream(fileOut);
          out.writeObject(gameData);

        } catch (FileNotFoundException ex) {
          Logger.getLogger(DesktopGameSave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IOException ex) {
          Logger.getLogger(DesktopGameSave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } finally {
          if (fileOut != null) {
            try {
              fileOut.close();
            } catch (IOException ex) {
              Logger.getLogger(DesktopGameSave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
          }


        }

      }
    }
  }
}
