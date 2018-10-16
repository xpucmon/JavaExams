package entities.heroes.enduranceHeroes;

import static constants.HeroTypes.ORSIMER;

public class Orc extends Endurance {
    public Orc(String name, int magicka, int fatigue, int health) {
        super(name, magicka, fatigue, health, ORSIMER);
    }

    @Override
    public double getDefense() {
        return super.getDefense() * 1.1;
    }
}
