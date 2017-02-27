package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;

import java.nio.ByteOrder;
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
        for (ByteOrder order : new ByteOrder[]{ByteOrder.BIG_ENDIAN, ByteOrder.LITTLE_ENDIAN}) {
            b.write(buf, order);
            buf.readByte(); // discard id
            b.read(buf, order);
            assertEquals(test, b.get());
        }
    }

}
