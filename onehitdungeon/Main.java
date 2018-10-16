package onehitdungeon;

import onehitdungeon.interfaces.InputReader;
import onehitdungeon.interfaces.OutputWriter;
import onehitdungeon.io.ConsoleReader;
import onehitdungeon.io.ConsoleWriter;

public class Main {
    public static void main(String[] args) {
        InputReader reader = new ConsoleReader();
        OutputWriter writer = new ConsoleWriter();

        Runnable engine = new Engine(reader, writer);

        engine.run();
    }
}
