package info.kgeorgiy.ja.popov.hello;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Data {
    public SocketAddress address;
    public final ByteBuffer buffer;

    public Data(SocketAddress address, int bufferSize) {
        this.address = address;
        this.buffer = ByteBuffer.allocate(bufferSize).clear()
                .put("Hello, ".getBytes(StandardCharsets.UTF_8));
    }

    public void readyToRead() {
        buffer.clear().position(Util.responsePosition);
    }
}