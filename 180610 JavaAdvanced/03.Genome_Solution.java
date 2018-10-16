import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.LinkedHashMap;
        import java.util.Map;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class ExamJavaFund180610_3 {
    public static void main(String[] args) throws IOException {
        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));

        Pattern pattern = Pattern.compile("([A-Za-z!@#$?]+)=(\\d+)--(\\d+)<<([a-z]+)");

        Map<String, Integer> register = new LinkedHashMap<>();

        String input = "";
        while (!"Stop!".equals(input = console.readLine())) {
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()){
                if (matcher.groupCount() == 4){
                    String[] geneTemp = matcher.group(1).split("[!@#$?]");
                    int length = Integer.parseInt(matcher.group(2));
                    int genesCount = Integer.parseInt(matcher.group(3));
                    String host = matcher.group(4);

                    StringBuilder sb = new StringBuilder();
                    for (String ch : geneTemp) {
                        sb.append(ch);
                    }
                    String name = sb.toString();

                    if (length == name.length()){
                        if (!register.containsKey(host)){
                            register.put(host, genesCount);
                        } else register.put(host, genesCount + register.get(host));
                    }
                }
            }
        }
        register.entrySet().stream()
                .sorted((x, y) -> y.getValue().compareTo(x.getValue()))
                .forEach(pair -> System.out.printf("%s has genome size of %d%n", pair.getKey(), pair.getValue()));
    }
}