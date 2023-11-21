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
        rectMode(CENTER);
        textAlign(3, 3);
        stroke(255);
        strokeWeight(2);
        fill(50);
        screens = new Screen[4];
        screens[0] = new TitleScreen();
        screens[1] = new SelectionScreen();
        screens[2] = new StatsScreen();
        screens[3] = new BattleScreen();
        for (Screen screen : screens) {screen.initElements(this);}
        titleFont = createFont("BirchLeaf.ttf", 60);
        textFont = createFont("EpsonPixeled.ttf", 20);
        currentScreen = ScreenType.TITLESCREEN;
        textFont(textFont, 15);
        }
    public void draw() {
        background(50);
        for (Screen screen : screens) {
            if (screen.screenType == currentScreen) {
                screen.display(this, titleFont, frameCount, pvp);
            }
        }
    }
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
        } else if(key=='3'){
            currentScreen = screens[2].screenType;
        } else if(key=='4') {
            currentScreen = screens[3].screenType;
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