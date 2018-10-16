package interfaces;

public interface Province {

    String getName();

    String addGuild(Guild guild);

    String removeGuild(Guild guild);

    Guild getGuildByName(String guildName);

    boolean contains(String guildName);

}
