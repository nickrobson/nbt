package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a {@code long}.
 *
 * @author Nick Robson
 */
@NBTTagType(4)
public class LongTag extends NumberTag {

    private long payload;

    LongTag() {}

    /**
     * Creates a LongTag with a given value.
     *
     * @param val The value.
     */
    public LongTag(long val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public long get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(long val) {
        this.payload = val;
    }

    @Override
    public Number getAsNumber() {
        return get();
    }

    @Override
    public void set(Number number) {
        set(number.longValue());
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            this.payload = buf.readLong();
        } else {
            this.payload = buf.readLongLE();
        }
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            buf.writeLong(payload);
        } else {
            buf.writeLongLE(payload);
        }
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_Long(%s): %d", name(), get()));
    }

}
