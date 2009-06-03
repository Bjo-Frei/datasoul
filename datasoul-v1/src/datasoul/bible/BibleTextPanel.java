/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BibleTextPanel.java
 *
 * Created on Apr 20, 2009, 10:10:17 AM
 */

package datasoul.bible;

import datasoul.datashow.TextServiceItem;
import javax.swing.*;
import org.crosswire.jsword.versification.BibleInfo;
import org.crosswire.jsword.book.*;
import org.crosswire.jsword.passage.*;
import java.util.List;

/**
 *
 * @author samuel
 */
public class BibleTextPanel extends javax.swing.JPanel {

    private MyBooksListener listener;
    private JTextArea txtArea;

    /** Creates new form BibleTextPanel */
    public BibleTextPanel() {
        initComponents();

        listener = new MyBooksListener(this);
        Books.installed().addBooksListener(listener);
        updateBibles();


        // cbHowToSplit options
        // Keep the current idem ordem. They are handled by their index in Load.
        cbHowToSplit.removeAllItems();
        cbHowToSplit.addItem("Sections");
        cbHowToSplit.addItem("Slides");
        cbHowToSplit.addItem("Continuous");

        // cbRefType options
        // Keep the current idem ordem. They are handled by their index in Load.
        cbRefType.removeAllItems();
        cbRefType.addItem("Full");
        cbRefType.addItem("Chapter and Verse");
        cbRefType.addItem("Verse Only");
        cbRefType.addItem("None");
        cbRefType.setSelectedIndex(1);

        // Setup bible books
        cbBook.removeAllItems();
        for (int i = 0; i < BibleInfo.booksInBible(); i++){
            try {
                cbBook.addItem(BibleInfo.getPreferredBookName(i+1));
            } catch (NoSuchVerseException ex) {
                ex.printStackTrace();
            }
        }


    }

    public void registerTextArea(JTextArea area){
        this.txtArea = area;
    }

    public void onClose(){
        Books.installed().removeBooksListener(listener);
    }   

    public void updateBibles(){

        cbBibles.removeAllItems();

        List installed = Books.installed().getBooks(BookFilters.getOnlyBibles());

        for (Object o : installed){
            if (o instanceof Book){
                cbBibles.addItem(((Book)o).getName());
            }
        }

        boolean hasAny = installed.size() > 0;

        cbBibles.setEnabled(hasAny);
        cbBook.setEnabled(hasAny);
        cbChapter.setEnabled(hasAny);
        cbHowToSplit.setEnabled(hasAny);
        cbRefType.setEnabled(hasAny);
        cbVersesFrom.setEnabled(hasAny);
        cbVersesTo.setEnabled(hasAny);
        btnLoad.setEnabled(hasAny);

    }



    static class MyBooksListener implements BooksListener
    {
         private BibleTextPanel orig;

         public MyBooksListener(BibleTextPanel orig){
             this.orig = orig;
         }
         public void bookAdded(BooksEvent ev)
         {
             orig.updateBibles();
         }

         public void bookRemoved(BooksEvent ev)
         {
             orig.updateBibles();
         }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        cbBibles = new javax.swing.JComboBox();
        btnManageBible = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbBook = new javax.swing.JComboBox();
        cbChapter = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cbVersesFrom = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbVersesTo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cbHowToSplit = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cbRefType = new javax.swing.JComboBox();
        btnLoad = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(443, 101));

        jLabel3.setText("Bible:");

