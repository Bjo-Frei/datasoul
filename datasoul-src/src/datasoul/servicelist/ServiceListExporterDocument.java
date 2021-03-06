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

package datasoul.servicelist;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

import datasoul.DatasoulMainForm;
import datasoul.serviceitems.ServiceItem;
import datasoul.serviceitems.song.Chord;
import datasoul.serviceitems.song.ChordShapePanel;
import datasoul.serviceitems.song.ChordsDB;
import datasoul.serviceitems.song.Song;
import datasoul.serviceitems.text.TextServiceItem;

/**
 *
 * @author samuel
 */
public class ServiceListExporterDocument {

    private Document document;
    public static final int TYPE_PDF = 0;
    public static final int TYPE_RTF = 1;

    private boolean exportGuitarTabs;
    private LinkedList<Paragraph> guitarTabs;
    
    public ServiceListExporterDocument(int type, String filename, boolean exportGuitarTabs) throws FileNotFoundException, DocumentException{
        document = new Document();
        
        // ensure file do not exist to avoid garbage at the end of the file
        File f = new File(filename);
        if (f.exists()){
            f.delete();
        }
        
        if (type == TYPE_RTF){
            RtfWriter2.getInstance(document, new FileOutputStream(filename));
        }else{
            PdfWriter w = PdfWriter.getInstance(document, new FileOutputStream(filename));
            w.setStrictImageSequence(true);
        }
        document.open();
        document.addCreator("Datasoul "+DatasoulMainForm.getVersion());
        document.addCreationDate();

        this.exportGuitarTabs = exportGuitarTabs;
        if (exportGuitarTabs){
            guitarTabs = new LinkedList<Paragraph>();
        }
    }
    
    public void write() {
        document.close();
    }
    
    public void addServicePlan() throws DocumentException{
        
        ServiceListTable slt = ServiceListTable.getActiveInstance();
        
        Paragraph p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("SERVICE PLAN"), FontFactory.getFont(FontFactory.HELVETICA, 12));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        
        p = new Paragraph(slt.getTitle(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        
        Table t = new Table(4);
        t.setWidths(new int[]{10,5, 50, 35});
        t.setPadding(2.0f);
        t.setWidth(100.0f);
        
        t.addCell(createHeaderCell(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("TIME")));
        t.addCell(createHeaderCell(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("MIN")));
        t.addCell(createHeaderCell(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("TITLE")));
        t.addCell(createHeaderCell(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("NOTES")));
        
        
        for (int i=0; i<slt.getRowCount(); i++){
            
            ServiceItem si = (ServiceItem) slt.getServiceItem(i);
            Cell c;

            // Start time
            c = new Cell(si.getStartTime());
            c.setMaxLines(1);
            c.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            c.setHorizontalAlignment(Cell.ALIGN_CENTER);
            t.addCell(c);
            
            // Duration
            c = new Cell(Integer.toString(si.getDuration()));
            c.setMaxLines(1);
            c.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            c.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            t.addCell(c);
            
            // Title
            c = new Cell(si.getTitle());
            c.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            t.addCell(c);

            // Notes
            c = new Cell(si.getNotes());
            c.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            t.addCell(c);

        }
        
        document.add(t);
        
        p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("NOTES"), FontFactory.getFont(FontFactory.HELVETICA_BOLD));
        document.add(p);
        
        p = new Paragraph(slt.getNotes(), FontFactory.getFont(FontFactory.HELVETICA));
        p.setIndentationLeft(10.0f);
        document.add(p);

        document.newPage();
        
        
    }
    
    
    private Cell createHeaderCell(String text) throws BadElementException{

        Cell c = new Cell(new Chunk(text,FontFactory.getFont(FontFactory.HELVETICA_BOLD) ));
        c.setMaxLines(1);
        c.setHeader(true);
        c.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        c.setHorizontalAlignment(Cell.ALIGN_CENTER);
        return c;

    }
    
