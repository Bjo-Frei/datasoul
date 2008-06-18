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
 * SongEditorFrame.java
 *
 * Created on 26 de Dezembro de 2005, 21:14
 */

package datasoul.song;

import datasoul.*;
import datasoul.util.*;
import datasoul.datashow.*;
import datasoul.song.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xml.serialize.OutputFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author  Administrador
 */
public class SongEditorForm extends javax.swing.JFrame {
    

    private Song song;
    private boolean newSong;

    /**
     * Creates new form SongEditorFrame
     */
    public SongEditorForm(File file) {
        initComponents();
        
        Document dom=null;
        Node node=null;

        try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                //Using factory get an instance of document builder
                DocumentBuilder db = dbf.newDocumentBuilder();

                //parse using builder to get DOM representation of the XML file
                dom = db.parse(file);

                //node = dom.getDocumentElement().getChildNodes().item(0);
                node = dom.getElementsByTagName("Song").item(0);
                
        }catch(Exception e) {
            ShowDialog.showReadFileError(file, e);
        }        

        song = new Song();
        try {
            song.readObject(node);
        } catch (Exception e) {
            ShowDialog.showReadFileError(file, e);
        }

        this.setTitle(song.getFileName());
        
        //fill object
        fillGuiValues();
        
        newSong = false;
        
        this.center();        

