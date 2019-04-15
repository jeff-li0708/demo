package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by liangl on 2019/4/3.
 */
public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("E://tmp_14_befor_order_info_file.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(100);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip(); //flip方法将Buffer从写模式切换到读模式

            while(buf.hasRemaining()){
                System.out.print((char) buf.get()); //使用get()方法从Buffer中读取数据的例子
            }

            buf.clear(); //一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
