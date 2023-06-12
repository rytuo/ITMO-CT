package info.kgeorgiy.ja.popov.hello;

import java.nio.ByteBuffer;

public class ChannelData {
    public final int index;
    public final ByteBuffer buffer;
    public int counter = 0;

    public ChannelData(int index, int bufferSize) {
        this.index = index;
        this.buffer = ByteBuffer.allocate(bufferSize);
    }
}