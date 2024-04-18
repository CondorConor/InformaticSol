package MainCode;

import processing.core.PApplet;
import processing.core.PImage;

public class CircularButton {

    int x, y, r;
    String text;
    CircularButton(String text, int x, int y, int r){
        this.text = text;
        this.x = x;
        this.y = y;
        this.r = r;
    }
    boolean MouseOnButton(PApplet p5){
        return PApplet.dist(x,y,p5.mouseX, p5.mouseY)<r/2;
    }
    float abs(float x){
        if(x<0){
            return -x;
        }
        return x;
    }
    void display(PApplet p5, PImage img){
        p5.pushStyle();
        try{p5.image(img, x, y, r, r);}catch(Exception ignored){}
        p5.stroke(255);
        p5.noFill();
        p5.circle(x,y,r);
        p5.popStyle();
    }
}
