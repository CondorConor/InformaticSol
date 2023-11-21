package Phase1;

import processing.core.PApplet;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class UsableCharacters {
    Character[] characters = new Character[5];
    String special;
    PImage img = new PImage();
    PImage icon = new PImage();
    void initCharacters(PApplet p5) {

        //character 1
        icon = p5.loadImage("Icons/ShamanIcon.png");
        img = p5.loadImage("BigSprites/Shaman.png");
        special = """
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj""";

        characters[0] = new Character("Okopukupan", "Shaman", 10, 10, 10, 10, "\"bnjoijnboijboijnojnoj\"", special, img, icon);

        //character 2
        icon = p5.loadImage("Icons/PaladinIcon.png");
        img = p5.loadImage("BigSprites/Paladin.png");
        special = """
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj""";

        characters[1] = new Character("Isil", "Paladin", 10, 10, 10, 10, "\"bnjoijnboijboijnojnoj\"", special, img, icon);

        //character 4
        icon = p5.loadImage("Icons/BarbarianIcon.png");
        img = p5.loadImage("BigSprites/Barbarian.png");
        special = """
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj""";

        characters[2] = new Character("Suruyaha", "Barbarian", 10, 10, 10, 10, "\"bnjoijnboijboijnojnoj\"", special, img, icon);

        //character 4
        icon = p5.loadImage("Icons/WarriorIcon.png");
        img = p5.loadImage("BigSprites/Warrior.png");
        special = """
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj""";

        characters[3] = new Character("Steve", "Warrior", 10, 10, 10, 10, "\"bnjoijnboijboijnojnoj\"", special, img, icon);

        //character 5
        icon = p5.loadImage("Icons/MageIcon.png");
        img = p5.loadImage("BigSprites/Mage.png");
        special = """
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj
                bnjoijnboijboijnojnoj""";

        characters[4] = new Character("Rosalinda", "Mage", 10, 10, 10, 10, "\"bnjoijnboijboijnojnoj\"", special, img, icon);

    }
}
