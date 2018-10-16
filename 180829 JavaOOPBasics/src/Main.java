import io.ConsoleReader;
import io.ConsoleWriter;
import io.Reader;
import io.Writer;

public class Main {
    public static void main(String[] args) {
        Reader reader = new ConsoleReader();
        Writer writer = new ConsoleWriter();

        Runnable engine = new Engine(reader, writer);

        engine.run();
    }
}
