package Phase1;


import processing.core.PApplet;
import processing.core.PFont;

public class TitleScreen extends Screen{

    public boolean pitjat = false;
    boolean keyPressed;
    void initElements(PApplet p5){
        screenType = GUI.ScreenType.TITLESCREEN;
        keyPressed = false;

        //pve button
        b1 = new RectButton(p5,"1 player", p5.width*16/32,p5.height*20/32,p5.width*14/32,p5.height*2/32,true);

        //pvp button
        b2 = new RectButton(p5,"2 players", p5.width*16/32,p5.height*24/32,p5.width*14/32,p5.height*2/32,true);
    }
    void display(PApplet p5, PFont fontTitle, int iterator, boolean pvp, int clock){

        p5.pushStyle();
        p5.fill(255);
        p5.textFont(fontTitle, 60);
        p5.text("MYTHS OF ARALUNA",p5.width*16/32 , p5.height*11/32, p5.width*22/32, p5.height+8/32);
        p5.popStyle();
        if(this.keyPressed) {
            b1.display(p5);
            b2.display(p5);
        }else{
            if(iterator%48<=24){
                p5.pushStyle();
                p5.fill(255);
                p5.text("press enter to start", p5.width*16/32, p5.height*19/32);
                p5.popStyle();
            }
        }
    }
}