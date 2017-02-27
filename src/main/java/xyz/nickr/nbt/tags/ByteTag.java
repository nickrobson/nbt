package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a {@code byte}.
 *
 * @author Nick Robson
 */
@NBTTagType(1)
public class ByteTag extends NumberTag {

    private byte payload;

    ByteTag() {}

    /**
     * Creates a ByteTag with a given value.
     *
     * @param val The value.
     */
    public ByteTag(int val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public byte get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(int val) {
        this.payload = (byte) val;
    }

    @Override
    public Number getAsNumber() {
        return get();
    }

    @Override
    public void set(Number number) {
        set(number.byteValue());
    }

    @Override
    public void _read(ByteBuf buffer, ByteOrder order) {
        this.payload = buffer.readByte();
    }

    @Override
    public void _write(ByteBuf buffer, ByteOrder order) {
        buffer.writeByte(this.payload);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_Byte(%s): %d", name(), get()));
    }

}
