package info.kgeorgiy.ja.popov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPServer implements HelloServer {

    private ExecutorService processors;
    private DatagramSocket socket;

    @Override
    public void start(int port, int threads) {
        processors = Executors.newFixedThreadPool(threads);
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println(e.getMessage());
            return;
        }
        for (int i = 0; i < threads; ++i) {
            addProcessor();
        }
    }

    private void addProcessor() {
        Runnable processor = () -> {
            while (!socket.isClosed()) {
                try {
                    int len = socket.getReceiveBufferSize();
                    DatagramPacket packet = new DatagramPacket(new byte[len], len);
                    socket.receive(packet);
                    String msg = String.format("Hello, %s", new String(packet.getData(), packet.getOffset(),
                            packet.getLength(), StandardCharsets.UTF_8));
                    byte[] message = msg.getBytes(StandardCharsets.UTF_8);
                    socket.send(new DatagramPacket(message, message.length, packet.getSocketAddress()));
                } catch (IOException ignored) {
                }
            }
        };
        processors.execute(processor);
    }

    @Override
    public void close() {
        processors.shutdownNow();
        socket.close();
    }

    public static void main(String[] args) {
        Util.runServer(new HelloUDPServer(), args);
    }
}
