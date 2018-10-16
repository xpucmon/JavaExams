package entities.heroes.willpowerHeroes;

import static constants.HeroTypes.BRETON;

public class Breton extends Willpower {
    public Breton(String name, int magicka, int fatigue, int health) {
        super(name, magicka, fatigue, health, BRETON);
    }
}
