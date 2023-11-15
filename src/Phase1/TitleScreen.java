package Phase1;


import processing.core.PApplet;
import processing.core.PFont;

public class TitleScreen extends Screen{

    public boolean pitjat = false;
    void initElements(PApplet p5){
        screenType = GUI.ScreenType.TITLESCREEN;
        keyPressed = false;

        //pve button
        b1 = new RectButton(p5,"1 player", p5.width/2,p5.height*5/8,p5.width*2/5,p5.height/18,true);

        //pvp button
        b2 = new RectButton(p5,"2 players", p5.width/2,p5.height*6/8,p5.width*2/5,p5.height/18,true);
    }
    void display(PApplet p5, PFont fontTitle, PFont fontText, int iterator, boolean pvp){

        p5.pushStyle();
        p5.textFont(fontTitle, 60);
        p5.text("MYTHS OF ARALUNA",p5.width/2 , p5.height/3, p5.width*2/3, p5.height/4);

        if(this.keyPressed) {
            b1.display(p5, fontText);
            b2.display(p5, fontText);
        }else{
            if(iterator%48<=24){
                p5.textFont(fontText, 15);
                p5.text("press enter to start", p5.width/2, p5.height*3/5);
            }
        }
        p5.popStyle();
    }
}