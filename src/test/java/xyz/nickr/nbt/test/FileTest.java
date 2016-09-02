package xyz.nickr.nbt.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

import org.junit.Test;

import xyz.nickr.nbt.NBTCodec;
import xyz.nickr.nbt.NBTCompression;
import xyz.nickr.nbt.tags.NBTTag;

public class FileTest {

    private void parseFile(File file, NBTCompression compression) {
        try (FileInputStream fis = new FileInputStream(file)) {
            NBTCodec codec = new NBTCodec(ByteOrder.BIG_ENDIAN);
            BufferedInputStream bis = new BufferedInputStream(fis);
            if (compression == null)
                compression = NBTCompression.detect(bis);
            NBTTag tag = codec.decode(bis, compression);
            tag.print(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHelloWorld() {
        File file = new File("nbt/hello_world.nbt");
        parseFile(file, NBTCompression.UNCOMPRESSED);
    }

    @Test
    public void testBigtest() {
        File file = new File("nbt/bigtest.nbt");
        parseFile(file, NBTCompression.GZIP);
    }

    @Test
    public void testLevel() {
        File file = new File("nbt/level.dat");
        parseFile(file, NBTCompression.GZIP);
    }

}
