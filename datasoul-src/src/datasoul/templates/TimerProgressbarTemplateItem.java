/* 
 * Copyright 2005-2008 Samuel Mello & Eduardo Schnell
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
 * TimerProgressbarTemplateItem.java
 *
 * Created on 21 May 2006, 16:28
 *
 */

package datasoul.templates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author samuelm
 */
public class TimerProgressbarTemplateItem extends TemplateItem {
    
    private Color colorGoAhead;
    private Color colorFinish;
    private Color colorStop;
    private Color colorRemainingTime;
    private float alphaRemainingTime;
    private float alphaUsedTime; 
    private float position;
    private boolean showTimer;
    
    /** Creates a new instance of TimerProgressbarTemplateItem */
    public TimerProgressbarTemplateItem() {
        super();
        this.setColorGoAhead("00FF00");
        this.setColorFinish("FFFF00");
        this.setColorStop("FF0000");
        this.setColorRemainingTime("FFFFFF");
        this.setAlphaRemainingTime(0.5f);
        this.setAlphaUsedTime(1.0f);
        this.setPosition(0.8f);
        this.setWidth(100);
        this.setHeight(50);
        this.showTimer = true;
        
    }

    @Override
    public void setUpEdit(){
        super.setUpEdit();
        registerColorChooser("ColorGoAhead");
        registerColorChooser("ColorFinish");
        registerColorChooser("ColorStop");
        registerColorChooser("ColorRemainingTime");
    }

    public void assign(TimerProgressbarTemplateItem from){
        super.assign(from);
        this.setColorGoAhead(from.getColorGoAhead());
        this.setColorFinish(from.getColorFinish());
        this.setColorStop(from.getColorStop());
        this.setColorRemainingTime(from.getColorRemainingTime());
        this.setAlphaRemainingTime(from.getAlphaRemainingTime());
        this.setAlphaUsedTime(from.getAlphaUsedTime());
        this.setPosition(from.getPosition());
        this.setShowTimer(from.getShowTimer());
    }
    
    @Override
    protected void registerProperties(){
        super.registerProperties();
        properties.add("ColorGoAhead");
        registerDisplayString("ColorGoAhead", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("COLOR GO AHEAD"));
        properties.add("ColorFinish");
        registerDisplayString("ColorFinish", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("COLOR FINISH"));
        properties.add("ColorStop");
        registerDisplayString("ColorStop", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("COLOR STOP"));
        properties.add("ColorRemainingTime");
        registerDisplayString("ColorRemainingTime", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("COLOR REMAINING TIME"));
        properties.add("AlphaRemainingTime");
        registerDisplayString("AlphaRemainingTime", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("ALPHA REMAINING TIME"));
        properties.add("AlphaUsedTime");
        registerDisplayString("AlphaUsedTime", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("ALPHA USED TIME"));
        properties.add("Position");
        registerDisplayString("Position", java.util.ResourceBundle.getBundle("datasoul/internationalize").getString("POSITION"));
    }

