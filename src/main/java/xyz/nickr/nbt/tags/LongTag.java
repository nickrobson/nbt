package xyz.nickr.nbt.tags;

import java.io.PrintStream;

import io.netty.buffer.ByteBuf;
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
    public void _read(ByteBuf buf) {
        this.payload = buf.readLong();
    }

    @Override
    public void _write(ByteBuf buf) {
        buf.writeLong(payload);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_Long(%s): %d", name(), get()));
    }

}