        cbBibles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnManageBible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/stock_data-sources-new.png"))); // NOI18N
        btnManageBible.setText("Manage Installed Bibles");
        btnManageBible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageBibleActionPerformed(evt);
            }
        });

        jLabel1.setText("Verses:");

        cbBook.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBookActionPerformed(evt);
            }
        });

        cbChapter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbChapter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChapterActionPerformed(evt);
            }
        });

        jLabel4.setText(":");

        cbVersesFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("-");

        cbVersesTo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVersesTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVersesToActionPerformed(evt);
            }
        });

        jLabel2.setText("Put verses in");

        cbHowToSplit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Add reference");

        cbRefType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/v2/stock_edit-bookmark.png"))); // NOI18N
        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbBibles, 0, 204, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnManageBible))
            .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbBook, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cbChapter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbVersesFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbVersesTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(layout.createSequentialGroup()
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbHowToSplit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cbRefType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnLoad))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnManageBible)
                    .add(cbBibles, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cbBook, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(cbChapter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cbVersesFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(cbVersesTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(btnLoad)
                    .add(cbHowToSplit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(cbRefType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .add(layout.createSequentialGroup()
                .add(8, 8, 8)
                .add(jLabel3))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageBibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageBibleActionPerformed
        BibleInstaller bi = new BibleInstaller();
        bi.setLocationRelativeTo(this);
        bi.setVisible(true);
}//GEN-LAST:event_btnManageBibleActionPerformed

    private void cbBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBookActionPerformed
        cbChapter.removeAllItems();
        if (cbBook.getSelectedIndex() >= 0){

            try {
                int max = BibleInfo.chaptersInBook(cbBook.getSelectedIndex() + 1);
                for (int i = 0; i < max; i++) {
                    cbChapter.addItem(Integer.toString(i+1));
                }
            } catch (NoSuchVerseException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_cbBookActionPerformed

    private void cbChapterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChapterActionPerformed

        cbVersesFrom.removeAllItems();
        cbVersesTo.removeAllItems();

        if (cbBook.getSelectedIndex() >= 0 && cbChapter.getSelectedIndex() >= 0){

            try {
                int max = BibleInfo.versesInChapter(cbBook.getSelectedIndex() + 1, cbChapter.getSelectedIndex()+1);
                for (int i = 0; i < max; i++) {
                    cbVersesFrom.addItem(Integer.toString(i+1));
                    cbVersesTo.addItem(Integer.toString(i+1));
                }
            } catch (NoSuchVerseException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_cbChapterActionPerformed

    private void cbVersesToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVersesToActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cbVersesToActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed

        int begin = cbVersesFrom.getSelectedIndex()+1;
        int end = cbVersesTo.getSelectedIndex()+1;

        if (begin > end){
            JOptionPane.showMessageDialog(this, "Invalid verse range");
            return;
        }

        Book book = Books.installed().getBook(cbBibles.getSelectedItem().toString());
        if (book == null) return;

        StringBuffer sb = new StringBuffer();

        if (txtArea.getText().length() > 0 ){
            sb.append(txtArea.getText()+"\n");
        }

        try {
            for (int i = begin; i<=end; i++){
                Verse temp = new Verse(cbBook.getSelectedIndex() + 1, cbChapter.getSelectedIndex() + 1, i);
                BookData data = new BookData(book, temp);
                String versetext = OSISUtil.getCanonicalText(data.getOsisFragment());

                // Skip blank lines (this may happen if the selected bible does not
                // contain the choosed text, it may be a New Testment only, for example)
                if (versetext.trim().length() == 0 )
                    continue;

                // Add break if needed
                if (sb.length() > 0){
                    if (cbHowToSplit.getSelectedIndex() == 1)
                        sb.append(TextServiceItem.SLIDE_BREAK+"\n");
                    else if (cbHowToSplit.getSelectedIndex() == 0)
                        sb.append(TextServiceItem.CHORUS_MARK+"\n");
                }

                // Add the reference, if needed
                switch(cbRefType.getSelectedIndex()){
                    case 0: // Full
                        sb.append(cbBook.getSelectedItem().toString());
                        sb.append(" ");
                        // do not break
                    case 1: // chapter + verse
                        sb.append(cbChapter.getSelectedItem().toString());
                        sb.append(":");
                        // do not break
                    case 2: // verse only
                        sb.append(Integer.toString(i));
                        sb.append(" ");
                        break;
                }

                // Add the text
                sb.append(versetext.trim()+"\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        txtArea.setText(sb.toString());
    }//GEN-LAST:event_btnLoadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnManageBible;
    private javax.swing.JComboBox cbBibles;
    private javax.swing.JComboBox cbBook;
    private javax.swing.JComboBox cbChapter;
    private javax.swing.JComboBox cbHowToSplit;
    private javax.swing.JComboBox cbRefType;
    private javax.swing.JComboBox cbVersesFrom;
    private javax.swing.JComboBox cbVersesTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables

}
