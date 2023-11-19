package Phase1;
import processing.core.PApplet;
import processing.core.PFont;
public class StatsScreen extends Screen{
    Character character;
    public void setCharacter(Character character) {
        this.character = character;
    }
    void initElements(PApplet p5){
        screenType = GUI.ScreenType.STATSCREEN;
        character = new Character();
        character.name = "Steve";
        character.role = "Warrior";
        character.special = """
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj""";
    }
    void display(PApplet p5, PFont fontTitle, int frameCount, boolean pvp){
        p5.pushStyle();
        p5.textAlign(0, 3);
        p5.textFont(fontTitle, 40);
        p5.text(character.name, p5.width*2/32, p5.height*3/32);
        p5.popStyle();

        p5.pushStyle();
        p5.textAlign(0, 0);
        p5.text(character.special, p5.width*22/32, p5.height*8/32);
        p5.fill(200);
        p5.text("\""+character.role+"\"", p5.width*4/32, p5.height*5/32);
        p5.popStyle();

        p5.pushStyle();
        p5.stroke(255);p5.strokeWeight(10);
        p5.fill(50);
        p5.rect(p5.width*5/32, p5.height*19/32, p5.width*6/32, p5.height*22/32);
        p5.rectMode(0);
        p5.rect(p5.width*22/32, p5.height*3/32, p5.width*6/32, p5.height*10/32);
        p5.popStyle();

    }
}