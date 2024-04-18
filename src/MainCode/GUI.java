package MainCode;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.Arrays;

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
    PopUp comingSoon;
    PopUp notSavedWarning;
    PopUp notConnectedWarning;
    PopUp versatile;
    TextField inputText;
    boolean saved = false;

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
        comingSoon = new PopUp("Coming\nSoon!",this);
        notSavedWarning = new PopUp("Careful!\nYou are trying\nto exit without saving",this);
        notConnectedWarning = new PopUp("NO CONNECTION\nYou games won't\nsaved in database",this);
        versatile = new PopUp("",this);
        if(db.connected){
            notConnectedWarning.notShowAgain = true;
        }
        inputText = new TextField(width/2, height/2, width/2, height/3, 10, "Name player 1",this);
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
        if(comingSoon.visible){
            pushStyle();
            textSize(100);
            comingSoon.display(this);
            popStyle();
        }
        if(notSavedWarning.visible) {
            pushStyle();
            textSize(50);
            notSavedWarning.display(this);
            popStyle();
        }
        if(notConnectedWarning.visible) {
            pushStyle();
            textSize(50);
            notConnectedWarning.display(this);
            popStyle();
        }
        if(versatile.visible) {
            pushStyle();
            textSize(50);
            versatile.display(this);
            popStyle();
        }
        if(inputText.visible) {
            pushStyle();
            textSize(40);
            inputText.display(this,clock);
            popStyle();
        }
    }

    public void keyPressed() {
        if (inputText.visible) {
            if((key >= 65&&key<=90)||(key >= 97&&key<=122)){
                inputText.addText(String.valueOf(key).toUpperCase());
            }else if((key >= 48&&key<=57)){
                inputText.addText(String.valueOf(key));
            } else if(keyCode == BACKSPACE){
                inputText.removeText();
            }
        } else {
            if (currentScreen == ScreenType.TITLESCREEN) {
                ((TitleScreen) screens[0]).keyPressed = true;
            } else if (currentScreen == ScreenType.BATTLESCREEN) {
                if (((BattleScreen) screens[3]).targeting) {
                    int charPosition = ((BattleScreen) screens[3]).items.get(1).charPosition;
                    if (keyCode == DOWN && charPosition != 2 && charPosition != 5) {
                        if (!((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[charPosition + 1], charPosition + 1) && (charPosition == 0 || charPosition == 3)) {
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[charPosition + 2], charPosition + 2);
                        }
                    } else if (keyCode == UP && charPosition != 0 && charPosition != 3) {
                        if (!((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[charPosition - 1], charPosition - 1) && (charPosition == 2 || charPosition == 5)) {
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[charPosition - 2], charPosition - 2);
                        }
                    } else if (keyCode == ENTER) {
                        ((BattleScreen) screens[3]).actions[((BattleScreen) screens[3]).characterChoosing] = Character.Action.ATTACK;
                        ((BattleScreen) screens[3]).targets[((BattleScreen) screens[3]).characterChoosing] = ((BattleScreen) screens[3]).items.get(1).character;
                        ((BattleScreen) screens[3]).toggleTargeting();
                    }
                }
            }
            if (key == '1') {
                currentScreen = screens[0].screenType;
                ((TitleScreen) screens[0]).keyPressed = false;
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
                ((GameStatsScreen) screens[5]).initAnimationElements(this);
                currentScreen = screens[5].screenType;
            } else if (key == '7') {
                if (currentScreen == ScreenType.BATTLESCREEN) {
                    if (((BattleScreen) screens[3]).initiative[((BattleScreen) screens[3]).characterActing].stage == Character.AnimationStage.END) {
                        ((BattleScreen) screens[3]).characterActing = 0;
                        for (int i = 0; i < 6; i++) {
                            ((BattleScreen) screens[3]).selectedCharacters[i].acted = false;
                            ((BattleScreen) screens[3]).selectedCharacters[i].stage = Character.AnimationStage.WAITING;
                        }
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
        if (comingSoon.visible) {
            if (comingSoon.close.MouseOnButton(this)) {
                comingSoon.visible = false;
            }
        } else if (notSavedWarning.visible) {
            if (notSavedWarning.close.MouseOnButton(this)) {
                notSavedWarning.visible = false;
            }
        }else if (notConnectedWarning.visible) {
            if (notConnectedWarning.close.MouseOnButton(this)) {
                notConnectedWarning.visible = false;
                notConnectedWarning.notShowAgain = true;
            }
        }else if (versatile.visible) {
            if (versatile.close.MouseOnButton(this)) {
                versatile.visible = false;
            }
        }else if (inputText.visible) {
            if (inputText.accept.MouseOnButton(this)) {
                if(((TitleScreen)screens[0]).namePlayer1==null){
                    ((TitleScreen)screens[0]).namePlayer1=inputText.returnText();
                    inputText.title = "Name player 2";
                    Arrays.fill(inputText.text, null);
                } else{
                    ((TitleScreen)screens[0]).namePlayer2=inputText.returnText();
                    inputText.title = "Name player 1";
                    Arrays.fill(inputText.text, null);
                    inputText.visible = false;
                    pvp = true;
                    currentScreen = ScreenType.SELECTIONSCREEN;
                }
            }else if(inputText.cancel.MouseOnButton(this)){
                ((TitleScreen)screens[0]).namePlayer1= null;
                ((TitleScreen)screens[0]).namePlayer2= null;
                inputText.title = "Name player 1";
                Arrays.fill(inputText.text, null);
                inputText.visible = false;
            }
        }else {
            if (currentScreen == ScreenType.TITLESCREEN && ((TitleScreen) screens[0]).keyPressed) {
                if (screens[0].b1.MouseOnButton(this)) {
                    comingSoon.visible=true;
                } else if (screens[0].b2.MouseOnButton(this)) {
                    if(notConnectedWarning.notShowAgain) {
                        inputText.title = "Name player 1";
                        inputText.visible = true;
                    }else{
                        notConnectedWarning.visible = true;
                    }
                } else if (screens[0].b3.MouseOnButton(this)) {
                    currentScreen = screens[4].screenType;
                    screens[4].scroller.table = Scroller.Tables.players;
                    screens[4].scroller.initTable();
                }
            } else if (currentScreen == ScreenType.SELECTIONSCREEN) {
                if (screens[1].cb1.MouseOnButton(this)) {
                    currentScreen = screens[2].screenType;
                    ((StatsScreen) screens[2]).character = ((SelectionScreen) screens[1]).characters.characters[((SelectionScreen) screens[1]).position];

                } else if (screens[1].cb2.MouseOnButton(this) || screens[1].b1.MouseOnButton(this)) {
                    ((SelectionScreen) screens[1]).position = ((SelectionScreen) screens[1]).PreviousPosition(((SelectionScreen) screens[1]).position);

                } else if (screens[1].cb3.MouseOnButton(this) || screens[1].b2.MouseOnButton(this)) {
                    ((SelectionScreen) screens[1]).position = ((SelectionScreen) screens[1]).NextPosition(((SelectionScreen) screens[1]).position);

                } else if (screens[1].b3.MouseOnButton(this)) {
                    if (((SelectionScreen) screens[1]).firstNullPosition() == 0) {
                        currentScreen = ScreenType.TITLESCREEN;
                        ((TitleScreen) screens[0]).keyPressed = false;
                    } else {
                        ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition() - 1] = null;
                    }
                } else if (screens[1].b4.MouseOnButton(this)) {
                    ((SelectionScreen) screens[1]).selectedCharacters[((SelectionScreen) screens[1]).firstNullPosition()] = ((SelectionScreen) screens[1]).characters.characters[frameCount % 3].createCopy();
                    if (((SelectionScreen) screens[1]).firstNullPosition() == ((SelectionScreen) screens[1]).selectedCharacters.length) {
                        for (int i = 0; i < ((BattleScreen) screens[3]).selectedCharacters.length; i++) {
                            ((BattleScreen) screens[3]).selectedCharacters[i] = ((SelectionScreen) screens[1]).selectedCharacters[i].createCopy();
                        }
                        ((BattleScreen) screens[3]).initAnimationElements(this);
                        currentScreen = ScreenType.BATTLESCREEN;
                    }
                }
            } else if (currentScreen == ScreenType.STATSCREEN) {
                if (screens[2].b1.MouseOnButton(this)) {
                    if (((StatsScreen) screens[2]).character.role.equals("Warrior") || ((StatsScreen) screens[2]).character.role.equals("Paladin") || ((StatsScreen) screens[2]).character.role.equals("Mage")) {
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
                    }else{
                        comingSoon.visible = true;
                    }
                } else if (screens[2].b2.MouseOnButton(this)) {
                    currentScreen = screens[1].screenType;
                }
            } else if (currentScreen == ScreenType.BATTLESCREEN) {
                if (screens[3].b1.MouseOnButton(this) && ((BattleScreen) screens[3]).choosing && !((BattleScreen) screens[3]).targeting) {
                    ((BattleScreen) screens[3]).toggleTargeting();
                    if (((BattleScreen) screens[3]).characterChoosing < 3) {
                        if(!((BattleScreen) screens[3]).selectedCharacters[3].died) {
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[3], 3);
                        }else if(!((BattleScreen) screens[3]).selectedCharacters[4].died) {
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[4], 4);
                        }else{
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[5], 5);
                        }
                    } else {
                        if(!((BattleScreen) screens[3]).selectedCharacters[0].died) {
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[0], 0);
                        }else if(!((BattleScreen) screens[3]).selectedCharacters[1].died) {
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[1], 1);
                        }else{
                            ((BattleScreen) screens[3]).items.get(1).setCharacter(((BattleScreen) screens[3]).selectedCharacters[2], 2);

                        }
                    }
                } else if (screens[3].b2.MouseOnButton(this) && ((BattleScreen) screens[3]).choosing && !((BattleScreen) screens[3]).targeting) {
                    ((BattleScreen) screens[3]).actions[((BattleScreen) screens[3]).characterChoosing] = Character.Action.DEFEND;
                    ((BattleScreen) screens[3]).selectedCharacters[((BattleScreen) screens[3]).characterChoosing].blocking = true;
                    ((BattleScreen) screens[3]).targets[((BattleScreen) screens[3]).characterChoosing] = ((BattleScreen) screens[3]).selectedCharacters[((BattleScreen) screens[3]).characterChoosing];
                    ((BattleScreen) screens[3]).addDefendSprite(this, ((BattleScreen) screens[3]).characterChoosing);
                } else if (screens[3].b4.MouseOnButton(this) && !((BattleScreen) screens[3]).battling){
                    if(!saved&&db.connected&&!notSavedWarning.notShowAgain){
                        notSavedWarning.visible = true;
                        notSavedWarning.notShowAgain = true;
                    }else{
                        ((BattleScreen) screens[3]).restart(this);
                        saved = false;
                    }
                } else if (screens[3].b5.MouseOnButton(this) && !((BattleScreen) screens[3]).battling){
                    if(!saved&&db.connected&&!notSavedWarning.notShowAgain){
                        notSavedWarning.visible = true;
                        notSavedWarning.notShowAgain = true;
                    }else{
                        ((BattleScreen) screens[3]).restart(this);
                        Arrays.fill(((BattleScreen) screens[3]).selectedCharacters, null);
                        Arrays.fill(((SelectionScreen) screens[1]).selectedCharacters, null);
                        currentScreen = ScreenType.SELECTIONSCREEN;
                        saved = false;
                    }
                } else if (screens[3].b6.MouseOnButton(this) && !((BattleScreen) screens[3]).battling) {
                    if (!saved && db.connected&&!notSavedWarning.notShowAgain) {
                        notSavedWarning.visible = true;
                        notSavedWarning.notShowAgain = true;
                    } else {
                        ((BattleScreen) screens[3]).restart(this);
                        Arrays.fill(((BattleScreen) screens[3]).selectedCharacters, null);
                        ((TitleScreen)screens[0]).keyPressed = false;
                        currentScreen = ScreenType.TITLESCREEN;
                        saved = false;
                    }
                } else if (screens[3].b7.MouseOnButton(this) && screens[3].b7.enabled &&!((BattleScreen) screens[3]).battling) {
                    if (!saved && db.connected) {
                        boolean inserted = db.insert("games", ((BattleScreen)screens[3]).gameValues(this, ((TitleScreen)screens[0]).namePlayer1, ((TitleScreen)screens[0]).namePlayer2));
                        if(inserted){
                            versatile.text = "Your game was\nsaved in database\nsuccessfully!";
                            versatile.visible = true;
                            screens[3].b7.enabled = false;
                            saved = true;
                        }else{
                            versatile.text = "ERROR\nProblem connecting\nto database";
                            versatile.visible = true;
                        }
                  }
              }
            } else if (currentScreen == ScreenType.SCORESCREEN) {
                if (screens[4].scroller.mouseOnScroller(this) && screens[4].scroller.h - screens[4].scroller.scrollerSize > 2) {
                    mouseOffSetY = mouseY - screens[4].scroller.getScrollerLocation();
                    screens[4].scroller.setMovingScroller(true);
                } else if (screens[4].b1.MouseOnButton(this)) {
                    currentScreen = ScreenType.TITLESCREEN;
                    ((TitleScreen) screens[0]).keyPressed = false;
                } else if (screens[4].b2.MouseOnButton(this)) {
                    screens[4].scroller.toggleTable();
                    screens[4].scroller.initTable();
                }
            }
        }
    }
}