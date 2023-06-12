package info.kgeorgiy.ja.popov.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class WebCrawler implements Crawler {

    final private Downloader downloader;
    final private ExecutorService loaders;
    final private ExecutorService extractors;

    public WebCrawler(final Downloader downloader, final int downloads, final int extractors, final int perHost) {
        this.downloader = downloader;
        this.loaders = Executors.newFixedThreadPool(downloads);
        this.extractors = Executors.newFixedThreadPool(extractors);
    }

    @Override
    public Result download(final String url, final int depth) {
        final Set<String> pages = ConcurrentHashMap.newKeySet();
        final Map<String, IOException> errors = new ConcurrentHashMap<>();
        Set<String> level = ConcurrentHashMap.newKeySet();
        level.add(url);
        for (int i = 1; i <= depth; ++i) {
            pages.addAll(level);
            level = downloadLevel(level, pages, errors);
        }
        pages.removeAll(errors.keySet());
        return new Result(new ArrayList<>(pages), errors);
    }

    private Set<String> downloadLevel(final Set<String> level, final Set<String> pages, final Map<String, IOException> errors) {
        final Set<String> next = ConcurrentHashMap.newKeySet();
        final CountDownLatch latch = new CountDownLatch(level.size());
        for (final String url : level) {
            loaders.submit(() -> {
                try {
                    final Document document = downloader.download(url);
                    extractors.submit(() -> {
                        try {
                            next.addAll(document.extractLinks());
                        } catch (final IOException e) {
                            errors.put(url, e);
                        } finally {
                            latch.countDown();
                        }
                    });
                } catch (final IOException e) {
                    errors.put(url, e);
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (final InterruptedException ignored) {
        }
        next.removeAll(pages);
        return next;
    }

    @Override
    public void close() {
        loaders.shutdown();
        extractors.shutdown();
        while(true) {
            try {
                if (loaders.awaitTermination(5, TimeUnit.SECONDS) &&
                        extractors.awaitTermination(5, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        if (args == null || args.length < 1 || args.length > 5 || args[0] == null) {
            System.out.println("Usage:\n\tWebCrawler url [depth [downloads [extractors [perHost]]]]");
            return;
        }

        final int[] intArgs = new int[args.length - 1];
        for (int i = 0; i < intArgs.length; ++i) {
            try {
                intArgs[i] = Math.max(Integer.parseInt(args[i + 1]), 1);
            } catch (final NumberFormatException e) {
                intArgs[i] = 1;
            }
        }

        final Crawler crawler = new WebCrawler(new CachingDownloader(), intArgs[1], intArgs[2], intArgs[3]);
        final Result result = crawler.download(args[0], intArgs[0]);
        for (final String page : result.getDownloaded()) {
            System.out.println(page);
        }
    }
}
