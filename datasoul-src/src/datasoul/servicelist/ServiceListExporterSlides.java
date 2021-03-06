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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import datasoul.DatasoulMainForm;
import datasoul.render.ContentDisplayRenderer;
import datasoul.render.ContentRender;

/**
 *
 * @author samuel
 */
public class ServiceListExporterSlides {

    private Document document;
    private LinkedList<String> deleteOnDispose;
    private ExporterContentRender render;
    private int slideCount;
    
    public ServiceListExporterSlides(String filename, int width, int height) throws FileNotFoundException, DocumentException{
        document = new Document();
        deleteOnDispose = new LinkedList<String>();
        slideCount = 0;
        
        // ensure file do not exist to avoid garbage at the end of the file
        File f = new File(filename);
        if (f.exists()){
            f.delete();
        }
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.setMargins(0, 0, 0, 0);

        document.setPageSize(new Rectangle (width, height));
        document.open();
        document.addCreator("Datasoul "+DatasoulMainForm.getVersion());
        document.addCreationDate();



        render = new ExporterContentRender(width, height, new DummyContentDisplay());

    }
    
    public void write() {
        document.close();
    }

    public void cleanup(){
        /* Delete temporary files */
        for (String s : deleteOnDispose){
            File f = new File(s);
            f.delete();
        }
        /* Stop render thread */
        render.cleanup();
    }

    public void addEmptySlide() throws DocumentException{
        try{
            File tmp = File.createTempFile("datasoul-img-bg", ".png");
            tmp.deleteOnExit();

            ImageIO.write(render.getBackgroundImage(), "png", tmp);
            deleteOnDispose.add(tmp.getAbsolutePath());

            document.add(Image.getInstance(tmp.getAbsolutePath()));
            slideCount++;
        }catch(IOException e){
            e.printStackTrace();
            slideCount = -1;
        }

    }

    public ContentRender getRender() {
        return render;
    }

    public int getSlideCount(){
        return slideCount;
    }

    public class DummyContentDisplay extends ContentDisplayRenderer {

        @Override
        public void repaint() {
            // Ignore
        }
        
    }

    public class ExporterContentRender extends ContentRender {

        private int width;
        private int height;

        public ExporterContentRender(int width, int height, DummyContentDisplay dummy ){
            super(width, height, dummy);
            dummy.initDisplay(width, height);
            this.width = width;
            this.height = height;
        }

        @Override
        public void slideChange(int transictionTime){
            updateDisplayValues();

            BufferedImage outputImage0 = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

            synchronized(outputImage0){
                Graphics2D g = outputImage0.createGraphics();

                // Clear it first
                g.setComposite( AlphaComposite.getInstance(AlphaComposite.CLEAR, 0) );
                g.fillRect(0, 0, outputImage0.getWidth(), outputImage0.getHeight());

                // Paint Background
                g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER) );
                g.drawImage(backgroundImage, 0, 0, outputImage0.getWidth(), outputImage0.getHeight(), null);

                // Paint Template
                if (template != null){
                    template.paint(g, 1.0f);
                }
                g.dispose();
            }

            try{
                File tmp = File.createTempFile("datasoul-img", ".png");
                tmp.deleteOnExit();

                ImageIO.write(outputImage0, "png", tmp);
                deleteOnDispose.add(tmp.getAbsolutePath());

                document.add(Image.getInstance(tmp.getAbsolutePath()));
                slideCount++;
            }catch(Exception e){
                e.printStackTrace();
                slideCount = -1;
            }

        }

        public BufferedImage getBackgroundImage(){
            return backgroundImage;
        }

        public void cleanup(){
            shutdown();
        }

    }

}

