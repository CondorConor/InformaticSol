package MainCode;

import processing.core.PApplet;
import processing.core.PConstants;

public class PopUp {
    String text;
    RectButton close;
    int x,y,w,h;
    boolean visible = false;
    boolean notShowAgain = false;
    PopUp(String text, PApplet p5){
        this.text =text;
        x = p5.width/2;
        y = p5.height/2;
        w = p5.width/2;
        h = p5.height/2;
        initButton(p5);
    }
    void initButton(PApplet p5){
        close = new RectButton(p5, "CLOSE", x+w/3,y+h*2/5, p5.width*5/32, p5.height*2/32, true);
    }
    void display(PApplet p5){
        p5.pushStyle();
        p5.fill(40);
        p5.stroke(150);
        p5.strokeWeight(3);
        p5.rectMode(PConstants.CENTER);
        p5.rect(x,y,w,h);
        p5.fill(0,200,0);
        close.display(p5);
        p5.text(text, x, y-((float)p5.height*2/32));
        p5.popStyle();
    }
}
