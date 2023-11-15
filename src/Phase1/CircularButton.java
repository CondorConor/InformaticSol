package Phase1;

import processing.core.PApplet;

public class CircularButton {

    int x, y, r;
    String text;
    CircularButton(String text, int x, int y, int r){
        this.text = text;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    void display(PApplet p5){
        p5.pushStyle();
        p5.stroke(255);
        p5.fill(50);
        p5.circle(x,y,r);
        p5.fill(0);
        p5.text(text, x, y);
        p5.popStyle();
    }
}
