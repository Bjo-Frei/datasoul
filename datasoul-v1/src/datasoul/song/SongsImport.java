/*
 * ImportSong.java
 *
 * Created on 21 de Maio de 2007, 20:12
 */

package datasoul.song;

import datasoul.util.ListTable;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import datasoul.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xml.serialize.OutputFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author  Administrador
 */
public class SongsImport extends javax.swing.JFrame {

    AllSongsListTable actualSongs;

    static final int SONG_FIELD_EQUALS=0;
    static final int SONG_FIELD_NEW=1;
    static final int SONG_FIELD_OLD=2;
    static final int SONG_FIELD_CONFLICT=3;

    static final int MERGE_DEFAULT=0;
    static final int MERGE_OLD=1;
    static final int MERGE_NEW=2;
    
    Hashtable<String, MergeSong> mergeSongs = new Hashtable<String, MergeSong>();
    MergeSong mergeSongDefault = new MergeSong();

    /** Creates new form ImportSong */
    public SongsImport() {
        initComponents();

        this.center();

        titleRadioGroup.add(radioTitleOld);
        titleRadioGroup.add(radioTitleNew);

        authorRadioGroup.add(radioAuthorOld);
        authorRadioGroup.add(radioAuthorNew);

        lyricsRadioGroup.add(radioLyricsOld);
        lyricsRadioGroup.add(radioLyricsNew);

        completedRadioGroup.add(radioCompletedOld);
        completedRadioGroup.add(radioCompletedNew);

        simplifiedRadioGroup.add(radioSimplifiedOld);
        simplifiedRadioGroup.add(radioSimplifiedNew);

        
        this.actualSongs = AllSongsListTable.getInstance();
        
       SongListTable addSongListTable = new SongListTable();
       this.listAddSongs.setModel(addSongListTable);        
       this.listAddSongs.setDeleteAfterDragAndDrop(true);

       SongListTable ignoreSongListTable = new SongListTable();
       this.listIgnoreSongs.setModel(ignoreSongListTable);        
       this.listIgnoreSongs.setDeleteAfterDragAndDrop(true);

       SongListTable mergeSongListTable = new SongListTable();
       this.listMergeSongs.setModel(mergeSongListTable);        
       this.listMergeSongs.setDeleteAfterDragAndDrop(true);

        File[] files = openFilesToImport();
        for(File file:files){
            preProcessFile(file);
        }
        
        mergeSongDefault.title = MERGE_NEW;
        mergeSongDefault.author = MERGE_NEW;
        mergeSongDefault.lyrics = MERGE_NEW;
        mergeSongDefault.chordsCompleted = MERGE_NEW;
        mergeSongDefault.chordsSimplified = MERGE_NEW;

        showMergeSongOnScreen(mergeSongDefault);
        
        applyMergeForAllSongs(mergeSongDefault);
    }
    
    private void applyMergeForAllSongs(MergeSong mergeSong){
        mergeSongDefault = mergeSong;
        for(String name:mergeSongs.keySet()){
            mergeSongs.remove(name);
        }
    }

    private void applyMergeForSong(Song song, MergeSong mergeSong){
        String name = song.getTitle();
        if(mergeSongs.contains(name)){
            mergeSongs.remove(name);
        }
        
        mergeSongs.put(name, mergeSong);
    }
    
    public void center(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle frame = getBounds();
        setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
    }
    
    private void preProcessFile(File file){
        Document dom=null;
        Node node = null;
        Song newSong;
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

        newSong = new Song();
        try {
            newSong.readObject(node);
            newSong.setFilePath(file.getPath());
        } catch (Exception e) {
            ShowDialog.showReadFileError(file, e);
        }
        
        if(actualSongs.getSong(newSong.getTitle())!=null){
            Song actualSong = actualSongs.getSong(newSong.getFileName());

            solveConflicts(actualSong, newSong);
        }else{
            ((ListTable)this.listAddSongs.getModel()).addItem(newSong);
        }
        
    }
    
