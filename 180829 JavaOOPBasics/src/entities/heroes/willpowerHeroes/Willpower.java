package entities.heroes.willpowerHeroes;

import entities.heroes.Characters;

public class Willpower extends Characters {
    private double spellPentration;

    public Willpower(String name, int magicka, int fatigue, int health, String heroType) {
        super(name, magicka, fatigue, health, heroType);
        this.spellPentration = fatigue * 0.2;
    }

    @Override
    public double getOffense() {
        return (this.getMagicka() * 1.8) + this.spellPentration;
    }

    @Override
    public double getDefense() {
        return this.getHealth();
    }
}
