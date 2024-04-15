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
    int extraW;
    PImage icon;
    PImage specialIcon;
    enum Action {ATTACK, DEFEND, SPECIAL}
    enum AttackType {JUMP, RANGED}
    enum AnimationStage {WAITING, PREPARING, PERFORMING, FINISHING, RETRIEVING, END}
    AnimationStage stage = AnimationStage.WAITING;
    AttackType attackType;
    PImage[] standSprites = new PImage[2];
    PImage[] standSpritesRight = new PImage[2];
    PImage[] attackSprites = new PImage[6];
    PImage[] attackSpritesRight = new PImage[6];
    PImage[] specialSprites = new PImage[5];
    PImage[] specialSpritesRight = new PImage[5];
    PImage[] effectSprites = new PImage[4];
    PImage[] effectSpritesRight = new PImage[4];
    PImage damagedSprite;
    PImage damagedSpriteRight;
    boolean right;
    float x,y,h,w,initialX, initialY, projectileX, projectileY;
    boolean acted = false;
    void useSpecial(){}

    Character(String name, String role, int hps, int dmg, int def, int spd, String specialName, String special, PImage sprite, PImage icon, PImage sIcon, AttackType attackType, int extraW){
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
        this.extraW = extraW;
    }

    void initAnimationSprites(PImage damagedSprite, PImage damagedSpriteRight, PImage[] standSprites, PImage[] standSpritesRight,PImage[] attackSprites, PImage[] attackSpritesRight){
        this.damagedSprite = damagedSprite;
        this.damagedSpriteRight = damagedSpriteRight;
        this.standSprites = standSprites;
        this.standSpritesRight = standSpritesRight;
        this.attackSprites = attackSprites;
        this.attackSpritesRight = attackSpritesRight;
    }
    void initAnimationSprites(PApplet p5){
        standSprites[0] = p5.loadImage("SmallSprites/"+role+"/Standing/Standing0.png");
        standSprites[1] = p5.loadImage("SmallSprites/"+role+"/Standing/Standing1.png");
        standSpritesRight[0] = p5.loadImage("SmallSprites/"+role+"/Standing/StandingRight0.png");
        standSpritesRight[1] = p5.loadImage("SmallSprites/"+role+"/Standing/StandingRight1.png");
        for(int i=0; i<attackSprites.length;i++){
            attackSprites[i]=p5.loadImage("SmallSprites/"+role+"/Attack/Attack"+i+".png");
            attackSpritesRight[i]=p5.loadImage("SmallSprites/"+role+"/Attack/AttackRight"+i+".png");
        }
        for(int i=0;i<specialSprites.length;i++){
            specialSprites[i] = p5.loadImage("SmallSprites/"+role+"/Special/Special"+i+".png");
            specialSpritesRight[i] = p5.loadImage("SmallSprites/" + role + "/Special/SpecialRight" + i + ".png");
        }
        for(int i=0;i<effectSprites.length;i++){
            effectSprites[i] = p5.loadImage("SmallSprites/"+role+"/Effects/Effects"+i+".png");
            effectSpritesRight[i] = p5.loadImage("SmallSprites/"+role+"/Effects/EffectsRight"+i+".png");
        }
        damagedSprite = p5.loadImage("SmallSprites/"+role+"/Damaged.png");
        damagedSpriteRight = p5.loadImage("SmallSprites/"+role+"/DamagedRight.png");
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

    void attackAnimation(PApplet p5, Character target, int clock){
        if (right) {
                if (stage == AnimationStage.WAITING){
                    target.standStill(p5);
                    standStill(p5);
                    if(clock == 0){
                        stage = AnimationStage.PREPARING;
                    }
                } else if (stage == AnimationStage.PREPARING) {
                    target.standStill(p5);
                    p5.image(attackSpritesRight[0], x, y, w, h);
                    if (clock == 1) {
                        stage = AnimationStage.PERFORMING;
                    }
                } else if (stage == AnimationStage.PERFORMING) {
                    target.standStill(p5);
                    if(attackType == AttackType.JUMP) {
                        x = x - (initialX - target.x - (float) p5.width * 2 / 32) / 24;
                        y = y - (initialY - target.y) / 24;
                        if (clock < 3 || clock == 5) {
                            y = y - 5;
                        } else {
                            y = y + 5;
                        }
                    }
                    if ((p5.frameCount % 4) < 2) {
                        p5.image(attackSpritesRight[1], x, y, w, h);
                    } else {
                        p5.image(attackSpritesRight[2], x, y, w, h);
                    }
                    if (clock == 5) {
                        stage = AnimationStage.FINISHING;
                    }
                } else if (stage == AnimationStage.FINISHING) {
                    if (clock == 5) {
                        target.standStill(p5);
                        p5.image(attackSpritesRight[3], x, y, w, h);
                    } else {
                        if (clock == 6) {
                            if(attackType == AttackType.JUMP){
                                target.damagedAnimation(p5);
                            } else{
                                target.standStill(p5);
                                p5.image(attackSpritesRight[3], x, y, w, h);
                                stage = AnimationStage.RETRIEVING;
                            }
                        } else {
                            target.standStill(p5);
                        }
                        if ((p5.frameCount % 4) < 2) {
                            p5.image(attackSpritesRight[4], x, y, w, h);
                        } else {
                            p5.image(attackSpritesRight[5], x, y, w, h);
                        }
                    }
                    if (clock == 0) {
                        stage = AnimationStage.RETRIEVING;
                    }
                } else if (stage == AnimationStage.RETRIEVING) {
                    if(attackType == AttackType.RANGED){
                        if(clock == 3){
                            target.damagedAnimation(p5);
                        }else{
                            target.standStill(p5);
                        }
                    }else{
                        target.standStill(p5);
                    }
                    if(attackType == AttackType.JUMP) {
                        x = x + (initialX - target.x - (float) p5.width * 2 / 32) / 24;
                        y = y + (initialY - target.y) / 24;
                        if (clock < 2 || clock == 4) {
                            y = y - 5;
                        } else {
                            y = y + 5;
                        }
                        if ((p5.frameCount % 4) < 2) {
                            p5.image(attackSpritesRight[1], x, y, w, h);
                        } else {
                            p5.image(attackSpritesRight[2], x, y, w, h);
                        }
                    } else if(attackType == AttackType.RANGED){
                        p5.image(attackSpritesRight[4], x, y, w, h);
                        if(clock != 3 && clock != 4) {
                            projectileX = projectileX - (initialX - target.x) / 24;
                            projectileY = projectileY - (initialY - target.y) / 24;
                            p5.image(attackSpritesRight[6], projectileX, projectileY, w, h);
                        }
                    }
                    if (clock == 4) {
                        projectileX = initialX;
                        projectileY = initialY-h/5;
                        stage = AnimationStage.END;
                        acted = true;
                    }
                }

        } else {
            if (stage == AnimationStage.WAITING){
                target.standStill(p5);
                standStill(p5);
                if(clock == 0){
                    stage = AnimationStage.PREPARING;
                }
            } else if (stage == AnimationStage.PREPARING) {
                target.standStill(p5);
                p5.image(attackSprites[0], x, y, w, h);
                if (clock == 1) {
                    stage = AnimationStage.PERFORMING;
                }
            } else if (stage == AnimationStage.PERFORMING) {
                target.standStill(p5);
                if(attackType == AttackType.JUMP) {
                    x = x - (initialX - target.x + (float) p5.width * 2 / 32) / 24;
                    y = y - (initialY - target.y) / 24;
                    if (clock < 3 || clock == 5) {
                        y = y - 5;
                    } else {
                        y = y + 5;
                    }
                }
                if ((p5.frameCount % 4) < 2) {
                    p5.image(attackSprites[1], x, y, w, h);
                } else {
                    p5.image(attackSprites[2], x, y, w, h);
                }
                if (clock == 5) {
                    stage = AnimationStage.FINISHING;
                }
            } else if (stage == AnimationStage.FINISHING) {
                if (clock == 5) {
                    target.standStill(p5);
                    p5.image(attackSprites[3], x, y, w, h);
                } else {
                    if (clock == 6) {
                        if(attackType == AttackType.JUMP){
                            target.damagedAnimation(p5);
                        } else{
                            target.standStill(p5);
                            p5.image(attackSprites[3], x, y, w, h);
                            stage = AnimationStage.RETRIEVING;
                        }
                    } else {
                        target.standStill(p5);
                    }
                    if ((p5.frameCount % 4) < 2) {
                        p5.image(attackSprites[4], x, y, w, h);
                    } else {
                        p5.image(attackSprites[5], x, y, w, h);
                    }
                }
                if (clock == 0) {
                    stage = AnimationStage.RETRIEVING;
                }
            } else if (stage == AnimationStage.RETRIEVING) {
                if(attackType == AttackType.RANGED){
                    if(clock == 3){
                        target.damagedAnimation(p5);
                    }else{
                        target.standStill(p5);
                    }
                }else{
                    target.standStill(p5);
                }
                if(attackType == AttackType.JUMP) {
                    x = x + (initialX - target.x + (float) p5.width * 2 / 32) / 24;
                    y = y + (initialY - target.y) / 24;
                    if (clock < 2 || clock == 4) {
                        y = y - 5;
                    } else {
                        y = y + 5;
                    }
                    if ((p5.frameCount % 4) < 2) {
                        p5.image(attackSprites[1], x, y, w, h);
                    } else {
                        p5.image(attackSprites[2], x, y, w, h);
                    }
                } else if(attackType == AttackType.RANGED) {
                    p5.image(attackSprites[4], x, y, w, h);
                    if (clock != 3 && clock != 4) {
                        projectileX = projectileX - (initialX - target.x) / 24;
                        projectileY = projectileY - (initialY - target.y) / 24;
                        p5.image(attackSprites[6], projectileX, projectileY, w, h);
                    }
                }
                if (clock == 4) {
                    projectileX = initialX;
                    projectileY = initialY-h/5;
                    stage = AnimationStage.END;
                    acted = true;
                }
            }
        }
    }

    Character createCopy() {
        Character character = new Character(name, role, hps, dmg, def, spd, specialName, special, sprite, icon, specialIcon, attackType, extraW);
        try {
            character.initAnimationSprites(damagedSprite, damagedSpriteRight, standSprites, standSpritesRight, attackSprites, attackSpritesRight);
        }catch (Exception ignore){}
        return character;
    }
}
