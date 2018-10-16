package onehitdungeon;

import onehitdungeon.core.DungeonManagerImpl;
import onehitdungeon.interfaces.DungeonManager;
import onehitdungeon.interfaces.InputReader;
import onehitdungeon.interfaces.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine implements Runnable {
    private InputReader reader;
    private OutputWriter writer;
    private DungeonManager dungeonManager;

    public Engine(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
        this.dungeonManager = new DungeonManagerImpl();
    }

    @Override
    public void run() {

        while (true) {
            String[] cmdArgs = reader.readLine().split("\\s+", 2);
            String commandName = cmdArgs[0];

            List<String> commandParams = new ArrayList<>();

            if (cmdArgs.length > 1){
                commandParams = Arrays.asList(cmdArgs[1].split("\\s+"));
            }

            String result = "";
            switch (commandName) {
                case "Hero":
                    result = dungeonManager.hero(commandParams);
                    break;
                case "Select":
                    result = dungeonManager.select(commandParams);
                    break;
                case "Status":
                    result = dungeonManager.status(commandParams);
                    break;
                case "Fight":
                    result = dungeonManager.fight(commandParams);
                    break;
                case "Advance":
                    result = dungeonManager.advance(commandParams);
                    break;
                case "Train":
                    result = dungeonManager.train(commandParams);
                    break;
                case "Quit":
                    result = dungeonManager.quit(commandParams);
                    break;
            }

            if (result != ""){
                writer.println(result);
            }

            if ("Quit".equals(commandName)){
                return;
            }
        }
    }
}
