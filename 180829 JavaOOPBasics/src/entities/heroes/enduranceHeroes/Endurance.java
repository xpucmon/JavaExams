package entities.heroes.enduranceHeroes;

import entities.heroes.Characters;

public class Endurance extends Characters {
    private double magicResistance;

    public Endurance(String name, int magicka, int fatigue, int health, String heroType) {
        super(name, magicka, fatigue, health * 2, heroType);
        this.magicResistance = magicka * 0.4;
    }

    @Override
    public double getOffense() {
        return this.getFatigue();
    }

    @Override
    public double getDefense() {
        return this.getHealth() + (this.getFatigue() * 0.6) + this.magicResistance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(System.lineSeparator());
        sb.append("#Endurance bonuses:").append(System.lineSeparator());
        sb.append(String.format("Magic resistance: +%.2f defense.", this.magicResistance));
        return sb.toString();
    }
}