    public void draw(Graphics2D g, float time) {
        
        if (showTimer == false)
            return;
        
        Color oldColor = g.getColor();
        Composite oldComposite = g.getComposite();
        try{
            
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // draw the used time part
            g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getAlpha() * alphaUsedTime * time ) );
            if (position < 0.8f){
                g.setColor(colorGoAhead);
            }else if (position >= 0.8f && position < 1.0f){
                g.setColor(colorFinish);
            }else{
                g.setColor(colorStop);
            }
            g.fillRect(this.getLeft(), this.getTop(), (int) (this.getWidth()*position), this.getHeight() );
            
            
            // draw the remaining time parte
            g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getAlpha() * alphaRemainingTime * time ) );
            g.setColor(colorRemainingTime);
            g.fillRect(this.getLeft() + ((int) (this.getWidth()*position)) , this.getTop(), (int) (this.getWidth()* (1.0f - position)), this.getHeight() );
            
            
        }finally{
            g.setColor(oldColor);
            g.setComposite(oldComposite);
        }
        
        
    }

    public String getColorGoAhead() {
        Color color = colorGoAhead;
        String hexcolor = (color.getRed()>0x09) ? Integer.toHexString(color.getRed()) : Integer.toHexString(color.getRed())+"0";
        hexcolor = hexcolor + ((color.getGreen()>0x09) ? Integer.toHexString(color.getGreen()) : Integer.toHexString(color.getGreen())+"0");
        hexcolor = hexcolor + ((color.getBlue()>0x09)?Integer.toHexString(color.getBlue()) : Integer.toHexString(color.getBlue())+"0");
        return hexcolor;
    }

    public void setColorGoAhead(String colorGoAhead) {
        this.colorGoAhead = stringToColor(colorGoAhead);
        firePropChanged("ColorGoAhead");
    }

    public String getColorFinish() {
        Color color = colorFinish;
        String hexcolor = (color.getRed()>0x09) ? Integer.toHexString(color.getRed()) : Integer.toHexString(color.getRed())+"0";
        hexcolor = hexcolor + ((color.getGreen()>0x09) ? Integer.toHexString(color.getGreen()) : Integer.toHexString(color.getGreen())+"0");
        hexcolor = hexcolor + ((color.getBlue()>0x09)?Integer.toHexString(color.getBlue()) : Integer.toHexString(color.getBlue())+"0");
        return hexcolor;
    }

    public void setColorFinish(String colorFinish) {
        this.colorFinish = stringToColor(colorFinish);
        firePropChanged("ColorFinish");
    }

    public String getColorStop() {
        Color color = colorStop;
        String hexcolor = (color.getRed()>0x09) ? Integer.toHexString(color.getRed()) : Integer.toHexString(color.getRed())+"0";
        hexcolor = hexcolor + ((color.getGreen()>0x09) ? Integer.toHexString(color.getGreen()) : Integer.toHexString(color.getGreen())+"0");
        hexcolor = hexcolor + ((color.getBlue()>0x09)?Integer.toHexString(color.getBlue()) : Integer.toHexString(color.getBlue())+"0");
        return hexcolor;
    }

    public void setColorStop(String colorStop) {
        this.colorStop = stringToColor(colorStop);
        firePropChanged("ColorStop");
    }

    public String getColorRemainingTime() {
        Color color = colorRemainingTime;
        String hexcolor = (color.getRed()>0x09) ? Integer.toHexString(color.getRed()) : Integer.toHexString(color.getRed())+"0";
        hexcolor = hexcolor + ((color.getGreen()>0x09) ? Integer.toHexString(color.getGreen()) : Integer.toHexString(color.getGreen())+"0");
        hexcolor = hexcolor + ((color.getBlue()>0x09)?Integer.toHexString(color.getBlue()) : Integer.toHexString(color.getBlue())+"0");
        return hexcolor;

    }

    public void setColorRemainingTime(String colorRemainingTime) {
        this.colorRemainingTime = stringToColor(colorRemainingTime);
        firePropChanged("ColorRemainingTime");
    }

    public float getAlphaRemainingTime() {
        return alphaRemainingTime;
    }

    public void setAlphaRemainingTime(float alphaRemainingTime) {
        if (alphaRemainingTime >= 0.0 && alphaRemainingTime <= 1.0f){
            this.alphaRemainingTime = alphaRemainingTime;
            firePropChanged("AlphaRemainingTime");
        }
    }
    
    public void setAlphaRemainingTime(String alphaRemainingTime) {
        this.setAlphaRemainingTime( Float.parseFloat(alphaRemainingTime) );
    }
    

    public float getAlphaUsedTime() {
        return alphaUsedTime;
    }

    public void setAlphaUsedTime(float alphaUsedTime) {
        if (alphaUsedTime >= 0.0f && alphaUsedTime <= 1.0f){
            this.alphaUsedTime = alphaUsedTime;
            firePropChanged("AlphaUsedTime");
        }
    }
    
    public void setAlphaUsedTime(String alphaUsedTime) {
        this.setAlphaUsedTime( Float.parseFloat(alphaUsedTime) );
    }

    public float getPosition(){
        return this.position;
    }
    
    public void setPosition(float position){
        if (position < 0.0f)
            position = 0.0f;
        if (position > 1.0f)
            position = 1.0f;
                
        this.position = position;
        firePropChanged("Position");
    }

    public void setPosition(String position){
        this.setPosition(Float.parseFloat(position));
    }

    public boolean getShowTimer(){
        return showTimer;
    }

    public void setShowTimer(String show){
        // Ignore for backward compatibility
    }

    
    public void setShowTimer(boolean show){
        this.showTimer = show;
    }

}


