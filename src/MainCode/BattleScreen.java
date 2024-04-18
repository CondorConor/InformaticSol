package MainCode;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Float.valueOf;

public class BattleScreen extends Screen{
    boolean battling;
    boolean choosing;
    boolean targeting = false;
    boolean computerWins;
    boolean player1Wins;
    int characterActing = 0;
    int characterChoosing = 0;
    int rounds = 0;
    int arrowPointingAt = 0;
    Character[] selectedCharacters;
    Character[] initiative = new Character[6];
    Character[] targets = new Character[6];
    Character.Action[] actions = new Character.Action[6];
    List<Item> items = new ArrayList<>();
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
        b7 = new RectButton(p, "Save game", p.width*16/32, p.height*24/32, p.width*12/32, p.height*4/32, true);
        selectedCharacters = new Character[6];
        Arrays.fill(selectedCharacters, null);
    }
    String[] gameValues(PApplet p5, String player1, String player2){
        String[] values = new String[17];
        values[0] = "'pvp'";
        values[1] = "'"+player1+"'";
        int j = 0;
        for(int i = 2; i<selectedCharacters.length+2;i=i+2){
            if(selectedCharacters[j].name.split(" ")[0].contains(",")){
                values[i] = "'"+selectedCharacters[j].name.split(",")[0]+"'";
            }else {
                values[i] = "'"+selectedCharacters[j].name.split(" ")[0]+"'";
            }
            values[i+1] = Integer.toString(selectedCharacters[j].currentHps);
            j++;
        }
        values[8] = "'"+player2+"'";
        for(int i = 9; i<selectedCharacters.length+9;i=i+2){
            if(selectedCharacters[j].name.split(" ")[0].contains(",")){
                values[i] = "'"+selectedCharacters[j].name.split(",")[0]+"'";
            }else {
                values[i] = "'"+selectedCharacters[j].name.split(" ")[0]+"'";
            }
            values[i+1] = Integer.toString(selectedCharacters[j].currentHps);
            j++;
        }
        values[15] = Integer.toString(rounds);
        values[16] = "'"+(PApplet.year())+ "-" +  (PApplet.month()<10?"0":"")+PApplet.month() + "-" +(PApplet.day()<10?"0":"")+PApplet.day() +"'";
        return values;
    }
    void restart(PApplet p5){
        battling = true;
        choosing = true;
        for(int i = 0; i<selectedCharacters.length;i++){
            actions[i] = null;
            targets[i] = null;
            initiative[i] = null;
            selectedCharacters[i].restore(p5);
        }
        while (items.size()>2){
            items.remove(items.size()-1);
        }
        characterActing = 0;
        characterChoosing = 0;
        rounds = 0;
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
        items.add(new Item(p5.loadImage("SmallSprites/Items/GreenArrow.png"),selectedCharacters[0],0,Item.Position.TOP));
        items.add(new Item(p5.loadImage("SmallSprites/Items/Arrow.png"),selectedCharacters[0],0,Item.Position.TOP));
    }
    void addDefendSprite(PApplet p5, int charPosition){
        if(selectedCharacters[charPosition].right){
            items.add(new Item(p5.loadImage("SmallSprites/Items/ShieldRight.png"),selectedCharacters[charPosition],charPosition,Item.Position.FRONT));
        } else{
            items.add(new Item(p5.loadImage("SmallSprites/Items/Shield.png"),selectedCharacters[charPosition],charPosition,Item.Position.FRONT));
        }
    }
    void orderInitiative(){
        Character[] tempC = new Character[6];
        Character[] tempT = new Character[6];
        Character.Action[] tempA = new Character.Action[6];
        for(int i = 0; i<tempC.length;i++){
            int maxSpd = -1;
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
            try {
                if (tempC[i].died) {
                    tempC[i].acted = true;
                }
            }catch(Exception ignore){}
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
                        if(!selectedCharacter.died) {
                            selectedCharacter.standAnimation(p5, clock);
                        }
                    }
                } catch (Exception ignore) {
                }
                p5.popStyle();

                items.get(1).upDownAnimation(p5,clock);
                items.get(1).display(p5);

            } else if(choosing) {

                //characters
                p5.pushStyle();
                p5.imageMode(PConstants.CENTER);
                try {
                    for (Character selectedCharacter : selectedCharacters) {
                        if(!selectedCharacter.died) {
                            selectedCharacter.standAnimation(p5, clock);
                        }
                    }
                } catch (Exception ignore) {
                }
                p5.popStyle();
                if (initiative[characterChoosing] == null) {
                    initiative[characterChoosing] = selectedCharacters[characterChoosing];
                }
                if (actions[characterChoosing] != null) {
                    characterChoosing++;
                    while(characterChoosing<actions.length&&selectedCharacters[characterChoosing].died){
                        characterChoosing++;
                    }
                }
                try{
                    items.get(0).setCharacter(selectedCharacters[characterChoosing],characterChoosing);
                    items.get(0).display(p5);
                }catch(ArrayIndexOutOfBoundsException ignore){}
                if (characterChoosing == actions.length) {
                    orderInitiative();
                    toggleChoosing();
                    rounds++;
                }

                p5.pushStyle();
                p5.fill(255);
                p5.textFont(fontTitle, 30);
                p5.text("Player " + (characterChoosing < 3 ? "1" : "2") + " choosing", p5.width * 16 / 32, p5.height / 32);
                p5.popStyle();

                p5.pushStyle();
                p5.rect(p5.width * 16 / 32, p5.height * 19 / 32, p5.width * 10 / 32, p5.height * 3 / 32);
                p5.fill(255);
                p5.text("Character " + (characterChoosing+1) + ": ", p5.width * 16 / 32, p5.height * 19 / 32);
                p5.popStyle();

                b1.display(p5);
                b2.display(p5);
                b3.display(p5);

            }else {
                for (Character selectedCharacter : selectedCharacters) {
                    if (!selectedCharacter.equals(initiative[characterActing]) && !selectedCharacter.equals(targets[characterActing])) {
                            selectedCharacter.standStill(p5);
                    }
                }
                if (actions[characterActing] == Character.Action.ATTACK) {
                    if ((!initiative[characterActing].acted&&!targets[characterActing].died)||(initiative[characterActing].stage!= Character.AnimationStage.WAITING&&initiative[characterActing].stage!= Character.AnimationStage.END)) {
                        initiative[characterActing].attackAnimation(p5, targets[characterActing], clock);
                    } else {
                        initiative[characterActing].standStill(p5);
                        targets[characterActing].standStill(p5);
                        if (characterActing < selectedCharacters.length - 1) {
                            characterActing++;
                        } else {
                            characterChoosing = 0;
                            characterActing = 0;
                            if(selectedCharacters[0].died) {
                                characterChoosing++;
                                characterActing++;
                                if (selectedCharacters[1].died) {
                                    characterChoosing++;
                                    characterActing++;
                                    if (selectedCharacters[2].died) {
                                        battling = false;
                                    }
                                }
                            }
                            for (int i = 0; i < selectedCharacters.length; i++) {
                                selectedCharacters[i].acted = false;
                                selectedCharacters[i].blocking = false;
                                selectedCharacters[i].stage = Character.AnimationStage.WAITING;
                                actions[i] = null;
                                initiative[i] = null;
                                targets[i] = null;
                            }
                            while(items.size()>2){
                                items.remove(items.size()-1);
                            }
                            choosing = true;
                        }
                    }
                }else{
                    try {
                        initiative[characterActing].standStill(p5);
                    }catch(NullPointerException ignore){}
                    try{
                        targets[characterActing].standStill(p5);
                    }catch(NullPointerException ignore){}

                    if (characterActing < selectedCharacters.length - 1) {
                        characterActing++;
                    }
                    else{
                        characterChoosing = 0;
                        characterActing = 0;
                        if(selectedCharacters[0].died) {
                            characterChoosing++;
                            characterActing++;
                            if (selectedCharacters[1].died) {
                                characterChoosing++;
                                characterActing++;
                                if (selectedCharacters[2].died) {
                                    battling = false;
                                    player1Wins = false;
                                }
                            }
                        }
                        for (int i = 0; i < selectedCharacters.length; i++) {
                            selectedCharacters[i].acted = false;
                            selectedCharacters[i].blocking = false;
                            selectedCharacters[i].stage = Character.AnimationStage.WAITING;
                            actions[i] = null;
                            initiative[i] = null;
                            targets[i] = null;
                        }
                        while(items.size()>2){
                            items.remove(items.size()-1);
                        }
                        choosing = true;
                    }
                }
                for(int i=2; i<items.size();i++){
                    items.get(i).display(p5);
                }
            }
            if(selectedCharacters[3].died&&selectedCharacters[4].died&&selectedCharacters[5].died){
                battling = false;
                player1Wins = true;
            }
            }else{
            for (Character selectedCharacter : selectedCharacters) {
                try {
                    selectedCharacter.standStill(p5);
                }catch(NullPointerException ignore){}
            }
            p5.pushStyle();
            p5.fill(255);
            p5.textFont(fontTitle, 40);
            if(pvp) {
                p5.text("PLAYER "+(player1Wins?1:2)+" WINS!", p5.width * 16 / 32, p5.height * 8 / 32);
            }else{
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
            b7.display(p5);
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
