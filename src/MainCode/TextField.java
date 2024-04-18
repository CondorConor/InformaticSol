package MainCode;

import processing.core.PApplet;

public class TextField {
    float x,y,w,h;
    String title;
    String[] text;
    RectButton cancel;
    RectButton accept;
    boolean visible = false;
    TextField(int x, int y, int w, int h, int maxTextLength, String title, PApplet p5){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.title = title;
        text = new String[maxTextLength];
        cancel = new RectButton(p5, "CANCEL", x-w/4, y+h/3, w/3, h/10,true);
        accept = new RectButton(p5, "ACCEPT", x+w/4, y+h/3, w/3, h/10,true);
    }

    void display(PApplet p5, int clock){
        p5.pushStyle();
        p5.rect(x,y,w,h);
        p5.fill(0,255,0);
        p5.text(title, x, y-h*4/10);
        for(int i = 0; i<text.length;i++){
            try {
                p5.text(text[i], x - (w / 2) + w*(i+1)/(text.length+1), y-(h / 10));
            }catch(NullPointerException ignore){}
            if(firstNull()!=i||clock%6>2) {
                p5.text("_", x - (w / 2) + w * (i + 1) / (text.length + 1), y);
            }
        }
        p5.popStyle();
        p5.pushStyle();
        p5.textSize(20);
        accept.display(p5);
        cancel.display(p5);
        p5.popStyle();
    }
    void addText(char letter){
        if(firstNull()>-1){
            text[firstNull()] = String.valueOf(letter);
        }
    }
    void addText(String letter){
        if(firstNull()>-1){
            text[firstNull()] = String.valueOf(letter);
        }
    }
    void removeText(){
        if(firstNull()-1>-1){
            text[firstNull()-1] = null;
        }else if(firstNull()==-1){
            text[text.length-1] = null;
        }
    }
    String returnText(){
        String t = "";
        for (String s:text) {
            if(s!=null){
                t= t.concat(s);
            }
        }
        return t;
    }
    int firstNull(){
        for(int i=0; i<text.length;i++){
            if(text[i]==null){
                return i;
            }
        }
        return -1;
    }



}
