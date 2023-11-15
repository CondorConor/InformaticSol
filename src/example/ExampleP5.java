package example;

import processing.core.PApplet;

public class ExampleP5 extends PApplet {

    int c;

    public static void main(String[] args) {
        PApplet.main("example.ExampleP5", args);
    }

    public void settings(){
        size(800, 800, P2D);
        smooth(10);
    }

    public void setup(){
        c = color(255, 0, 255);
    }

    public void draw(){
        background(255, 255, 0);

        fill(c); strokeWeight(5);
        line(width/2, height/2, mouseX, mouseY);
        ellipse(mouseX, mouseY, 50, 50);


        fill(0); textSize(18);
        text("Exemple P5 + Intellij IDEA", 50, 50);
    }


    // ******************* KEYBOARD interaction ***************************** //

    public void keyPressed(){
        println("KEY PRESSED");
    }

    // ******************* MOUSE interaction ***************************** //

    public void mousePressed(){
        println("MOUSE PRESSED");
    }

    public void mouseDragged(){
        println("MOUSE DRAGGED");
    }

    public void mouseReleased() {
        println("MOUSE RELEASED");
    }

}
