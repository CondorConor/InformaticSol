package Phase1;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class ScoreScreen extends Screen{

    @Override
    void initElements(PApplet p5) {
        screenType = GUI.ScreenType.SCORESCREEN;
        scroller = new Scroller(p5, p5.width*29/32, p5.height*20/32, p5.width/64, p5.height*22/32);
        b1 = new RectButton(p5, "<-- TITLE",p5.width*3/32, p5.height*3/32,p5.width*3/32,p5.height*2/32, true);
        b2 = new RectButton(p5, null,p5.width*29/32, p5.height*3/32,p5.width*4/32,p5.height*2/32, true);
    }
    
    @Override
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp, int clock) {
        //display scroller
        scroller.display(p5);
        b1.display(p5);

        if(scroller.table == Scroller.Tables.games){
            b2.text = "PLAYERS -->";
        }else{
            b2.text = "GAMES -->";
        }
        b2.display(p5);

        p5.pushStyle();
        p5.textSize(15);
        p5.fill(250);
        p5.text("SCORES", p5.width/2, p5.height*5/32);
        p5.strokeWeight(3);
        p5.line((p5.width/2)-40, (float)(p5.height*5.5/32),(p5.width/2)+40, (float)(p5.height*5.5/32));
        p5.text("GAMES", p5.width*2/32, scroller.tableInitialY+(p5.height*2/32));
        p5.fill(0,210,0);
        p5.textSize(12);
        p5.text("0", p5.width*2/32, scroller.tableInitialY+(p5.height*3/32));
        p5.textSize(15);
        p5.fill(250);
        p5.text("LAST GAME\nPLAYED", p5.width*2/32, scroller.tableInitialY+(p5.height*7/32));
        p5.fill(0,210,0);
        p5.textSize(12);
        p5.text("CONOR vs SHIMFU", p5.width*2/32, scroller.tableInitialY+(p5.height*9/32));
        p5.textSize(15);
        p5.fill(250);
        p5.text("TOP SCORERS", p5.width*2/32, scroller.tableInitialY+(p5.height*12/32));
        p5.fill(0,210,0);
        p5.textSize(12);
        p5.text("1. CONOR\n2. SHIMFU\n3. MARK", p5.width*2/32, scroller.tableInitialY+(p5.height*14/32));
        p5.popStyle();

        p5.pushStyle();
        p5.textFont(fontTitle, 50);
        p5.fill(250);
        p5.text("MYTHS OF ARALUNA", p5.width/2, p5.height*2/32);
        p5.popStyle();
    }
}
