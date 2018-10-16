package entities.heroes;

import interfaces.Hero;

public abstract class Characters implements Hero {
    private String name;
    private int magicka;
    private int fatigue;
    private int health;
    private String heroType;

    public Characters(String name, int magicka, int fatigue, int health, String heroType) {
        this.name = name;
        this.magicka = magicka;
        this.fatigue = fatigue;
        this.health = health;
        this.heroType = heroType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getMagicka() {
        return magicka;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public abstract double getOffense();

    @Override
    public abstract double getDefense();

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public double getTotalPoints() {
        return this.getOffense() + this.getDefense();
    }

    @Override
    public boolean isDead() {
        return this.health < 1;
    }

    @Override
    public void attack(Hero hero) {
        hero.receiveDamage(this.getOffense());
    }

    @Override
    public void receiveDamage(double amount) {
        this.health = this.getHealth() - (int)Math.floor(amount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Hero: %s, Type: [%s]%n",
                this.getName(), this.heroType));
        sb.append("#Stats: ").append(System.lineSeparator());
        sb.append("Health: " + this.getHealth()).append(System.lineSeparator());
        sb.append("Fatigue: " + this.getFatigue()).append(System.lineSeparator());
        sb.append("Magicka: " + this.getMagicka());
        return sb.toString();
    }
}
