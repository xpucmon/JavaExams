import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.ArrayDeque;
        import java.util.Deque;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class ExamJavaFund180610_1 {
    public static void main(String[] args) throws IOException {
        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(console.readLine());
        int m = Integer.parseInt(console.readLine());

        Deque<String> problems = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            problems.push(console.readLine());
        }

        Pattern pattern = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+");

        Deque<String> candidates = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            String name = console.readLine();
            Matcher matcher = pattern.matcher(name);
            while (matcher.find()) {
                if (name.equals(matcher.group(0))) {
                    candidates.offer(name);
                }
            }
        }

        while (!problems.isEmpty()) {
            String problem = problems.peek();
            String candidate = candidates.peek();

            int prodSum = 0;
            for (char c : problem.toCharArray()) {
                prodSum += c;
            }
            int candSum = 0;
            for (char c : candidate.toCharArray()) {
                candSum += c;
            }

            if (candSum > prodSum) {
                System.out.printf("%s solved %s.%n", candidate, problem);
                candidates.offer(candidates.poll());
                problems.remove();
            } else {
                System.out.printf("%s failed %s.%n", candidate, problem);
                candidates.remove(candidate);
                problems.offer(problems.pop());
            }

            if (candidates.size() == 1) {
                System.out.printf("%s gets the job!", candidates.peek());
                return;
            }
        }
        System.out.println(String.join(", ", candidates));
    }
}

