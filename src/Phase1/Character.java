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
    enum AnimationStage {PREPARING, PERFORMING, FINISHING, RETRIEVING, END}
    AttackType attackType;
    PImage currentSprite;
    PImage[] attackSprites;
    PImage[] attackSpritesRight;
    PImage[] standSprites;
    PImage[] standSpritesRight;
    PImage[] specialSprites;
    PImage damagedSprite;
    PImage damagedSpriteRight;
    boolean right;
    int x,y,h,w,initialX, initialY;
    void useSpecial(){}

    Character(String name, String role, int hps, int dmg, int def, int spd, String specialName, String special, PImage sprite, PImage icon, PImage sIcon, AttackType attackType){
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
        this.attackType = attackType;
    }

    void initAnimationSprites(PImage damagedSprite, PImage damagedSpriteRight, PImage[] standSprites, PImage[] standSpritesRight,PImage[] attackSprites, PImage[] attackSpritesRight){
        this.damagedSprite = damagedSprite;
        this.damagedSpriteRight = damagedSpriteRight;
        this.standSprites = standSprites;
        this.standSpritesRight = standSpritesRight;
        this.attackSprites = attackSprites;
        this.attackSpritesRight = attackSpritesRight;
    }
    void standAnimation(PApplet p5, int clock) {
        if (right) {
            if(clock<3){
                p5.image(standSpritesRight[0], x, y, w, h);
            }else{
                p5.image(standSpritesRight[1], x, y, w, h);
            }
        } else {
            if(clock<3){
                p5.image(standSprites[0], x, y, w, h);
            }else{
                p5.image(standSprites[1], x, y, w, h);
            }
        }
    }

    void standStill(PApplet p5){
        if(right){
            p5.image(standSpritesRight[0], x, y , w, h);
        }else {
            p5.image(standSprites[0], x, y, w, h);
        }
    }

    void damagedAnimation(PApplet p5){
        if(p5.frameCount%4<2){
            x=x-3;
        }else{
            x=x+3;
        }
        if (right) {
            p5.image(damagedSpriteRight, x,y,w,h);
        }else{
            p5.image(damagedSprite, x,y,w,h);
        }
    }

    AnimationStage attackAnimation(PApplet p5, AnimationStage stage, Character target, int clock){
        if (right) {
            //if (attackType == AttackType.JUMP) {
                if (stage == AnimationStage.PREPARING) {
                    p5.image(attackSpritesRight[0], x, y, w, h);
                    target.standStill(p5);
                    if (clock == 0) {
                        stage = AnimationStage.PERFORMING;
                    }
                } else if (stage == AnimationStage.PERFORMING) {
                    int a = p5.width/80;
                    x = x - a;
                    if ((p5.frameCount % 4) < 2) {
                        p5.image(attackSpritesRight[1], x, y, w, h);
                    } else {
                        p5.image(attackSpritesRight[2], x, y, w, h);
                    }
                    target.standStill(p5);
                    if (clock == 5) {
                        stage = AnimationStage.FINISHING;
                    }
                } else if (stage == AnimationStage.FINISHING) {
                    if (clock == 5) {
                        p5.image(attackSpritesRight[3], x, y, w, h);
                        target.standStill(p5);
                    } else {
                        p5.image(attackSpritesRight[4], x, y, w, h);
                        if (clock == 0) {
                            target.damagedAnimation(p5);
                        } else {
                            target.standStill(p5);
                        }
                    }
                    if (clock == 2) {
                        stage = AnimationStage.RETRIEVING;
                    }
                } else if (stage == AnimationStage.RETRIEVING) {

                    target.standStill(p5);
                    if (clock == 0) {
                        p5.image(attackSpritesRight[0], x, y, w, h);
                    } else if (clock == 1) {
                        stage = AnimationStage.END;
                    }else {
                        int a = p5.width/80;
                        x = x + a;
                        if ((p5.frameCount % 4) < 2) {
                            p5.image(attackSpritesRight[1], x, y, w, h);
                        } else {
                            p5.image(attackSpritesRight[2], x, y, w, h);
                        }
                    }
              //  }

            } else {
                //if (attackType == AttackType.JUMP) {
                    if (stage == AnimationStage.PREPARING) {
                        p5.image(attackSprites[0], x, y, w, h);
                        target.standStill(p5);
                        if (clock == 3) {
                            stage = AnimationStage.PERFORMING;
                        }
                    } else if (stage == AnimationStage.PERFORMING) {
                        int a = p5.width/32;
                        x = x + a;
                        if ((p5.frameCount % 4) < 2) {
                            p5.image(attackSprites[1], x, y, w, h);
                        } else {
                            p5.image(attackSprites[2], x, y, w, h);
                        }
                        target.standStill(p5);
                        if (clock == 2) {
                            stage = AnimationStage.FINISHING;
                        }
                    } else if (stage == AnimationStage.FINISHING) {
                        if (clock == 2) {
                            p5.image(attackSprites[3], x, y, w, h);
                            target.standStill(p5);
                        } else {
                            p5.image(attackSprites[4], x, y, w, h);
                            if (clock == 3) {
                                target.damagedAnimation(p5);
                            } else {
                                target.standStill(p5);
                            }
                        }
                        if (clock == 1) {
                            stage = AnimationStage.RETRIEVING;
                        }
                    } else if (stage == AnimationStage.RETRIEVING) {
                        int a = p5.width/32;
                        x = x - a;
                        if ((p5.frameCount % 4) < 2) {
                            p5.image(attackSprites[1], x, y, w, h);
                        } else {
                            p5.image(attackSprites[2], x, y, w, h);
                        }
                        target.standStill(p5);
                        if (clock == 3) {
                            p5.image(attackSprites[0], x, y, w, h);
                        } else if (clock == 0) {
                            stage = AnimationStage.END;
                        }
                   // }
                }
            }
        }
        return stage;
    }

    Character createCopy() {
        Character character = new Character(name, role, hps, dmg, def, spd, specialName, special, sprite, icon, specialIcon, attackType);
        character.initAnimationSprites(damagedSprite,damagedSpriteRight,standSprites,standSpritesRight,attackSprites,attackSpritesRight);
        return character;
    }
}
