/*
 * ManuLocationCapturerApp.java
 */

package com.chojnacki.manufaktura.manulocationcapturer;

import java.io.File;
import java.util.HashMap;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ManuLocationCapturerApp extends SingleFrameApplication {

    private File inputGraphicFile;
    private File outputFile;
    private HashMap<String, String> resources;
    private int[] imageOffset;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new ManuLocationCapturerView(this));
        resources = new HashMap<String, String>();
        imageOffset = new int[]{10,20};
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ManuLocationCapturerApp
     */
    public static ManuLocationCapturerApp getApplication() {
        return Application.getInstance(ManuLocationCapturerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ManuLocationCapturerApp.class, args);
    }

    public static String getParametrizedString(String parametrizedKey, Class objectClass, Object... parameters) {
        return (String) getApplication().getContext().getResourceMap(objectClass).getString(parametrizedKey, parameters);
    }

    void setInputGraphicFile(File selectedFile) {
        inputGraphicFile = selectedFile;
    }

    void setOutputPropertiesFile(File selectedFile) {
        outputFile = selectedFile;
    }

    public File getOutputPropertiesFile() {
        return outputFile;
    }

    public File getInputGraphicFile() {
        return inputGraphicFile;
    }

    public void clearResources() {
        resources.clear();
    }
    
    public void addValue(String key, String value) {
        resources.put(key, value);
    }

    public HashMap<String, String> getResources() {
        return resources;
    }

    public int[] getImageOffset() {
        return imageOffset;
    }
}
