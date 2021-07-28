package com.yaroslavtir;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static Map<String, Integer> generateMap(Integer count) {

        return IntStream.range(1, count).boxed()
                .collect(Collectors.toMap(
                        index -> String.format("field_%s", index),
                        Function.identity()
                ));
    }
}
