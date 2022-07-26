/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.galago.ui.listeners;

/**
 *
 * @author NideBruyn
 */
public interface PickListener {
    
    public void picked(PickEvent pickEvent, float tpf);
    
    public void drag(PickEvent pickEvent, float tpf);
    
}
