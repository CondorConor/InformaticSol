package Phase1;

import processing.core.PApplet;
import processing.core.PImage;

public class Character {

    String name;
    String role;
    int hps; //health points
    int dmg; //damage
    int def; //defence
    int spd; //speed
    int currentHps;
    float hpPercentage;
    String specialName;
    String special;
    PImage sprite;
    PImage icon;
    PImage specialIcon;
    enum AttackType {JUMP, RUN, RANGED}
    AttackType attackType;
    PImage currentSprite;
    PImage[] attackSprites;
    PImage[] standSprites;
    PImage[] specialSprites;
    PImage damagedSprite;
    int x,y,h,w;


    void useSpecial(){}

    Character(String name, String role, int hps, int dmg, int def, int spd, String specialName, String special, PImage sprite, PImage icon, PImage sIcon){
        this.name = name;
        this.role = role;
        this.hps = hps;
        this.dmg = dmg;
        this.def = def;
        this.spd = spd;
        this.specialName = specialName;
        this.special = special;
        this.sprite = sprite;
        this.icon = icon;
        this.specialIcon = sIcon;
        this.currentHps = hps;
        this.hpPercentage= currentHps/hps;
    }
    void standAnimation(PApplet p5){
        p5.image(damagedSprite,x,y,w,h);
    }

    Character createCopy() {
        return new Character(name, role, hps, dmg, def, spd, specialName, special, sprite, icon, specialIcon);
    }
}
