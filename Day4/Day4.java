package Day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Day4 {
    private static Optional<List<String>> readInput(String filename) {
        Path path = Path.of(filename);
        try {
            return Optional.of(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static int findRollsOfPaper_part1(char[][] matrix) {
        int total = 0;
        int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

        // loop through the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != '@')
                    continue;
                int count = 0;
                for (int k = 0; k < 8; k++) {
                    int ni = i + dr[k];
                    int nj = j + dc[k];
                    if(ni >= 0 && nj >= 0 && ni < matrix.length && nj < matrix[i].length && matrix[ni][nj] == '@') {
                        count++;
                    }
                }
                if(count < 4) {
                    total++;
                }
            }
        }

        return total;
    }

    private record rollPapers(int total, char[][] matrix) {}

    private static rollPapers findRollsOfPaper_part2(char[][] matrix) {
        int total = 0;
        int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

        // loop through the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != '@')
                    continue;
                int count = 0;
                for (int k = 0; k < 8; k++) {
                    int ni = i + dr[k];
                    int nj = j + dc[k];
                    if (ni >= 0 && nj >= 0 && ni < matrix.length && nj < matrix[i].length && matrix[ni][nj] == '@') {
                        count++;
                    }
                }
                if (count < 4) {
                    matrix[i][j] = 'x';
                    total++;
                }
            }
        }

        return new rollPapers(total, matrix);
    }

    public static void main(String[] args) {
        Optional<List<String>> linesOptional = readInput("day4_input.txt");
        if (linesOptional.isPresent()) {
            List<String> lines = linesOptional.get();
            int rows = lines.size();
            int cols = lines.get(0).length();
            char[][] matrix = new char[rows][cols];

            for (int i = 0; i < rows; i++) {
                matrix[i] = lines.get(i).toCharArray();
            }

            int total = findRollsOfPaper_part1(matrix);
            System.out.println("Total rolls of paper for part1: " + total);

            rollPapers roll;
            int total2 = 0;
            do {
                roll = findRollsOfPaper_part2(matrix);
                total2 += roll.total();
            } while (roll.total() > 0);
            System.out.println("Total rolls of paper for part2: " + total2);
        }
    }
}
