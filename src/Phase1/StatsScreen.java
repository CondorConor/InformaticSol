package Phase1;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

import javax.management.StringValueExp;

public class StatsScreen extends Screen{
    Character character;
    PImage hpIcon;
    PImage dmgIcon;
    PImage defIcon;
    PImage spdIcon;
    public void setCharacter(Character character) {
        this.character = character;
    }
    void initElements(PApplet p5){
        screenType = GUI.ScreenType.STATSCREEN;
        b1 = new RectButton(p5, "SELECT", p5.width*28/32, p5.height*28/32, p5.width*5/32, p5.height*2/32, true);
        b2 = new RectButton(p5, "BACK", p5.width*20/32, p5.height*28/32, p5.width*5/32, p5.height*2/32, true);
        hpIcon = p5.loadImage("Icons/add.png");
        dmgIcon = p5.loadImage("Icons/sword.png");
        defIcon = p5.loadImage("Icons/armor.png");
        spdIcon = p5.loadImage("Icons/boots.png");
    }
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp){
        //button
        b1.display(p5);
        b2.display(p5);

        //title
        p5.pushStyle();
        p5.fill(255);
        p5.textAlign(0, 3);
        p5.textFont(fontTitle, 40);
        p5.text(character.name, p5.width*2/32, p5.height*3/32);
        p5.popStyle();

        //text
        p5.pushStyle();
        p5.fill(255);
        p5.text(character.specialName, p5.width*25/32, p5.height*15/32);
        p5.textAlign(0, 0);
        p5.text(character.special, p5.width*22/32, p5.height*18/32);
        p5.text("Hps   "+character.hps, p5.width*11/32, p5.height*10/32);
        p5.text("Dmg   "+character.dmg, p5.width*11/32, p5.height*16/32);
        p5.text("Def   "+character.def, p5.width*11/32, p5.height*22/32);
        p5.text("Spd   "+character.spd, p5.width*11/32, p5.height*28/32);

        //subtitle
        p5.fill(200);
        p5.text("\""+character.role+"\"", p5.width*4/32, p5.height*5/32);
        p5.popStyle();

        //rectangles
        p5.pushStyle();
        p5.image(character.sprite, p5.width*5/32, p5.height*19/32, p5.width*6/32, p5.height*25/32);
        p5.image(hpIcon,p5.width*10/32, p5.height*10/32, p5.width*1/32, p5.height*2/32);
        p5.image(dmgIcon,p5.width*10/32, p5.height*16/32, p5.width*1/32, p5.height*2/32);
        p5.image(defIcon,p5.width*10/32, p5.height*22/32, p5.width*1/32, p5.height*2/32);
        p5.image(spdIcon,p5.width*10/32, p5.height*28/32, p5.width*1/32, p5.height*2/32);
        p5.image(character.specialIcon, p5.width*25/32, p5.height*8/32, p5.width*7/32, p5.height*12/32);
        p5.popStyle();

    }
}