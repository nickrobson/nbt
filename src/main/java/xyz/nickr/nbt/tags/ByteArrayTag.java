package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import java.util.Objects;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a {@code byte array}.
 *
 * @author Nick Robson
 */
@NBTTagType(7)
public class ByteArrayTag extends NBTTag {

    private byte[] payload;

    ByteArrayTag() {}

    /**
     * Creates a ByteArrayTag with a given value.
     *
     * @param val The value.
     */
    public ByteArrayTag(byte[] val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public byte[] get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(byte[] val) {
        this.payload = Objects.requireNonNull(val, "byte array cannot be null");
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        int len;
        if (order == ByteOrder.BIG_ENDIAN) {
            len = buf.readInt();
        } else {
            len = buf.readIntLE();
        }
        this.payload = new byte[len];
        buf.readBytes(payload);
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        if (payload == null)
            throw new IllegalStateException("string tag is missing value");
        if (order == ByteOrder.BIG_ENDIAN) {
            buf.writeInt(payload.length);
        } else {
            buf.writeIntLE(payload.length);
        }
        buf.writeBytes(payload);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_ByteArray(%s): [%d bytes]", name(), get().length));
    }

}
