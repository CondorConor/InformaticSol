package Phase1;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Scroller {
    public enum Tables {games, players};
    Tables table;
    float x, y, w, h;
    float barRadiusY;
    float scrollerSize, scrollerRadius;
    float scrollerLocation;
    int colorBar;
    int colorScroller;
    boolean movingScroller = false;
    String[] rowSectionsGames = {"MODALITY", "PLAYERS' NAMES", "WINNER", "DATE"};
    int[] rowsLengthPercentGames = {15, 35, 30, 20};
    String[][] gamesTable;
    String[] rowSectionsPlayers = {"N", "NAME", "FAVOURITE CHARACTER", "WINS/LOSSES", "BEST WIN", "1st GAME"};
    int[] rowsLengthPercentPlayers = {5, 10, 33, 15, 20, 17};
    String[][] playersTable;
    int totalRows;
    int rowsDisplayed = 13;
    float yPerElement;
    row4Table[] rows;
    row4Table[] displayedRows = new row4Table[rowsDisplayed];
    row4Table titleRow;
    float tableInitialX;
    float tableInitialY;
    float rowW;
    float rowH;
    Scroller(PApplet p5, float x, float y, float w, float h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        barRadiusY = h/2;
        colorBar = p5.color(100);
        colorScroller = p5.color(200);
        rowW = p5.width*24/32;
        rowH = p5.height*2/32;
        tableInitialX = p5.width*4/32;
        tableInitialY = y-barRadiusY-(rowH/2);
    }

    void initTable(){
        if(table == Tables.players) {
            totalRows = playersTable.length;
        }else{
            totalRows = gamesTable.length;
        }
        rows = new row4Table[totalRows];
        yPerElement = h/totalRows;
        scrollerSize = rowsDisplayed*yPerElement;
        scrollerRadius = scrollerSize/2;
        scrollerLocation = y-barRadiusY+scrollerRadius;
        for (int i=0; i<rows.length;i++) {
            rows[i] = new row4Table(rowW, rowH, tableInitialX, tableInitialY,  0,  table == Tables.players?playersTable[i]:gamesTable[i], table == Tables.players?rowsLengthPercentPlayers:rowsLengthPercentGames, false);
        }
        actualizeTable(0,0);
        titleRow = new row4Table(rowW, rowH, tableInitialX, tableInitialY, 0, table == Tables.players?rowSectionsPlayers:rowSectionsGames, table == Tables.players?rowsLengthPercentPlayers:rowsLengthPercentGames,true);
    }
    void setupPlayerTable(){
        Object[] names = GUI.db.getColumnFrom(1, "players");
        playersTable = new String[names.length][6];
        double[] winPercent = new double[playersTable.length];
        for (int i = 0; i<names.length;i++) {
            //id
            playersTable[i][0] = Integer.toString(i);

            //name
            playersTable[i][1] = (String)names[i];

            //favouriteCharacter
            List<Object[]> characters = new ArrayList<>();
            Object[][] c1 = GUI.db.getInfoTable("SELECT COUNT(*), char1 FROM games WHERE player1 = '"+playersTable[i][1]+"' GROUP BY char1 ORDER BY COUNT(*) DESC", "games",2);
            try{
                Collections.addAll(characters, c1);
            }catch (Exception e){
                System.out.println(e);
            }
            Object[][] c2 = GUI.db.getInfoTable("SELECT COUNT(*), char2 FROM games WHERE player1 = '"+playersTable[i][1]+"' GROUP BY char2 ORDER BY COUNT(*) DESC", "games",2);
            try{

                Collections.addAll(characters, c2);
            }catch (Exception e){
                System.out.println(e);
            }
            Object[][] c3 = GUI.db.getInfoTable("SELECT COUNT(*), char3 FROM games WHERE player1 = '"+playersTable[i][1]+"' GROUP BY char3 ORDER BY COUNT(*) DESC", "games",2);
            try{

                Collections.addAll(characters, c3);
            }catch (Exception e){
                System.out.println(e);
            }
            Object[][] c4 = GUI.db.getInfoTable("SELECT COUNT(*), char4 FROM games WHERE player2 = '"+playersTable[i][1]+"' GROUP BY char4 ORDER BY COUNT(*) DESC", "games",2);
            try{

                Collections.addAll(characters, c4);
            }catch (Exception e){
                System.out.println(e);
            }
            Object[][] c5 = GUI.db.getInfoTable("SELECT COUNT(*), char5 FROM games WHERE player2 = '"+playersTable[i][1]+"' GROUP BY char5 ORDER BY COUNT(*) DESC", "games",2);
            try{

                Collections.addAll(characters, c5);
            }catch (Exception e){
                System.out.println(e);
            }
            Object[][] c6 = GUI.db.getInfoTable("SELECT COUNT(*), char6 FROM games WHERE player2 = '"+playersTable[i][1]+"' GROUP BY char6 ORDER BY COUNT(*) DESC", "games",2);
            try{

                Collections.addAll(characters, c6);
            }catch (Exception e){
                System.out.println(e);
            }
            List<Object[]> favCharacters = new ArrayList<>();
            aa:
            for (int k = 0; k<characters.size();k++) {
                if (characters.get(k)[0] != null) {
                    for (int j = 0; j < favCharacters.size(); j++) {
                        try {
                            if (favCharacters.get(j)[1].equals(characters.get(k)[1])) {
                                favCharacters.set(j, new Object[]{((Long) favCharacters.get(j)[0] + (Long) characters.get(k)[0]), favCharacters.get(j)[1]});
                                continue aa;
                            }
                        } catch (NullPointerException e) {
                            System.out.println(e);
                        }
                    }
                    favCharacters.add(characters.get(k));
                }
            }
            int max = 0;
            String favChar;
            try {
                favChar = (String) favCharacters.get(0)[1];
                for (Object[] obj : favCharacters) {
                    if (Math.toIntExact((Long) obj[0]) > max) {
                        max = Math.toIntExact((Long) obj[0]);
                        favChar = (String) obj[1];
                    }
                }
            }catch(Exception e){
                System.out.println(e);
                favChar = null;
            }

            playersTable[i][2] = favChar;

            //wins/losses
            Object[][] wins1 = GUI.db.getInfoTable("SELECT COUNT(*) FROM games WHERE player1 = '"+playersTable[i][1]+"' AND char4Hps = 0 AND char5Hps = 0 AND char6Hps = 0", "games",1);
            Object[][] wins2 = GUI.db.getInfoTable("SELECT COUNT(*) FROM games WHERE player2 = '"+playersTable[i][1]+"' AND char1Hps = 0 AND char2Hps = 0 AND char3Hps = 0", "games",1);
            int wins;
            try{
                wins = Math.toIntExact((Long) wins1[0][0]) + Math.toIntExact((Long) wins2[0][0]);
            }catch(NullPointerException e){
                System.out.println(e);
                wins = 0;
            }
            Object[][] games1 = GUI.db.getInfoTable("SELECT COUNT(*) FROM games WHERE player1 = '"+playersTable[i][1]+"'", "games",1);
            Object[][] games2 = GUI.db.getInfoTable("SELECT COUNT(*) FROM games WHERE player2 = '"+playersTable[i][1]+"'", "games",1);
            int losses;
            try{
                losses = Math.toIntExact((Long) games1[0][0]) + Math.toIntExact((Long) games2[0][0]) - wins;
            }catch (NullPointerException e){
                System.out.println(e);

                losses = 0;
            }

            playersTable[i][3] = wins + "/" + losses;

            if(wins+losses == 0){
                winPercent[i] = 0;

            }else {
                winPercent[i] = (double)wins/(wins+losses);
            }

            //firstGameDate
            try{
                playersTable[i][5] = (String)GUI.db.getInfoTable("SELECT MIN(gameDate) FROM games WHERE player1 = '"+playersTable[i][1]+"'", "games",1)[0][0];
            }catch(NullPointerException e){
                System.out.println(e);
                playersTable[i][5] = null;
            }
        }
        double strongest;
        String bestWin;
        for (int j = 0;j<playersTable.length;j++) {
            strongest = 0;
            bestWin = null;
            Object[][] wins1 = GUI.db.getInfoTable("SELECT player2 FROM games WHERE player1 = '"+playersTable[j][1]+"' AND char4Hps = 0 AND char5Hps = 0 AND char6Hps = 0", "games",1);
            Object[][] wins2 = GUI.db.getInfoTable("SELECT player1 FROM games WHERE player2 = '"+playersTable[j][1]+"' AND char1Hps = 0 AND char2Hps = 0 AND char3Hps = 0", "games",1);
            try {
                for (Object[] a : wins1) {
                    for (int k = 0; k < playersTable.length; k++) {
                        if (a[0].equals(playersTable[k][1])) {
                            if (winPercent[k] > strongest) {
                                strongest = winPercent[k];
                                bestWin = playersTable[k][1];
                            }
                        }
                    }
                }
            }catch(NullPointerException e){
                System.out.println(e);
            }
            try {

                for (Object[] a : wins2) {
                    for (int k = 0; k < playersTable.length; k++) {
                        if (a[0].equals(playersTable[k][1])) {
                            if (winPercent[k] > strongest) {
                                strongest = winPercent[k];
                                bestWin = playersTable[k][1];
                            }
                        }
                    }
                }
            }catch(NullPointerException e){
                System.out.println(e);
            }
            playersTable[j][4] = bestWin;
        }
    }

    void setupGamesTable(){
        Object[] gamesIDs = GUI.db.getColumnFrom(0, "games");
        Object[] modality = GUI.db.getColumnFrom("modality", "games");
        Object[][] playersNames = {GUI.db.getColumnFrom("player1", "games"), GUI.db.getColumnFrom("player2", "games")};
        Object[][] charactersHPs = {GUI.db.getColumnFrom("char1Hps", "games"), GUI.db.getColumnFrom("char2Hps", "games"),GUI.db.getColumnFrom("char3Hps", "games"), GUI.db.getColumnFrom("char4Hps", "games"), GUI.db.getColumnFrom("char5Hps", "games"), GUI.db.getColumnFrom("char6Hps", "games")};
        Object[] date = GUI.db.getColumnFrom("gameDate", "games");
        gamesTable = new String[gamesIDs.length][4];

        for(int i = 0; i<gamesIDs.length;i++){
            //modality
            try{
                gamesTable[i][0] = modality[i].toString();
            } catch(NullPointerException e){
                gamesTable[i][0] = null;
            }
            //playersNames
            try{
                gamesTable[i][1] = playersNames[0][i].toString().concat("vs"+playersNames[1][i]);
            } catch(NullPointerException e){
                gamesTable[i][1] = null;
            }
            //winner
            try{
                if((Integer)charactersHPs[3][i] == 0 && (Integer)charactersHPs[4][i] == 0 && (Integer)charactersHPs[5][i] == 0){
                    gamesTable[i][2] = playersNames[0][i].toString();
                }else if((Integer)charactersHPs[0][i] == 0 && (Integer)charactersHPs[1][i] == 0 && (Integer)charactersHPs[2][i] == 0){
                    gamesTable[i][2] = playersNames[1][i].toString();
                }else{
                    gamesTable[i][2] = "N/F";
                }
            } catch(NullPointerException e){
                gamesTable[i][2] = null;
            }
            //date
            try{
                gamesTable[i][3] = date[i].toString();
            } catch(NullPointerException e){
                gamesTable[i][3] = null;
            }
        }
    }
    void actualizeTable(int rowsBefore, float additionalDisplacement){
        float a = additionalDisplacement/yPerElement;
        for(int i = rowsBefore; i<rowsDisplayed+rowsBefore; i++){
            try {
                displayedRows[i - rowsBefore] = rows[i-1].copy();
                float b = a*displayedRows[i-rowsBefore].h;
                displayedRows[i - rowsBefore].initialY = tableInitialY-b;
                displayedRows[i - rowsBefore].displayPosition = i - rowsBefore;
                displayedRows[i - rowsBefore].setDisplaceY();
            }catch(Exception ArrayIndexOutOfBounds){
                displayedRows[i - rowsBefore] = null;
            }
        }
    }
    void display(PApplet p5){
        p5.pushStyle();
        p5.noStroke();
        p5.fill(colorBar);
        p5.rect(x, y, w, h);
        p5.fill(colorScroller);
        p5.rect(x, scrollerLocation,w*13/10, scrollerSize );
        p5.popStyle();
        float a = scrollerLocation-scrollerRadius-y+barRadiusY;
        actualizeTable((int)Math.floor(a/yPerElement), a%yPerElement);
        for (row4Table row: displayedRows) {
            try{
                row.display(p5);
            }catch (Exception ignore){}
        }
        p5.pushStyle();
        p5.noStroke();
        p5.fill(50);
        p5.rect(p5.width/2, tableInitialY-(rowH/2)-100, p5.width, 200);
        p5.rect(p5.width/2, y+barRadiusY+250, p5.width, 500);
        p5.popStyle();

        p5.pushStyle();
        p5.stroke(250);
        p5.line(tableInitialX, tableInitialY+(float)(rowH*(rowsDisplayed-1.5)), tableInitialX+rowW, tableInitialY+(float)(rowH*(rowsDisplayed-1.5)));
        p5.popStyle();
        titleRow.display(p5);
    }

    void toggleTable(){
        if(table == Tables.players){
            table = Tables.games;
        } else{
            table = Tables.players;
        }
    }

    boolean mouseOnScroller(PApplet p5){
        return RectButton.abs(p5.mouseX - this.x) < w * 13 / 20 && RectButton.abs(p5.mouseY - this.scrollerLocation) < scrollerSize / 2;
    }

    public float getScrollerLocation() {
        return scrollerLocation;
    }

    public void setScrollerLocation(float scrollerLocation) {
        this.scrollerLocation = scrollerLocation;
    }

    public boolean movingScroller() {
        return movingScroller;
    }

    public void setMovingScroller(boolean movingScroller) {
        this.movingScroller = movingScroller;
    }
}
