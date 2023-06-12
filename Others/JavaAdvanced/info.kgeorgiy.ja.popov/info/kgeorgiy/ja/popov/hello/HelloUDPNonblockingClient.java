package info.kgeorgiy.ja.popov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.IntStream;

public class HelloUDPNonblockingClient implements HelloClient {

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        Selector selector;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        final SocketAddress address;
        try {
            address = new InetSocketAddress(InetAddress.getByName(host), port);
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
            return;
        }
        IntStream.range(0, threads).forEach(i -> addChannel(selector, address, i));
        mainProcess(selector, address, prefix, requests);
        try {
            selector.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addChannel(Selector selector, SocketAddress address, int index) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(address);
            channel.register(selector, SelectionKey.OP_WRITE,
                    new ChannelData(index, channel.socket().getReceiveBufferSize()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void mainProcess(Selector selector, SocketAddress address, String prefix, int requests) {
        while(!Thread.interrupted() && !selector.keys().isEmpty()) {
            try {
                selector.select(Util.TIMEOUT);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            if (keys.isEmpty()) {
                selector.keys().forEach(key -> send(key, address, prefix));
            } else {
                keys.forEach(key -> {
                    send(key, address, prefix);
                    receive(key, prefix, requests);
                });
                keys.clear();
            }
        }
    }

    private void send(SelectionKey key, SocketAddress address, String prefix) {
        if (key.isWritable()) {
            DatagramChannel channel = (DatagramChannel) key.channel();
            ChannelData data = (ChannelData) key.attachment();
            String requestMessage = String.format("%s%d_%d", prefix, data.index, data.counter);

            data.buffer.clear().put(requestMessage.getBytes(StandardCharsets.UTF_8)).flip();
            try {
                channel.send(data.buffer, address);
//            System.out.println("Sent: " + requestMessage);
            } catch (IOException ignored) {
            }
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    private void receive(SelectionKey key, String prefix, int requests) {
        if (key.isReadable()) {
            DatagramChannel channel = (DatagramChannel) key.channel();
            ChannelData data = (ChannelData) key.attachment();
            String requestMessage = String.format("%s%d_%d", prefix, data.index, data.counter);

            data.buffer.clear();
            try {
                channel.receive(data.buffer);
                data.buffer.flip();
                String responseMessage = StandardCharsets.UTF_8.decode(data.buffer).toString();
                if (responseMessage.contains(requestMessage)) {
                    System.out.println("Received: " + responseMessage);
                    data.counter++;
                }
                key.interestOps(SelectionKey.OP_WRITE);
                if (data.counter >= requests) {
                    key.channel().close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        Util.runClient(new HelloUDPNonblockingClient(), args);
    }
}
