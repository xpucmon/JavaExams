package entities.heroes.enduranceHeroes;

import static constants.HeroTypes.NORD;

public class Nord extends Endurance {
    public Nord(String name, int magicka, int fatigue, int health) {
        super(name, magicka, fatigue, health, NORD);
    }
}
