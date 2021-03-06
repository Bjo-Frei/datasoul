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

/*
 * BibleInstaller.java
 *
 * Created on Apr 16, 2009, 11:08:10 PM
 */
package datasoul.bible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookException;
import org.crosswire.jsword.book.BookFilters;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.install.InstallException;
import org.crosswire.jsword.book.install.InstallManager;
import org.crosswire.jsword.book.install.Installer;

import datasoul.DatasoulMainForm;
import datasoul.config.WindowPropConfig;



/**
 *
 * @author samuel
 */
public class BibleInstaller extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8631105525458347323L;
	protected InstallManager imanager;
    protected MyBookTableModel myAvailableModel;
    protected MyBookTableModel myInstalledModel;

    private static boolean downloadAllowed = false;

    /** Creates new form BibleInstaller */
    public BibleInstaller() {
        initComponents();
        DatasoulMainForm.setDatasoulIcon(this);

        Logger.getLogger("").setLevel(Level.OFF);

        // An installer knows how to install books
        imanager = new InstallManager();

        myAvailableModel = new MyBookTableModel();
        tblAvailable.setModel(myAvailableModel);
        tblAvailable.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblAvailable.getColumnModel().getColumn(1).setPreferredWidth(400);

        myInstalledModel = new MyBookTableModel();
        tblInstalled.setModel(myInstalledModel);
        tblInstalled.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblInstalled.getColumnModel().getColumn(1).setPreferredWidth(400);

        updateComboboxes();
        updateInstalled();
        updateAvailable();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbSource = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInstalled = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblInstalledCount = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAvailable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnInstallSelected = new javax.swing.JButton();
        lblAvailableCount = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("datasoul/internationalize"); // NOI18N
        setTitle(bundle.getString("DATASOUL - BIBLE MANAGER")); // NOI18N

        cbSource.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSourceActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("INSTALLATION SOURCE:")); // NOI18N

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/view-refresh.png"))); // NOI18N
        btnRefresh.setText(bundle.getString("REFRESH ALL")); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        tblInstalled.setModel(new javax.swing.table.DefaultTableModel(
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
        tblInstalled.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblInstalled);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/edit-delete.png"))); // NOI18N
        btnDelete.setText(bundle.getString("UNINSTALL SELECTED")); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel3.setText(bundle.getString("INSTALLED BIBLES:")); // NOI18N

        lblInstalledCount.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInstalledCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(jLabel3)
                    .addComponent(lblInstalledCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        tblAvailable.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAvailable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblAvailable);

        jLabel2.setText(bundle.getString("BIBLES AVAILABLE FOR DOWNLOAD:")); // NOI18N

        btnInstallSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/stock_insert-url.png"))); // NOI18N
        btnInstallSelected.setText(bundle.getString("DOWNLOAD SELECTED")); // NOI18N
        btnInstallSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstallSelectedActionPerformed(evt);
            }
        });

        lblAvailableCount.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAvailableCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(btnInstallSelected)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInstallSelected)
                    .addComponent(jLabel2)
                    .addComponent(lblAvailableCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/window-close.png"))); // NOI18N
        btnClose.setText(bundle.getString("CLOSE")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSource, 0, 301, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh))
                    .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnRefresh)
                    .addComponent(cbSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static boolean checkDownloadAllowed(JPanel parent){
        if (! BibleInstaller.downloadAllowed){
            int allow = JOptionPane.showConfirmDialog(parent,
                    java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("THIS OPERATION REQUIRES INTERNET ACCESS.") + "\n" +
                    java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("IF YOU LIVE IN A COUNTRY WHERE CHRISTIANS ARE PERSECUTED AND DO NOT WISH TO RISK DETECTION YOU SHOULD NOT PROCEED.") + "\n" +
                    java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("DO YOU WANT TO CONTINUE?"),
                    java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("DATASOUL - WARNING"), JOptionPane.YES_NO_OPTION);
            if (allow == JOptionPane.YES_OPTION){
                BibleInstaller.downloadAllowed = true;
           }
        }
        return BibleInstaller.downloadAllowed;
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

        final ProgressDialog pd = new ProgressDialog(BibleInstaller.this, true);
        pd.isBibleDownload(false);
        pd.setText(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("UPDATING AVAILABLE BIBLES"));
        pd.setLocationRelativeTo(this);

        Thread t = new Thread() {

            @Override
            public void run() {

                // Ask the Install Manager for a map of all known module sites
                Map<?,?> installers = imanager.getInstallers();

                // Get all the installers one after the other
                Iterator<?> iter = installers.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<?,?> mapEntry = (Map.Entry<?,?>) iter.next();
                    try {
                        pd.setStatus(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("UPDATING ") + " " + mapEntry.getKey().toString() + "...");
                        ((Installer) mapEntry.getValue()).reloadBookList();
                    } catch (InstallException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                pd.dispose();
            }
        };
        t.start();
        pd.setVisible(true);

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnInstallSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInstallSelectedActionPerformed

        if (tblAvailable.getSelectedRow() < 0) {
            return;
        }

        Book book = myAvailableModel.getBook(tblAvailable.getSelectedRow());

        // Check if already installed
        if (Books.installed().getBook(book.getInitials()) != null) {
            JOptionPane.showMessageDialog(this, book.getName() + " " + java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("IS ALREADY INSTALLED."));
            return;
        }

        if (JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("ARE YOU SURE TO DOWNLOAD AND INSTALL") + " " + book.getName() + " ?", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("CONFIRM DOWNLOAD AND INSTALL"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                ProgressDialog pd = new ProgressDialog(this, true);
                pd.setText(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("DOWNLOADING..."));
                pd.isBibleDownload(true);
                pd.setLocationRelativeTo(this);

                WindowPropConfig.getInstance().setSelectedBible(book.getName());

                Installer installer = imanager.getInstaller(cbSource.getSelectedItem().toString());
                installer.install(book);

                pd.setVisible(true);

                updateInstalled();

            } catch (InstallException ex) {
                ex.printStackTrace();
            }
        }


    }//GEN-LAST:event_btnInstallSelectedActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        if (tblInstalled.getSelectedRow() < 0) {
            return;
        }

        Book selected = myInstalledModel.getBook(tblInstalled.getSelectedRow());

        if (JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("ARE YOU SURE TO UNINSTALL") + " " + selected.getName() + " ?", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("CONFIRM UNINSTALL"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                selected.getDriver().delete(selected);               
            } catch (BookException ex) {
                ex.printStackTrace();
            }
        }

        updateInstalled();

}//GEN-LAST:event_btnDeleteActionPerformed

    private void cbSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSourceActionPerformed
        updateAvailable();
    }//GEN-LAST:event_cbSourceActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    public void updateComboboxes() {

        cbSource.removeAllItems();

        // Ask the Install Manager for a map of all known module sites
        Map<?,?> installers = imanager.getInstallers();

        // Get all the installers one after the other
        Iterator<?> iter = installers.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?,?> mapEntry = (Map.Entry<?,?>) iter.next();
            cbSource.addItem(mapEntry.getKey());
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInstallSelected;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cbSource;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblAvailableCount;
    private javax.swing.JLabel lblInstalledCount;
    private javax.swing.JTable tblAvailable;
    private javax.swing.JTable tblInstalled;
    // End of variables declaration//GEN-END:variables

    public void updateAvailable() {
        if (cbSource.getSelectedIndex() >= 0) {
            myAvailableModel.setNewList(imanager.getInstaller(cbSource.getSelectedItem().toString()).getBooks(BookFilters.getOnlyBibles()));
        }
        lblAvailableCount.setText("(" + Integer.toString(tblAvailable.getRowCount()) + ")");
    }

    public void updateInstalled() {
        myInstalledModel.setNewList(Books.installed().getBooks(BookFilters.getOnlyBibles()));
        lblInstalledCount.setText("(" + Integer.toString(tblInstalled.getRowCount()) + ")");
    }

    public class MyBookTableModel extends DefaultTableModel {

        /**
		 * 
		 */
		private static final long serialVersionUID = -9212438386839906862L;
		List<Book> list;

        public MyBookTableModel() {
            this.list = new ArrayList<Book>();
        }

        public Book getBook(int i) {
            return list.get(i);
        }

        @SuppressWarnings("unchecked")
        public void setNewList(List newlist) {
            list = new ArrayList<Book>(newlist);
            Comparator<Book> langcomp = new Comparator<Book>() {

                public int compare(Book arg0, Book arg1) {
                    return arg0.getLanguage().getName().compareTo(arg1.getLanguage().getName());
                }
            };
            Collections.sort(list, langcomp);
            fireTableDataChanged();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            if (list != null) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            if (list != null) {
                switch (column) {
                    case 0:
                        return list.get(row).getLanguage();
                    case 1:
                        return list.get(row).getName();
                    default:
                        return null;
                }
            } else {
                return "";
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("LANGUAGE");
                case 1:
                    return java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("BIBLE");
            }
            return "";
        }
    }
}


