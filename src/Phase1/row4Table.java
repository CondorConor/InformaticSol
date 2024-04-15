package Phase1;

import processing.core.PApplet;

public class row4Table {

    float w, h, initialX, initialY, displaceY, displaceX;
    int displayPosition;
    String[] text;
    boolean bold;
    int[] rowsLengthPercent;

    row4Table(float w, float h, float initialX, float initialY, int displayPosition, String[] text, int[] rowsLengttPercentage, boolean bold){
        this.w = w;
        this.h = h;
        this.initialX = initialX;
        this.initialY = initialY;
        this.displayPosition = displayPosition;
        displaceY = displayPosition*h;
        this.text = text;
        this.rowsLengthPercent = rowsLengttPercentage;
        this.bold = bold;

    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
    public void setDisplaceY(){
        displaceY = displayPosition*h;
    }
    public row4Table copy(){
        return new row4Table(this.w,this.h, this.initialX, this.initialY, this.displayPosition, this.text, this.rowsLengthPercent, this.bold);
    }

    void display(PApplet p5){
        p5.pushStyle();
        if(bold){p5.strokeWeight(5);}
        displaceX = initialX;
        int i=0;
        float radius;
        for (int pcent: rowsLengthPercent) {
            radius = w*pcent/200;
            displaceX = displaceX+radius;
            p5.rect(displaceX, initialY+displaceY, 2*radius, h);
            p5.pushStyle();
            if(bold){p5.textSize(17);}
            try{
                p5.fill(0,210,0);
                p5.text(text[i], displaceX, initialY+displaceY);
            }catch(Exception ignore){}
            p5.popStyle();
            displaceX = displaceX+radius;
            i++;
        }
        p5.popStyle();
    }

}