        highlightchord(this.textChordsSimplified);
        highlightchord(this.textChordsCompleted);
        highlightlyric(this.textLyrics);
        textChordsSimplified.setCaretPosition(0);
        textChordsCompleted.setCaretPosition(0);
        textLyrics.setCaretPosition(0);
    }

    /**
     * Creates new form SongEditorFrame
     */
    public SongEditorForm(Song songIn) {
        initComponents();

        song = songIn;
        this.setTitle(songIn.getFileName());
        
        //fill object
        fillGuiValues();
        
        newSong = false;
        
        this.center();        
        
        highlightchord(this.textChordsSimplified);
        highlightchord(this.textChordsCompleted);
        highlightlyric(this.textLyrics);
        textChordsSimplified.setCaretPosition(0);
        textChordsCompleted.setCaretPosition(0);
        textLyrics.setCaretPosition(0);
    }
    
    public SongEditorForm() {
        initComponents();

        song = new Song();

        this.setTitle("");
        
        newSong = true;
        
        this.center();
        
        highlightchord(this.textChordsSimplified);
        highlightchord(this.textChordsCompleted);
        highlightlyric(this.textLyrics);
        textChordsSimplified.setCaretPosition(0);
        textChordsCompleted.setCaretPosition(0);
        textLyrics.setCaretPosition(0);
    }

    public void center(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle frame = getBounds();
        setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
    }
    
    
    private void fillGuiValues(){
        fieldName.setText(song.getTitle());
        fieldAuthor.setText(song.getSongAuthor());
        textLyrics.setText(song.getText());
        textChordsCompleted.setText(song.getChordsComplete());
        textChordsSimplified.setText(song.getChordsSimplified());
        txtCopyright.setText(song.getCopyright());
        txtSongSource.setText(song.getSongSource());
    }

    private void actualizeValues(){
        song.setTitle(fieldName.getText());
        song.setSongAuthor(fieldAuthor.getText());
        song.setText(textLyrics.getText());
        song.setChordsComplete(textChordsCompleted.getText());
        song.setChordsSimplified(textChordsSimplified.getText());
        song.setCopyright(txtCopyright.getText());
        song.setSongSource(txtSongSource.getText());
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fieldName = new javax.swing.JTextField();
        labelName = new javax.swing.JLabel();
        labelAuthor = new javax.swing.JLabel();
        fieldAuthor = new javax.swing.JTextField();
        tabSong = new javax.swing.JTabbedPane();
        scroolLyric = new javax.swing.JScrollPane();
        textLyrics = new javax.swing.JTextPane();
        scroolChordsComplete = new javax.swing.JScrollPane();
        textChordsCompleted = new javax.swing.JTextPane();
        scroolChordsSimplified = new javax.swing.JScrollPane();
        textChordsSimplified = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        textLine = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSplit = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCopyright = new javax.swing.JTextField();
        txtSongSource = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        fieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldNameKeyTyped(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("datasoul/internationalize"); // NOI18N
        labelName.setText(bundle.getString("NAME")); // NOI18N
        labelName.setFocusable(false);

        labelAuthor.setText(bundle.getString("AUTHOR")); // NOI18N
        labelAuthor.setFocusable(false);

        tabSong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textChordsCompleteKeyPressed(evt);
            }
        });

        textLyrics.setFont(new java.awt.Font("Courier New", 0, 12));
        textLyrics.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textLyricsKeyPressed(evt);
            }
        });
        scroolLyric.setViewportView(textLyrics);

        tabSong.addTab(bundle.getString("LYRICS"), scroolLyric); // NOI18N

        textChordsCompleted.setFont(new java.awt.Font("Courier New", 0, 12));
        textChordsCompleted.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textChordsCompleteKeyPressed(evt);
            }
        });
        scroolChordsComplete.setViewportView(textChordsCompleted);

        tabSong.addTab(bundle.getString("CHORDS_COMPLETE"), scroolChordsComplete); // NOI18N

        textChordsSimplified.setFont(new java.awt.Font("Courier New", 0, 12));
        textChordsSimplified.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textChordsSimplifiedKeyPressed(evt);
            }
        });
        scroolChordsSimplified.setViewportView(textChordsSimplified);

        tabSong.addTab(bundle.getString("Chords_Simplified"), scroolChordsSimplified); // NOI18N

        jLabel1.setText(bundle.getString("Split_lyrics_in_slides_with")); // NOI18N

        textLine.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        textLine.setText("2");

        jLabel2.setText(bundle.getString("lines")); // NOI18N

        btnSplit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/show_table_row.png"))); // NOI18N
        btnSplit.setText(bundle.getString("Split")); // NOI18N
        btnSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSplitActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/button_cancel.png"))); // NOI18N
        btnClose.setText(bundle.getString("Cancel")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/apply.png"))); // NOI18N
        btnSave.setText(bundle.getString("Apply")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 2, 10));
        jLabel3.setText(bundle.getString("*_Use_a_line_with_==_to_split_slides_and_a_line_with_===_to_split_sessions")); // NOI18N

        jToolBar1.setFloatable(false);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/playsound.png"))); // NOI18N
        jLabel4.setText(bundle.getString("Edit_Song")); // NOI18N
        jToolBar1.add(jLabel4);

        jSeparator2.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        jToolBar1.add(jSeparator2);

        jLabel5.setText("Copyright");

        jLabel6.setText("Source");

        txtCopyright.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCopyrightActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel6)
                .addContainerGap(627, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, tabSong, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(textLine, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 220, Short.MAX_VALUE)
                        .add(btnSplit)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnSave)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnClose))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(labelName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(labelAuthor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(fieldName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                            .add(fieldAuthor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtCopyright, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtSongSource, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelName)
                    .add(fieldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelAuthor)
                    .add(fieldAuthor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(txtCopyright, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(txtSongSource, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabSong, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(btnClose)
                    .add(btnSave)
                    .add(textLine, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2)
                    .add(btnSplit))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(this.fieldName.getText().equals("")){
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Please_fill_the_field_\"Song_Name\""));
            return;
        }

        String filename = this.fieldName.getText();
        if(!filename.contains(".song"))
            filename = filename + ".song";
        String path = System.getProperty("datasoul.stgloc") + System.getProperty("file.separator") + 
                "songs"+ System.getProperty("file.separator")+filename;
        
        if(!path.equals(song.getFilePath())){
            File file = new File(song.getFilePath());
            file.delete();
        }
        song.setFilePath(path);
        
        saveFile();    
        dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSplitActionPerformed
        int lines = 0;
        String str = this.textLine.getText();
        try{
            lines = Integer.parseInt(str);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("Please_in_the_next_time_digit_a_number!"));
            return;
        }
       
        String inStr = this.textLyrics.getText();
        StringBuffer sb = new StringBuffer();
        inStr.replace("\r", "");
        inStr = inStr.replace("\n"+TextServiceItem.SLIDE_BREAK+"\n","\n");

        /*
        inStr = inStr.replace(TextServiceItem.CHORUS_MARK+"\n","\n\n");
        String str2;
        int count = 0;
        for(int i=0; i< inStr.length()-2;i++){
            str = inStr.substring(i,i+1);
            str2 = inStr.substring(i,i+2);
            if(str2.equals("\n\r")){
                sb.append(TextServiceItem.CHORUS_MARK+"\n");
                count =0;
                i=i+2;
                continue;
            }
            if(str2.equals("\n\n")){
                sb.append("\n"+TextServiceItem.CHORUS_MARK+"\n");
                count =0;
                i=i+2;
                continue;
            }
            if(str.equals("\n")){
                count ++;
            }
            sb.append(str);
            if(count==lines){
                sb.append(TextServiceItem.SLIDE_BREAK+"\n");
                count = 0;
            }
        }
        sb.append(inStr.substring(inStr.length()-2,inStr.length()));
         */
        
        String verses[] = inStr.split(TextServiceItem.CHORUS_MARK+"\n");
        for (int i=0; i<verses.length; i++){
            String vlines[] = verses[i].split("\n");
            for (int j=0; j<vlines.length; j++){
                sb.append(vlines[j]);
                sb.append("\n");
                if (lines != 0 && (j+1)%lines == 0 && vlines.length-j>1){
                    sb.append(TextServiceItem.SLIDE_BREAK);
                    sb.append("\n");
                }
            }
            if (i < verses.length -1){
                sb.append(TextServiceItem.CHORUS_MARK);
                sb.append("\n");
            }
            
        }
        
        this.textLyrics.setText(sb.toString());
        highlightlyric(this.textLyrics);        
    }//GEN-LAST:event_btnSplitActionPerformed

    private void textChordsSimplifiedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textChordsSimplifiedKeyPressed
        highlightchord(this.textChordsSimplified);
    }//GEN-LAST:event_textChordsSimplifiedKeyPressed

    private void textChordsCompleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textChordsCompleteKeyPressed
        highlightchord(this.textChordsCompleted);
    }//GEN-LAST:event_textChordsCompleteKeyPressed

    private void textLyricsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textLyricsKeyPressed
        highlightlyric(this.textLyrics);
    }//GEN-LAST:event_textLyricsKeyPressed

    private void fieldNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldNameKeyTyped
        if(evt.getKeyCode()==16)
            return;
        // samuelm: changed to check against disallowd chars to avoid problems with non-english chars
        //String allowed="ZXCVBNMASDFGHJKL?QWERTYUIOPzxcvbnmasdfghjkl?qwertyuiop1234567890'???????????";
        String disallowed = "\\/:*?\"<>|";
        if(disallowed.contains(String.valueOf(evt.getKeyChar())))
            evt.consume();
    }//GEN-LAST:event_fieldNameKeyTyped

    private void txtCopyrightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCopyrightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCopyrightActionPerformed

  private void saveFile(){
        actualizeValues();
        
        try{
            Node node = song.writeObject();
            Document doc = node.getOwnerDocument();
            doc.appendChild( node);                        // Add Root to Document
            FileOutputStream fos = new FileOutputStream(song.getFilePath());
            org.apache.xml.serialize.XMLSerializer xs = new org.apache.xml.serialize.XMLSerializer();
            OutputFormat outFormat = new OutputFormat();
            outFormat.setIndenting(true);
            outFormat.setEncoding("ISO-8859-1");
            xs.setOutputFormat(outFormat);
            xs.setOutputByteStream(fos);
            xs.serialize(doc);
            fos.close();

        } catch(Exception e){
            ShowDialog.showWriteFileError(song.getFileName(), e);
        }
        
        if(newSong){
            AllSongsListTable.getInstance().addItem(song);
            newSong = false;
            AllSongsListTable.getInstance().sortByName();            
        }
    }
    
    /**
     * @param args the command line arguments
     */
