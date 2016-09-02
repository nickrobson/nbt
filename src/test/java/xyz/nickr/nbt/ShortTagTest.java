package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

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
        b.write(buf);
        buf.readByte(); // discard id
        b.read(buf);
        assertEquals(test, b.get());
    }

}
