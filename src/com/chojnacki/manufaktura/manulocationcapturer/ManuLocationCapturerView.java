/*
 * ManuLocationCapturerView.java
 */

package com.chojnacki.manufaktura.manulocationcapturer;

import java.awt.Component;
import java.awt.Point;
import java.io.IOException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * The application's main frame.
 */
public class ManuLocationCapturerView extends FrameView {
    private int selectedIndex;

    public ManuLocationCapturerView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ManuLocationCapturerApp.getApplication().getMainFrame();
            aboutBox = new ManuLocationCapturerAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ManuLocationCapturerApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        exitButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        currentProperty = new javax.swing.JTextField();
        currentPropertyLabel = new javax.swing.JLabel();
        choosePropertyLabel = new javax.swing.JLabel();
        propertyList = new javax.swing.JScrollPane();
        propertiesList = new javax.swing.JList();
        openFileLabel = new javax.swing.JLabel();
        openFileTextField = new javax.swing.JTextField();
        propertiesFileLabel = new javax.swing.JLabel();
        propertiesFileTextField = new javax.swing.JTextField();
        imagePanel = new com.chojnacki.manufaktura.manulocationcapturer.ImagePanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.chojnacki.manufaktura.manulocationcapturer.ManuLocationCapturerApp.class).getContext().getResourceMap(ManuLocationCapturerView.class);
        menuPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("menuPanel.border.title"))); // NOI18N
        menuPanel.setName("menuPanel"); // NOI18N
        menuPanel.setLayout(new java.awt.GridBagLayout());

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.chojnacki.manufaktura.manulocationcapturer.ManuLocationCapturerApp.class).getContext().getActionMap(ManuLocationCapturerView.class, this);
        exitButton.setAction(actionMap.get("quit")); // NOI18N
        exitButton.setName("exitButton"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        menuPanel.add(exitButton, gridBagConstraints);

        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        menuPanel.add(saveButton, gridBagConstraints);

        nextButton.setAction(actionMap.get("nextLocation")); // NOI18N
        nextButton.setText(resourceMap.getString("nextButton.text")); // NOI18N
        nextButton.setName("nextButton"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        menuPanel.add(nextButton, gridBagConstraints);

        previousButton.setAction(actionMap.get("previousLocation")); // NOI18N
        previousButton.setText(resourceMap.getString("previousButton.text")); // NOI18N
        previousButton.setName("previousButton"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        menuPanel.add(previousButton, gridBagConstraints);

        currentProperty.setText(resourceMap.getString("currentProperty.text")); // NOI18N
        currentProperty.setName("currentProperty"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        menuPanel.add(currentProperty, gridBagConstraints);

        currentPropertyLabel.setText(resourceMap.getString("currentPropertyLabel.text")); // NOI18N
        currentPropertyLabel.setName("currentPropertyLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        menuPanel.add(currentPropertyLabel, gridBagConstraints);

        choosePropertyLabel.setText(resourceMap.getString("choosePropertyLabel.text")); // NOI18N
        choosePropertyLabel.setName("choosePropertyLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 3, 0);
        menuPanel.add(choosePropertyLabel, gridBagConstraints);

        propertyList.setName("propertyList"); // NOI18N

        propertiesList.setModel(new DefaultListModel());
        propertiesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        propertiesList.setName("propertiesList"); // NOI18N
        propertiesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                propertiesListValueChanged(evt);
            }
        });
        propertyList.setViewportView(propertiesList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        menuPanel.add(propertyList, gridBagConstraints);

        openFileLabel.setText(resourceMap.getString("openFileLabel.text")); // NOI18N
        openFileLabel.setName("openFileLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        menuPanel.add(openFileLabel, gridBagConstraints);

        openFileTextField.setText(resourceMap.getString("openFileTextField.text")); // NOI18N
        openFileTextField.setName("openFileTextField"); // NOI18N
        openFileTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                openFileTextFieldMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        menuPanel.add(openFileTextField, gridBagConstraints);

        propertiesFileLabel.setText(resourceMap.getString("propertiesFileLabel.text")); // NOI18N
        propertiesFileLabel.setName("propertiesFileLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        menuPanel.add(propertiesFileLabel, gridBagConstraints);

        propertiesFileTextField.setName("propertiesFileTextField"); // NOI18N
        propertiesFileTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                propertiesFileTextFieldMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        menuPanel.add(propertiesFileTextField, gridBagConstraints);

        imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("imagePanel.border.title"))); // NOI18N
        imagePanel.setName("imagePanel"); // NOI18N
        imagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                imagePanelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imagePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 694, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void openFileTextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openFileTextFieldMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (((Component)evt.getSource()).isEnabled()) {
                JFileChooser fileInputChooser = new JFileChooser();
                FileFilter fileFilter = new ParametrizedFileFilter("config.acceptedFiles", "config.acceptedFilesDesc") ;
                fileInputChooser.setFileFilter(fileFilter);
                int answer = fileInputChooser.showOpenDialog((Component) evt.getSource());
                if (answer != JFileChooser.CANCEL_OPTION) {
                    boolean accept = properFile(fileInputChooser.getSelectedFile(), "config.acceptedFiles");
                    if (accept) {
                        openFileTextField.setText(fileInputChooser.getSelectedFile().getAbsolutePath());
                        try {
                            imagePanel.setImage(ImageIO.read(fileInputChooser.getSelectedFile()));
                            imagePanel.repaint();
                            ManuLocationCapturerApp.getApplication().setInputGraphicFile(fileInputChooser.getSelectedFile());
                            ManuLocationCapturerApp.getApplication().getMainFrame().setTitle(propertiesFileTextField.getText()
                                    + " | " + openFileTextField.getText());
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                            ManuLocationCapturerApp.getParametrizedString("errorDialog.errorWhileOpening.message", ManuLocationCapturerView.class,
                                    fileInputChooser.getSelectedFile().getAbsolutePath()),
                            ManuLocationCapturerApp.getParametrizedString("errorDialog.errorWhileOpening.title", ManuLocationCapturerView.class),
                            JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                                ManuLocationCapturerApp.getParametrizedString("errorDialog.invalidInputExtension.message", ManuLocationCapturerView.class,
                                ManuLocationCapturerApp.getParametrizedString("config.acceptedInput", ManuLocationCapturerView.class)),
                                ManuLocationCapturerApp.getParametrizedString("errorDialog.invalidInputExtension.title", ManuLocationCapturerView.class),
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_openFileTextFieldMouseReleased

    private void imagePanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseReleased
        if (imagePanel.isImageLoaded()){
            if (evt.getButton() == MouseEvent.BUTTON1) {
                Point point = ((ImagePanel)evt.getSource()).getMousePosition();
                ManuLocationCapturerApp.getApplication().addValue(currentProperty.getText(),
                        (point.x - (ManuLocationCapturerApp.getApplication().getImageOffset()[0]) - 1)
                        + "," + (point.y - (ManuLocationCapturerApp.getApplication().getImageOffset()[1]) - 1));
                System.out.println((point.x - (ManuLocationCapturerApp.getApplication().getImageOffset()[0]) - 1)
                        + "," + (point.y - (ManuLocationCapturerApp.getApplication().getImageOffset()[1]) - 1));
                DefaultListModel model = (DefaultListModel) propertiesList.getModel();
                String selectedValue = (String) propertiesList.getSelectedValue();
                selectedIndex = propertiesList.getSelectedIndex();
                model.remove(propertiesList.getSelectedIndex());
                model.addElement(selectedValue);
                propertiesList.setSelectedIndex(selectedIndex);
            } else {
                nextLocation();
            }
        }
    }//GEN-LAST:event_imagePanelMouseReleased

    private void propertiesFileTextFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_propertiesFileTextFieldMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (((Component)evt.getSource()).isEnabled()) {
                JFileChooser fileInputChooser = new JFileChooser();
                FileFilter fileFilter = new ParametrizedFileFilter("config.propertiesExtension", "config.propertiesDescExtension") ;
                fileInputChooser.setFileFilter(fileFilter);
                int answer = fileInputChooser.showOpenDialog((Component) evt.getSource());
                if (answer != JFileChooser.CANCEL_OPTION) {
                    File selectedFile = fileInputChooser.getSelectedFile();
                    boolean accept = properFile(selectedFile, "config.propertiesExtension");
                    if (accept) {
                        propertiesFileTextField.setText(selectedFile.getAbsolutePath());
                        try {
                            ManuLocationCapturerApp.getApplication().clearResources();
                            fillList(propertiesList, selectedFile);
                            ManuLocationCapturerApp.getApplication().getMainFrame().setTitle(propertiesFileTextField.getText()
                                    + " | " + openFileTextField.getText());
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                            ManuLocationCapturerApp.getParametrizedString("errorDialog.errorWhileOpening.message", ManuLocationCapturerView.class,
                                    fileInputChooser.getSelectedFile().getAbsolutePath()),
                            ManuLocationCapturerApp.getParametrizedString("errorDialog.errorWhileOpening.title", ManuLocationCapturerView.class),
                            JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                                ManuLocationCapturerApp.getParametrizedString("errorDialog.invalidInputExtension.message", ManuLocationCapturerView.class,
                                ManuLocationCapturerApp.getParametrizedString("config.propertiesExtension", ManuLocationCapturerView.class)),
                                ManuLocationCapturerApp.getParametrizedString("errorDialog.invalidInputExtension.title", ManuLocationCapturerView.class),
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_propertiesFileTextFieldMouseReleased

    private void propertiesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_propertiesListValueChanged
        JList source = (JList) evt.getSource();
        String selectedValue = (String) source.getSelectedValue();
        currentProperty.setText(selectedValue);
        statusMessageLabel.setText(selectedValue);
        //propertyList.getVerticalScrollBar().setValue(propertyList.getVerticalScrollBar().getUnitIncrement() * selectedIndex * 16);
    }//GEN-LAST:event_propertiesListValueChanged

    private void fillList(JList list, File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        DefaultListModel model = (DefaultListModel) list.getModel();
        PropertyResourceBundle propertyResource = new PropertyResourceBundle(reader);
        Enumeration<String> keys = propertyResource.getKeys();
        String key;
        List<String> keyList = new ArrayList<String>();
        while(keys.hasMoreElements()) {
            key = keys.nextElement();
            keyList.add(key);
        }
        Collections.sort(keyList);
        for (Iterator<String> it = keyList.iterator(); it.hasNext();) {
            key = it.next();
            model.addElement(key);
            ManuLocationCapturerApp.getApplication().addValue(key, "");
        }
        list.setSelectedIndex(0);
    }

    protected boolean properFile(File file, String keyForExtensions) {
        boolean accept = false;
        if (file != null) {
            String fileNames[] = file.getName().split("\\.");
            if (fileNames.length != 1) {
                String acceptedExtensions[] = ManuLocationCapturerApp.getParametrizedString(keyForExtensions, ManuLocationCapturerView.class).split(",");
                String currentExtension;
                for (int i = 0; i < acceptedExtensions.length; i++) {
                    currentExtension = acceptedExtensions[i].trim();
                    if (currentExtension.equalsIgnoreCase(fileNames[1])) {
                        accept = true;
                        break;
                    }
                }
            }
        }
        return accept;
    }

    @Action
    public void previousLocation() {
        if (propertiesList.getModel().getSize() != 0) {
            if (selectedIndex > 0) {
                --selectedIndex;
            } else if (selectedIndex == -1) {
                selectedIndex = propertiesList.getModel().getSize() - 1;
            }else {
                JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                        ManuLocationCapturerApp.getParametrizedString("infoDialog.endOfList.message", ManuLocationCapturerView.class),
                        ManuLocationCapturerApp.getParametrizedString("infoDialog.endOfList.title", ManuLocationCapturerView.class), JOptionPane.INFORMATION_MESSAGE);
            }
            propertiesList.setSelectedIndex(selectedIndex);
        }
    }

    @Action
    public void nextLocation() {
        if (propertiesList.getModel().getSize() != 0) {
            if (selectedIndex < propertiesList.getModel().getSize() - 1) {
                ++selectedIndex;
            } else {
                JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                        ManuLocationCapturerApp.getParametrizedString("infoDialog.endOfList.message", ManuLocationCapturerView.class),
                        ManuLocationCapturerApp.getParametrizedString("infoDialog.endOfList.title", ManuLocationCapturerView.class), JOptionPane.INFORMATION_MESSAGE);
            }
            propertiesList.setSelectedIndex(selectedIndex);
        }
    }

    @Action
    public void saveAction() {
        JFileChooser fileSaver = new JFileChooser();
        FileFilter fileFilter = new ParametrizedFileFilter("config.propertiesExtension", "config.propertiesDescExtension") ;
        fileSaver.setFileFilter(fileFilter);
        int openAnswer = fileSaver.showSaveDialog(saveButton);
        if (openAnswer != JFileChooser.CANCEL_OPTION) {
            boolean accept = properFile(fileSaver.getSelectedFile(), "config.propertiesExtension");
            if (accept) {
                boolean overrideFile = false;
                if (fileSaver.getSelectedFile().exists()) {
                     int overrideAnswer = JOptionPane.showConfirmDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                             ManuLocationCapturerApp.getParametrizedString("questionDialog.overrideFile.message", ManuLocationCapturerView.class,
                                fileSaver.getSelectedFile().getName()),
                             ManuLocationCapturerApp.getParametrizedString("questionDialog.overrideFile.title", ManuLocationCapturerView.class),
                             JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                     overrideFile = overrideAnswer == JOptionPane.YES_OPTION ? true : false;
                }
                if (overrideFile || !fileSaver.getSelectedFile().exists()) {
                    ManuLocationCapturerApp.getApplication().setOutputPropertiesFile(fileSaver.getSelectedFile());
                    try {
                        writePropertiesToFile();
                        statusMessageLabel.setText(ManuLocationCapturerApp.getParametrizedString("savingFileFinished", ManuLocationCapturerView.class));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                        ManuLocationCapturerApp.getParametrizedString("errorDialog.errorWhileSaving.message", ManuLocationCapturerView.class,
                                fileSaver.getSelectedFile().getAbsolutePath()),
                        ManuLocationCapturerApp.getParametrizedString("errorDialog.errorWhileSaving.title", ManuLocationCapturerView.class),
                        JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(ManuLocationCapturerApp.getApplication().getMainFrame(),
                        ManuLocationCapturerApp.getParametrizedString("errorDialog.invalidExtension.message", ManuLocationCapturerView.class,
                            "output",
                            ManuLocationCapturerApp.getParametrizedString("config.acceptedFiles", ManuLocationCapturerView.class)),
                        ManuLocationCapturerApp.getParametrizedString("errorDialog.invalidExtension.title", ManuLocationCapturerView.class),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel choosePropertyLabel;
    private javax.swing.JTextField currentProperty;
    private javax.swing.JLabel currentPropertyLabel;
    private javax.swing.JButton exitButton;
    private com.chojnacki.manufaktura.manulocationcapturer.ImagePanel imagePanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel openFileLabel;
    private javax.swing.JTextField openFileTextField;
    private javax.swing.JButton previousButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel propertiesFileLabel;
    private javax.swing.JTextField propertiesFileTextField;
    private javax.swing.JList propertiesList;
    private javax.swing.JScrollPane propertyList;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

    private void writePropertiesToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ManuLocationCapturerApp.getApplication().getOutputPropertiesFile()));
        HashMap<String, String> resources = ManuLocationCapturerApp.getApplication().getResources();
        Set<String> keys = resources.keySet();
        String key;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            key = it.next();
            writer.write(key + " = " + resources.get(key) + "\n");
        }
        writer.close();
    }

    class ParametrizedFileFilter extends FileFilter {
        private String acceptedExtensionDescKey;
        private String acceptedExtensionKey;

        public ParametrizedFileFilter(String acceptedExtensionKey, String acceptedExtensionDescKey) {
            this.acceptedExtensionKey = acceptedExtensionKey;
            this.acceptedExtensionDescKey = acceptedExtensionDescKey;
        }

        @Override
        public boolean accept(File file) {
            String fileNames[] = file.getName().split("\\.");
            boolean accept = false;

            if (file.isDirectory()) {
                accept = true;
            } else if (fileNames.length == 2) {
                String acceptedExtensions[] = ManuLocationCapturerApp.getParametrizedString(acceptedExtensionKey, ManuLocationCapturerView.class).split(",");
                String currentExtension;
                for (int i = 0; i < acceptedExtensions.length; i++) {
                    currentExtension = acceptedExtensions[i].trim();
                    if (currentExtension.equalsIgnoreCase(fileNames[1])) {
                        accept = true;
                        break;
                    }
                }
            }
            return accept;
        }

        @Override
        public String getDescription() {
            return ManuLocationCapturerApp.getParametrizedString(acceptedExtensionDescKey, ManuLocationCapturerView.class);
        }
    }
}