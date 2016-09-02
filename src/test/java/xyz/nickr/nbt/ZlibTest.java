package xyz.nickr.nbt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.ByteOrder;
import java.util.Optional;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import xyz.nickr.nbt.NBTCodec;
import xyz.nickr.nbt.NBTCompression;
import xyz.nickr.nbt.tags.CompoundTag;
import xyz.nickr.nbt.tags.NBTTag;
import xyz.nickr.nbt.tags.ShortTag;

public class ZlibTest {

    @Test
    public void testZlib() {
        NBTCodec codec = new NBTCodec(ByteOrder.BIG_ENDIAN);
        ShortTag sh = new ShortTag(5);
        sh.setName("example");
        CompoundTag cp = new CompoundTag();
        cp.setName("compound");
        cp.add(sh);

        ByteBuf buf = Unpooled.buffer();
        codec.encode(buf, cp, NBTCompression.ZLIB);

        NBTTag tag = codec.decode(buf, NBTCompression.ZLIB);
        assertTrue(tag instanceof CompoundTag);
        CompoundTag cp2 = (CompoundTag) tag;
        assertEquals("compound", cp2.getName().get());
        assertEquals(1, cp2.size());
        Optional<NBTTag> opt = cp2.get("example");
        assertTrue(opt.isPresent());
        NBTTag t2 = opt.get();
        assertTrue(t2 instanceof ShortTag);
        ShortTag sh2 = (ShortTag) t2;
        assertEquals("example", sh2.getName().get());
        assertEquals(5, sh2.get());
    }

}
