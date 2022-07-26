package com.galago.ui.listeners;

/**
 *
 * @author NideBruyn
 */
public interface RewardAdListener {
    
    public void doAdRewarded(int amount, String type);
    
    public void doAdClosed();
    
    public void doAdLoaded();
    
}
