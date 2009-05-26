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
 * ContentRender.java
 *
 * Created on March 20, 2006, 8:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasoul.render;

import datasoul.config.ConfigObj;
import datasoul.templates.DisplayTemplate;
import datasoul.templates.TemplateItem;
import datasoul.templates.TextTemplateItem;
import datasoul.templates.TimerProgressbarTemplateItem;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

/**
 *
 * @author root
 */
public abstract class ContentRender {
    
    private DisplayTemplate template;
    private boolean templateChanged;
    private String title;
    private boolean titleChanged;
    private String slide;
    private boolean slideChanged;
    private String nextSlide;
    private boolean nextSlideChanged;
    private String clock;
    private boolean clockChanged;
    private String timer;
    private boolean timerChanged;
    private String alert;
    private boolean alertChanged;
    private String songAuthor;
    private boolean songAuthorChanged;
    private String copyright;
    private boolean copyrightChanged;
    private String songSource;
    private boolean songSourceChanged;
    private DisplayTemplate alertTemplate;
    private boolean alertTemplateChanged;
    private boolean alertActive;
    private boolean showTimer;
    private float timerProgress;
    private boolean showHideNeedUpdate;
    
    private Semaphore updSemaphore;
    private UpdateThread updThread;
    
    public static final int TRANSITION_HIDE = 0;
    public static final int TRANSITION_SHOW = 1;
    public static final int TRANSITION_CHANGE = 2;
    
    private int slideTransTimer;
    private int slideTransTimerTotal;
    private int slideTransition;
    
    private int alertTransTimer;
    private int alertTransTimerTotal;
    private int alertTransition;
    
    protected int width, height, left, top;
    protected BufferedImage transitionImage;
    private BufferedImage templateImage;
    private BufferedImage alertImage;
    
    /** Creates a new instance of ContentRender */
    public ContentRender() {
        updSemaphore = new Semaphore(0);
        updThread = new UpdateThread();
        updThread.start();
        slideTransition = TRANSITION_HIDE;
        slideTransTimerTotal = 1;
        slideTransTimer = 0;
    }
    
