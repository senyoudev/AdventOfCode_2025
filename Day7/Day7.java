package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class Day7 {

    private record Position(int x, int y) {
        Position nextPos() {
            return new Position(x, y + 1);
        }
    }

    private static Position findStartPos(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == 'S') {
                    return new Position(j, i);
                }
            }
        }
        return null;
    }


    private static int calculateTotalSplits(List<String> lines, Position startPos) {
        int totalSplits = 0;
        int height = lines.size();
        int width = lines.get(0).length();

        List<Position> beams = new ArrayList<>();
        beams.add(startPos);

        Set<Position> visited = new HashSet<>();
        
        while(!beams.isEmpty()) {
            List<Position> newBeams = new ArrayList<>();
            for(Position beam : beams) {
               int x = beam.x;
               int y = beam.y;
              
               if(x < 0 || x >= lines.get(0).length() || y < 0 || y >= lines.size()) {
                   continue;
               }

               char c = lines.get(y).charAt(x);
               if(c == '^') {
                totalSplits++;
                
                // add positions
                if (x - 1 >= 0 && visited.add(new Position(x - 1, y + 1)))
                    newBeams.add(new Position(x - 1, y + 1));
                if (x + 1 < width && visited.add(new Position(x + 1, y + 1)))
                    newBeams.add(new Position(x + 1, y + 1));
               } else if (c == '.' || c == 'S') {
                   Position down = beam.nextPos();
                   if (visited.add(down)) {
                       newBeams.add(down);
                   }
               }
            }

            beams = newBeams;
        }

        return totalSplits;
    }

    private static long countTimelinesDP(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();
        Long[][] memo = new Long[height][width];

        return countFrom(lines, 0, lines.get(0).indexOf('S'), memo);
    }

    private static long countFrom(List<String> lines, int y, int x, Long[][] memo) {
        int height = lines.size();
        int width = lines.get(0).length();

        if (y >= height || x < 0 || x >= width)
            return 1;

        if (memo[y][x] != null)
            return memo[y][x];

        char c = lines.get(y).charAt(x);
        long total = 0;

        if (c == '^') {
            total += countFrom(lines, y + 1, x - 1, memo);
            total += countFrom(lines, y + 1, x + 1, memo);
        } else if (c == '.' || c == 'S') {
            total += countFrom(lines, y + 1, x, memo);
        }

        memo[y][x] = total;
        return total;
    }

    public static void main(String[] args) {
        Optional<List<String>> linesOptional = readInput("day7_input.txt");
        if (linesOptional.isPresent()) {
            List<String> lines = linesOptional.get();
            Position startPos = findStartPos(lines);

            int totalSplits = calculateTotalSplits(lines, startPos);
            System.out.println(totalSplits);

            long totalTimelines = countTimelinesDP(lines);
            System.out.println(totalTimelines);
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
}
