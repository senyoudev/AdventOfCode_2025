import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

class Day1 {

    private static final int MAX_DIAL = 100;
    private record Move(char dir, int steps) {
        public static Optional<Move> parse(String line) {
            if(line == null || line.length() < 2) {
                return Optional.empty();
            }

            try {
                char dir = line.charAt(0);
                int steps = Integer.parseInt(line.substring(1));
                if (dir == 'R' || dir == 'L') {
                    return Optional.of(new Move(dir, steps));
                }
            } catch(NumberFormatException e) {
                // Ignore it
            } 
            return Optional.empty();
        }
    }

    private static Optional<List<String>> readInput(String filename) {
        Path path = Path.of(filename);
        try {
            return Optional.of(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static int findPassword_part1(List<String> lines) {
        int password = 0;
        int currentDialPosition = 50;

        for(String line : lines) {
            Optional<Move> maybeMove = Move.parse(line);
            if(maybeMove.isPresent()) {
                Move move = maybeMove.get();
                int change = move.dir() == 'R' ? move.steps() : -move.steps();

                currentDialPosition = (currentDialPosition + change) % MAX_DIAL;
                if(currentDialPosition < 0) {
                    currentDialPosition += MAX_DIAL;
                }

                if(currentDialPosition == 0) password ++;
            }
        }
        return password;
    }

    /**
     * To address the part2, we actually don't care about the dial position, but about the number of times we cross the border
     * of the dial. we count the number of times we cross the border and return it.
     */
    private static int findPassword_part2(List<String> lines) {
        int password = 0;
        int currentDialPosition = 50;

        for (String line : lines) {
            Optional<Move> maybeMove = Move.parse(line);
            if (maybeMove.isPresent()) {
                Move move = maybeMove.get();
                int change = move.dir() == 'R' ? move.steps() : -move.steps();
                int nextPosition = currentDialPosition + change;

                if (change > 0) {
                    password += Math.floorDiv(nextPosition, MAX_DIAL) - Math.floorDiv(currentDialPosition, MAX_DIAL);
                } else {
                    password += Math.floorDiv(currentDialPosition - 1, MAX_DIAL)
                            - Math.floorDiv(nextPosition - 1, MAX_DIAL);
                }

                currentDialPosition = nextPosition;
            }
        }
        return password;
    }

    public static void main(String[] args) {
        Optional<List<String>> linesOptional = readInput("day1_input.txt");

        if (linesOptional.isPresent()) {
            int password = findPassword_part1(linesOptional.get());
            int seCondPassword = findPassword_part2(linesOptional.get());
            System.out.println("Password Count (Part 1): " + password);
            System.out.println("Password Count (Part 2): " + seCondPassword);
        } else {
            System.out.println("Could not run puzzle. Input file not available.");
        }
    }
}