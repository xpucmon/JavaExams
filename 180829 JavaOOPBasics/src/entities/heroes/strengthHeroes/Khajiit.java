package entities.heroes.strengthHeroes;

import static constants.HeroTypes.KHAJIIT;

public class Khajiit extends Strength {
    public Khajiit(String name, int magicka, int fatigue, int health) {
        super(name, magicka, fatigue, health, KHAJIIT);
    }
}
