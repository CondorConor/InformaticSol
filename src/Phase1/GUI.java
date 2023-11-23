package Phase1;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import java.util.Arrays;

public class GUI extends PApplet {
    public enum ScreenType {TITLESCREEN, SELECTIONSCREEN, STATSCREEN, BATTLESCREEN}
    PFont titleFont;
    PFont textFont;
    ScreenType currentScreen;
    Screen[] screens;
    boolean pvp;
    PImage image = new PImage();
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
        imageMode(CENTER);
        textAlign(3, 3);
        stroke(255);
        strokeWeight(2);
        fill(50);
        screens = new Screen[4];
        screens[0] = new TitleScreen();
        screens[1] = new SelectionScreen();
        screens[2] = new StatsScreen();
        screens[3] = new BattleScreen();
        for (Screen screen : screens) {
            screen.initElements(this);
        }
        ((StatsScreen) screens[2]).setCharacter(((SelectionScreen) screens[1]).characters.characters[0]);
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

    public void keyPressed() {
        if (currentScreen == ScreenType.TITLESCREEN) {
            ((TitleScreen)screens[0]).keyPressed = true;
        }
        if (key == '1') {
            currentScreen = screens[0].screenType;
            ((TitleScreen)screens[0]).keyPressed = false;
        } else if (key == '2') {
            currentScreen = screens[1].screenType;
            Arrays.fill(((SelectionScreen) screens[1]).selectedCharacters, null);
        } else if (key == '3') {
            ((BattleScreen) screens[3]).selectedCharacters = ((SelectionScreen) screens[1]).selectedCharacters;
            currentScreen = screens[2].screenType;
        } else if (key == '4') {
            currentScreen = screens[3].screenType;
            ((BattleScreen) screens[3]).battling = true;
            ((BattleScreen) screens[3]).choosing = true;
            ((BattleScreen) screens[3]).computerWins = false;
        }
    }

    public void mousePressed() {
        if (currentScreen == ScreenType.TITLESCREEN && ((TitleScreen)screens[0]).keyPressed) {
            if (screens[0].b1.MouseOnButton(this)) {
                pvp = false;
                currentScreen = ScreenType.SELECTIONSCREEN;

            } else if (screens[0].b2.MouseOnButton(this)) {
                pvp = true;
                currentScreen = ScreenType.SELECTIONSCREEN;
            }
        }


        else if (currentScreen == ScreenType.SELECTIONSCREEN) {
            if (screens[1].cb1.MouseOnButton(this)) {
                currentScreen = screens[2].screenType;
                ((StatsScreen)screens[2]).character=((SelectionScreen) screens[1]).characters.characters[((SelectionScreen) screens[1]).position];

            } else if (screens[1].cb2.MouseOnButton(this) || screens[1].b1.MouseOnButton(this)) {
                ((SelectionScreen) screens[1]).position = ((SelectionScreen) screens[1]).PreviousPosition(((SelectionScreen) screens[1]).position);

            } else if (screens[1].cb3.MouseOnButton(this) || screens[1].b2.MouseOnButton(this)) {
                ((SelectionScreen) screens[1]).position = ((SelectionScreen) screens[1]).NextPosition(((SelectionScreen) screens[1]).position);

            } else if (screens[1].b3.MouseOnButton(this)) {
                if (((SelectionScreen) screens[1]).firstNullPosition() == 0) {
                    currentScreen = ScreenType.TITLESCREEN;
                    ((TitleScreen)screens[0]).keyPressed = false;
                } else {
                    ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition() - 1] = null;
                }
            } else if (screens[1].b4.MouseOnButton(this)) {
                ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition()] = ((SelectionScreen) screens[1]).characters.characters[frameCount % 4];
                if (((SelectionScreen) screens[1]).firstNullPosition() == ((SelectionScreen) screens[1]).selectedCharacters.length) {
                    ((BattleScreen) screens[3]).selectedCharacters = ((SelectionScreen) screens[1]).selectedCharacters;
                    currentScreen = screens[3].screenType;
                }
            }
        }
        else if (currentScreen == ScreenType.STATSCREEN) {
            if(screens[2].b1.MouseOnButton(this)) {
                ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition()] = ((StatsScreen) screens[2]).character;
                if (((SelectionScreen) screens[1]).firstNullPosition() == ((SelectionScreen) screens[1]).selectedCharacters.length) {
                    ((BattleScreen) screens[3]).selectedCharacters = ((SelectionScreen) screens[1]).selectedCharacters;
                    currentScreen = screens[3].screenType;
                } else {
                    currentScreen = screens[1].screenType;
                }
            }else if(screens[2].b2.MouseOnButton(this)) {
                currentScreen = screens[1].screenType;
            }
        }
    }
}