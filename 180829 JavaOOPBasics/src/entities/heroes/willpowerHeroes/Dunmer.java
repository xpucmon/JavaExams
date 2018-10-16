package entities.heroes.willpowerHeroes;

import static constants.HeroTypes.DUNMER;

public class Dunmer extends Willpower {
    public Dunmer(String name, int magicka, int fatigue, int health) {
        super(name, magicka, fatigue, health, DUNMER);
    }
}
