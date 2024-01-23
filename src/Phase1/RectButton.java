package Phase1;

import processing.core.PApplet;
import processing.core.PFont;

public class RectButton {
    String text;
    int x, y, w, h;
    int colorDisabled;
    int colorMouseOn;
    int colorMouseOff;
    boolean enabled;
    RectButton(PApplet p5, String text, int x, int y, int w, int h, boolean enabled){
        this.text = text;
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.enabled=enabled;
        colorDisabled = p5.color(100);
        colorMouseOn = p5.color(100,255,100);
        colorMouseOff = p5.color(50);
    }
    void display(PApplet p5){
        p5.pushStyle();
        if(!enabled){
            p5.fill(colorDisabled);
        }else if(MouseOnButton(p5)){
            p5.fill(colorMouseOn);
        }else{
            p5.fill(colorMouseOff);
        }
        p5.stroke(255);p5.strokeWeight(3);
        p5.rect(this.x, this.y, this.w, this.h);
        p5.fill(255);
        if(MouseOnButton(p5)&&enabled){
            p5.fill(0,220,0);
        }
        p5.text(text, this.x, this.y);
        p5.popStyle();
    }
    boolean MouseOnButton(PApplet p5){
        return abs(p5.mouseX-this.x)<this.w/2&&abs(p5.mouseY-this.y)<this.h/2;
    }
    static float abs(float x){
        if(x<0){
            return -x;
        }
        return x;
    }
}