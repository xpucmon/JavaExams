import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ExamJavaFund180610 {
    public static void main(String[] args) throws IOException {
        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));

        Map<String, String> passwords = new LinkedHashMap<>();

        String input = "";
        while (!"end of contests".equals(input = console.readLine())) {
            String[] line = input.split(":");
            if (line.length == 2) {
                String exam = line[0];
                String pass = line[1];
                passwords.put(exam, pass);
            }
        }

        Map<String, Map<String, Integer>> records = new TreeMap<>();

        while (!"end of submissions".equals(input = console.readLine())) {
            String[] line = input.split("=>");
            String exam = line[0];
            String pass = line[1];
            String name = line[2];
            int points = Integer.parseInt(line[3]);

            boolean validExamPass = false;
            for (Map.Entry<String, String> kvp : passwords.entrySet()) {
                if (exam.equals(kvp.getKey()) && pass.equals(kvp.getValue())) {
                    validExamPass = true;
                    break;
                }
            }
            if (validExamPass) {
                if (!records.containsKey(name)) {
                    records.put(name, new LinkedHashMap<>());
                }
                if (!records.get(name).containsKey(exam)) {
                    records.get(name).put(exam, points);
                }
                if (points > records.get(name).get(exam))
                    records.get(name).put(exam, points);
            }
        }
        int maxPoints = 0;
        String winner = "";
        for (Map.Entry<String, Map<String, Integer>> kvp : records.entrySet()) {
            int userTotalPoints = kvp.getValue().values().stream().reduce((x, y) -> x + y).get();
            if (userTotalPoints > maxPoints) {
                maxPoints = userTotalPoints;
                winner = kvp.getKey();
            }
        }
        System.out.printf("Best candidate is %s with total %d points.%n", winner, maxPoints);

        for (Map.Entry<String, Map<String, Integer>> record : records.entrySet()) {
            System.out.println(record.getKey());
            for (Map.Entry<String, Integer> part : record.getValue().entrySet()) {
                System.out.printf("#  %s -> %d%n", part.getKey(), part.getValue());
            }
        }
    }
}

