package Phase1;

import processing.core.PApplet;
import processing.core.PImage;

public class Item {
    float x,y,w,h;
    PImage sprite;
    Character character;
    boolean down = false;
    Item(PImage sprite, Character character){
        this.sprite = sprite;
        this.character = character;
        this.w = character.w;
        this.h = character.h;
    }
    void upDownAnimation(PApplet p5, int clock) {
        down = (clock >= 4);
    }
    void display(PApplet p5){
        x = character.x;
        y = character.y-(float)p5.width*(down?1:2)/32;
        p5.image(sprite, x, y, w, h);
    }
}
