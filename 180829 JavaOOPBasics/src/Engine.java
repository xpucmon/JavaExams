import entities.heroes.enduranceHeroes.Nord;
import entities.heroes.enduranceHeroes.Orc;
import entities.heroes.strengthHeroes.Khajiit;
import entities.heroes.strengthHeroes.Redguard;
import entities.heroes.willpowerHeroes.Breton;
import entities.heroes.willpowerHeroes.Dunmer;
import entities.places.GuildImpl;
import entities.places.YoungerProvince;
import interfaces.Guild;
import interfaces.Hero;
import interfaces.Province;
import io.Reader;
import io.Writer;

import java.io.IOException;
import java.util.*;

public class Engine implements Runnable {
    private Reader reader;
    private Writer writer;
    Province province;
    Map<String, Province> provinces;
    List<String> heroTypesList;

    public Engine(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
        this.provinces = new TreeMap<>();
        this.heroTypesList = new ArrayList<>();
    }

    @Override
    public void run() {
        String input = "";
        while (true) {

            try {
                input = reader.read();
            } catch (IOException ignored) {
            }

            String[] params = input.split("\\s+");
            String command = params[0];
            String result = null;

            switch (command) {
                case "CREATE_PROVINCE:": {
                    String provinceName = params[1];
                    if (this.provinces.containsKey(provinceName)) {
                        result = String.format("Province with name %s already exists!", provinceName);
                    } else {
                        Province province = new YoungerProvince(provinceName);
                        this.province = province;
                        this.provinces.put(provinceName, province);
                        result = String.format("Created province %s", provinceName);
                    }
                }
                break;
                case "SELECT:":
                    String provinceName = params[1];
                    if (provinceName.equals(this.province.getName())) {
                        result = String.format("Province %s has already been selected!", provinceName);
                    } else if (!this.provinces.containsKey(provinceName)) {
                        result = String.format("Province %s does not exist", provinceName);
                    } else {
                        this.province = this.provinces.get(provinceName);
                        result = String.format("Province %s selected!", provinceName);
                    }
                    break;
                case "ADD_HERO:":
                    if (this.province == null) {
                        result = "No province selected!";
                    } else {
                        String guildName = params[1];
                        String heroType = params[2];
                        String heroName = params[3];
                        int health = Integer.parseInt(params[4]);
                        int fatigue = Integer.parseInt(params[5]);
                        int magicka = Integer.parseInt(params[6]);

                        if (!this.province.contains(guildName)) {
                            this.province.addGuild(new GuildImpl(guildName));
                        }
//                        if (!this.heroTypesList.contains(heroType)) {
//                            result = "No such hero type!";
//                            //TODO herotypes creation???
//                        }
                        else {
                            if (health < 1 || fatigue < 1 || magicka < 1) {
                                result = "Invalid character stats!";
                            } else {
                                Hero hero = null;
                                switch (heroType) {
                                    case "Redguard":
                                        hero = new Redguard(heroName, magicka, fatigue, health);
                                        break;
                                    case "Khajiit":
                                        hero = new Khajiit(heroName, magicka, fatigue, health);
                                        break;
                                    case "Nord":
                                        hero = new Nord(heroName, magicka, fatigue, health);
                                        break;
                                    case "Orc":
                                        hero = new Orc(heroName, magicka, fatigue, health);
                                        break;
                                    case "Breton":
                                        hero = new Breton(heroName, magicka, fatigue, health);
                                        break;
                                    case "Dunmer":
                                        hero = new Dunmer(heroName, magicka, fatigue, health);
                                        break;

                                }
                                result = this.province.getGuildByName(guildName).addHero(hero);
                            }
                        }
                    }
                    break;
                case "ADD_GUILD:": {
                    String guildName = params[1];
                    if (this.province.contains(guildName)) {
                        result = "Guild already exists.";
                    } else {
                        Guild guild = new GuildImpl(guildName);
                        this.province.addGuild(guild);
                        result = String.format("Added Guild: %s", guildName);
                    }
                }
                break;
                case "DETAILS:": {
                    String guildName = params[1];
                    if (params.length == 2) {
                        if (!this.province.contains(guildName)) {
                            result = String.format("Guild [%s] does not exist.", guildName);
                        } else {
                            result = this.province.getGuildByName(guildName).toString();
                        }
                    } else {
                        String heroName = params[2];
                        if (!this.province.contains(guildName)) {
                            result = String.format("Guild [%s] does not exist.", guildName);
                        } else {
                            if (!this.province.getGuildByName(guildName).getHeroes().containsKey(heroName)) {
                                result = "No such hero in this guild!";
                            } else {
                                result = this.province.getGuildByName(guildName).getHeroByName(heroName).toString();
                            }
                        }
                    }
                }
                break;
                case "REMOVE:":
                    String guildName = params[1];
                    if (params.length == 2) {
                        if (!this.province.contains(guildName)) {
                            result = String.format("Guild [%s] does not exist.", guildName);
                        } else {
                            result = this.province.removeGuild(this.province.getGuildByName(guildName));
                        }
                    } else {
                        String heroName = params[2];
                        if (!this.province.contains(guildName)) {
                            result = String.format("Guild [%s] does not exist.", guildName);
                        } else {
                            if (!this.province.getGuildByName(guildName).getHeroes().containsKey(heroName)) {
                                result = "No such hero in this guild!";
                            } else {
                                result = this.province.getGuildByName(guildName)
                                        .removeHero(this.province.getGuildByName(guildName).getHeroByName(heroName));
                            }
                        }
                    }
                    break;
                case "FIGHT:":
                    String guild1Name = params[1];
                    String hero1Name = params[2];
                    String guild2Name = params[3];
                    String hero2Name = params[4];

                    if (guild1Name.equals(guild2Name)) {
                        result = "Heroes from the same guild can not fight each other.";
                    } else {
                        Hero hero1 = this.province.getGuildByName(guild1Name).getHeroByName(hero1Name);
                        Hero hero2 = this.province.getGuildByName(guild2Name).getHeroByName(hero2Name);

                        if (hero1 == null || hero2 == null) {
                            result = "No such hero in this guild!";
                        } else {
                            Hero attacker;
                            Hero target;

                            if (hero1.getHealth() == hero2.getHealth()) {
                                if (hero1.getOffense() <= hero2.getOffense()) {
                                    attacker = hero1;
                                    target = hero2;
                                } else {
                                    attacker = hero2;
                                    target = hero1;
                                }
                            } else if (hero1.getHealth() < hero2.getHealth()) {
                                attacker = hero1;
                                target = hero2;
                            } else {
                                attacker = hero2;
                                target = hero1;
                            }

                            while (true) {
                                attacker.attack(target);
                                if (target.isDead()) {
                                    result = String.format("Hero %s sliced hero %s.", attacker.getName(), target.getName());
                                    if (target.getName().equals(hero1Name)) {
                                        this.province.getGuildByName(guild1Name).removeHero(target);
                                    } else {
                                        this.province.getGuildByName(guild2Name).removeHero(target);
                                    }
                                    break;
                                }
                                Hero tempHero = attacker;
                                attacker = target;
                                target = tempHero;
                            }
                        }
                    }
                    break;
                case "END":
                    StringBuilder sb = new StringBuilder();

                    for (Province prov : this.provinces.values()) {
                        sb.append(prov.toString());
                    }
                    result = sb.toString();
                    break;
            }

            if (result != null) {
                writer.write(result);
                if ("END".equals(command)) {
                    return;
                }
                writer.write("\n---\n");
            }

        }
    }
}
