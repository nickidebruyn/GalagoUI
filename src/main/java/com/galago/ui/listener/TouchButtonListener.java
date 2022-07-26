/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galago.ui.listener;

/**
 * The listener which listens to TouchButton events.
 * 
 * @author nidebruyn
 */
public interface TouchButtonListener {
    
    public void doTouchDown(float touchX, float touchY, float tpf, String uid);
    
    public void doTouchUp(float touchX, float touchY, float tpf, String uid);
    
    public void doTouchMove(float touchX, float touchY, float tpf, String uid);
    
    public void doTouchCancel(float touchX, float touchY, float tpf, String uid);

    public void doHoverOver(float touchX, float touchY, float tpf, String uid);

    public void doHoverOff(float touchX, float touchY, float tpf, String uid);
    
}