/*    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SongEditorFrame().setVisible(true);
            }
        });
    }
  */  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSplit;
    private javax.swing.JTextField fieldAuthor;
    private javax.swing.JTextField fieldName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelAuthor;
    private javax.swing.JLabel labelName;
    private javax.swing.JScrollPane scroolChordsComplete;
    private javax.swing.JScrollPane scroolChordsSimplified;
    private javax.swing.JScrollPane scroolLyric;
    private javax.swing.JTabbedPane tabSong;
    private javax.swing.JTextPane textChordsCompleted;
    private javax.swing.JTextPane textChordsSimplified;
    private javax.swing.JTextField textLine;
    private javax.swing.JTextPane textLyrics;
    private javax.swing.JTextField txtCopyright;
    private javax.swing.JTextField txtSongSource;
    // End of variables declaration//GEN-END:variables
    

    public void highlightlyric(JTextComponent textComp){
        removeHighlights(textComp);
        highlight(textComp,"\n"+TextServiceItem.SLIDE_BREAK+"\n",Color.ORANGE);
        highlight(textComp,"\n"+TextServiceItem.CHORUS_MARK+"\n",Color.PINK);
    }

    public void highlightchord(JTextComponent textComp){
        removeHighlights(textComp);
        highlight(textComp,"\n"+TextServiceItem.SLIDE_BREAK+"\n",Color.ORANGE);
        highlight(textComp,"\n"+TextServiceItem.CHORUS_MARK+"\n",Color.PINK);
        /*
        for(String chord:ChordsDB.getInstance().getChordsName()){
            highlight(textComp,chord,Color.decode("0xddddff"));
        }
         */
    }
    
    // Creates highlights around all occurrences of pattern in textComp
    public void highlight(JTextComponent textComp, String pattern, Color color) {
        Highlighter.HighlightPainter highlightPainter = new MyHighlightPainter(color);
    
        try {
            Highlighter hilite = textComp.getHighlighter();
            javax.swing.text.Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            // Search for pattern
            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                // Create highlighter using private painter and apply around pattern
                hilite.addHighlight(pos, pos+pattern.length(), highlightPainter);
                pos += pattern.length();
            }
        } catch (BadLocationException e) {

        }
    }
    
    // Removes only our private highlights
    public void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        for ( Highlighter.Highlight h : hilite.getHighlights()){
            if (h.getPainter() instanceof MyHighlightPainter){
                hilite.removeHighlight(h);
            }
        }
    }
    

    // A private subclass of the default highlight painter
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        public MyHighlightPainter(Color color) {
            super(color);
        }
    }
    
}
