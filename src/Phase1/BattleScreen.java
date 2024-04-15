package Phase1;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

import java.util.Arrays;

import static java.lang.Float.valueOf;

public class BattleScreen extends Screen{

    boolean battling;
    boolean choosing;
    boolean targeting = false;
    boolean computerWins;
    int characterActing = 1;
    int characterChoosing = 0;
    int arrowPointingAt = 0;
    Character[] selectedCharacters;
    Character[] initiative = new Character[6];
    Character[] targets = new Character[6];
    Character.Action[] actions = new Character.Action[6];
    Item[] items = new Item[1];
    void initElements(PApplet p){
        screenType = GUI.ScreenType.BATTLESCREEN;
        battling = true;
        choosing = true;
        computerWins = false;
        b1 = new RectButton(p, "Attack", p.width*16/32, p.height*22/32, p.width*10/32, p.height*3/32, true);
        b2 = new RectButton(p, "Defend", p.width*16/32, p.height*25/32, p.width*10/32, p.height*3/32, true);
        b3 = new RectButton(p, "Special", p.width*16/32, p.height*28/32, p.width*10/32, p.height*3/32, false);
        b4 = new RectButton(p, "Play again", p.width*16/32, p.height*12/32, p.width*12/32, p.height*4/32, true);
        b5 = new RectButton(p, "Reselect characters", p.width*16/32, p.height*16/32, p.width*12/32, p.height*4/32, true);
        b6 = new RectButton(p, "Exit", p.width*16/32, p.height*20/32, p.width*12/32, p.height*4/32, true);
        selectedCharacters = new Character[6];
        Arrays.fill(selectedCharacters, null);
        items[0] = new Item(p.loadImage("SmallSprites/Items/Arrow.png"),selectedCharacters[0]);
    }
    void initAnimationElements(PApplet p5){
        int a;
        selectedCharacters[0].x =p5.width*9/32;
        selectedCharacters[1].x =p5.width*7/32;
        selectedCharacters[2].x =p5.width*8/32;
        selectedCharacters[3].x =p5.width*23/32;
        selectedCharacters[4].x =p5.width*25/32;
        selectedCharacters[5].x =p5.width*24/32;

        for(int i=0;i<selectedCharacters.length;i++){
            selectedCharacters[i].w =p5.width*7/32;
            selectedCharacters[i].h =p5.height*7/32;
            if(i<3){
                a=4*i+7;
                selectedCharacters[i].right = false;
            }else{
                a=4*i-5;
                selectedCharacters[i].right = true;
            }
            selectedCharacters[i].y = p5.height*a/32;
            selectedCharacters[i].initialX = selectedCharacters[i].x;
            selectedCharacters[i].initialY = selectedCharacters[i].y;
            selectedCharacters[i].projectileX = selectedCharacters[i].x;
            selectedCharacters[i].projectileY = selectedCharacters[i].y-selectedCharacters[i].h/4;
        }
    }

