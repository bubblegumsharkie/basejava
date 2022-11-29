package org.resumebase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {

    /*

    Реализовать метод через стрим int minValue(int[] values).
    Метод принимает массив цифр от 1 до 9, надо выбрать уникальные
    и вернуть минимально возможное число, составленное из этих уникальных цифр.
    Не использовать преобразование в строку и обратно.
    Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89

    Реализовать метод List<Integer> oddOrEven(List<Integer> integers).
    Если сумма всех чисел нечетная — удалить все нечетные, если четная — удалить все четные.
    Сложность алгоритма должна быть O(N).
    Optional - решение в один стрим.

    */

    public static void main(String[] args) {

        System.out.println(minValue(new int[]{10, 30, 20, 30, 20, 30}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5)));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 4, 5, 6, 7)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
        /*
        return IntStream
                .range(0, (int) Arrays.stream(values).distinct().count())
                .boxed()
                .sorted(Comparator.reverseOrder())
                .map(index -> (
                                (Math.pow(10, index)) * (Integer) Arrays.stream(values)
                                        .boxed()
                                        .sorted(Comparator.reverseOrder())
                                        .distinct()
                                        .toArray()[index]
                        )
                )
                .mapToInt(Double::intValue)
                .sum();
         */
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .filter(
                        integer ->
                                integer % 2 != (
                                        integers.stream()
                                                .mapToInt(Integer::intValue)
                                                .sum() % 2
                                )
                ).collect(Collectors.toList());
    }

}
