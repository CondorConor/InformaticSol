package Phase1;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class ScoreScreen extends Screen{

    @Override
    void initElements(PApplet p5) {
        screenType = GUI.ScreenType.SCORESCREEN;
        scroller = new Scroller(p5, p5.width*29/32, p5.height*21/32, p5.width/64, p5.height*20/32);
        scroller.initElements(p5);

        /*for (int i=0; i<rows.length;i++) {
            rows[i] = new row4Table(p5.width*24/32, p5.height*2/32, p5.width*4/32, p5.height*10/32, i+1, null, false);
            if(i<rowsDisplayed){
                displayedRows[i] = rows[i].copy();
            }
        }
        titleRow = new row4Table(p5.width*24/32, p5.height*2/32, p5.width*4/32, p5.height*10/32, 0, rowSections, true);
        rows[2].setText(textProva);*/
    }


    @Override
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp, int clock) {
        //display scroller
        scroller.display(p5);
    }
}
