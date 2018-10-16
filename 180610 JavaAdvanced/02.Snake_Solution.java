import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExamJavaFund180610_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader console =
                new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(console.readLine());

        String[] commands = console.readLine().split(", ");

        int length = 1;
        int rowPos = 0;
        int colPos = 0;

        String[][] matrix = new String[n][n];
        for (int row = 0; row < n; row++) {
            String[] line = console.readLine().split(" ");
            for (int col = 0; col < n; col++) {
                matrix[row][col] = line[col];
                if ("s".equals(line[col])) {
                    rowPos = row;
                    colPos = col;
                }
            }
        }

        for (int i = 0; i < commands.length; i++) {
            if ("up".equals(commands[i])) {
                if (rowPos == 0) rowPos = n - 1;
                else rowPos -= 1;
            } else if ("down".equals(commands[i])) {
                if (rowPos == n - 1) rowPos = 0;
                else rowPos += 1;
            } else if ("right".equals(commands[i])) {
                if (colPos == n - 1) colPos = 0;
                else colPos += 1;
            } else if ("left".equals(commands[i])) {
                if (colPos == 0) colPos = n - 1;
                else colPos -= 1;
            }

            if ("e".equals(matrix[rowPos][colPos])) {
                System.out.println("You lose! Killed by an enemy!");
                return;
            } else if ("f".equals(matrix[rowPos][colPos])) {
                length += 1;
                matrix[rowPos][colPos] = "*";
                if (foodExists(matrix) == 0) {
                    System.out.printf("You win! Final snake length is %d", length);
                    return;
                }
            }
        }

        if (foodExists(matrix) > 0) {
            System.out.printf("You lose! There is still %d food to be eaten.", foodExists(matrix));
        }
    }

    private static int foodExists(String[][] matrix) {
        int food = 0;
        for (String[] rows : matrix) {
            for (String col : rows) {
                if ("f".equals(col)) food++;
            }
        }
        return food;
    }
}

