package com.galago.ui.saves;

import com.jme3.system.JmeSystem;

import java.io.*;
import java.util.logging.Logger;

/**
 * This class will handle all player data. Save user settings with this class.
 *
 * @author NideBruyn
 */
public class GameSaves {

  private File file;
  private File levelfile;
  private String fileName = "defaultgame.save";
  private GameData gameData;

  /**
   * @param fileName
   */
  public GameSaves(String fileName) {
    this.fileName = fileName;
  }

  public GameData getGameData() {
    if (gameData == null) {
      gameData = new GameData();
    }
    return gameData;
  }

  public void setGameData(GameData gameData) {
    this.gameData = gameData;
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
            Logger.getLogger(GameSaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
          }

        } else {
          file.createNewFile();
          gameData = new GameData();
          save();
        }

      } catch (IOException ex) {
        Logger.getLogger(GameSaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
          Logger.getLogger(GameSaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IOException ex) {
          Logger.getLogger(GameSaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } finally {
          if (fileOut != null) {
            try {
              fileOut.close();
            } catch (IOException ex) {
              Logger.getLogger(GameSaves.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
          }


        }

      }
    }
  }
}
