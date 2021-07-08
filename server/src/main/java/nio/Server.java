package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private static int cnt = 1;
    private ServerSocketChannel sc;
    private Selector selector;
    private String name = "user";

    public Server() throws IOException {
        sc = ServerSocketChannel.open();
        selector = Selector.open();
        sc.bind(new InetSocketAddress(8189));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_ACCEPT);

        while (sc.isOpen()) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

    private void handleRead(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        SocketChannel channel = (SocketChannel) key.channel();
        String name = (String) key.attachment();
        int read;
        StringBuilder sb = new StringBuilder();
        while (true) {
            read = channel.read(buffer);
            buffer.flip();
            if (read == -1) {
                channel.close();
                break;
            }
            if (read > 0) {
                while (buffer.hasRemaining()) {
                    sb.append((char) buffer.get());
                }
                buffer.clear();
            } else {
                break;
            }
        }
        System.out.println("received: " + sb);
        for (SelectionKey selectionKey : selector.keys()) {
            if (selectionKey.isValid() && selectionKey.channel() instanceof SocketChannel) {
                SocketChannel ch = (SocketChannel) selectionKey.channel();
                ch.write(ByteBuffer.wrap((name + ": " + sb).getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = sc.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, name + cnt);
        cnt++;
    }

}
