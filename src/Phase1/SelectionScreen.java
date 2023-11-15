package Phase1;

import processing.core.PApplet;
import processing.core.PFont;

public class SelectionScreen extends Screen{
    int playerChoosing;
    String topText;
    void initElements(PApplet p5) {
        screenType = GUI.ScreenType.SELECTIONSCREEN;
        playerChoosing = 1;
        cb1 = new CircularButton("c1", p5.width/2, p5.height*3/8, p5.width*2/8);
        cb2 = new CircularButton("c2", p5.width*2/8, p5.height*4/8, p5.width/6);
        cb3 = new CircularButton("c3", p5.width*6/8, p5.height*4/8, p5.width/6);
        b1 = new RectButton(p5, "<---", p5.width*2/8, p5.height*2/8, p5.width/6, p5.height/10, true);
        b2 = new RectButton(p5, "--->", p5.width*6/8, p5.height*2/8, p5.width/6, p5.height/10, true);
        b3 = new RectButton(p5, "Back", p5.width/8, p5.height*7/8, p5.width/8, p5.height/12, true);

    }
    void display(PApplet p5, PFont fontTitle, PFont fontText, int frameCount, boolean pvp){
        p5.pushStyle();
        p5.textFont(fontText);
        cb1.display(p5);
        cb2.display(p5);
        cb3.display(p5);
        b1.display(p5, fontText);
        b2.display(p5, fontText);
        if(playerChoosing == 1){
            topText = "Choose your characters P1";
        }else if(pvp){
            topText = "Choose your characters P2";
        }else{
            topText = "Choose the AI characters";
        }
        p5.text(topText, p5.width/2, p5.height/20);

        p5.popStyle();
    }
}
