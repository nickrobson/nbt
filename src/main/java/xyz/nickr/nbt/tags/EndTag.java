package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing an {@code end tag}, representing the end of a {@link CompoundTag}.
 *
 * @author Nick Robson
 */
@NBTTagType(0)
public class EndTag extends NBTTag {

    EndTag() {
        this.setHasName(false);
    }

    @Override
    public void _read(ByteBuf buffer, ByteOrder order) {}

    @Override
    public void _write(ByteBuf buffer, ByteOrder order) {}

    @Override
    protected void _print(PrintStream stream, String indent) {}

}
