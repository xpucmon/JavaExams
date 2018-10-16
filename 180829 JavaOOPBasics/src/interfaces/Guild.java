package interfaces;

import java.util.Map;

public interface Guild {

    Map<String, Hero> getHeroes();

    String addHero(Hero hero);

    String removeHero(Hero hero);

    Hero getHeroByName(String heroName);

    Long getGuildSize();

    double getGuildPower();

    String getGuildSpecialization();

    String getName();

    String toString();
}