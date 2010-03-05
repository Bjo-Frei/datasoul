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
 * PreviewPanel.java
 *
 * Created on 26 de Dezembro de 2005, 23:21
 */

package datasoul.datashow;

import datasoul.config.DisplayControlConfig;
import datasoul.util.ObjectManager;
import datasoul.render.ContentManager;
import datasoul.song.Song;
import java.awt.Dimension;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author  Administrador
 */
public class PreviewPanel extends javax.swing.JPanel implements ListSelectionListener {

    /**
     * Creates new form PreviewPanel
     */
    public PreviewPanel() {
        initComponents();
        serviceItemTable1.addTableListener(this);
        serviceItemTable1.setHeaderVisible(false);

        cbAutoLive.setSelected(DisplayControlConfig.getInstance().getAutomaticGoLiveBool());
        btnGoLive.setVisible(!DisplayControlConfig.getInstance().getAutomaticGoLiveBool());

        Dimension size = new Dimension(ContentManager.PREVIEW_WIDTH, ContentManager.getInstance().getPreviewHeight());
        previewDisplayPanel1.setSize(size);
        previewDisplayPanel1.setPreferredSize(size);
        previewDisplayPanel1.setMinimumSize(size);
        previewDisplayPanel1.setMaximumSize(size);
        previewDisplayPanel1.initDisplay((int) size.getWidth(), (int) size.getHeight());
        ContentManager.getInstance().registerPreviewDisplay(previewDisplayPanel1);

    }


    public void previewItem(ServiceItem serviceItem){
        ContentManager cm = ContentManager.getInstance();
        cm.setTemplatePreview(serviceItem.getTemplate());
        cm.setTitlePreview(serviceItem.getTitle());
        serviceItemTable1.setServiceItem(serviceItem, 0);
        cm.setSlidePreview( serviceItemTable1.getSlideText() );
        cm.setNextSlidePreview( serviceItemTable1.getNextSlideText() );
        cm.setActiveImagePreview(serviceItemTable1.getSlideImage());
        cm.setNextImagePreview(serviceItemTable1.getNextSlideImage());
        if(serviceItem instanceof Song){
            cm.setSongAuthorPreview( ((Song)serviceItem).getSongAuthor() );
            cm.setSongSourcePreview( ((Song)serviceItem).getSongSource() );
            cm.setCopyrightPreview( ((Song)serviceItem).getCopyright() );
        }else{
            cm.setSongAuthorPreview("");
            cm.setSongSourcePreview("");
            cm.setCopyrightPreview("");
        }
        lblTemplate.setText(serviceItem.getTemplate());
        cm.updatePreview();

        if (cbAutoLive.isSelected()){
            goLive(false);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serviceItemTable1 = new datasoul.datashow.ServiceItemTable();
        pnlPreviewBox = new javax.swing.JPanel();
        previewDisplayPanel1 = new datasoul.render.SwingDisplayPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTemplate = new javax.swing.JLabel();
        cbAutoLive = new javax.swing.JCheckBox();
        btnGoLive = new javax.swing.JButton();

        setBorder(null);
        setDoubleBuffered(false);

        previewDisplayPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        previewDisplayPanel1.setPreferredSize(new java.awt.Dimension(160, 120));
        previewDisplayPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                previewDisplayPanel1previewDisplayResized(evt);
            }
        });

        javax.swing.GroupLayout previewDisplayPanel1Layout = new javax.swing.GroupLayout(previewDisplayPanel1);
        previewDisplayPanel1.setLayout(previewDisplayPanel1Layout);
        previewDisplayPanel1Layout.setHorizontalGroup(
            previewDisplayPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );
        previewDisplayPanel1Layout.setVerticalGroup(
            previewDisplayPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );

        jLabel8.setText("Preview:");

        jLabel1.setText("Template:");

        lblTemplate.setText("     ");

        cbAutoLive.setText("Present automatically");
        cbAutoLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutoLiveActionPerformed(evt);
            }
        });

        btnGoLive.setText("Start Presentation");
        btnGoLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoLiveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPreviewBoxLayout = new javax.swing.GroupLayout(pnlPreviewBox);
        pnlPreviewBox.setLayout(pnlPreviewBoxLayout);
        pnlPreviewBoxLayout.setHorizontalGroup(
            pnlPreviewBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(previewDisplayPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel8)
            .addGroup(pnlPreviewBoxLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTemplate))
            .addComponent(cbAutoLive)
            .addGroup(pnlPreviewBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGoLive))
        );
        pnlPreviewBoxLayout.setVerticalGroup(
            pnlPreviewBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPreviewBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbAutoLive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGoLive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(pnlPreviewBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTemplate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(previewDisplayPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(serviceItemTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPreviewBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPreviewBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(serviceItemTable1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void previewDisplayPanel1previewDisplayResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_previewDisplayPanel1previewDisplayResized

}//GEN-LAST:event_previewDisplayPanel1previewDisplayResized

    private void cbAutoLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutoLiveActionPerformed

        btnGoLive.setVisible(! cbAutoLive.isSelected());
        DisplayControlConfig.getInstance().setAutomaticGoLive(cbAutoLive.isSelected());

    }//GEN-LAST:event_cbAutoLiveActionPerformed

    private void btnGoLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoLiveActionPerformed
        goLive(false);
    }//GEN-LAST:event_btnGoLiveActionPerformed

    public void goLive(boolean startAtLastSlide){
        try{
            ObjectManager.getInstance().setBusyCursor();
            ServiceItem previewItem = ObjectManager.getInstance().getPreviewPanel().serviceItemTable1.getServiceItem();
            ObjectManager.getInstance().getLivePanel().showItem(previewItem, startAtLastSlide);
            ObjectManager.getInstance().getDatasoulMainForm().showDisplayControls();
        }finally{
            ObjectManager.getInstance().setDefaultCursor();
        }        
    }

    public void valueChanged(ListSelectionEvent e) {
        ContentManager cm = ContentManager.getInstance();
        cm.setSlidePreview( serviceItemTable1.getSlideText() );
        cm.setNextSlidePreview( serviceItemTable1.getNextSlideText() );
        cm.setActiveImagePreview(serviceItemTable1.getSlideImage());
        cm.setNextImagePreview(serviceItemTable1.getNextSlideImage());
        cm.updatePreview();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoLive;
    private javax.swing.JCheckBox cbAutoLive;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblTemplate;
    private javax.swing.JPanel pnlPreviewBox;
    private datasoul.render.SwingDisplayPanel previewDisplayPanel1;
    private datasoul.datashow.ServiceItemTable serviceItemTable1;
    // End of variables declaration//GEN-END:variables
    
}
