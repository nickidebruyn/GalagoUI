package com.galago.ui.saves;

import java.io.File;

/**
 * This class will handle all player data. Save user settings with this class.
 *
 * @author NideBruyn
 */
public abstract class GameSave {

  protected File file;
  protected File levelfile;
  protected String fileName = "defaultgame.save";
  protected GameData gameData;

  /**
   * @param fileName
   */
  public GameSave(String fileName) {
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
  public abstract void read();

  /**
   * Write game data to the file system.
   */
  public abstract void save();
}
