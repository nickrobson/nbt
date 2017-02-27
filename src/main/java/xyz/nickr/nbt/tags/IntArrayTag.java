package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import java.util.Objects;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing an {@code int array}.
 *
 * @author Nick Robson
 */
@NBTTagType(11)
public class IntArrayTag extends NBTTag {

    private int[] payload;

    IntArrayTag() {}

    /**
     * Creates an IntArrayTag with a given value.
     *
     * @param val The value.
     */
    public IntArrayTag(int[] val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public int[] get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(int[] val) {
        this.payload = Objects.requireNonNull(val, "int array cannot be null");
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            int len = buf.readInt();
            this.payload = new int[len];
            for (int i = 0; i < len; i++)
                this.payload[i] = buf.readInt();
        } else {
            int len = buf.readIntLE();
            this.payload = new int[len];
            for (int i = 0; i < len; i++)
                this.payload[i] = buf.readIntLE();
        }
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        if (payload == null)
            throw new IllegalStateException("int array tag is missing value");
        if (order == ByteOrder.BIG_ENDIAN) {
            buf.writeInt(payload.length);
            for (int i : payload)
                buf.writeInt(i);
        } else {
            buf.writeIntLE(payload.length);
            for (int i : payload)
                buf.writeIntLE(i);
        }
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_IntArray(%s): [%d ints]", name(), get().length));
    }

}
