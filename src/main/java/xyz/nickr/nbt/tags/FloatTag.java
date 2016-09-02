package xyz.nickr.nbt.tags;

import java.io.PrintStream;

import io.netty.buffer.ByteBuf;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a {@code float}.
 *
 * @author Nick Robson
 */
@NBTTagType(5)
public class FloatTag extends NumberTag {

    private float payload;

    FloatTag() {}

    /**
     * Creates a FloatTag with a given value.
     *
     * @param val The value.
     */
    public FloatTag(float val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public float get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(float val) {
        this.payload = val;
    }

    @Override
    public Number getAsNumber() {
        return get();
    }

    @Override
    public void set(Number number) {
        set(number.floatValue());
    }

    @Override
    public void _read(ByteBuf buf) {
        this.payload = buf.readFloat();
    }

    @Override
    public void _write(ByteBuf buf) {
        buf.writeFloat(payload);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + rtz(String.format("TAG_Float(%s): %.17f", name(), get())));
    }

    private String rtz(String s) {
        boolean was = s.endsWith("0");
        while (s.endsWith("0")) {
            s = s.substring(0, s.length()-1);
        }
        if (was && s.endsWith("."))
            s += "0";
        return s;
    }

}
