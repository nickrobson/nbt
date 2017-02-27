package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

import java.nio.ByteOrder;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import xyz.nickr.nbt.tags.IntTag;

public class IntTagTest {

    @Test
    public void testIntTag() {
        int test = 1000000;

        IntTag b = new IntTag(test);
        b.setName("int");

        ByteBuf buf = Unpooled.buffer();
        for (ByteOrder order : new ByteOrder[]{ByteOrder.BIG_ENDIAN, ByteOrder.LITTLE_ENDIAN}) {
            b.write(buf, order);
            buf.readByte(); // discard id
            b.read(buf, order);
            assertEquals(test, b.get());
        }
    }

}