    private void solveConflicts(Song actualSong, Song newSong){
        int lyrics = checkFieldConflict(actualSong.getText(), newSong.getText());
        int completed = checkFieldConflict(actualSong.getChordsComplete(), newSong.getChordsComplete());
        int simplified = checkFieldConflict(actualSong.getChordsSimplified(), newSong.getChordsSimplified());

        int total = (lyrics + completed + simplified);
        
        if( total == 0){
            ((ListTable)this.listIgnoreSongs.getModel()).addItem(newSong);    
            return;
        }

        if( lyrics == SONG_FIELD_CONFLICT ||
            completed == SONG_FIELD_CONFLICT ||
            simplified == SONG_FIELD_CONFLICT ){
                ((ListTable)this.listMergeSongs.getModel()).addItem(newSong);
                return;
        }else{
           ((ListTable)this.listMergeSongs.getModel()).addItem(newSong);    
        }
    }

    private int checkFieldConflict(String fieldActualSong, String fieldNewSong){
        if(fieldActualSong.equals(fieldNewSong)){
            return SONG_FIELD_EQUALS;
        }else{
            //same lyrics, same Completed, diff Simplified
            if(fieldActualSong.equals("")){
                return SONG_FIELD_NEW;
            }else{
                if(fieldNewSong.equals("")){
                    return SONG_FIELD_OLD;
                }else{
                    return SONG_FIELD_CONFLICT;
                }
            }
        }
    }
    
    private File[] openFilesToImport(){
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.setFileFilter(new javax.swing.filechooser.FileFilter() { 
                      public boolean accept(File f) { 
                          if (f.isDirectory()) { 
                              return true; 
                          } 
                          String name = f.getName(); 
                          if (name.endsWith(".song")) { 
                              return true; 
                          } 
                          return false; 
                      } 
   
                      public String getDescription() { 
                          return "*.song"; 
                      } 
                  });
        //File dir = new File (System.getProperty("user.dir"));
        File dir = new File ("D:\\temp\\songs");
        fc.setCurrentDirectory(dir);
        if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
/*
            String fileName = fc.getSelectedFile().getPath();

            File file = new File(fileName);

            Document dom=null;
            Node node = null;
            ServiceListTable slt;
            try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                    //Using factory get an instance of document builder
                    DocumentBuilder db = dbf.newDocumentBuilder();

                    //parse using builder to get DOM representation of the XML file
                    dom = db.parse(file);

                    //node = dom.getDocumentElement().getChildNodes().item(0);
                    node = dom.getElementsByTagName("ServiceListTable").item(0);

            }catch(Exception e) {
                ShowDialog.showReadFileError(file, e);
            }        

            slt = ServiceListTable.getActiveInstance();
            try {
                slt.readObject(node);
            } catch (Exception e) {
                ShowDialog.showReadFileError(file, e);
            }
         
            tableServiceList.setModel(slt);
 */
        }
        return fc.getSelectedFiles();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel5 = new javax.swing.JPanel();
        titleRadioGroup = new javax.swing.ButtonGroup();
        authorRadioGroup = new javax.swing.ButtonGroup();
        lyricsRadioGroup = new javax.swing.ButtonGroup();
        completedRadioGroup = new javax.swing.ButtonGroup();
        simplifiedRadioGroup = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textOldSong = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textNewSong = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        scrollListMergeSongs = new javax.swing.JScrollPane();
        listMergeSongs = new datasoul.util.DnDTable();
        radioTitleOld = new javax.swing.JRadioButton();
        radioTitleNew = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        radioAuthorOld = new javax.swing.JRadioButton();
        radioAuthorNew = new javax.swing.JRadioButton();
        radioLyricsNew = new javax.swing.JRadioButton();
        radioLyricsOld = new javax.swing.JRadioButton();
        radioCompletedOld = new javax.swing.JRadioButton();
        radioCompletedNew = new javax.swing.JRadioButton();
        radioSimplifiedNew = new javax.swing.JRadioButton();
        radioSimplifiedOld = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        scrollListAddSongs = new javax.swing.JScrollPane();
        listAddSongs = new datasoul.util.DnDTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        scrollListIgnoreSongs = new javax.swing.JScrollPane();
        listIgnoreSongs = new datasoul.util.DnDTable();
        btnImportFiles = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 177, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 97, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jSplitPane2.setDividerLocation(230);
        jScrollPane1.setViewportView(textOldSong);

