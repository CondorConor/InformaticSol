package MainCode;

import processing.core.PApplet;
import processing.core.PImage;

public class Item {
    float x,y,w,h;
    PImage sprite;
    Character character;
    int charPosition;
    boolean down = false;
    enum Position{TOP, FRONT}
    Position position;
    Item(PImage sprite, Character character, int charPosition, Position position){
        this.sprite = sprite;
        this.character = character;
        this.charPosition = charPosition;
        this.position = position;
        this.w = character.w;
        this.h = character.h;
    }
    void upDownAnimation(PApplet p5, int clock) {
        down = (clock >= 4);
    }
    boolean setCharacter(Character character, int charPosition){
        if(character!=null&&!character.died) {
            this.character = character;
            this.charPosition = charPosition;
            return true;
        }
        return false;
    }
    void display(PApplet p5){
        x = character.x;
        y = character.y;
        if(position == Position.TOP){
            y = y-(float)p5.width*(down?2:3)/48;
        }else if(position == Position.FRONT){
            if(charPosition<3){
                x = x+(float)p5.width/32;
            }else{
                x = x-(float)p5.width/32;
            }
            y = y+(float)p5.width/48;
        }
        p5.image(sprite, x, y, w, h);
    }
}
