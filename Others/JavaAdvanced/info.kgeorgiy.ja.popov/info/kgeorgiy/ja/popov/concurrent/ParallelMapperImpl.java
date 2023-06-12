package info.kgeorgiy.ja.popov.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;

public class ParallelMapperImpl implements ParallelMapper {

    private final List<Thread> workers;
    private final Queue<Runnable> tasks;

    public ParallelMapperImpl(int n) throws InterruptedException {
        if (n <= 0) {
            throw new InterruptedException("Invalid thread number");
        }
        tasks = new ArrayDeque<>();
        workers = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            Thread thread = new Thread(() -> {
                while (!Thread.interrupted()) {
                    Runnable task;
                    try {
                        synchronized (tasks) {
                            while (tasks.isEmpty()) {
                                    tasks.wait();
                            }
                            task = tasks.poll();
                        }
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.start();
            workers.add(thread);
        }
    }

    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        List<R> results = new ArrayList<>(Collections.nCopies(args.size(), null));
        Counter counter = new Counter(args.size());
        for (int i = 0; i < args.size(); ++i) {
            addTask(results, i, f, args.get(i), counter);
        }
        synchronized (counter) {
            while (!counter.finished()) {
                counter.wait();
            }
        }
        return results;
    }

    private <T, R> void addTask(List<R> results, int i, Function<T, ? extends R> f, T arg, Counter counter) {
        synchronized (tasks) {
            tasks.add(() -> {
                results.set(i, f.apply(arg));
                synchronized (counter) {
                    counter.tick();
                    if (counter.finished()) {
                        counter.notify();
                    }
                }
            });
            tasks.notifyAll();
        }
    }

    @Override
    public void close() {
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }
}