        jLabel4.setText("Old file");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jLabel4)
                .addContainerGap(196, Short.MAX_VALUE))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
        );
        jSplitPane2.setLeftComponent(jPanel2);

        jScrollPane2.setViewportView(textNewSong);

        jLabel5.setText("New file");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jLabel5)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
        );
        jSplitPane2.setRightComponent(jPanel3);

        jSplitPane1.setRightComponent(jSplitPane2);

        jSplitPane3.setDividerLocation(210);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jLabel3.setText("Merge song");

        listMergeSongs.setModel(new javax.swing.table.DefaultTableModel(
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
        listMergeSongs.setMinimumSize(new java.awt.Dimension(19, 19));
        listMergeSongs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mergeSongsOnKeyReleased(evt);
            }
        });
        listMergeSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mergeSongsOnMouseClicked(evt);
            }
        });

        scrollListMergeSongs.setViewportView(listMergeSongs);

        radioTitleOld.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioTitleOld.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioTitleNew.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioTitleNew.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel7.setText("New");

        jLabel6.setText("Old");

        radioAuthorOld.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioAuthorOld.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioAuthorNew.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioAuthorNew.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioLyricsNew.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioLyricsNew.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioLyricsOld.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioLyricsOld.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioCompletedOld.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioCompletedOld.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioCompletedNew.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioCompletedNew.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioSimplifiedNew.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioSimplifiedNew.setMargin(new java.awt.Insets(0, 0, 0, 0));

        radioSimplifiedOld.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioSimplifiedOld.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jButton1.setText("Apply it for selected song");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyForSongActionPeformed(evt);
            }
        });

        jButton2.setText("Apply it for all songs");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyForAllSongsActionPeformed(evt);
            }
        });

        jLabel12.setText("Chords Simplified");

        jLabel11.setText("Chords Completed");

        jLabel10.setText("Lyrics");

        jLabel9.setText("Author");

        jLabel8.setText("Title");

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, scrollListMergeSongs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3)
                    .add(jButton2)
                    .add(jButton1)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel8)
                            .add(jLabel9)
                            .add(jLabel10)
                            .add(jLabel11)
                            .add(jLabel12))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 117, Short.MAX_VALUE)
                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel6Layout.createSequentialGroup()
                                .add(radioTitleOld)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(radioTitleNew))
                            .add(jPanel6Layout.createSequentialGroup()
                                .add(jLabel6)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel7))
                            .add(jPanel6Layout.createSequentialGroup()
                                .add(radioLyricsOld)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(radioLyricsNew))
                            .add(jPanel6Layout.createSequentialGroup()
                                .add(radioAuthorOld)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(radioAuthorNew))
                            .add(jPanel6Layout.createSequentialGroup()
                                .add(radioCompletedOld)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(radioCompletedNew))
                            .add(jPanel6Layout.createSequentialGroup()
                                .add(radioSimplifiedOld)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(radioSimplifiedNew)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollListMergeSongs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(radioTitleOld)
                    .add(radioTitleNew)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(radioAuthorOld)
                    .add(radioAuthorNew)
                    .add(jLabel9))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(radioLyricsOld)
                    .add(radioLyricsNew)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(radioCompletedOld)
                    .add(radioCompletedNew)
                    .add(jLabel11))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(radioSimplifiedOld)
                    .add(radioSimplifiedNew)
                    .add(jLabel12))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton2)
                .addContainerGap())
        );
        jSplitPane3.setRightComponent(jPanel6);

        jSplitPane4.setDividerLocation(80);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jLabel1.setText("Add song");

        listAddSongs.setModel(new javax.swing.table.DefaultTableModel(
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
        listAddSongs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addSongOnKeyReleased(evt);
            }
        });
        listAddSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSongOnMouseClicked(evt);
            }
        });

        scrollListAddSongs.setViewportView(listAddSongs);

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scrollListAddSongs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .add(jLabel1))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollListAddSongs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
        );
        jSplitPane4.setTopComponent(jPanel7);

        jLabel2.setText("Ignore song");

        listIgnoreSongs.setModel(new javax.swing.table.DefaultTableModel(
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
        listIgnoreSongs.setMinimumSize(new java.awt.Dimension(19, 19));
        listIgnoreSongs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ignoreSongsOnKeyReleased(evt);
            }
        });
        listIgnoreSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ignoreSongsOnMouseClicked(evt);
            }
        });

        scrollListIgnoreSongs.setViewportView(listIgnoreSongs);

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scrollListIgnoreSongs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .add(jLabel2))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollListIgnoreSongs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
        );
        jSplitPane4.setRightComponent(jPanel8);

        jSplitPane3.setLeftComponent(jSplitPane4);

        jSplitPane1.setLeftComponent(jSplitPane3);

        btnImportFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/apply.png")));
        btnImportFiles.setText("ImportFiles");
        btnImportFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportFilesActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/datasoul/icons/button_cancel.png")));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(663, Short.MAX_VALUE)
                .add(btnImportFiles)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancel)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnCancel)
                    .add(btnImportFiles))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnImportFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportFilesActionPerformed
        
        boolean isNewSong = false;
        
        //ADD files
        for(int i=0; i<((ListTable)this.listAddSongs.getModel()).getRowCount(); i++){
            Song song = (Song)((ListTable)this.listAddSongs.getModel()).getValueAt(i,AllSongsListTable.getInstance().getSongColumn());
            if(actualSongs.getSong(song.getTitle())!=null){
                deleteFile(actualSongs.getSong(song.getTitle()));
                isNewSong = true;
            }
            
            saveFile(song);

            if(isNewSong){
                AllSongsListTable.getInstance().addItem(song);
                isNewSong = false;
                AllSongsListTable.getInstance().sortByName();            
            }
        }
        
        //MERGE files
        for(int i=0; i<((ListTable)this.listMergeSongs.getModel()).getRowCount(); i++){
            Song song = (Song)((ListTable)this.listMergeSongs.getModel()).getValueAt(i,AllSongsListTable.getInstance().getSongColumn());
            Song oldSong = actualSongs.getSong(song.getTitle());
            
            Song newSong = oldSong;
            
            MergeSong mergeSong = mergeSongs.get(song.getTitle());

            if(!mergeSongs.contains(song.getTitle())){
                mergeSong = mergeSongDefault;
            }
            
            if(mergeSong.author == MERGE_NEW){
                newSong.setSongAuthor(song.getSongAuthor());
            }
            if(mergeSong.lyrics == MERGE_NEW){
                newSong.setText(song.getText());
            }
            if(mergeSong.chordsCompleted == MERGE_NEW){
                newSong.setChordsComplete(song.getChordsComplete());
            }
            if(mergeSong.chordsSimplified == MERGE_NEW){
                newSong.setSongAuthor(song.getChordsSimplified());
            }
            
            deleteFile(oldSong);
            
            saveFile(newSong);

        }
        
        JOptionPane.showMessageDialog(null, "Files was imported corredtly");
        this.dispose();
    }//GEN-LAST:event_btnImportFilesActionPerformed

  private void deleteFile(Song song){
        File file = new File(song.getFilePath());
        file.delete();
  }  
    
  private void saveFile(Song song){
      String path = System.getProperty("user.dir") + System.getProperty("file.separator") + 
                "songs"+ System.getProperty("file.separator")+song.getTitle()+".song";
      song.setFilePath(path);
      
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
        
    }
    
    
    private void applyForAllSongsActionPeformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyForAllSongsActionPeformed
        MergeSong mergeSong = getMergeSongFromScreen();
        applyMergeForAllSongs(mergeSong);
    }//GEN-LAST:event_applyForAllSongsActionPeformed

    private void applyForSongActionPeformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyForSongActionPeformed
        if(listMergeSongs.getSelectedRow()>=0){
            Song song = (Song)listMergeSongs.getModel().getValueAt(listMergeSongs.getSelectedRow(),AllSongsListTable.getInstance().getSongColumn());
            MergeSong mergeSong = getMergeSongFromScreen();
            applyMergeForSong(song,mergeSong);
        }
    }//GEN-LAST:event_applyForSongActionPeformed
    
    private MergeSong getMergeSongFromScreen(){
        MergeSong mergeSong = new MergeSong();
    
        if(radioTitleOld.isSelected()){
            mergeSong.title = MERGE_OLD;
        }else{
            mergeSong.title = MERGE_NEW;
        }
        
        if(radioAuthorOld.isSelected()){
            mergeSong.author = MERGE_OLD;
        }else{
            mergeSong.author = MERGE_NEW;
        }

        if(radioLyricsOld.isSelected()){
            mergeSong.lyrics = MERGE_OLD;
        }else{
            mergeSong.lyrics = MERGE_NEW;
        }

        if(radioCompletedOld.isSelected()){
            mergeSong.chordsCompleted = MERGE_OLD;
        }else{
            mergeSong.chordsCompleted = MERGE_NEW;
        }

        if(radioSimplifiedOld.isSelected()){
            mergeSong.chordsSimplified = MERGE_OLD;
        }else{
            mergeSong.chordsSimplified = MERGE_NEW;
        }
        
        return mergeSong;
    }

    private void showMergeSongOnScreen(MergeSong mergeSong){
        if(mergeSong == null){
            mergeSong = mergeSongDefault;
        }

        if(mergeSong.title == MERGE_OLD){
            radioTitleOld.setSelected(true);
            radioTitleNew.setSelected(false);
        }else{
            radioTitleNew.setSelected(true);
            radioTitleOld.setSelected(false);
        }

        if(mergeSong.author == MERGE_OLD){
            radioAuthorOld.setSelected(true);
            radioAuthorNew.setSelected(false);
        }else{
            radioAuthorOld.setSelected(false);
            radioAuthorNew.setSelected(true);
        }
        
        if(mergeSong.lyrics == MERGE_OLD){
            radioLyricsOld.setSelected(true);
            radioLyricsNew.setSelected(false);
        }else{
            radioLyricsOld.setSelected(false);
            radioLyricsNew.setSelected(true);
        }
        
        if(mergeSong.chordsCompleted == MERGE_OLD){
            radioCompletedOld.setSelected(true);
            radioCompletedNew.setSelected(false);
        }else{
            radioCompletedOld.setSelected(false);
            radioCompletedNew.setSelected(true);
        }
        
        if(mergeSong.chordsSimplified == MERGE_OLD){
            radioSimplifiedOld.setSelected(true);
            radioSimplifiedNew.setSelected(false);
        }else{
            radioSimplifiedOld.setSelected(false);
            radioSimplifiedNew.setSelected(true);
        }
        
        
    }
    
    private void ignoreSongsOnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ignoreSongsOnKeyReleased
        ignoreSongsOnMouseClicked(null);
    }//GEN-LAST:event_ignoreSongsOnKeyReleased

    private void mergeSongsOnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mergeSongsOnMouseClicked
        if(listMergeSongs.getSelectedRow()<0){
            return;
        }
        
        Song song = (Song)listMergeSongs.getModel().getValueAt(listMergeSongs.getSelectedRow(),AllSongsListTable.getInstance().getSongColumn());

        if(listAddSongs.getSelectedRowCount()>0)
            listAddSongs.removeRowSelectionInterval(0,listAddSongs.getSelectedRow());
        if(listIgnoreSongs.getSelectedRowCount()>0)
            listIgnoreSongs.removeRowSelectionInterval(0,listIgnoreSongs.getSelectedRow());

        if(actualSongs.getSong(song.getTitle())!=null){
            Song actualSong = actualSongs.getSong(song.getFileName());
            showSong(textOldSong,actualSong);
        }else{
            textOldSong.setText("");            
        }
        
        showMergeSongOnScreen(mergeSongs.get(song.getTitle()));
        
        showSong(textNewSong,song);
    }//GEN-LAST:event_mergeSongsOnMouseClicked

    private void ignoreSongsOnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ignoreSongsOnMouseClicked
        if(listIgnoreSongs.getSelectedRow()<0){
            return;
        }
        Song song = (Song)listIgnoreSongs.getModel().getValueAt(listIgnoreSongs.getSelectedRow(),AllSongsListTable.getInstance().getSongColumn());

        if(listAddSongs.getSelectedRowCount()>0)
            listAddSongs.removeRowSelectionInterval(1,listAddSongs.getSelectedRow());
        if(listMergeSongs.getSelectedRowCount()>0)
            listMergeSongs.removeRowSelectionInterval(1,listMergeSongs.getSelectedRow());
        
        textOldSong.setText("");
        showSong(textNewSong,song);
    }//GEN-LAST:event_ignoreSongsOnMouseClicked

    private void mergeSongsOnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mergeSongsOnKeyReleased
        mergeSongsOnMouseClicked(null);
    }//GEN-LAST:event_mergeSongsOnKeyReleased

    private void addSongOnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addSongOnKeyReleased
        addSongOnMouseClicked(null);
    }//GEN-LAST:event_addSongOnKeyReleased

    private void addSongOnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSongOnMouseClicked
        if(listAddSongs.getSelectedRow()<0){
            return;
        }
        
        Song song = (Song)listAddSongs.getModel().getValueAt(listAddSongs.getSelectedRow(),AllSongsListTable.getInstance().getSongColumn());

        if(listMergeSongs.getSelectedRowCount()>0)
            listMergeSongs.removeRowSelectionInterval(0,listMergeSongs.getSelectedRow());
        if(listIgnoreSongs.getSelectedRowCount()>0)
            listIgnoreSongs.removeRowSelectionInterval(0,listIgnoreSongs.getSelectedRow());
        
        textOldSong.setText("");
        showSong(textNewSong,song);
    }//GEN-LAST:event_addSongOnMouseClicked

    private void showSong(JTextPane textPane, Song song){
        StringBuffer sb = new StringBuffer();
        
        stringTitle(sb, "Title");
        sb.append(song.getTitle()); sb.append("\n");
        stringTitle(sb, "Author");        
        sb.append(song.getSongAuthor()); sb.append("\n");
        stringTitle(sb, "Lyrics");
        sb.append(song.getText()); sb.append("\n");
        stringTitle(sb, "Chords Completed");
        sb.append(song.getChordsComplete()); sb.append("\n");
        stringTitle(sb, "Chords Simplified");
        sb.append(song.getChordsSimplified()); sb.append("\n");
        
        textPane.setText(sb.toString());
    }
    
    private void stringTitle(StringBuffer sb, String type){
        sb.append("***** ");
        sb.append(type);
        sb.append(" *****");
        sb.append("\n");
    }
        
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup authorRadioGroup;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnImportFiles;
    private javax.swing.ButtonGroup completedRadioGroup;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private datasoul.util.DnDTable listAddSongs;
    private datasoul.util.DnDTable listIgnoreSongs;
    private datasoul.util.DnDTable listMergeSongs;
    private javax.swing.ButtonGroup lyricsRadioGroup;
    private javax.swing.JRadioButton radioAuthorNew;
    private javax.swing.JRadioButton radioAuthorOld;
    private javax.swing.JRadioButton radioCompletedNew;
    private javax.swing.JRadioButton radioCompletedOld;
    private javax.swing.JRadioButton radioLyricsNew;
    private javax.swing.JRadioButton radioLyricsOld;
    private javax.swing.JRadioButton radioSimplifiedNew;
    private javax.swing.JRadioButton radioSimplifiedOld;
    private javax.swing.JRadioButton radioTitleNew;
    private javax.swing.JRadioButton radioTitleOld;
    private javax.swing.JScrollPane scrollListAddSongs;
    private javax.swing.JScrollPane scrollListIgnoreSongs;
    private javax.swing.JScrollPane scrollListMergeSongs;
    private javax.swing.ButtonGroup simplifiedRadioGroup;
    private javax.swing.JTextPane textNewSong;
    private javax.swing.JTextPane textOldSong;
    private javax.swing.ButtonGroup titleRadioGroup;
    // End of variables declaration//GEN-END:variables
 
    public class MergeSong extends javax.swing.JFrame {
        public int title=0;
        public int author=0;
        public int lyrics=0;
        public int chordsCompleted=0;
        public int chordsSimplified=0;
        
    }
    
}