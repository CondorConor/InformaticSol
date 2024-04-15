package Phase1;

import processing.core.PApplet;
import processing.core.PImage;

public class UsableCharacters {
    Character[] characters = new Character[6];
    String role;
    String special;
    PImage img = new PImage();
    PImage icon = new PImage();
    PImage sIcon = new PImage();
    PImage[] standSprites = new PImage[2];
    PImage[] standSpritesRight = new PImage[2];
    PImage[] attackSprites = new PImage[5];
    PImage[] attackSpritesRight = new PImage[5];
    PImage[] specialSprites = new PImage[5];
    PImage[] specialSpritesRight = new PImage[5];
    PImage[] effectSprites = new PImage[4];
    PImage[] effectSpritesRight = new PImage[4];
    int extraW;

    Character.AttackType attackType = Character.AttackType.JUMP;

    void initCharacters(PApplet p5) {

        //character 1
        icon = p5.loadImage("Icons/WarriorIcon.png");
        img = p5.loadImage("BigSprites/Warrior.png");
        extraW = 1;
        sIcon = p5.loadImage("Icons/warCry.png");

        special = """
                Uses a vigorous
                chant to boost the
                team's confidence and
                energy. It buffs the
                user and allies' attack
                and defence stats by 30%""";

        characters[0] = new Character("Jordi Alcover of Manacor", "Warrior", 10, 10, 10, 10, "\"War Cry\"", special, img, icon, sIcon, Character.AttackType.JUMP, extraW);
        characters[0].initAnimationSprites(p5);

        //character 2
        icon = p5.loadImage("Icons/PaladinIcon.png");
        img = p5.loadImage("BigSprites/Paladin.png");
        extraW = 0;
        sIcon = p5.loadImage("Icons/healingSpell.png");

        special = """
                A green aura emerges
                form an ally, healing
                them to full health
                and nullifying any
                negative status effects""";

        characters[1] = new Character("Isil III of Talayotum", "Paladin", 10, 10, 10, 10, "\"Healing\"", special, img, icon, sIcon, Character.AttackType.JUMP, extraW);
        characters[1].initAnimationSprites(p5);


        //character 3
        icon = p5.loadImage("Icons/MageIcon.png");
        img = p5.loadImage("BigSprites/Mage.png");
        extraW = 2;
        sIcon = p5.loadImage("Icons/fireSpell.png");

        special = """
                Summons flames upon
                the opposing team
                that deal high amounts
                of damage to them.
                AOI, 80% accuracy""";

        characters[2] = new Character("Giulia, first of her class", "Mage", 10, 10, 10, 10, "\"Fire Summoning\"", special, img, icon, sIcon, Character.AttackType.RANGED, extraW);
        characters[2].attackSprites = new PImage[7];
        characters[2].attackSpritesRight = new PImage[7];
        characters[2].initAnimationSprites(p5);

        //character 4
        icon = p5.loadImage("Icons/AssassinIcon.png");
        img = p5.loadImage("BigSprites/Assassin.png");
        extraW = 0;
        sIcon = p5.loadImage("Icons/backStab.png");

        special = """
                A quick and silent
                attack that deals
                ginormous amounts of
                damage to one enemy.
                This attack always hits
                the target and it always
                deals critical damage.""";

        characters[3] = new Character("Bertha, Fools' Shadow", "Assassin", 10, 10, 10, 10, "\"Back Stab\"", special, img, icon, sIcon, attackType, extraW);
        characters[3].initAnimationSprites(p5.loadImage("SmallSprites/"+characters[0].role+"/Damaged.png"),p5.loadImage("SmallSprites/"+characters[0].role+"/DamagedRight.png"), standSprites,standSpritesRight,attackSprites, attackSpritesRight);

        //character 5
        icon = p5.loadImage("Icons/BarbarianIcon.png");
        img = p5.loadImage("BigSprites/Barbarian.png");
        extraW = 0;
        sIcon = p5.loadImage("Icons/rage.png");

        special = """
                Enters a state of
                absolute anger. It
                buffs the attack and
                speed stats of the
                user by 50%""";

        characters[4] = new Character("Emma-O, hell's bringer", "Barbarian", 10, 10, 10, 10, "\"Rage\"", special, img, icon, sIcon, Character.AttackType.JUMP, extraW);
        characters[4].initAnimationSprites(p5.loadImage("SmallSprites/"+characters[0].role+"/Damaged.png"),p5.loadImage("SmallSprites/"+characters[0].role+"/DamagedRight.png"), standSprites,standSpritesRight,attackSprites, attackSpritesRight);

        //character 6
        icon = p5.loadImage("Icons/ShamanIcon.png");
        img = p5.loadImage("BigSprites/Shaman.png");
        extraW = 1;
        sIcon = p5.loadImage("Icons/poisoned.png");
        special = """
                Draws power from the
                Gods to try and poison
                the enemy team. The poison
                deals a fix amount of
                damage every round for
                3 rounds. It also reduces
                their speed stat by 30%.
                AOI, 75% accuracy""";

        characters[5] = new Character("Okupukutan, Gods' vessel", "Shaman", 10, 10, 10, 10, "\"Rite of Poison\"", special, img, icon, sIcon, attackType, extraW);
        characters[5].initAnimationSprites(p5.loadImage("SmallSprites/"+characters[0].role+"/Damaged.png"),p5.loadImage("SmallSprites/"+characters[0].role+"/DamagedRight.png"), standSprites,standSpritesRight, attackSprites, attackSpritesRight);

    }
}