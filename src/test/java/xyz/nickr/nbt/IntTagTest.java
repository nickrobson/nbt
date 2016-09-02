package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

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
        b.write(buf);
        buf.readByte(); // discard id
        b.read(buf);
        assertEquals(test, b.get());
    }

}
