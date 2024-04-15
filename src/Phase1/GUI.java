package Phase1;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

public class GUI extends PApplet {
    public enum ScreenType {TITLESCREEN, SELECTIONSCREEN, STATSCREEN, BATTLESCREEN, SCORESCREEN, GAMESTATSSCREEN}
    PFont titleFont;
    PFont textFont;
    ScreenType currentScreen;
    Screen[] screens;
    boolean pvp;
    PImage image;
    int hold, delay, clock;
    float mouseOffSetX;
    float mouseOffSetY;
    static DataBase db = new DataBase("admin","12345","mythsofaraluna");
    static DataBase infoSchema = new DataBase("admin","12345","information_schema");
    public static void main(String[] args) {
        PApplet.main("Phase1.GUI", args);
    }
    public void settings() {
        fullScreen();
        smooth(10);
    }
    public void setup() {
        db.connect();
        infoSchema.connect();
        System.out.println(db.connected);
        hold = 0;
        delay = 6;
        clock = 0;
        frameRate(24);
        rectMode(CENTER);
        imageMode(CENTER);
        textAlign(3, 3);
        stroke(255);
        strokeWeight(2);
        fill(50);
        screens = new Screen[6];
        screens[0] = new TitleScreen();
        screens[1] = new SelectionScreen();
        screens[2] = new StatsScreen();
        screens[3] = new BattleScreen();
        screens[4] = new ScoreScreen();
        screens[5] = new GameStatsScreen();

        for (Screen screen : screens) {
            screen.initElements(this);
        }
        screens[4].scroller.setupPlayerTable();
        screens[4].scroller.setupGamesTable();

        ((StatsScreen) screens[2]).setCharacter(((SelectionScreen) screens[1]).characters.characters[0]);
        titleFont = createFont("BirchLeaf.ttf", 60);
        textFont = createFont("VCR_OSD.ttf", 20);
        currentScreen = ScreenType.TITLESCREEN;
        textFont(textFont);
    }
    public void draw() {
        hold = (hold+1)%delay;
        if(hold==0){
            clock = (clock+1)%7;
        }
        background(50);
        for (Screen screen : screens) {
            if (screen.screenType == currentScreen) {
                screen.display(this, titleFont, frameCount, pvp, clock);
            }
        }

    }

