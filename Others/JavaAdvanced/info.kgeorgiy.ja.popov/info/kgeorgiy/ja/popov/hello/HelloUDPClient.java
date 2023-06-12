package info.kgeorgiy.ja.popov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPClient implements HelloClient {

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        final ExecutorService senders = Executors.newFixedThreadPool(threads);
        final SocketAddress socketAddress = new InetSocketAddress(host, port);
        for (int i = 0; i < threads; i++) {
            addSender(senders, socketAddress, prefix, i, requests);
        }
        close(senders);
    }

    private void addSender(ExecutorService senders, SocketAddress socketAddress, String prefix, int threadNumber, int requests) {
        final Runnable task = () -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(100); // :NOTE: move to a const value
                for (int i = 0; i < requests; i++) {
                    String requestMessage = String.format("%s%d_%d", prefix, threadNumber, i);
                    byte[] requestBytes = requestMessage.getBytes(StandardCharsets.UTF_8);
                    DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, socketAddress);
                    while (!socket.isClosed()) {
                        try {
                            socket.send(request);
                            int len = socket.getReceiveBufferSize();
                            DatagramPacket response = new DatagramPacket(new byte[len], len);
                            socket.receive(response);
                            String responseMessage = new String(response.getData(), response.getOffset(),
                                    response.getLength(), StandardCharsets.UTF_8);
                            if (responseMessage.contains(requestMessage)) {
                                break;
                            }
                        } catch (SocketTimeoutException ignored) {
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            } catch (SocketException e) {
                System.err.println(e.getMessage());
            }
        };
        senders.execute(task);
    }

    private void close(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args) {
        Util.runClient(new HelloUDPClient(), args);
    }
}
