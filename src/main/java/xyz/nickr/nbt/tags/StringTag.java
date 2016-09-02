package xyz.nickr.nbt.tags;

import java.io.PrintStream;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a String.
 *
 * @author Nick Robson
 */
@NBTTagType(8)
public class StringTag extends NBTTag {

    private String payload;

    StringTag() {}

    /**
     * Creates a StringTag with a given String value.
     *
     * @param val The value.
     */
    public StringTag(String val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public String get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(String val) {
        this.payload = Objects.requireNonNull(val, "string cannot be null");
    }

    @Override
    public void _read(ByteBuf buf) {
        this.payload = readString(buf);
    }

    @Override
    public void _write(ByteBuf buf) {
        if (payload == null)
            throw new IllegalStateException("string tag is missing value");
        writeString(buf, payload);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_String(%s): '%s'", name(), get()));
    }

}
