package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a {@code short}.
 *
 * @author Nick Robson
 */
@NBTTagType(2)
public class ShortTag extends NumberTag {

    private short payload;

    ShortTag() {}

    /**
     * Creates a ShortTag with a given value.
     *
     * @param val The value.
     */
    public ShortTag(int val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public short get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(int val) {
        this.payload = (short) val;
    }

    @Override
    public Number getAsNumber() {
        return get();
    }

    @Override
    public void set(Number number) {
        set(number.shortValue());
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            this.payload = buf.readShort();
        } else {
            this.payload = buf.readShortLE();
        }
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            buf.writeShort(payload);
        } else {
            buf.writeShortLE(payload);
        }
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_Short(%s): %d", name(), get()));
    }

}
