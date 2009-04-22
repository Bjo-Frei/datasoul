/* 
 * Copyright 2005-2008 Samuel Mello & Eduardo Schnell
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; version 2 of the License.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 */

/*
 * TemplatePanel.java
 *
 * Created on February 25, 2006, 10:11 AM
 */

package datasoul.templates;

import datasoul.util.ObjectManager;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author  samuelm
 */
public class TemplatePanel extends javax.swing.JPanel {
    
    /** Creates new form TemplatePanel */
    public TemplatePanel() {
        initComponents();
        
        TemplateManager manager = TemplateManager.getInstance();
        manager.refreshAvailableTemplates();
        jTableTemplates.setModel( manager );
        
        templateEditorPanel1.setPropertiesTable(jTableProperties);
        
        lblTemplateName.setText( templateEditorPanel1.getTemplate().getName() );
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelTemplateEditor = new javax.swing.JPanel();
        lblTemplateName = new javax.swing.JLabel();
        templateEditorPanel1 = new datasoul.templates.TemplateEditorPanel();
        jLabel5 = new javax.swing.JLabel();
        jToolBar4 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnProperties = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel8 = new javax.swing.JLabel();
        btnAddText = new javax.swing.JButton();
        btnAddImage = new javax.swing.JButton();
        btnTimerProgress = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();
        btnDeleteItem = new javax.swing.JButton();
        btnMoveUp = new javax.swing.JButton();
        btnMoveDown = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        panelTemplates = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTemplates = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnLoad = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDeleteTemplate = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel6 = new javax.swing.JLabel();
        panelProperties = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel7 = new javax.swing.JLabel();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(250);

        panelTemplateEditor.setAutoscrolls(true);

        lblTemplateName.setText("jLabel2");

        org.jdesktop.layout.GroupLayout templateEditorPanel1Layout = new org.jdesktop.layout.GroupLayout(templateEditorPanel1);
        templateEditorPanel1.setLayout(templateEditorPanel1Layout);
        templateEditorPanel1Layout.setHorizontalGroup(
            templateEditorPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 796, Short.MAX_VALUE)
        );
        templateEditorPanel1Layout.setVerticalGroup(
            templateEditorPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 596, Short.MAX_VALUE)
        );

        jLabel5.setFont(jLabel5.getFont().deriveFont((jLabel5.getFont().getStyle() | java.awt.Font.ITALIC), jLabel5.getFont().getSize()-2));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("datasoul/internationalize"); // NOI18N
        jLabel5.setText(bundle.getString("Hold_Shift_down_for_resizing_items")); // NOI18N

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        jLabel2.setText(bundle.getString("Template")); // NOI18N
        jToolBar4.add(jLabel2);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/document-save.png"))); // NOI18N
        btnSave.setText(bundle.getString("Save")); // NOI18N
        btnSave.setToolTipText(bundle.getString("Save_template")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar4.add(btnSave);

        btnProperties.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/document-properties.png"))); // NOI18N
        btnProperties.setText(bundle.getString("Properties")); // NOI18N
        btnProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropertiesActionPerformed(evt);
            }
        });
        jToolBar4.add(btnProperties);
        jToolBar4.add(jSeparator2);

        jLabel8.setText("Add");
        jToolBar4.add(jLabel8);

        btnAddText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/insert-text.png"))); // NOI18N
        btnAddText.setText(bundle.getString("Text")); // NOI18N
        btnAddText.setToolTipText(bundle.getString("Add_a_text")); // NOI18N
        btnAddText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTextActionPerformed(evt);
            }
        });
        jToolBar4.add(btnAddText);

        btnAddImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/insert-image.png"))); // NOI18N
        btnAddImage.setText(bundle.getString("Image")); // NOI18N
        btnAddImage.setToolTipText(bundle.getString("Add_an_image_...")); // NOI18N
        btnAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImageActionPerformed(evt);
            }
        });
        jToolBar4.add(btnAddImage);

        btnTimerProgress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/stock_timer.png"))); // NOI18N
        btnTimerProgress.setText(bundle.getString("Timer")); // NOI18N
        btnTimerProgress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimerProgressActionPerformed(evt);
            }
        });
        jToolBar4.add(btnTimerProgress);
        jToolBar4.add(jSeparator1);

        jLabel4.setText(bundle.getString("Selected_Item")); // NOI18N
        jToolBar4.add(jLabel4);

        btnDeleteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/edit-delete.png"))); // NOI18N
        btnDeleteItem.setText(bundle.getString("Remove")); // NOI18N
        btnDeleteItem.setToolTipText(bundle.getString("Delete_item")); // NOI18N
        btnDeleteItem.setFocusable(false);
        btnDeleteItem.setMaximumSize(new java.awt.Dimension(91, 26));
        btnDeleteItem.setMinimumSize(new java.awt.Dimension(91, 26));
        btnDeleteItem.setPreferredSize(new java.awt.Dimension(91, 26));
        btnDeleteItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });
        jToolBar4.add(btnDeleteItem);

        btnMoveUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/stock_object-behind.png"))); // NOI18N
        btnMoveUp.setText(bundle.getString("To_Front")); // NOI18N
        btnMoveUp.setToolTipText(bundle.getString("Move_the_item_to_the_upper_layer")); // NOI18N
        btnMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveUpActionPerformed(evt);
            }
        });
        jToolBar4.add(btnMoveUp);

        btnMoveDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/stock_object-infront.png"))); // NOI18N
        btnMoveDown.setText(bundle.getString("To_Back")); // NOI18N
        btnMoveDown.setToolTipText(bundle.getString("Move_the_item_to_the_lowest_layer")); // NOI18N
        btnMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveDownActionPerformed(evt);
            }
        });
        jToolBar4.add(btnMoveDown);

        jLabel1.setText(bundle.getString("Template:")); // NOI18N

        org.jdesktop.layout.GroupLayout panelTemplateEditorLayout = new org.jdesktop.layout.GroupLayout(panelTemplateEditor);
        panelTemplateEditor.setLayout(panelTemplateEditorLayout);
        panelTemplateEditorLayout.setHorizontalGroup(
            panelTemplateEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTemplateEditorLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel5)
                .addContainerGap(619, Short.MAX_VALUE))
            .add(jToolBar4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 824, Short.MAX_VALUE)
            .add(panelTemplateEditorLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(14, 14, 14)
                .add(lblTemplateName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(590, Short.MAX_VALUE))
            .add(panelTemplateEditorLayout.createSequentialGroup()
                .addContainerGap()
                .add(templateEditorPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTemplateEditorLayout.setVerticalGroup(
            panelTemplateEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTemplateEditorLayout.createSequentialGroup()
                .add(jToolBar4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelTemplateEditorLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblTemplateName)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(templateEditorPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel5))
        );

        jScrollPane3.setViewportView(panelTemplateEditor);

        jSplitPane1.setRightComponent(jScrollPane3);

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(250);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelTemplates.setDoubleBuffered(false);

        jTableTemplates.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableTemplates);

        jToolBar1.setFloatable(false);
        jToolBar1.setOpaque(false);

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/document-open.png"))); // NOI18N
        btnLoad.setText(bundle.getString("Load")); // NOI18N
        btnLoad.setToolTipText(bundle.getString("Load_selected_template")); // NOI18N
        btnLoad.setBorderPainted(false);
        btnLoad.setFocusPainted(false);
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLoad);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/document-new.png"))); // NOI18N
        btnNew.setText(bundle.getString("New")); // NOI18N
        btnNew.setToolTipText(bundle.getString("Create_a_new_template")); // NOI18N
        btnNew.setBorderPainted(false);
        btnNew.setFocusPainted(false);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNew);

        btnDeleteTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/edit-delete.png"))); // NOI18N
        btnDeleteTemplate.setText(bundle.getString("Delete")); // NOI18N
        btnDeleteTemplate.setToolTipText(bundle.getString("Delete_selected_template")); // NOI18N
        btnDeleteTemplate.setBorderPainted(false);
        btnDeleteTemplate.setFocusPainted(false);
        btnDeleteTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTemplateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDeleteTemplate);

        jToolBar2.setFloatable(false);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/applications-graphics_small.png"))); // NOI18N
        jLabel6.setText(bundle.getString("Templates")); // NOI18N
        jToolBar2.add(jLabel6);

        org.jdesktop.layout.GroupLayout panelTemplatesLayout = new org.jdesktop.layout.GroupLayout(panelTemplates);
        panelTemplates.setLayout(panelTemplatesLayout);
        panelTemplatesLayout.setHorizontalGroup(
            panelTemplatesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelTemplatesLayout.setVerticalGroup(
            panelTemplatesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTemplatesLayout.createSequentialGroup()
                .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane2.setTopComponent(panelTemplates);

        jTableProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableProperties);

        jToolBar3.setFloatable(false);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/document-properties.png"))); // NOI18N
        jLabel7.setText(bundle.getString("Properties")); // NOI18N
        jToolBar3.add(jLabel7);

        org.jdesktop.layout.GroupLayout panelPropertiesLayout = new org.jdesktop.layout.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelPropertiesLayout.createSequentialGroup()
                .add(jToolBar3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
        );

        jSplitPane2.setBottomComponent(panelProperties);

        jSplitPane1.setLeftComponent(jSplitPane2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropertiesActionPerformed
        templateEditorPanel1.unselectItem();
    }//GEN-LAST:event_btnPropertiesActionPerformed

    private void btnTimerProgressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimerProgressActionPerformed
        TimerProgressbarTemplateItem timer = new TimerProgressbarTemplateItem();
        templateEditorPanel1.addItem(timer);
    }//GEN-LAST:event_btnTimerProgressActionPerformed

    private void btnDeleteTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTemplateActionPerformed


        if ( jTableTemplates.getSelectedRowCount() == 1){
            Object x = jTableTemplates.getValueAt( jTableTemplates.getSelectedRow(), jTableTemplates.getSelectedColumn() );
            if (x instanceof String){
                String str = (String) x;
                int confirm = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Delete_template_")+x+"?", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Confirm"), JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION){
                    try{
                        TemplateManager.getInstance().deleteTemplate(str);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Unable_to_delete_template:")+e.getMessage(),java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Datasoul_Error"),0);    
                    }// try
                }//if confirm
            }// if instance of
        }// if selected

        
    }//GEN-LAST:event_btnDeleteTemplateActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed

        int resp = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Do_you_want_to_save_")+templateEditorPanel1.getTemplate().getName()+ "?" );
        
        switch(resp){
            case JOptionPane.CANCEL_OPTION:
                return;
            case JOptionPane.YES_OPTION:
                templateEditorPanel1.save();
                break;
        }
        
        try{
            ObjectManager.getInstance().setBusyCursor();
            if ( jTableTemplates.getSelectedRowCount() == 1){
                Object x = jTableTemplates.getValueAt( jTableTemplates.getSelectedRow(), jTableTemplates.getSelectedColumn() );
                if (x instanceof String){
                    String str = (String) x;
                    templateEditorPanel1.open( str );
                    lblTemplateName.setText( str );
                }
            }
            TemplateManager.getInstance().refreshAvailableTemplates();
        }finally{
            ObjectManager.getInstance().setDefaultCursor();
        }

    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        templateEditorPanel1.openNewTemplate();
        lblTemplateName.setText( templateEditorPanel1.getTemplate().getName() );
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        try{
            ObjectManager.getInstance().setBusyCursor();
            templateEditorPanel1.save();
            TemplateManager.getInstance().refreshAvailableTemplates();
            lblTemplateName.setText( templateEditorPanel1.getTemplate().getName() );
        }finally{
            ObjectManager.getInstance().setDefaultCursor();
        }
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        templateEditorPanel1.deleteSelectedItem();
    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void btnAddTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTextActionPerformed
        TextTemplateItem txt = new TextTemplateItem();
        templateEditorPanel1.addItem(txt);
    }//GEN-LAST:event_btnAddTextActionPerformed

    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        JFileChooser fc = new JFileChooser();
        File dir = new File (System.getProperty("datasoul.stgloc") + System.getProperty("file.separator") + "templates");
        fc.setCurrentDirectory(dir);
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setControlButtonsAreShown(true);
        fc.setDialogTitle(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Select_Image"));
        if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION && fc.getSelectedFile().exists() ){
            String filename = fc.getSelectedFile().getAbsolutePath();
            ImageTemplateItem img = new ImageTemplateItem( filename );
            templateEditorPanel1.addItem(img);
        }
    }//GEN-LAST:event_btnAddImageActionPerformed

    private void btnMoveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveUpActionPerformed
        templateEditorPanel1.moveUpSelectedItem();
    }//GEN-LAST:event_btnMoveUpActionPerformed

    private void btnMoveDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveDownActionPerformed
        templateEditorPanel1.moveDownSelectedItem();
    }//GEN-LAST:event_btnMoveDownActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImage;
    private javax.swing.JButton btnAddText;
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnDeleteTemplate;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnMoveDown;
    private javax.swing.JButton btnMoveUp;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnProperties;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTimerProgress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTableProperties;
    private javax.swing.JTable jTableTemplates;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JLabel lblTemplateName;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JPanel panelTemplateEditor;
    private javax.swing.JPanel panelTemplates;
    private datasoul.templates.TemplateEditorPanel templateEditorPanel1;
    // End of variables declaration//GEN-END:variables
    
}
