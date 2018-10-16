package entities.heroes.strengthHeroes;

import entities.heroes.Characters;

public class Strength extends Characters {
    private double magicDamage;

    public Strength(String name, int magicka, int fatigue, int health, String heroType) {
        super(name, magicka, fatigue, health, heroType);
        this.magicDamage = magicka * 0.5;
    }

    @Override
    public double getOffense() {
        return (this.getFatigue() * 1.25) + (this.getHealth() * 0.3) + this.magicDamage;
    }

    @Override
    public double getDefense() {
        return this.getHealth() + (this.getFatigue() * 0.1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(System.lineSeparator());
        sb.append("#Strength bonuses:").append(System.lineSeparator());
        sb.append(String.format("Magic damage: +%.1f offense.", this.magicDamage));
        return sb.toString();
    }
}