    void orderInitiative(){
        Character[] tempC = new Character[6];
        Character[] tempT = new Character[6];
        Character.Action[] tempA = new Character.Action[6];
        for(int i = 0; i<tempC.length;i++){
            int maxSpd = 0;
            int maxIndex = 0;
            for(int j = 0; j<actions.length;j++){
                try {
                    if (initiative[j].spd > maxSpd) {
                        maxSpd = initiative[j].spd;
                        maxIndex = j;
                    }
                }catch(NullPointerException ignore){}
            }
            tempC[i] = initiative[maxIndex];
            tempT[i] = targets[maxIndex];
            tempA[i] = actions[maxIndex];
            initiative[maxIndex] = null;
            targets[maxIndex] = null;
            actions[maxIndex] = null;
        }
        initiative = tempC;
        targets = tempT;
        actions = tempA;
    }
    void toggleChoosing(){
        choosing = !choosing;
    }
    void toggleTargeting(){
        targeting = !targeting;
    }
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp, int clock){
        if(battling) {
            if (targeting) {
                p5.pushStyle();
                p5.fill(255);
                p5.textFont(fontTitle, 30);
                p5.text("Player " + (characterChoosing < 3 ? "1" : "2") + " choosing", p5.width * 16 / 32, p5.height / 32);
                p5.popStyle();

                p5.pushStyle();
                p5.imageMode(PConstants.CENTER);
                try {
                    for (Character selectedCharacter : selectedCharacters) {
                        selectedCharacter.standStill(p5);
                    }
                } catch (Exception ignore) {
                }
                p5.popStyle();

                items[0].upDownAnimation(p5,clock);
                items[0].display(p5);


            } else if(choosing) {
                p5.pushStyle();
                p5.fill(255);
                p5.textFont(fontTitle, 30);
                p5.text("Player " + (characterChoosing < 3 ? "1" : "2") + " choosing", p5.width * 16 / 32, p5.height / 32);
                p5.popStyle();

                p5.pushStyle();
                p5.rect(p5.width * 16 / 32, p5.height * 19 / 32, p5.width * 10 / 32, p5.height * 3 / 32);
                p5.fill(255);
                p5.text("Character " + characterChoosing + ": ", p5.width * 16 / 32, p5.height * 19 / 32);
                p5.popStyle();

                b1.display(p5);
                b2.display(p5);
                b3.display(p5);

                //characters
                p5.pushStyle();
                p5.imageMode(PConstants.CENTER);
                try {
                    for (Character selectedCharacter : selectedCharacters) {
                        selectedCharacter.standAnimation(p5, clock);
                    }
                } catch (Exception ignore) {
                }
                p5.popStyle();
                if (initiative[characterChoosing] == null) {
                    initiative[characterChoosing] = selectedCharacters[characterChoosing];
                }
                if (actions[characterChoosing] != null) {
                    characterChoosing++;
                }
                if (characterChoosing == actions.length) {
                    orderInitiative();
                    toggleChoosing();
                }

            }else {
                if (actions[characterActing] == Character.Action.ATTACK) {
                    for (int i = 0; i < selectedCharacters.length; i++) {
                        if (!selectedCharacters[i].equals(initiative[characterActing]) && !selectedCharacters[i].equals(initiative[characterActing])) {
                            selectedCharacters[i].standStill(p5);
                        }
                    }
                    if (!initiative[characterActing].acted) {
                        initiative[characterActing].attackAnimation(p5, targets[characterActing], clock);
                    } else {
                        initiative[characterActing].standStill(p5);
                        targets[characterActing].standStill(p5);
                        if(characterActing<selectedCharacters.length){
                            characterActing++;
                        }else{
                            battling = false;
                        }
                    }
                }
            }
            } else{
            p5.pushStyle();
            p5.fill(255);
            p5.textFont(fontTitle, 40);
            if(pvp) {
                p5.text("PLAYER X WINS!", p5.width * 16 / 32, p5.height * 8 / 32);
            }else {
                if (computerWins) {
                    p5.text("YOU LOSE", p5.width * 16 / 32, p5.height * 8 / 32);
                } else {
                    p5.text("YOU WIN!", p5.width * 16 / 32, p5.height * 8 / 32);
                }
            }
            p5.popStyle();
            b4.display(p5);
            b5.display(p5);
            b6.display(p5);
        }


        //rectangles
        p5.pushStyle();
        p5.fill(0, 210, 0);
        p5.rectMode(PConstants.CORNER);
        try {
            p5.rect(p5.width / 32, p5.height * 23 / 32, p5.width * 8 * selectedCharacters[0].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width / 32, p5.height * 25 / 32, p5.width * 8 * selectedCharacters[1].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width / 32, p5.height * 27 / 32, p5.width * 8 * selectedCharacters[2].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width * 23 / 32, p5.height * 23 / 32, p5.width * 8 * selectedCharacters[3].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width * 23 / 32, p5.height * 25 / 32, p5.width * 8 * selectedCharacters[4].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width * 23 / 32, p5.height * 27 / 32, p5.width * 8 * selectedCharacters[5].hpPercentage / 32, p5.height * 2 / 32);
        }catch (Exception ignore){}
        p5.noFill();
        p5.rectMode(PConstants.CENTER);
        p5.rect(p5.width*5/32, p5.height*24/32, p5.width*8/32, p5.height*2/32);
        p5.rect(p5.width*5/32, p5.height*26/32, p5.width*8/32, p5.height*2/32);
        p5.rect(p5.width*5/32, p5.height*28/32, p5.width*8/32, p5.height*2/32);
        p5.rect(p5.width*27/32, p5.height*24/32, p5.width*8/32, p5.height*2/32);
        p5.rect(p5.width*27/32, p5.height*26/32, p5.width*8/32, p5.height*2/32);
        p5.rect(p5.width*27/32, p5.height*28/32, p5.width*8/32, p5.height*2/32);
        p5.popStyle();

        //text
        p5.pushStyle();
        p5.fill(255);
        try {
            p5.text(selectedCharacters[0].name, p5.width * 5 / 32, p5.height * 24 / 32);
            p5.text(selectedCharacters[1].name, p5.width * 5 / 32, p5.height * 26 / 32);
            p5.text(selectedCharacters[2].name, p5.width * 5 / 32, p5.height * 28 / 32);
            p5.text(selectedCharacters[3].name, p5.width * 27 / 32, p5.height * 24 / 32);
            p5.text(selectedCharacters[4].name, p5.width * 27 / 32, p5.height * 26 / 32);
            p5.text(selectedCharacters[5].name, p5.width * 27 / 32, p5.height * 28 / 32);
        }catch(Exception ignore){}
        p5.popStyle();

    }
}
