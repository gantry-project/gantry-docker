package org.gantry.apiserver.exception;


import java.util.function.Consumer;

public class ThrowableConsumer<T> {

    @FunctionalInterface
    public interface ThrowableConsumerInterface<T> {
        void accept(T t) throws Throwable;
    }

    public static <T> Consumer<T> unchecked(ThrowableConsumerInterface<T> consumer) {
        return (T t) -> {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

}
