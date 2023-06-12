package info.kgeorgiy.ja.popov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class HelloUDPNonblockingServer implements HelloServer {

    private Selector selector;
    private DatagramChannel channel;
    private ExecutorService processors;
    private ExecutorService mainProcess;
    private BlockingQueue<Data> receiveDataQueue;
    private BlockingQueue<Data> sendDataQueue;

    @Override
    public void start(int port, int threads) {
        int bufferSize;
        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            bufferSize = channel.socket().getReceiveBufferSize();
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        processors = Executors.newFixedThreadPool(threads);
        receiveDataQueue = new LinkedBlockingQueue<>();
        sendDataQueue = new LinkedBlockingQueue<>();
        IntStream.range(0, threads).forEach(i ->
                receiveDataQueue.add(new Data(null, bufferSize)));

        mainProcess = Executors.newSingleThreadExecutor();
        mainProcess.submit(this::mainProcess);
    }

    private void mainProcess() {
        while (!Thread.interrupted() && !channel.socket().isClosed()) {
            try {
                selector.select(key -> {
                    if (key.isWritable()) {
                        send(key);
                    }
                    if (key.isReadable()) {
                        receive(key);
                    }
                });
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void send(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Data data = sendDataQueue.remove();
        key.interestOpsAnd(~SelectionKey.OP_WRITE);

        processors.submit(() -> {
            try {
                channel.send(data.buffer.flip(), data.address);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            synchronized (key) {
                receiveDataQueue.add(data);
                key.interestOpsOr(SelectionKey.OP_READ);
                selector.wakeup();
            }
        });
    }

    private void receive(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Data data = receiveDataQueue.remove();
        key.interestOpsAnd(~SelectionKey.OP_READ);
        data.readyToRead();

        processors.submit(() -> {
            try {
                data.address = channel.receive(data.buffer);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            synchronized (key) {
                sendDataQueue.add(data);
                key.interestOpsOr(SelectionKey.OP_WRITE);
                selector.wakeup();
            }
        });
    }

    @Override
    public void close() {
        try {
            processors.shutdown();
            mainProcess.shutdown();
            selector.close();
            channel.close();
            processors.awaitTermination(Util.TERMINATION_TIMEOUT, TimeUnit.SECONDS);
            mainProcess.awaitTermination(Util.TERMINATION_TIMEOUT, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Util.runServer(new HelloUDPNonblockingServer(), args);
    }
}
