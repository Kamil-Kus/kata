import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


class DiceHand implements Iterable<Integer> {
    private final List<Integer> valuesOfDices = new ArrayList<>();

    public DiceHand(int d1, int d2, int d3, int d4, int d5) {
        valuesOfDices.add(d1);
        valuesOfDices.add(d2);
        valuesOfDices.add(d3);
        valuesOfDices.add(d4);
        valuesOfDices.add(d5);
    }

    public IntStream findRepetitions(int minimal_occurrence) {
        final Map<Integer, Long> collect = extractResultGroupedByOccurrence();
        final Set<Map.Entry<Integer, Long>> entries = collect.entrySet();
        return entries
            .stream()
            .filter(key -> key.getValue() >= minimal_occurrence)
            .mapToInt(Map.Entry::getKey);
    }

    public Map<Integer, Long> extractResultGroupedByOccurrence() {
        return stream().collect(Collectors.groupingBy(value -> value, Collectors.counting()));
    }

    public int getSumOfSpecificValue(int i) {
        return stream()
            .filter(value -> value.equals(i))
            .mapToInt(Integer::intValue)
            .sum();
    }

    @Override
    public Iterator<Integer> iterator() {
        return this.valuesOfDices.iterator();
    }

    public Stream<Integer> stream() {
        return valuesOfDices.stream();
    }
}

public class Yatzy {

    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        return d1 + d2 + d3 + d4 + d5;
    }

    public static int yatzy(DiceHand dice) {
        final long countOfUniqueValues = dice
            .stream()
            .distinct()
            .count();

        if (countOfUniqueValues == 1) {
            return 50;
        }
        return 0;
    }

    public static int ones(DiceHand diceHand) {
        return diceHand.getSumOfSpecificValue(1);
    }

    public static int twos(DiceHand diceHand) {
        return diceHand.getSumOfSpecificValue(2);
    }

    public static int threes(DiceHand diceHand) {
        return diceHand.getSumOfSpecificValue(3);
    }

    public int fours(DiceHand diceHand) {
        return diceHand.getSumOfSpecificValue(4);
    }

    public int fives(DiceHand diceHand) {
        return diceHand.getSumOfSpecificValue(5);
    }

    public int sixes(DiceHand diceHand) {
        return diceHand.getSumOfSpecificValue(6);
    }

    public static int score_pair(DiceHand diceHand) {
        final OptionalInt max = diceHand.findRepetitions(2)
            .max();
        return max.orElse(0) * 2;
    }

    public static int two_pair(DiceHand diceHand) {
        final int sum = diceHand.findRepetitions(2)
            .sum();
        return sum * 2;
    }

    public static int three_of_a_kind(DiceHand diceHand) {
        final IntStream twoOrMoreOccurrence = diceHand.findRepetitions(3);
        return twoOrMoreOccurrence.findFirst().orElse(0) * 3;
    }

    public static int four_of_a_kind(DiceHand diceHand) {
        return diceHand.findRepetitions(4).findFirst().orElse(0) * 4;
    }

    public static int smallStraight(DiceHand diceHand) {
        final List<Integer> collect = diceHand.stream().distinct().collect(Collectors.toList());
        if (collect.size() == 5 && Collections.min(collect) == 1) {
            return 15;
        }
        return 0;
    }

    public static int largeStraight(DiceHand diceHand) {
        final List<Integer> collect = diceHand.stream().distinct().collect(Collectors.toList());
        if (collect.size() == 5 && Collections.min(collect) == 2) {
            return 20;
        }
        return 0;
    }

    public static int fullHouse(DiceHand diceHand) {
        final Map<Integer, Long> resultGroupedByOccurrence = diceHand.extractResultGroupedByOccurrence();
        if (resultGroupedByOccurrence.size() == 2) {
            return resultGroupedByOccurrence.entrySet().stream().map(p -> p.getKey() * p.getValue()).mapToInt(Long::intValue).sum();
        }
        return 0;
    }
}



