package Phase1;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.Arrays;

public class GameStatsScreen extends Screen{


    Character[] characters;
    @Override
    void initElements(PApplet p) {
        screenType = GUI.ScreenType.GAMESTATSSCREEN;
        characters = new Character[6];
        Arrays.fill(characters, null);
    }
    void initAnimationElements(PApplet p5){
        for (int i = 0; i<characters.length;i++) {
            if(characters[i] != null){
                break;
            }
            if(i==characters.length-1){
                for(int j=0; j<characters.length;j++) {
                    characters[j] = new Character("", "", 1, 0, 0, 0, "", "", null, null, null, null, 0);
                    characters[j].standSpritesRight[0] = p5.loadImage("SmallSprites/SilhouetteStandingRight.png");
                    characters[j].standSprites[0] = p5.loadImage("SmallSprites/SilhouetteStanding.png");
                }
            }
        }
        int a;
        float shiftedW = (float)(p5.width/2);
        float shiftedH = (float)(p5.height/2);
        try {
            characters[0].x = shiftedW * 9 / 32;
        }catch (Exception ignore){}
        try {
            characters[1].x =shiftedW*7/32;
        }catch (Exception ignore){}
        try {
            characters[2].x =shiftedW*8/32;
        }catch (Exception ignore){}
        try {
            characters[3].x =shiftedW*23/32;
        }catch (Exception ignore){}
        try {
            characters[4].x =shiftedW*25/32;
        }catch (Exception ignore){}
        try {
            characters[5].x =shiftedW*24/32;
        }catch (Exception ignore){}


        for(int i=0;i<characters.length;i++){
            try {
                characters[i].w = shiftedW * 7 / 32;
                characters[i].h = shiftedH * 7 / 32;
                if (i < 3) {
                    a = 4 * i + 7;
                    characters[i].right = false;
                } else {
                    a = 4 * i - 5;
                    characters[i].right = true;
                }
                characters[i].y = shiftedH * a / 32;
                characters[i].initialX = characters[i].x;
                characters[i].initialY = characters[i].y;
            }catch(Exception ignore){}
        }

    }
    @Override
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp, int clock) {
        p5.pushMatrix();
        p5.translate(p5.width/4,0);
        for (Character c:characters) {
            c.standStill(p5);
        }
        p5.popMatrix();
    }
}
