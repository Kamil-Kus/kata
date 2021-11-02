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

    IntStream findRepetitions(int minimal_occurrence) {
        final Map<Integer, Long> collect = stream().collect(Collectors.groupingBy(value -> value, Collectors.counting()));
        final Set<Map.Entry<Integer, Long>> entries = collect.entrySet();
        return entries
            .stream()
            .filter(key -> key.getValue() >= 2)
            .mapToInt(Map.Entry::getKey);
    }

    int getSumOfSpecificValue(int i) {
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
            .distinct().count();

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

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;
        if (tallies[0] == 1 &&
            tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1)
            return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;
        if (tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1
            && tallies[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;


        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 2) {
                _2 = true;
                _2_at = i + 1;
            }

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 3) {
                _3 = true;
                _3_at = i + 1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
}



