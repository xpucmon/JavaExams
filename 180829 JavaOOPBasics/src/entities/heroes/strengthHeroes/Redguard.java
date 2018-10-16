package entities.heroes.strengthHeroes;

import static constants.HeroTypes.REDGUARD;

public class Redguard extends Strength {

    public Redguard(String name, int magicka, int fatigue, int health) {
        super(name, magicka, fatigue, health, REDGUARD);
    }
}
