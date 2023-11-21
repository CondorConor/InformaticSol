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
    String specialName;
    String special;
    PImage sprite;
    PImage icon;

    void useSpecial(){}
    Character(String name, String role, int hps, int dmg, int def, int spd, String specialName, String special, PImage sprite, PImage icon){
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
        this.currentHps = hps;
    }
}