    private void addSongHeader(Song s) throws DocumentException {
        Paragraph p;

        p = new Paragraph(s.getTitle(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        document.add(p);
        
        if (!s.getSongAuthor().trim().equals("")){
            p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("AUTHOR")+": "+s.getSongAuthor(), FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            document.add(p);
        }
        
        if (!s.getCopyright().trim().equals("")){
            p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("COPYRIGHT")+s.getCopyright(), FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            document.add(p);
        }

        if (!s.getSongSource().trim().equals("")){
            p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("SOURCE")+s.getSongSource(), FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            document.add(p);
        }
        

        
    }
    
    public void addSongLyrics(Song s) throws DocumentException{

        addSongHeader(s);

        Paragraph p;

        p = new Paragraph("("+java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("LYRICS")+")", FontFactory.getFont(FontFactory.HELVETICA, 8));
        document.add(p);
        
        String text = s.getText().replace(Song.CHORUS_MARK , "").replace(Song.SLIDE_BREAK, "");

        p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);
        
        p = new Paragraph(text, FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);
        
        document.newPage();
        
    }
    
    public void addTextItem(TextServiceItem t) throws DocumentException{

        String text = t.getText().replace(Song.CHORUS_MARK , "").replace(Song.SLIDE_BREAK, "");
        
        Paragraph p;
        
        p = new Paragraph(t.getTitle(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        document.add(p);

        p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);
        
        p = new Paragraph(text, FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);
        
        document.newPage();
    }
    
    public void addSongChordsSimple(Song s) throws DocumentException{

        
        // If we don't have the chords saved, skip it
        if (s.getChordsSimplified().trim().equals(""))
            return;
        
        addSongHeader(s);

        Paragraph p;

        p = new Paragraph("("+java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("CHORDS SIMPLE")+")", FontFactory.getFont(FontFactory.HELVETICA, 8));
        document.add(p);

        p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);

        String text[] = s.getChordsSimplified().replace(Song.CHORUS_MARK , " ").replace(Song.SLIDE_BREAK, " ").split("\n");
        
        addSongChords(text);
        
        p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);

        if (exportGuitarTabs){
            p = new Paragraph(s.getTitle(), FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            guitarTabs.add(p);

            p = new Paragraph("("+java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("CHORDS COMPLETE")+")", FontFactory.getFont(FontFactory.HELVETICA, 8));
            guitarTabs.add(p);

            guitarTabs.addAll(addChordsShape(Song.getUsedChords(s.getChordsSimplified())));
        }

        document.newPage();
        
    }


    public void addSongChordsComplete(Song s) throws DocumentException{

        
        // If we don't have the chords saved, skip it
        if (s.getChordsComplete().trim().equals(""))
            return;
        
        addSongHeader(s);

        Paragraph p;

        p = new Paragraph("("+java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("CHORDS COMPLETE")+")", FontFactory.getFont(FontFactory.HELVETICA, 8));
        document.add(p);
        
        p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);

        String text[] = s.getChordsComplete().replace(Song.CHORUS_MARK , " ").replace(Song.SLIDE_BREAK, " ").split("\n");
        addSongChords(text);

        
        p = new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA));
        document.add(p);

        if (exportGuitarTabs){
            p = new Paragraph(s.getTitle(), FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            guitarTabs.add(p);
            p = new Paragraph("("+java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("CHORDS COMPLETE")+")", FontFactory.getFont(FontFactory.HELVETICA, 8));
            guitarTabs.add(p);
            
            guitarTabs.addAll(addChordsShape(Song.getUsedChords(s.getChordsComplete())));
        }
                
        document.newPage();
        
    }

    private LinkedList<Paragraph> addChordsShape(ArrayList<String> chordsName) throws DocumentException{
        ChordsDB chordsDB = ChordsDB.getInstance();
        String notCatalogued = "";
        LinkedList<Chunk> images = new LinkedList<Chunk>();
        LinkedList<File> filesToDelete = new LinkedList<File>();
        LinkedList<Paragraph> ret = new LinkedList<Paragraph>();

        for(int i=0; i<chordsName.size();i++){
            Chord chord = chordsDB.getChordByName(chordsName.get(i));
            if(chord!=null){
                ChordShapePanel csp = new ChordShapePanel(2, chord.getName(),chord.getShape());
                BufferedImage im = csp.createImage();
                try{
                    File tmp = File.createTempFile("datasoul-img", ".png");
                    tmp.deleteOnExit();
                    filesToDelete.add(tmp);
                    
                    ImageIO.write(im, "png", tmp);
                    Chunk c = new Chunk(Image.getInstance(tmp.getAbsolutePath()), 0, 0, false);
                    images.add(c);
                }catch(IOException e){
                    JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("INTERNAL ERROR: ")+e.getMessage());
                }
            }else{
                notCatalogued += chordsName.get(i);
            }
        }
        
        Paragraph p = new Paragraph();

        if (!images.isEmpty()){
            for (Chunk c : images){
                p.add(c);
            }
            p.setLeading(images.getFirst().getImage().getScaledHeight());
            p.setKeepTogether(true);
        }

        ret.add(p);
        
        if (!notCatalogued.equals("") ){
            p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("THE FOLLOWING CHORDS ARE NOT CATALOGED: ")+notCatalogued, FontFactory.getFont(FontFactory.HELVETICA, 8));
            ret.add(p);
        }

        for (File f : filesToDelete){
            try{
                f.delete();
            }catch(Exception e){
                //ignore, it will be deleted on exit
            }
        }

        return ret;
    }
    

    private void addSongChords(String[] text) throws DocumentException{
        Font chordfont  = new Font(Font.COURIER, 12, Font.BOLD, Color.BLUE.darker());
        Font lyricfont  = new Font(Font.COURIER, 12, Font.NORMAL);
        Paragraph p;

        for (String line : text){

            Matcher matcher = Song.CHORDS_REGEX_PATTERN.matcher(line);

            if (matcher.find()){
                if (line.startsWith("=")){
                    line = line.replaceFirst("=", " ");
                }
                p = new Paragraph(line, chordfont);
            }else{
                p = new Paragraph(line, lyricfont);
            }
            document.add(p);
        }
        
    }

    public void addGuitarTabs() throws DocumentException{

        document.newPage();

        Paragraph p = new Paragraph(java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("GUITAR TABS"), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        document.add(p);

        for (Paragraph p2 : guitarTabs){
            document.add(p2);
        }

    }
    
}


