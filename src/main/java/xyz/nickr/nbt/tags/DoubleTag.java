package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a {@code double}.
 *
 * @author Nick Robson
 */
@NBTTagType(6)
public class DoubleTag extends NumberTag {

    private double payload;

    DoubleTag() {}

    /**
     * Creates a DoubleTag with a given value.
     *
     * @param val The value.
     */
    public DoubleTag(double val) {
        set(val);
    }

    /**
     * Gets this tag's value.
     *
     * @return The value.
     */
    public double get() {
        return payload;
    }

    /**
     * Sets the value of this tag.
     *
     * @param val The new value.
     */
    public void set(double val) {
        this.payload = val;
    }

    @Override
    public Number getAsNumber() {
        return get();
    }

    @Override
    public void set(Number number) {
        set(number.doubleValue());
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        this.payload = buf.readDouble();
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        buf.writeDouble(payload);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + rtz(String.format("TAG_Double(%s): %.17f", name(), get())));
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
