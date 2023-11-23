package Phase1;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class BattleScreen extends Screen{

    GUI.ScreenType screenType;
    boolean battling;
    boolean choosing;
    boolean computerWins;
    Character[] selectedCharacters;
    void initElements(PApplet p){
        screenType = GUI.ScreenType.BATTLESCREEN;
        battling = true;
        choosing = true;
        computerWins = false;
        b1 = new RectButton(p, "Attack", p.width*16/32, p.height*22/32, p.width*10/32, p.height*3/32, true);
        b2 = new RectButton(p, "Defend", p.width*16/32, p.height*25/32, p.width*10/32, p.height*3/32, true);
        b3 = new RectButton(p, "Special", p.width*16/32, p.height*28/32, p.width*10/32, p.height*3/32, true);
        b4 = new RectButton(p, "Play again", p.width*16/32, p.height*12/32, p.width*12/32, p.height*4/32, true);
        b5 = new RectButton(p, "Reselect characters", p.width*16/32, p.height*16/32, p.width*12/32, p.height*4/32, true);
        b6 = new RectButton(p, "Exit", p.width*16/32, p.height*20/32, p.width*12/32, p.height*4/32, true);
    };
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp){

        if(battling) {
            if(choosing) {
                p5.pushStyle();
                p5.fill(255);
                p5.textFont(fontTitle, 30);
                p5.text("Player x choosing", p5.width * 16 / 32, p5.height / 32);
                p5.popStyle();

                p5.pushStyle();
                p5.rect(p5.width * 16 / 32, p5.height * 19 / 32, p5.width * 10 / 32, p5.height * 3 / 32);
                p5.fill(255);
                p5.text("Character x:", p5.width * 16 / 32, p5.height * 19 / 32);
                p5.popStyle();

                b1.display(p5);
                b2.display(p5);
                b3.display(p5);
            }
        }else{
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
            p5.rect(p5.width / 32, p5.height * 23 / 32, p5.width * 6 * selectedCharacters[0].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width / 32, p5.height * 25 / 32, p5.width * 6 * selectedCharacters[1].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width / 32, p5.height * 27 / 32, p5.width * 6 * selectedCharacters[2].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width * 25 / 32, p5.height * 23 / 32, p5.width * 6 * selectedCharacters[3].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width * 25 / 32, p5.height * 25 / 32, p5.width * 6 * selectedCharacters[4].hpPercentage / 32, p5.height * 2 / 32);
            p5.rect(p5.width * 25 / 32, p5.height * 27 / 32, p5.width * 6 * selectedCharacters[5].hpPercentage / 32, p5.height * 2 / 32);
        }catch (Exception ignore){}
        p5.noFill();
        p5.rectMode(PConstants.CENTER);
        p5.rect(p5.width*4/32, p5.height*24/32, p5.width*6/32, p5.height*2/32);
        p5.rect(p5.width*4/32, p5.height*26/32, p5.width*6/32, p5.height*2/32);
        p5.rect(p5.width*4/32, p5.height*28/32, p5.width*6/32, p5.height*2/32);
        p5.rect(p5.width*28/32, p5.height*24/32, p5.width*6/32, p5.height*2/32);
        p5.rect(p5.width*28/32, p5.height*26/32, p5.width*6/32, p5.height*2/32);
        p5.rect(p5.width*28/32, p5.height*28/32, p5.width*6/32, p5.height*2/32);


        p5.rect(p5.width*8/32, p5.height*7/32, p5.width*3/32, p5.height*10/32);
        p5.rect(p5.width*4/32, p5.height*10/32, p5.width*3/32, p5.height*10/32);
        p5.rect(p5.width*7/32, p5.height*13/32, p5.width*3/32, p5.height*10/32);
        p5.rect(p5.width*24/32, p5.height*7/32, p5.width*3/32, p5.height*10/32);
        p5.rect(p5.width*28/32, p5.height*10/32, p5.width*3/32, p5.height*10/32);
        p5.rect(p5.width*25/32, p5.height*13/32, p5.width*3/32, p5.height*10/32);
        p5.popStyle();

        //text
        p5.pushStyle();
        p5.fill(255);
        try {
            p5.text(selectedCharacters[0].name, p5.width * 4 / 32, p5.height * 24 / 32);
            p5.text(selectedCharacters[1].name, p5.width * 4 / 32, p5.height * 26 / 32);
            p5.text(selectedCharacters[2].name, p5.width * 4 / 32, p5.height * 28 / 32);
            p5.text(selectedCharacters[3].name, p5.width * 28 / 32, p5.height * 24 / 32);
            p5.text(selectedCharacters[4].name, p5.width * 28 / 32, p5.height * 26 / 32);
            p5.text(selectedCharacters[5].name, p5.width * 28 / 32, p5.height * 28 / 32);
        }catch(Exception ignore){}
        p5.popStyle();




    }




}
