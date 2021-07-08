package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferExample {

    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(5);

        buffer.putInt(142124);
        buffer.flip();


        RandomAccessFile raf = new RandomAccessFile("server/serverFiles/test.txt", "rw");
        FileChannel channel = raf.getChannel();
        channel.write(buffer);

        channel.position(0);

        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        System.out.println(buffer.getInt());
//        buffer.rewind();
//        while (buffer.hasRemaining()) {
//            System.out.println((char) buffer.get());
//        }
//
//        buffer.clear();
//        while (buffer.hasRemaining()) {
//            System.out.println((char) buffer.get());
//        }
    }
}
