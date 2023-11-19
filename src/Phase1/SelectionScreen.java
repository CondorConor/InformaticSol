package Phase1;

import processing.core.PApplet;
import processing.core.PFont;

public class SelectionScreen extends Screen{
    int playerChoosing;
    String topText;
    void initElements(PApplet p5) {
        screenType = GUI.ScreenType.SELECTIONSCREEN;
        playerChoosing = 1;
        cb1 = new CircularButton("c1", p5.width*16/32, p5.height*12/32, p5.width*8/32);
        cb2 = new CircularButton("c2", p5.width*8/32, p5.height*16/32, p5.width*5/32);
        cb3 = new CircularButton("c3", p5.width*24/32, p5.height*16/32, p5.width*5/32);
        cb4 = new CircularButton("c4", p5.width*16/32, p5.height*28/32, p5.width*3/32);
        cb5 = new CircularButton("c5", p5.width*12/32, p5.height*28/32, p5.width*3/32);
        cb6 = new CircularButton("c6", p5.width*20/32, p5.height*28/32, p5.width*3/32);
        b1 = new RectButton(p5, "<---", p5.width*8/32, p5.height*8/32, p5.width*5/32, p5.height*3/32, true);
        b2 = new RectButton(p5, "--->", p5.width*24/32, p5.height*8/32, p5.width*5/32, p5.height*3/32, true);
        b3 = new RectButton(p5, "Back", p5.width*4/32, p5.height*28/32, p5.width*5/32, p5.height*2/32, true);
        b4 = new RectButton(p5, "Random pick", p5.width*28/32, p5.height*28/32, p5.width*5/32, p5.height*2/32, true);
    }
    void display(PApplet p5, PFont fontTitle,int frameCount, boolean pvp){
        cb1.display(p5);
        cb2.display(p5);
        cb3.display(p5);
        cb4.display(p5);
        cb5.display(p5);
        cb6.display(p5);
        b1.display(p5);
        b2.display(p5);
        b3.display(p5);
        b4.display(p5);
        if(playerChoosing == 1){
            topText = "Choose your characters P1";
        }else if(pvp){
            topText = "Choose your characters P2";
        }else{
            topText = "Choose the AI characters";
        }
        p5.text(topText, p5.width*16/32, p5.height*2/32);

    }
}
