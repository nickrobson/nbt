package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import xyz.nickr.nbt.tags.ByteTag;

public class ByteTagTest {

    @Test
    public void testByteTag() {
        byte test = 100;

        ByteTag b = new ByteTag(test);
        b.setName("byte");

        ByteBuf buf = Unpooled.buffer().clear();
        b.write(buf);
        buf.readByte(); // discard id
        b.read(buf);
        assertEquals(test, b.get());
    }

}
