/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.chojnacki.manufaktura.manulocationcapturer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Dawid Chojnacki
 */
public class ImagePanel extends JPanel {

    BufferedImage image;

    public ImagePanel() {
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            g.drawImage(image, ManuLocationCapturerApp.getApplication().getImageOffset()[0], ManuLocationCapturerApp.getApplication().getImageOffset()[1], this);
        }
    }

    public boolean isImageLoaded() {
        boolean result = false;
        if (image != null) {
            result = true;
        }
        return result;
    }
}
