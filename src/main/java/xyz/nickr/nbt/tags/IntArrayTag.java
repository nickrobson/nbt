package xyz.nickr.nbt.tags;

import java.io.PrintStream;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
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
    public void _read(ByteBuf buf) {
        int len = buf.readInt();
        this.payload = new int[len];
        for (int i = 0; i < len; i++)
            payload[i] = buf.readInt();
    }

    @Override
    public void _write(ByteBuf buf) {
        if (payload == null)
            throw new IllegalStateException("int array tag is missing value");
        buf.writeInt(payload.length);
        for (int i = 0, j = payload.length; i < j; i++)
            buf.writeInt(payload[i]);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_IntArray(%s): [%d ints]", name(), get().length));
    }

}
