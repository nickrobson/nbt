package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing an {@code int}.
 *
 * @author Nick Robson
 */
@NBTTagType(3)
public class IntTag extends NumberTag {

    private int payload;

    IntTag() {}

    /**
     * Creates an IntTag with a given value.
     *
     * @param val The value.
     */
    public IntTag(int val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public int get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(int val) {
        this.payload = val;
    }

    @Override
    public Number getAsNumber() {
        return get();
    }

    @Override
    public void set(Number number) {
        set(number.intValue());
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            this.payload = buf.readInt();
        } else {
            this.payload = buf.readIntLE();
        }
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            buf.writeInt(payload);
        } else {
            buf.writeIntLE(payload);
        }
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_Int(%s): %d", name(), get()));
    }

}
