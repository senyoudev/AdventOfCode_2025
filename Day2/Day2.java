package Day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Day2 {

    private static Optional<List<String>> readInput(String filename) {
        Path path = Path.of(filename);
        try {
            return Optional.of(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static long findInvalidIds_part1(List<String> lines) {
        long sumOfInvalidIds = 0;
        for (String line : lines) {
            String[] IdRanges = line.split(",");
            for (String idRange : IdRanges) {
                String firstId = idRange.split("-")[0];
                String secondId = idRange.split("-")[1];
                for (long id = Long.parseLong(firstId); id <= Long.parseLong(secondId); id++) {
                    int length = String.valueOf(id).length();
                    // check if it's same value repeated
                    if (String.valueOf(id).substring(0, length / 2).equals(String.valueOf(id).substring(length / 2))) {
                        sumOfInvalidIds += id;
                    }
                }
            }
        }
        return sumOfInvalidIds;
    }

    private static boolean isValid(long id, int length) {
        // Check if it's same value repeated twice or more
        String s = String.valueOf(id);

        for (int k = 1; k <= length / 2; k++) {
            if (length % k == 0) {
                int reps = length / k;
                String elem = s.substring(0, k);

                String repeated = elem.repeat(reps);
                if (repeated.equals(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static long findInvalidIds_part2(List<String> lines) {
        long sumOfInvalidIds = 0;
        for (String line : lines) {
            String[] IdRanges = line.split(",");
            for (String idRange : IdRanges) {
                String firstId = idRange.split("-")[0];
                String secondId = idRange.split("-")[1];
                for (long id = Long.parseLong(firstId); id <= Long.parseLong(secondId); id++) {
                    int length = String.valueOf(id).length();
                    // check if it's same value repeated twice or more
                    if (isValid(id, length)) {
                        sumOfInvalidIds += id;
                    }
                }
            }
        }
        return sumOfInvalidIds;
    }

    public static void main(String[] args) {
        Optional<List<String>> linesOptional = readInput("day2_input.txt");
        if (linesOptional.isPresent()) {
            long sum = findInvalidIds_part1(linesOptional.get());
            System.out.println("Invalid Ids for part 1: " + sum);
            sum = findInvalidIds_part2(linesOptional.get());
            System.out.println("Invalid Ids for part 2: " + sum);
        }
    }
}
