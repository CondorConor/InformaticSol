package Phase1;

public class Character {
    String name;
    String role;
    int hps; //health points
    int dmg; //damage
    int def; //defence
    int spd; //speed
    String specialName;
    String special;
    void useSpecial(){}
    Character(String name, String role, int hps, int dmg, int def, int spd, String specialName, String special){
        this.name = name;
        this.role = role;
        this.hps = hps;
        this.dmg = dmg;
        this.def = def;
        this.spd = spd;
        this.specialName = specialName;
        this.special = special;

    }
}
