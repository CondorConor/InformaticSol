package MainCode;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.Arrays;

public class SelectionScreen extends Screen{
    String topText;
    int position = 0;
    PImage img;
    UsableCharacters characters = new UsableCharacters();
    Character[] selectedCharacters = new Character[6];
    void initElements(PApplet p5) {
        screenType = GUI.ScreenType.SELECTIONSCREEN;
        Arrays.fill(selectedCharacters, null);
        characters.initCharacters(p5);
        cb1 = new CircularButton("c1", p5.width*16/32, p5.height*12/32, p5.width*8/32);
        cb2 = new CircularButton("c2", p5.width*8/32, p5.height*16/32, p5.width*5/32);
        cb3 = new CircularButton("c3", p5.width*24/32, p5.height*16/32, p5.width*5/32);
        cb4 = new CircularButton("c4", p5.width*12/32, p5.height*28/32, p5.width*3/32);
        cb5 = new CircularButton("c5", p5.width*16/32, p5.height*28/32, p5.width*3/32);
        cb6 = new CircularButton("c6", p5.width*20/32, p5.height*28/32, p5.width*3/32);
        cb7 = new CircularButton("c7", p5.width*14/32, p5.height*24/32, p5.width/32);
        cb8 = new CircularButton("c8", p5.width*16/32, p5.height*24/32, p5.width/32);
        cb9 = new CircularButton("c9", p5.width*18/32, p5.height*24/32, p5.width/32);
        b1 = new RectButton(p5, "<---", p5.width*8/32, p5.height*8/32, p5.width*5/32, p5.height*3/32, true);
        b2 = new RectButton(p5, "--->", p5.width*24/32, p5.height*8/32, p5.width*5/32, p5.height*3/32, true);
        b3 = new RectButton(p5, "Back", p5.width*4/32, p5.height*28/32, p5.width*5/32, p5.height*2/32, true);
        b4 = new RectButton(p5, "Random pick", p5.width*28/32, p5.height*28/32, p5.width*5/32, p5.height*2/32, true);
    }

    int PreviousPosition(int i){
        if(i-1<0){return characters.characters.length-1;}
        return i-1;
    }
    int NextPosition(int i){
        if(i+1>characters.characters.length-1){return 0;}
        return i+1;
    }
    void display(PApplet p5, PFont fontTitle,int frameCount, boolean pvp, int clock){
        p5.pushStyle();
        p5.fill(255);
        cb1.display(p5, characters.characters[position].icon);
        cb2.display(p5, characters.characters[PreviousPosition(position)].icon);
        cb3.display(p5, characters.characters[NextPosition(position)].icon);
        try{
            if(firstNullPosition()<3){
            img=selectedCharacters[0].icon;
            }else{
                img=selectedCharacters[3].icon;
            }
        }catch (Exception e){img=null;}
        cb4.display(p5, img);
        try{
            if(firstNullPosition()<3){
                img=selectedCharacters[1].icon;
            }else{
                img=selectedCharacters[4].icon;
            }
        }catch (Exception e){img=null;}
        cb5.display(p5, img);
        try{
            if(firstNullPosition()<3){
                img=selectedCharacters[3].icon;
            }else{
                img=selectedCharacters[5].icon;
            }
        }catch (Exception e){img=null;}
        cb6.display(p5, img);
        if(firstNullPosition()>2) {
            p5.text("Team player 1",p5.width*16/32, p5.height*22/32);
            img = selectedCharacters[0].icon;
            cb7.display(p5, img);
            img = selectedCharacters[1].icon;
            cb8.display(p5, img);
            img = selectedCharacters[2].icon;
            cb9.display(p5, img);
        }
        b1.display(p5);
        b2.display(p5);
        b3.display(p5);
        b4.display(p5);
        if(firstNullPosition()<3){
            topText = "Choose your characters P1";
        }else if(pvp){
            topText = "Choose your characters P2";
        }else{
            topText = "Choose the AI characters";
        }
        p5.text(topText, p5.width*16/32, p5.height*2/32);
        p5.popStyle();

    }
    int firstNullPosition(){
        for (int i=0; i<selectedCharacters.length;i++) {
            if(selectedCharacters[i]==null){
                return i;
            }
        }
        return selectedCharacters.length;
    }
}

