package Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Day5 {

    private record Range(long start, long end) implements Comparable<Range> {
        boolean contains(String numberStr) {
            try {
                long number = Long.parseLong(numberStr);
                return number >= start && number <= end;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public int compareTo(Range other) {
            return Long.compare(this.start, other.start);
        }

        boolean overlaps(Range other) {
            return this.start <= other.end && this.end >= other.start;
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

    private static Set<Range> extractRanges(List<String> lines) {
        Set<Range> ranges = new HashSet<>();
        for (String line : lines) {
            if (line.isBlank()) {
                return ranges;
            }
            String[] item = line.split("-");
            String firstItem = item[0];
            String secondItem = item[1];
            ranges.add(new Range(Long.parseLong(firstItem), Long.parseLong(secondItem)));
        }
        return ranges;
    }

    private static List<String> extractIds(List<String> lines) {
        List<String> ids = new ArrayList<>();
        boolean notBlankYet = true;
        for (String line : lines) {
            if (line.isBlank()) {
                notBlankYet = false;
            }
            if (!notBlankYet && !line.isBlank()) {
                ids.add(line);
            }
        }
        return ids;
    }

    private static int findFreshElements_part1(Set<Range> ranges, List<String> numbers) {
        int freshElements = 0;
        for (String number : numbers) {
            if(ranges.stream().anyMatch(range -> range.contains(number))) {
                freshElements++;
            }
        }
        return freshElements;
    }

    private static long findConsideredFreshElements_part2(Set<Range> ranges, List<String> numbers) {
        long freshElements = 0;
        for (Range range : ranges) {
            freshElements += (range.end() - range.start() + 1);
        }
        return freshElements;
    }

    private static Set<Range> mergeRanges(Set<Range> ranges) {
        List<Range> sortedRanges = new ArrayList<>(ranges);
        sortedRanges.sort(Range::compareTo);
        Set<Range> mergedRanges = new HashSet<>();

        // First Range
        Range currentRange = sortedRanges.get(0);
        for(int i = 1; i < sortedRanges.size(); i++) {
            Range nextRange = sortedRanges.get(i);

            if(currentRange.overlaps(nextRange)) {
                long newEnd = Math.max(currentRange.end(), nextRange.end());
                currentRange = new Range(currentRange.start(), newEnd);
            } else {
                mergedRanges.add(currentRange);
                currentRange = nextRange;
            }
        }
        mergedRanges.add(currentRange);
        return mergedRanges;
    }

    public static void main(String[] args) {
        Optional<List<String>> input = readInput("day5_input.txt");
        if (input.isPresent()) {
            List<String> lines = input.get();
            Set<Range> ranges = extractRanges(lines);
            List<String> numbers = extractIds(lines);
            int freshElements = findFreshElements_part1(ranges, numbers);
            System.out.println("Fresh elements: " + freshElements);

            Set<Range> mergedRanges = mergeRanges(ranges);

            long consideredFreshElements = findConsideredFreshElements_part2(mergedRanges, numbers);
            System.out.println("Considered fresh elements: " + consideredFreshElements);
        }
    }
}
