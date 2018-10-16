package entities.places;

import interfaces.Guild;
import interfaces.Hero;

import java.util.*;
import java.util.stream.Collectors;

public class GuildImpl implements Guild {
    private Map<String, Hero> heroes;
    private String name;

    public GuildImpl(String name) {
        this.heroes = new TreeMap<>();
        this.name = name;
    }

    @Override
    public Map<String, Hero> getHeroes() {
        return Collections.unmodifiableMap(this.heroes);
    }

    @Override
    public String addHero(Hero hero) {
        if (!this.heroes.containsKey(hero.getName())) {
            this.heroes.put(hero.getName(), hero);
            return String.format("Added hero: %s", hero.getName());
        } else {
            if (hero.getTotalPoints() > this.heroes.get(hero.getName()).getTotalPoints()) {
                heroes.put(hero.getName(), hero);
                return String.format("Updated hero: %s", this.getName());
            } else {
                return String.format("Hero %s can not be replaced by a weaker one.",
                        hero.getName());
            }
        }
    }

    @Override
    public String removeHero(Hero hero) {
        this.heroes.remove(hero.getName());
        return String.format("Successfully removed hero [%s] from guild %s",
                hero.getName(), this.getName());
    }

    @Override
    public Hero getHeroByName(String heroName) {
        return this.heroes.get(heroName);
    }

    @Override
    public Long getGuildSize() {
        return (long) this.heroes.size();
    }

    @Override
    public double getGuildPower() {
        return Double.valueOf(String.format("%.2f", this.heroes
                .values().stream().mapToDouble(Hero::getTotalPoints).sum()));
    }

    @Override
    public String getGuildSpecialization() {
        Map<List<Integer>, List<Double>> compareMap = new HashMap<>();

        Map<String, Integer> counts = new HashMap<>();
        counts.put("Willpower", this.heroes.values().stream()
                .filter(h -> h.getClass().getSuperclass().getSimpleName()
                        .equals("Willpower")).collect(Collectors.toList()).size());
        counts.put("Strength", this.heroes.values()
                .stream().filter(h -> h.getClass().getSuperclass().getSimpleName()
                        .equals("Strength")).collect(Collectors.toList()).size());
        counts.put("Endurance", this.heroes.values()
                .stream().filter(h -> h.getClass().getSuperclass().getSimpleName()
                        .equals("Endurance")).collect(Collectors.toList()).size());

        Map<String, Double> powers = new HashMap<>();
        powers.put("Willpower", this.heroes.values().stream()
                .filter(h -> h.getClass().getSuperclass().getSimpleName().equals("Willpower"))
                .collect(Collectors.toList()).stream().mapToDouble(Hero::getTotalPoints).sum());
        powers.put("Strength", this.heroes.values().stream()
                .filter(h -> h.getClass().getSuperclass().getSimpleName().equals("Strength"))
                .collect(Collectors.toList()).stream().mapToDouble(Hero::getTotalPoints).sum());
        powers.put("Endurance", this.heroes.values().stream()
                .filter(h -> h.getClass().getSuperclass().getSimpleName().equals("Endurance"))
                .collect(Collectors.toList()).stream().mapToDouble(Hero::getTotalPoints).sum());

        int maxCount = Integer.MIN_VALUE;
        String result = "";

        for (Map.Entry<String, Integer> stringIntegerEntry : counts.entrySet()) {
            if (stringIntegerEntry.getValue() > maxCount) {
                if (result.equals("")) {
                    maxCount = stringIntegerEntry.getValue();
                    result = stringIntegerEntry.getKey();
                } else {
                    if (powers.get(stringIntegerEntry.getKey()) > powers.get(result)) {
                        maxCount = stringIntegerEntry.getValue();
                        result = stringIntegerEntry.getKey();
                    }
                }
            }
        }

        return result;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Guild: " + this.getName()).append(System.lineSeparator());
        sb.append("###Heroes: ");
        if (this.getHeroes().isEmpty()) {
            sb.append("None");
        } else {
            sb.append(System.lineSeparator());
            this.heroes.values().stream().sorted((h1, h2) -> (int) ((h2.getTotalPoints()) - h1.getTotalPoints())).forEach(hero -> sb.append(String.format("Hero: %s, Offense: %.2f, Defense: %.2f%n",
                    hero.getName(), hero.getOffense(), hero.getDefense())));
        }
        return sb.toString();
    }
}