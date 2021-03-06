/* 
 * Copyright 2005-2010 Samuel Mello
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; version 2 or later of the License.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 */

package datasoul.serviceitems.imagelist;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import datasoul.DatasoulMainForm;
import datasoul.util.ObjectManager;
import datasoul.util.OpenofficeHelper;

public class ImageListEditorForm extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4390112556858243062L;

	/** Creates new form ImageListEditorForm */
    public ImageListEditorForm() {
        initComponents();
        DatasoulMainForm.setDatasoulIcon(this);
    }
    
    private ImageListServiceItem origitem;
    private ImageListServiceItem edititem;

    public ImageListEditorForm(ImageListServiceItem item){
        this();
        tblImages.setHeaderVisible(false);
        this.origitem = item;
        this.edititem = item.getEditableCopy();
        tblImages.setServiceItem(this.edititem, 0);
        txtTitle.setText(item.getTitle());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        tblImages = new datasoul.serviceitems.ServiceItemTable();
        jToolBar2 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAddImages = new javax.swing.JButton();
        btnAddOffice = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("datasoul/internationalize"); // NOI18N
        setTitle(bundle.getString("EDIT IMAGE LIST")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel2.setText(bundle.getString("IMAGE LIST TITLE:")); // NOI18N

        txtTitle.setText("jTextField1");

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/document-save_big.png"))); // NOI18N
        btnSave.setToolTipText(bundle.getString("SAVE")); // NOI18N
        btnSave.setBorderPainted(false);
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSave);
        jToolBar2.add(jSeparator1);

        btnAddImages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/insert-image.png"))); // NOI18N
        btnAddImages.setText(bundle.getString("ADD IMAGE")); // NOI18N
        btnAddImages.setBorderPainted(false);
        btnAddImages.setFocusPainted(false);
        btnAddImages.setFocusable(false);
        btnAddImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImagesActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAddImages);

        btnAddOffice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/x-office-presentation.png"))); // NOI18N
        btnAddOffice.setText(bundle.getString("ADD PRESENTATION")); // NOI18N
        btnAddOffice.setBorderPainted(false);
        btnAddOffice.setFocusPainted(false);
        btnAddOffice.setFocusable(false);
        btnAddOffice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOfficeActionPerformed(evt);
            }
        });
        jToolBar2.add(btnAddOffice);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/edit-delete_big.png"))); // NOI18N
        btnDelete.setText(bundle.getString("DELETE")); // NOI18N
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setFocusable(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar2.add(btnDelete);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblImages, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblImages, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImagesActionPerformed

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileFilter() {

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName();
                if (name.toLowerCase().endsWith(".jpg")) {
                    return true;
                }
                if (name.toLowerCase().endsWith(".png")) {
                    return true;
                }
                if (name.toLowerCase().endsWith(".gif")) {
                    return true;
                }
                if (name.toLowerCase().endsWith(".bmp")) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                return java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("IMAGE FILES (*.JPG, *.GIF, *.PNG, *.BMP)");
            }
        });
        fc.setMultiSelectionEnabled(true);
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try{
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                for (File f : fc.getSelectedFiles()){
                    try {
                        edititem.addImage(f);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }finally{
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }


    }//GEN-LAST:event_btnAddImagesActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int idx = tblImages.getSlideIndex();
        if (idx >= 0){
            edititem.delImage(idx);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
}//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (hasChanged()){
            int resp = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("SAVE THE CHANGES?"), "Datasoul", JOptionPane.YES_NO_CANCEL_OPTION );

            if (resp == JOptionPane.YES_OPTION){
                save();
                this.dispose();
            }else if (resp == JOptionPane.NO_OPTION){
                this.dispose();
            }
        }else{
            this.dispose();
        }

    }//GEN-LAST:event_formWindowClosing

    private void btnAddOfficeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOfficeActionPerformed

        final JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter( new FileNameExtensionFilter(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("PRESENTATION FILES")+" (*.odp,*.ppt,*.pptx)", "odp", "ppt", "pptx") );
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            Thread t = new Thread(){
                @Override
                public void run(){
                    PresentationConverterProgress pcp = new PresentationConverterProgress();
                    try{
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        OpenofficeHelper helper = new OpenofficeHelper();
                        pcp.setLocationRelativeTo(ImageListEditorForm.this);
                        pcp.registerHelper(helper);
                        pcp.setVisible(true);
                        helper.convertImages(fc.getSelectedFile(), edititem);
                        helper.dispose();
                    }catch(Exception e){
                        ImageListEditorForm.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        JOptionPane.showMessageDialog(ImageListEditorForm.this, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("ERROR CONVERTING FILE")+": "+e.getMessage());
                    }finally{
                        pcp.dispose();
                        ImageListEditorForm.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            };
            t.start();
        }
        

    }//GEN-LAST:event_btnAddOfficeActionPerformed

    public boolean hasChanged(){
        return !origitem.getTitle().equals(txtTitle.getText()) ||
               !origitem.getSlides().equals(edititem.getSlides());
    }

    private void save(){
        origitem.setTitle(txtTitle.getText());
        origitem.assignImages(edititem);
        ObjectManager.getInstance().getDatasoulMainForm().repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImages;
    private javax.swing.JButton btnAddOffice;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar2;
    private datasoul.serviceitems.ServiceItemTable tblImages;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables

}


