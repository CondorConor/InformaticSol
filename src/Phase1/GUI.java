package Phase1;

import processing.core.PApplet;
import processing.core.PFont;

public class GUI extends PApplet {
    public enum ScreenType {TITLESCREEN, SELECTIONSCREEN, STATSCREEN, BATTLESCREEN}
    PFont titleFont;
    PFont textFont;
    ScreenType currentScreen;
    Screen[] screens;
    boolean pvp;
    public static void main(String[] args) {
        PApplet.main("Phase1.GUI", args);
    }

    public void settings() {
        fullScreen();
        smooth(10);
    }

    public void setup() {
        frameRate(24);
        rectMode(3);
        textAlign(3, 3);
        screens = new Screen[2];
        screens[0] = new TitleScreen();
        screens[0].initElements(this);
        screens[1] = new SelectionScreen();
        screens[1].initElements(this);
        titleFont = createFont("BirchLeaf.ttf", 60);
        textFont = createFont("EpsonPixeled.ttf", 20);
        currentScreen = ScreenType.TITLESCREEN;

        }


    public void draw() {

        background(50);
        for (Screen screen : screens) {
            if (screen.screenType == currentScreen) {
                screen.display(this, titleFont, textFont, frameCount, pvp);
            }
        }

    }

    //vbnmn

    public void keyPressed(){
        if(currentScreen==ScreenType.TITLESCREEN){
            screens[0].keyPressed = true;
        }
        if(key=='1'){
            currentScreen = screens[0].screenType;
            screens[0].keyPressed = false;
        }else if(key=='2'){
            currentScreen = screens[1].screenType;
            ((SelectionScreen)screens[1]).playerChoosing = 1;
        }



    }

    public void mousePressed(){
        if(currentScreen==ScreenType.TITLESCREEN && screens[0].keyPressed){
            if(screens[0].b1.ButtonPressed(this)){
                pvp = false;
                currentScreen = ScreenType.SELECTIONSCREEN;

            }else if(screens[0].b2.ButtonPressed(this)){
                pvp = true;
                currentScreen = ScreenType.SELECTIONSCREEN;

            }
        }
    }
}