    public abstract void paint(BufferedImage img, AlphaComposite rule);
    public abstract void clear();
    public abstract void flip();
    public abstract void setWindowTitle(String title);
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        this.titleChanged = true;
    }

    public String getSongAuthor() {
        return songAuthor;
    }
    
    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
        this.songAuthorChanged = true;
    }
    
    public String getCopyright() {
        return copyright;
    }
    
    public void setCopyright(String copyright) {
        this.copyright = copyright;
        this.copyrightChanged = true;
    }
    
    public String getSongSource() {
        return songSource;
    }
    
    public void setSongSource(String songSource) {
        this.songSource = songSource;
        this.songSourceChanged = true;
    }
    
    public String getSlide() {
        return slide;
    }
    
    public void setSlide(String slide) {
        this.slide = slide;
        this.slideChanged = true;
    }
    
    public String getNextSlide() {
        return nextSlide;
    }
    
    public void setNextSlide(String nextSlide) {
        this.nextSlide = nextSlide;
        this.nextSlideChanged = true;
    }
    
    public String getClock() {
        return clock;
    }
    
    public void setClock(String clock) {
        this.clock = clock;
        this.clockChanged = true;
    }
    
    public String getTimer() {
        return timer;
    }
    
    public void setTimer(String timer) {
        this.timer = timer;
        this.timerChanged = true;
    }
    
    public void setShowTimer(boolean show){
        this.showTimer = show;
        this.timerChanged = true;
    }
    
    public void setTimerProgress(float f){
        this.timerProgress = f;
        this.timerChanged  = true;
    }
    
    public String getAlert(){
        return this.timer;
    }
    
    public void setAlert(String alert){
        this.alert = alert;
        this.alertChanged = true;
    }
    
    public void setTemplate(String template){
        try{
            try{
                this.template = new DisplayTemplate(template);
            }catch(FileNotFoundException f){
                // inexistent template, fallback to default
                this.template = new DisplayTemplate( ConfigObj.getInstance().getTemplateText() );
            }
            this.templateChanged = true;
        }catch(Exception e){
            e.printStackTrace();
            this.template = null;
        }
    }
    
    public String getTemplate(){
        if (template != null){
            return this.template.getName();
        }else{
            return null;
        }
    }
    
    public void setAlertTemplate(String template){
        try{
            this.alertTemplate = new DisplayTemplate(template);
            this.alertTemplateChanged = true;
        }catch(Exception e){
            e.printStackTrace();
            this.alertTemplate = null;
        }
    
    }
    
    public void setAlertActive(boolean active){
        this.alertActive = active;
        this.showHideNeedUpdate = true;
    }
    
    public void slideShow(int transictionTime){
        slideTransition = TRANSITION_SHOW;
        if (transictionTime >= 0){
            slideTransTimer = transictionTime;
            slideTransTimerTotal = transictionTime;
            showHideNeedUpdate = true;
            updateDisplayValues();
            saveImage(templateImage, template, 1.0f);   
        }
        update();
    }
    
    public void slideChange(int transictionTime){
        if (transictionTime >= 0 && slideTransition != TRANSITION_HIDE){
            slideTransition = TRANSITION_CHANGE;
            slideTransTimer = transictionTime;
            slideTransTimerTotal = transictionTime;
            updateDisplayValues();
            saveImage(templateImage, template, 1.0f);   
        }else if (transictionTime < 0){
            updateDisplayValues();
            saveImage(templateImage, template, 1.0f);   
        }
        update();
    }
    
    public void slideHide(int transictionTime){
        slideTransition = TRANSITION_HIDE;
        if (transictionTime >= 0){
            slideTransTimer = transictionTime;
            slideTransTimerTotal = transictionTime;
            showHideNeedUpdate = true;
        }
        update();
    }
    
    public void alertShow(int transictionTime){
        alertTransition = TRANSITION_SHOW;
        if (transictionTime >= 0){
            alertTransTimer = transictionTime;
            alertTransTimerTotal = transictionTime;
            updateDisplayValues();
            saveImage(alertImage, alertTemplate, 1.0f);
        }
        update();
    }
    
    public void alertHide(int transictionTime){
        alertTransition = TRANSITION_HIDE;
        if (transictionTime >= 0){
            alertTransTimer = transictionTime;
            alertTransTimerTotal = transictionTime;
        }
        update();
    }
    
    
    private void update(){
        updSemaphore.release();
    }
    
    /**
     * Update the screen only if needed.
     * That is, if the changes were only in content that isn't
     * being shown, no update is done.
     *
     * returns if a screen refresh is needed
     */
    public boolean updateDisplayValues(){
        
            boolean needUpdate = false;
            
            needUpdate = showHideNeedUpdate || templateChanged;
            
            int content;
            if (template != null){
                synchronized(template){
                    for (TemplateItem t : template.getItems() ){
                        if (t instanceof TextTemplateItem) {
                            content = ((TextTemplateItem)t).getContentIdx();
                            if ( (templateChanged || titleChanged) && content == TextTemplateItem.CONTENT_TITLE) {
                                ((TextTemplateItem)t).setText(title);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || slideChanged) && content == TextTemplateItem.CONTENT_SLIDE) {
                                ((TextTemplateItem)t).setText(slide);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || nextSlideChanged) && content == TextTemplateItem.CONTENT_NEXTSLIDE) {
                                ((TextTemplateItem)t).setText(nextSlide);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || clockChanged) && content == TextTemplateItem.CONTENT_CLOCK) {
                                ((TextTemplateItem)t).setText(clock);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || timerChanged) && content == TextTemplateItem.CONTENT_TIMER) {
                                ((TextTemplateItem)t).setText(timer);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || alertChanged) && content == TextTemplateItem.CONTENT_ALERT) {
                                ((TextTemplateItem)t).setText(alert);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || songAuthorChanged) && content == TextTemplateItem.CONTENT_SONGAUTHOR) {
                                ((TextTemplateItem)t).setText(songAuthor);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || copyrightChanged) && content == TextTemplateItem.CONTENT_COPYRIGHT) {
                                ((TextTemplateItem)t).setText(copyright);
                                needUpdate = true;
                                continue;
                            }
                            if ( (templateChanged || songSourceChanged) && content == TextTemplateItem.CONTENT_SONGSOURCE) {
                                ((TextTemplateItem)t).setText(songSource);
                                needUpdate = true;
                                continue;
                            }
                        }else if (t instanceof TimerProgressbarTemplateItem && timerChanged){
                            ((TimerProgressbarTemplateItem)t).setPosition(timerProgress);
                            ((TimerProgressbarTemplateItem)t).setShowTimer( showTimer );
                            needUpdate = true;
                            continue;
                        }// if is TextTemplateItem
                    }// for need update
                }//synchornized
            }// if template not null
            
            if (alertActive){
                for (TemplateItem t : alertTemplate.getItems() ){
                    if (t instanceof TextTemplateItem) {
                        content = ((TextTemplateItem)t).getContentIdx();
                        if ( titleChanged && content == TextTemplateItem.CONTENT_TITLE) {
                            ((TextTemplateItem)t).setText(title);
                            needUpdate = true;
                            continue;
                        }
                        if ( slideChanged && content == TextTemplateItem.CONTENT_SLIDE) {
                            ((TextTemplateItem)t).setText(slide);
                            needUpdate = true;
                            continue;
                        }
                        if ( nextSlideChanged && content == TextTemplateItem.CONTENT_NEXTSLIDE) {
                            ((TextTemplateItem)t).setText(nextSlide);
                            needUpdate = true;
                            continue;
                        }
                        if ( clockChanged && content == TextTemplateItem.CONTENT_CLOCK) {
                            ((TextTemplateItem)t).setText(clock);
                            needUpdate = true;
                            continue;
                        }
                        if ( timerChanged && content == TextTemplateItem.CONTENT_TIMER) {
                            ((TextTemplateItem)t).setText(timer);
                            needUpdate = true;
                            continue;
                        }
                        if ( alertChanged && content == TextTemplateItem.CONTENT_ALERT) {
                            ((TextTemplateItem)t).setText(alert);
                            needUpdate = true;
                            continue;
                        }
                        if ( songAuthorChanged && content == TextTemplateItem.CONTENT_SONGAUTHOR) {
                            ((TextTemplateItem)t).setText(songAuthor);
                            needUpdate = true;
                            continue;
                        }
                        if ( copyrightChanged && content == TextTemplateItem.CONTENT_COPYRIGHT) {
                            ((TextTemplateItem)t).setText(copyright);
                            needUpdate = true;
                            continue;
                        }
                        if ( songSourceChanged && content == TextTemplateItem.CONTENT_SONGSOURCE) {
                            ((TextTemplateItem)t).setText(songSource);
                            needUpdate = true;
                            continue;
                        }
                    }else if (t instanceof TimerProgressbarTemplateItem && timerChanged){
                        ((TimerProgressbarTemplateItem)t).setPosition(timerProgress);
                        ((TimerProgressbarTemplateItem)t).setShowTimer( showTimer );
                        continue;
                    }// if is TextTemplateItem
                }// for need update
            }

        // everything has been updated
        if (slideTransTimer == 0 && alertTransTimer == 0){
            templateChanged = false;
            titleChanged = false;
            slideChanged = false;
            nextSlideChanged = false;
            clockChanged = false;
            timerChanged = false;
            alertChanged = false;
            songAuthorChanged = false;
            copyrightChanged = false;
            songSourceChanged = false;
            showHideNeedUpdate = false;
        }
            
            
        return needUpdate;    
           
    }
    
    
    private void updateScreen(){
        
        float paintSlideLevel;
        float paintAlertLevel;
        
        // Determine the levels:
        if (alertTransTimerTotal > 0)
            paintAlertLevel = (float) alertTransTimer / (float) alertTransTimerTotal;
        else
            paintAlertLevel = 0;

        if (alertTransition != TRANSITION_HIDE){
            paintAlertLevel = 1 - paintAlertLevel;
        }
        
        if (slideTransTimerTotal > 0)
            paintSlideLevel = (float) slideTransTimer / (float) slideTransTimerTotal;
        else
            paintSlideLevel = 0;

        if (slideTransition != TRANSITION_HIDE){
            paintSlideLevel = 1 - paintSlideLevel;
        }
        
        
        //paint it
        synchronized(this){
            clear();

            if (slideTransition == TRANSITION_CHANGE){
                
                if (template.getTransitionKeepBGIdx() == DisplayTemplate.KEEP_BG_YES && paintSlideLevel < 1.0f){
                    synchronized(transitionImage){
                        paint(transitionImage, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    }
                    synchronized(templateImage){
                        paint(templateImage, AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, paintSlideLevel));
                    }
                }else{
                    synchronized(transitionImage){
                        paint(transitionImage, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - paintSlideLevel));
                    }
                    synchronized(templateImage){
                        paint(templateImage, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, paintSlideLevel));
                    }
                }
            }else{
                synchronized(templateImage){
                    paint(templateImage, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, paintSlideLevel));
                }
            }

            if (paintAlertLevel > 0){
                paint(alertImage, AlphaComposite.getInstance(AlphaComposite.SRC_OVER, paintAlertLevel));
            }

            //System.out.println(this+" AlertActive="+alertActive+" paintSlideLevel="+paintSlideLevel+" paintAlertLevel="+paintAlertLevel);
            
            flip();
        }

    }
    
    

    private void saveImage(BufferedImage dst, DisplayTemplate tpl, float alpha){
        synchronized(dst){
            Graphics2D g = dst.createGraphics();

            // Clear it first
            g.setComposite( AlphaComposite.getInstance(AlphaComposite.CLEAR, 0) );
            g.fillRect(0, 0, dst.getWidth(), dst.getHeight());

            if (tpl != null){
                tpl.paint(g, alpha);
            }   
        }
    }
    
    public void saveTransitionImage(){
        saveImage(transitionImage, template, 1.0f);
    }
    
    
    /**
     * this method MUST be overriden by super class
     */
    public void initDisplay(int width, int height, int top, int left){
        this.width = width;
        this.height = height;
        this.top = top;
        this.left = left;
        transitionImage = new BufferedImage(DisplayTemplate.TEMPLATE_WIDTH, DisplayTemplate.TEMPLATE_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        templateImage = new BufferedImage(DisplayTemplate.TEMPLATE_WIDTH, DisplayTemplate.TEMPLATE_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        alertImage = new BufferedImage(DisplayTemplate.TEMPLATE_WIDTH, DisplayTemplate.TEMPLATE_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    public abstract void shutdown();
    
    public abstract void paintBackground(BufferedImage bufferedImage);
    
    public abstract void setClear(int i);
    
    public abstract void setBlack(int i);
    
    private class UpdateThread extends Thread {
        public void run(){
            long t1, t2, t3, sleepTime;
            while (true){
                try{
                    updSemaphore.acquire();
                    
                    while ( slideTransTimer > 0 || alertTransTimer > 0){
                        t1 = System.currentTimeMillis();
                        
                        if (updateDisplayValues()){
                            updateScreen();
                        }
                        
                        t2 = System.currentTimeMillis();
                        
                        sleepTime = 50 - (t2 - t1);
                        
                        if (sleepTime > 0){
                            Thread.sleep( sleepTime );
                        }
                        
                        t3 = System.currentTimeMillis();
                        
                        slideTransTimer -= (t3 - t1);
                        alertTransTimer -= (t3 - t1);
                        
                        if (slideTransTimer < 0)
                            slideTransTimer = 0;
                        
                        if (alertTransTimer < 0)
                            alertTransTimer = 0;
                        
                    }
                    
                    updateDisplayValues();
                    updateScreen();
                    
                }catch(Exception e){
                    // ignore
                    e.printStackTrace();
                }
            }
        }
    }
    
    
}

