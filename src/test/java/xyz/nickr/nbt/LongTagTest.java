package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import xyz.nickr.nbt.tags.LongTag;

public class LongTagTest {

    @Test
    public void testLongTag() {
        long test = Integer.MAX_VALUE;

        LongTag b = new LongTag(test);
        b.setName("long");

        ByteBuf buf = Unpooled.buffer().clear();
        b.write(buf);
        buf.readByte(); // discard id
        b.read(buf);
        assertEquals(test, b.get());
    }

}