    public void keyPressed() {
        if (currentScreen == ScreenType.TITLESCREEN) {
            ((TitleScreen) screens[0]).keyPressed = true;
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
        } else if (key == '5') {
            currentScreen = screens[4].screenType;
            screens[4].scroller.table = Scroller.Tables.players;
            screens[4].scroller.initTable();

        } else if (key == '6') {
            screens[5].initElements(this);
            ((GameStatsScreen)screens[5]).initAnimationElements(this);
            currentScreen = screens[5].screenType;
        } else if (key == '7') {
            if(currentScreen == ScreenType.BATTLESCREEN) {
                if (((BattleScreen) screens[3]).initiative[((BattleScreen) screens[3]).characterActing].stage == Character.AnimationStage.END) {
                    ((BattleScreen) screens[3]).characterActing = 0;
                    for(int i = 0; i<6;i++) {
                        ((BattleScreen) screens[3]).selectedCharacters[i].acted = false;
                        ((BattleScreen) screens[3]).selectedCharacters[i].stage = Character.AnimationStage.WAITING;
                    }
                }
            }
        }
    }
    public void mouseDragged(){
        if (currentScreen == ScreenType.SCORESCREEN && screens[4].scroller.movingScroller()){
            screens[4].scroller.setScrollerLocation(mouseY-mouseOffSetY);
            float a = screens[4].scroller.scrollerSize/2;
            float b = screens[4].scroller.h/2;
            if(screens[4].scroller.getScrollerLocation()<screens[4].scroller.y-b+a){
                screens[4].scroller.setScrollerLocation(screens[4].scroller.y-b+a);
            }
            if(screens[4].scroller.getScrollerLocation()>screens[4].scroller.y+b-a){
                screens[4].scroller.setScrollerLocation(screens[4].scroller.y+b-a);
            }
        }
    }
    public void mouseReleased(){
        screens[4].scroller.setMovingScroller(false);

    }
    public void mousePressed() {
        if (currentScreen == ScreenType.TITLESCREEN && ((TitleScreen)screens[0]).keyPressed) {
            if (screens[0].b1.MouseOnButton(this)) {
                pvp = false;
                currentScreen = ScreenType.SELECTIONSCREEN;
            } else if (screens[0].b2.MouseOnButton(this)) {
                pvp = true;
                currentScreen = ScreenType.SELECTIONSCREEN;
            } else if (screens[0].b3.MouseOnButton(this)) {
                currentScreen = screens[4].screenType;
                screens[4].scroller.table = Scroller.Tables.players;
                screens[4].scroller.initTable();
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
                ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition()] = ((SelectionScreen) screens[1]).characters.characters[frameCount % 3].createCopy();
                if (((SelectionScreen) screens[1]).firstNullPosition() == ((SelectionScreen) screens[1]).selectedCharacters.length) {
                    for (int i = 0; i < ((BattleScreen) screens[3]).selectedCharacters.length; i++){
                        ((BattleScreen) screens[3]).selectedCharacters[i] = ((SelectionScreen) screens[1]).selectedCharacters[i].createCopy();
                    }
                    ((BattleScreen) screens[3]).initAnimationElements(this);
                    currentScreen = ScreenType.BATTLESCREEN;
                }
            }
        }
        else if (currentScreen == ScreenType.STATSCREEN) {
            if(screens[2].b1.MouseOnButton(this)) {
                if(((StatsScreen) screens[2]).character.role.equals("Warrior") || ((StatsScreen) screens[2]).character.role.equals("Paladin") || ((StatsScreen) screens[2]).character.role.equals("Mage")) {
                    ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition()] = ((StatsScreen) screens[2]).character.createCopy();
                    if (((SelectionScreen) screens[1]).firstNullPosition() == ((SelectionScreen) screens[1]).selectedCharacters.length) {
                        for (int i = 0; i < ((BattleScreen) screens[3]).selectedCharacters.length; i++) {
                            ((BattleScreen) screens[3]).selectedCharacters[i] = ((SelectionScreen) screens[1]).selectedCharacters[i].createCopy();
                        }
                        ((BattleScreen) screens[3]).initAnimationElements(this);
                        currentScreen = ScreenType.BATTLESCREEN;
                    } else {
                        currentScreen = screens[1].screenType;
                    }
                }
            }else if(screens[2].b2.MouseOnButton(this)) {
                currentScreen = screens[1].screenType;
            }
        }
        else if(currentScreen == ScreenType.BATTLESCREEN){
            if(screens[3].b1.MouseOnButton(this)&&((BattleScreen)screens[3]).choosing&&screens[3].b1.MouseOnButton(this)&&!((BattleScreen)screens[3]).targeting){

            }else if(screens[3].b2.MouseOnButton(this)){
                ((BattleScreen)screens[3]).actions[((BattleScreen)screens[3]).characterChoosing] = Character.Action.DEFEND;
            }
        }
        else if (currentScreen == ScreenType.SCORESCREEN){
            if(screens[4].scroller.mouseOnScroller(this)&&screens[4].scroller.h-screens[4].scroller.scrollerSize>2){
                mouseOffSetY = mouseY-screens[4].scroller.getScrollerLocation();
                screens[4].scroller.setMovingScroller(true);
            }else if(screens[4].b1.MouseOnButton(this)){
                currentScreen = ScreenType.TITLESCREEN;
                ((TitleScreen)screens[0]).keyPressed = false;
            }else if(screens[4].b2.MouseOnButton(this)){
                screens[4].scroller.toggleTable();
                screens[4].scroller.initTable();
            }
        }
    }
}