package com.api.btg.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    public static <T> UnaryOperator<T> peek(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return t;
        };
    }
}
