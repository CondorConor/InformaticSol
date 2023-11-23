package Phase1;

import processing.core.PApplet;
import processing.core.PImage;

import static processing.awt.ShimAWT.loadImage;

public class UsableCharacters {
    Character[] characters = new Character[5];
    String special;
    PImage img = new PImage();
    PImage icon = new PImage();
    PImage sIcon = new PImage();
    void initCharacters(PApplet p5) {

        //character 1
        icon = p5.loadImage("Icons/ShamanIcon.png");
        img = p5.loadImage("BigSprites/Shaman.png");
        sIcon = p5.loadImage("Icons/poisoned.png");
        special = """
                Draws power
                from the Gods to try
                and poison the enemy team.
                The poison deals a fix
                amount of damage every
                round for 3 rounds.
                It also reduces their
                speed stat in 30%.
                AOI, 75% accuracy""";

        characters[0] = new Character("Okupukutan, Gods' vessel", "Shaman", 10, 10, 10, 10, "\"Rite of Poison\"", special, img, icon, sIcon);

        //character 2
        icon = p5.loadImage("Icons/PaladinIcon.png");
        img = p5.loadImage("BigSprites/Paladin.png");
        sIcon = p5.loadImage("Icons/healingSpell.png");

        special = """
                A green aura emerges
                form an ally, healing
                them to full health
                and nullifying any
                negative status effects""";

        characters[1] = new Character("Isil III of Talayotum", "Paladin", 10, 10, 10, 10, "\"Healing\"", special, img, icon, sIcon);

        //character 4
        icon = p5.loadImage("Icons/BarbarianIcon.png");
        img = p5.loadImage("BigSprites/Barbarian.png");
        sIcon = p5.loadImage("Icons/rage.png");

        special = """
                Enters a state of
                absolute anger. It
                boosts the attack and
                speed stats of the
                user in 50%""";


        characters[2] = new Character("Suruyaha, Flesh Slasher", "Barbarian", 10, 10, 10, 10, "\"RAGE!\"", special, img, icon, sIcon);

        //character 4
        icon = p5.loadImage("Icons/WarriorIcon.png");
        img = p5.loadImage("BigSprites/Warrior.png");
        sIcon = p5.loadImage("Icons/attackBoost.png");

        special = """
                Performs a strong
                attack with a weapon
                that deals high amounts
                of damage to one enemy.
                This always hits the
                target but it can not
                deal critical damage""";

        characters[3] = new Character("Bernat Alcover de Manacor", "Warrior", 10, 10, 10, 10, "\"Strong Attack\"", special, img, icon, sIcon);

        //character 5
        icon = p5.loadImage("Icons/MageIcon.png");
        img = p5.loadImage("BigSprites/Mage.png");
        sIcon = p5.loadImage("Icons/fireSpell.png");

        special = """
                Summons flames upon
                the opposing team
                that deal high amounts
                of damage to them.
                AOI, 80% accuracy""";

        characters[4] = new Character("Rosalinda, first of her class", "Mage", 10, 10, 10, 10, "\"Fire Summoning\"", special, img, icon, sIcon);

    }
}
