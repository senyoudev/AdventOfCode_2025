package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Day3 {

    private static Optional<List<String>> readInput(String filename) {
        Path path = Path.of(filename);
        try {
            return Optional.of(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static int findBest(String line) {
        int length = line.length();
        int[] suffixMax = new int[length];
        suffixMax[length - 1] = line.charAt(length - 1) - '0';
        for(int i = length - 2; i >= 0 ; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], line.charAt(i) - '0');
        }

        int best = 0;
        for(int left = 0; left < length - 1; left++) {
            int right = suffixMax[left + 1];
            int curr = (line.charAt(left) - '0') * 10 + (right);
            best = Math.max(best, curr);
        }
        return best;
    }

    private static int findJoltage_part1(List<String> lines) {
        int totalJoltage = 0;
        for (String line : lines) {
            int duo = findBest(line);
            totalJoltage += duo;
        }
        return totalJoltage;
    }

    public static void main(String[] args) {
        Optional<List<String>> linesOptional = readInput("day3_input.txt");
        if (linesOptional.isPresent()) {
            List<String> lines = linesOptional.get();
            int total = findJoltage_part1(lines);
            System.out.println(total);
        }
    }
}
