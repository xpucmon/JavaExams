package onehitdungeon.core;

import onehitdungeon.entities.heroes.MageHero;
import onehitdungeon.entities.heroes.PaladinHero;
import onehitdungeon.interfaces.DungeonManager;
import onehitdungeon.interfaces.Hero;
import onehitdungeon.interfaces.HeroTrainer;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DungeonManagerImpl implements DungeonManager {
    private Map<String, Hero> heroes;
    private Hero selectedHero;
    private int selectedHeroLevel;
    private int currentDungeonLevel;
    private int currentLevelBattlePower;
    private double currentLevelPrice;
    private Map<Hero, Integer> heroTrainings;
    private Map<Hero, List<Integer>> heroLevels;
    private HeroTrainer heroTrainer;

    public DungeonManagerImpl() {
        this.heroes = new LinkedHashMap<>();
        this.selectedHeroLevel = 0;
        this.setCurrentDungeonLevel(1);
        this.currentLevelBattlePower = 20;
        this.currentLevelPrice = 15.0;
        this.heroLevels = new LinkedHashMap<>();
        this.heroTrainings = new LinkedHashMap<>();
        this.heroTrainer = new HeroTrainerImpl();
    }

    @Override
    public String hero(List<String> arguments) {
        String type = arguments.get(0);
        String name = arguments.get(1);

        Hero hero = null;
        switch (type) {
            case "Paladin":
                hero = new PaladinHero(name);
                break;
            case "Mage":
                hero = new MageHero(name);
                break;
        }

        if (this.selectedHero == null) {
            this.selectedHero = hero;
        }

        this.heroes.put(name, hero);
        this.heroLevels.put(hero, new LinkedList<>());
        this.setLastHeroLevel(1);

        return String.format("Successfully added %s - %s.", type, name);
    }

    @Override
    public String select(List<String> arguments) {
        String heroName = arguments.get(0);
        this.selectedHero = this.heroes.get(heroName);
        return String.format("Successfully selected %s - %s.", this.selectedHero.getHeroClass(), heroName);
    }

    @Override
    public String status(List<String> arguments) {
        return this.selectedHero.toString();
    }

    @Override
    public String fight(List<String> arguments) {
        if (this.selectedHero.getTotalBattlePower() > this.currentLevelBattlePower) {
            this.selectedHero.earnGold(this.currentLevelPrice);
            return String.format("Fight won. You've gained %.2f gold.", this.currentLevelPrice);
        } else {
            this.setCurrentDungeonLevel(--this.currentDungeonLevel);
            return "Fight lost. You've returned to the previous level.";
        }
    }

    @Override
    public String advance(List<String> arguments) {
        this.setCurrentDungeonLevel(++this.currentDungeonLevel);
        return String.format("Successfully advanced to dungeon level %d.", this.currentDungeonLevel);
    }

    @Override
    public String train(List<String> arguments) {

        if (this.selectedHero.getGold() >= this.selectedHero.getTotalPriceForUpgrade()) {
            this.selectedHero.payGold(this.selectedHero.getTotalPriceForUpgrade());
            this.heroTrainer.trainHero(this.selectedHero);

            if (this.heroTrainings.isEmpty()){
                heroTrainings.put(this.selectedHero, 0);
            } else {
                this.heroTrainings.put(this.selectedHero, this.heroTrainings.get(this.selectedHero) + 1);
            }

            return String.format("Successfully trained hero. Current total battle power: %d.",
                    this.selectedHero.getTotalBattlePower());
        } else {
            return String.format("Insufficient gold for training. Needed %.2f. Got %.2f.",
                    this.selectedHero.getTotalPriceForUpgrade(), this.selectedHero.getGold());
        }
    }

    @Override
    public String quit(List<String> arguments) {

        StringBuilder sb = new StringBuilder();
        for (Hero hero : heroes.values()) {
            sb.append(String.format("%s %s - %d (BP)%n",
                    hero.getHeroClass(), hero.getName(), hero.getTotalBattlePower()));
        }
        sb.append("####################").append(System.lineSeparator());
        sb.append(String.format("Dungeon level reached: %d", this.currentDungeonLevel));

        return sb.toString();
    }

    private void setCurrentDungeonLevel(int newLevel) {
        if (newLevel >= 1) {
            this.currentDungeonLevel = newLevel;
            this.setCurrentLevelBattlePower(newLevel);
            this.setCurrentLevelPrice(newLevel);
            if (this.selectedHero != null) {
                this.setLastHeroLevel(newLevel);
            }
        }
    }

    private void setCurrentLevelBattlePower(int level) {
        this.currentLevelBattlePower = (this.currentLevelBattlePower * 2);
    }

    private void setCurrentLevelPrice(int level) {
        this.currentLevelPrice = (this.currentLevelPrice * 2);
    }

    private void setLastHeroLevel(int level) {
        if (this.heroLevels.get(this.selectedHero).isEmpty()){
            this.heroLevels.get(this.selectedHero).add(level);
        }

        int currentLevel = this.heroLevels.get(this.selectedHero).get(this.heroLevels.get(this.selectedHero).size() - 1);

        if (this.selectedHeroLevel != level) {
            if (level > currentLevel) {
                this.heroLevels.get(this.selectedHero).add(level);
            } else if (level < currentLevel && level >= 1) {
                this.heroLevels.get(this.selectedHero).remove(this.heroLevels.get(this.selectedHero).size() - 1);
            }
            this.selectedHeroLevel = level;
        }
    }
}
