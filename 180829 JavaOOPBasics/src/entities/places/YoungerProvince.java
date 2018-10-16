package entities.places;

import interfaces.Guild;
import interfaces.Province;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class YoungerProvince implements Province {
    Map<String, Guild> guilds;
    private String name;

    public YoungerProvince(String name) {
        this.guilds = new LinkedHashMap<>();
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Map<String, Guild> getGuilds() {
        return Collections.unmodifiableMap(this.guilds);
    }

    @Override
    public String addGuild(Guild guild) {
        this.guilds.put(guild.getName(), guild);
        return String.format("Added Guild: %s", guild.getName());
    }

    @Override
    public String removeGuild(Guild guild) {
        this.guilds.remove(guild.getName());
        return String.format("Removed guild [%s] with %d members.", guild.getName(), guild.getGuildSize());
    }

    @Override
    public Guild getGuildByName(String guildName) {
        return this.guilds.get(guildName);
    }

    @Override
    public boolean contains(String guildName) {
        return this.guilds.containsKey(guildName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Province: " + this.getName()).append(System.lineSeparator());
        sb.append("#Guilds: ");
        if (this.getGuilds().isEmpty()) {
            sb.append("None").append(System.lineSeparator());
        } else {
            sb.append(System.lineSeparator());
            this.guilds.values().stream().sorted((g1, g2) -> (int) (g2.getGuildPower() - g1.getGuildPower()))
                    .forEach(guild -> sb.append(String
                            .format("##Guild: Name: %s, Power: %.2f, Size: %d%n",
                                    guild.getName(), guild.getGuildPower(), guild.getGuildSize())));
        }
        return sb.toString();
    }
}
