package Phase1;

import processing.core.PApplet;

public class Scroller {

    float x, y, w, h;
    float barRadiusY;
    float scrollerSize, scrollerRadius;
    float scrollerLocation;
    int colorBar;
    int colorScroller;
    boolean movingScroller;
    String[] rowSections = {"Modality", "Players' names", "Winner (remaining)", "Date"};
    String[] textProva = {"pvp", "CONOR vs SHIMFU", "SHIMFU (2 characters)", "17/01/24"};
    int totalRows = 50;
    int rowsDisplayed = 12;
    float yPerElement;
    row4Table[] rows = new row4Table[totalRows];
    row4Table[] displayedRows = new row4Table[rowsDisplayed];
    row4Table titleRow;
    float tableInitialX;
    float tableInitialY;
    float rowW;
    float rowH;

    void initElements(PApplet p5) {
        tableInitialX = p5.width*4/32;
        tableInitialY = p5.height*10/32;
        rowW = p5.width*24/32;
        rowH = p5.height*2/32;
        for (int i=0; i<rows.length;i++) {
            rows[i] = new row4Table(rowW, rowH, tableInitialX, tableInitialY,  0,  null, false);
        }
        rows[2].setText(textProva);
        actualizeTable(0,0);
        titleRow = new row4Table(rowW, rowH, tableInitialX, tableInitialY, 0, rowSections, true);
    }
    Scroller(PApplet p5, float x, float y, float w, float h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        yPerElement = h/totalRows;
        scrollerSize = rowsDisplayed*yPerElement;
        barRadiusY = h/2;
        scrollerRadius = scrollerSize/2;
        scrollerLocation = y-barRadiusY+scrollerRadius;
        colorBar = p5.color(100);
        colorScroller = p5.color(200);
        movingScroller = false;
    }
    void actualizeTable(int rowsBefore, float additionalDisplacement){
        float a = additionalDisplacement/yPerElement;
        for(int i = rowsBefore; i<rowsDisplayed+rowsBefore; i++){
            try {
                displayedRows[i - rowsBefore] = rows[i-1].copy();
                float b = a*displayedRows[i-rowsBefore].h;
                displayedRows[i - rowsBefore].initialY = tableInitialY-b;
                System.out.println(displayedRows[i - rowsBefore].initialY);
                displayedRows[i - rowsBefore].displayPosition = i - rowsBefore;
                displayedRows[i - rowsBefore].setDisplaceY();
                System.out.println("row set correctly");
            }catch(Exception ArrayIndexOutOfBounds){
                displayedRows[i - rowsBefore] = null;
                System.out.println("row set to null");
            }
        }
    }
    void display(PApplet p5){
        p5.pushStyle();
        p5.noStroke();
        p5.fill(colorBar);
        p5.rect(x, y, w, h);
        p5.fill(colorScroller);
        p5.rect(x, scrollerLocation,w*11/10, scrollerSize );
        p5.popStyle();
        //tableInitialY = scrollerLocation-scrollerRadius;
        float a = scrollerLocation-scrollerRadius-y+barRadiusY;
        System.out.println(a/yPerElement);
        actualizeTable((int)Math.floor(a/yPerElement), a%yPerElement);
        for (row4Table row: displayedRows) {
            try{
                row.display(p5);
            }catch (Exception e){
                System.out.println("row is null");
            }
        }
        p5.pushStyle();
        p5.noStroke();
        p5.fill(50);
        p5.rect(p5.width/2, tableInitialY-(displayedRows[1].h/2)-100, p5.width, 200);
        p5.rect(p5.width/2, y+barRadiusY+250, p5.width, 500);
        p5.popStyle();

        p5.pushStyle();
        p5.stroke(250);
        p5.line(tableInitialX, tableInitialY+(float)(rowH*(rowsDisplayed-1.5)), tableInitialX+rowW, tableInitialY+(float)(rowH*(rowsDisplayed-1.5)));
        p5.popStyle();
        titleRow.display(p5);
    }

    boolean mouseOnScroller(PApplet p5){
        return RectButton.abs(p5.mouseX - this.x) < w * 11 / 10 && RectButton.abs(p5.mouseY - this.scrollerLocation) < scrollerSize / 2;
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
