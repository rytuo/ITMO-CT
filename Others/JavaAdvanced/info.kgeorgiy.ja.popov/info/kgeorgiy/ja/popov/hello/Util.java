package info.kgeorgiy.ja.popov.hello;


import info.kgeorgiy.java.advanced.hello.HelloClient;
import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.nio.charset.StandardCharsets;

public class Util {
    static final int TIMEOUT = 100;
    static final int TERMINATION_TIMEOUT = 1;
    static final int responsePosition = "Hello, ".getBytes(StandardCharsets.UTF_8).length;

    static void runServer(HelloServer server, String[] args) {
        final int threads, port;
        try {
            threads = Math.max(Integer.parseInt(args[0]), 1);
            port = Math.max(Integer.parseInt(args[1]), 0);
        } catch (NumberFormatException e) {
            System.out.println("Usage:%n\tHelloServer threads port");
            return;
        }

        server.start(port, threads);
    }

    static void runClient(HelloClient client, String[] args) {
        final String host = args[0],
                prefix = args[2];
        final int port, threads, requests;
        try {
            port = Math.max(Integer.parseInt(args[1]), 0);
            threads = Math.max(Integer.parseInt(args[3]), 1);
            requests = Math.max(Integer.parseInt(args[4]), 1);
        } catch (NumberFormatException e) {
            System.out.println("Usage:%n\tHelloClient host port prefix threads requests");
            return;
        }

        client.run(host, port, prefix, threads, requests);
    }
}
