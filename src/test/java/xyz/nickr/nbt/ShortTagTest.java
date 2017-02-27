package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

import java.nio.ByteOrder;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import xyz.nickr.nbt.tags.ShortTag;

public class ShortTagTest {

    @Test
    public void testShortTag() {
        short test = 30000;

        ShortTag b = new ShortTag(test);
        b.setName("short");

        ByteBuf buf = Unpooled.buffer().clear();
        for (ByteOrder order : new ByteOrder[]{ByteOrder.BIG_ENDIAN, ByteOrder.LITTLE_ENDIAN}) {
            b.write(buf, order);
            buf.readByte(); // discard id
            b.read(buf, order);
            assertEquals(test, b.get());
        }
    }

}
