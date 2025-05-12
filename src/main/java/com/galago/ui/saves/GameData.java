package com.galago.ui.saves;

import java.io.Serializable;
import java.util.Properties;

/**
 * This class will be serialized when the game data is saved.
 *
 * @author NideBruyn
 */
public class GameData implements Serializable {

  private String playerName;
  private String gameName;
  private int score = 0;
  private int level = 0;
  private int completedLevel = 1;
  private boolean soundOn = true;
  private boolean musicOn = true;
  private boolean fxOn = true;
  private boolean debugOn = false;

  private boolean cameraShakeOn = false;

  private boolean joystickShakeOn = false;
  private Properties properties = new Properties();
  private boolean rated = false;
  private int gamesPlayed = 0;
  private boolean onlinePlayer = false;

  private float musicVolume = 0;
  private float soundVolume = 0;
  private int screenResolution = 0;
  private int visualFX = 0;
  private int coins = 0;


  public GameData() {

  }

  public boolean isDebugOn() {
    return debugOn;
  }

  public void setDebugOn(boolean debugOn) {
    this.debugOn = debugOn;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean isSoundOn() {
    return soundOn;
  }

  public void setSoundOn(boolean soundOn) {
    this.soundOn = soundOn;
  }

  public boolean isFxOn() {
    return fxOn;
  }

  public void setFxOn(boolean fxOn) {
    this.fxOn = fxOn;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getCompletedLevel() {
    return completedLevel;
  }

  public void setCompletedLevel(int completedLevel) {
    this.completedLevel = completedLevel;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public boolean isRated() {
    return rated;
  }

  public void setRated(boolean rated) {
    this.rated = rated;
  }

  public int getGamesPlayed() {
    return gamesPlayed;
  }

  public void setGamesPlayed(int gamesPlayed) {
    this.gamesPlayed = gamesPlayed;
  }

  public boolean isOnlinePlayer() {
    return onlinePlayer;
  }

  public void setOnlinePlayer(boolean onlinePlayer) {
    this.onlinePlayer = onlinePlayer;
  }

  public boolean isMusicOn() {
    return musicOn;
  }

  public void setMusicOn(boolean musicOn) {
    this.musicOn = musicOn;
  }

  public boolean isCameraShakeOn() {
    return cameraShakeOn;
  }

  public void setCameraShakeOn(boolean cameraShakeOn) {
    this.cameraShakeOn = cameraShakeOn;
  }

  public boolean isJoystickShakeOn() {
    return joystickShakeOn;
  }

  public void setJoystickShakeOn(boolean joystickShakeOn) {
    this.joystickShakeOn = joystickShakeOn;
  }

  public float getMusicVolume() {
    return musicVolume;
  }

  public void setMusicVolume(float musicVolume) {
    this.musicVolume = musicVolume;
  }

  public float getSoundVolume() {
    return soundVolume;
  }

  public void setSoundVolume(float soundVolume) {
    this.soundVolume = soundVolume;
  }

  public int getScreenResolution() {
    return screenResolution;
  }

  public void setScreenResolution(int screenResolution) {
    this.screenResolution = screenResolution;
  }

  public int getCoins() {
    return coins;
  }

  public void setCoins(int coins) {
    this.coins = coins;
  }

  public int getVisualFX() {
    return visualFX;
  }

  public void setVisualFX(int visualFX) {
    this.visualFX = visualFX;
  }
}
