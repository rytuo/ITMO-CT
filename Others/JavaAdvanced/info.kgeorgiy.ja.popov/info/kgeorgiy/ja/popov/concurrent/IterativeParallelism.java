package info.kgeorgiy.ja.popov.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class IterativeParallelism implements ScalarIP {

    private final ParallelMapper mapper;

    public IterativeParallelism() {
        this.mapper = null;
    }

    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return parallel(threads, values, stream -> stream.min(comparator).orElseThrow(),
                stream -> stream.min(comparator).orElseThrow());
    }

    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return minimum(threads, values, Collections.reverseOrder(comparator));
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return parallel(threads, values, stream -> stream.anyMatch(predicate),
                stream -> stream.anyMatch(item -> item));
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !any(threads, values, Predicate.not(predicate));
    }

    private <T, R> R parallel(int threads, List<T> values,
                              Function<Stream<T>, R> f,
                              Function<Stream<R>, R> joiner) throws InterruptedException {
        if (threads <= 0) {
            throw new IllegalArgumentException("Invalid amount of threads, should be positive number");
        }
        Objects.requireNonNull(values);

        threads = Math.min(values.size(), threads);
        List<Stream<T>> tasks = divide(values, threads);
        List<R> results;

        if (this.mapper == null) {
            results = processThreads(f, tasks);
        } else {
            results = this.mapper.map(f, tasks);
        }

        return joiner.apply(results.stream());
    }

    private <T> List<Stream<T>> divide(List<T> values, int threads) {
        List<Stream<T>> tasks = new ArrayList<>();
        final int valuesPerThread = values.size() / threads;
        int extraValues = values.size() % threads;
        int start, end = 0;
        while (end < values.size()) {
            start = end;
            end = start + valuesPerThread + (extraValues-- > 0 ? 1 : 0);

            tasks.add(values.subList(start, end).stream());
        }
        return tasks;
    }

    private <T, R> List<R> processThreads(Function<Stream<T>, R> f, List<Stream<T>> tasks) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        List<R> results = new ArrayList<>(Collections.nCopies(tasks.size(), null));
        for (int j = 0; j < tasks.size(); j++) {
            threadList.add(createThread(results, j, f, tasks.get(j)));
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        return results;
    }

    private <T, R> Thread createThread(List<R> results, int i, Function<Stream<T>, R> f, Stream<T> arg) {
        Thread thread = new Thread(() ->
                results.set(i, f.apply(arg)));
        thread.start();
        return thread;
    }

    public static void main(String[] args) throws InterruptedException {
        IterativeParallelism ip = new IterativeParallelism();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            values.add(i + 1);
        }
        int res = ip.parallel(2,
                values,
                stream -> stream.min(Comparator.naturalOrder()).orElseThrow(),
                stream -> stream.max(Comparator.naturalOrder()).orElseThrow());
        System.out.println(res);
    }
}