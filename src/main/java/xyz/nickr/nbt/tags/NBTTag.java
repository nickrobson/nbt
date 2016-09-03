package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;

import java.io.PrintStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

/**
 * The building blocks of NBT, an NBTTag is a container for data.
 *
 * @author Nick Robson
 */
public abstract class NBTTag {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface NBTTagType {

        byte value();

    }

    private boolean writeType = true;
    private boolean hasName = true;
    private String name;

    /**
     * Gets this tag's type ID.
     *
     * @return The type ID.
     */
    public final byte getTypeId() {
         NBTTagType type = getClass().getAnnotation(NBTTagType.class);
         if (type == null)
             throw new IllegalStateException("Class is missing @NBTTagType");
         return type.value();
    }

    /**
     * Gets this tag's name.
     *
     * @return The name.
     */
    public final Optional<String> getName() {
        return hasName && name != null ? Optional.of(name) : Optional.empty();
    }

    /**
     * Sets this tag's name.
     *
     * @param name The name.
     * @param <T> The type of NBT tag.
     *
     * @return This tag.
     */
    @SuppressWarnings("unchecked")
    public final <T extends NBTTag> T setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
        try {
            return (T) this;
        } catch (ClassCastException ex) {
            return null;
        }
    }

    /**
     * Reads data into this tag from the given buffer with the given byte order.
     *
     * @param buf The buffer to read from.
     * @param <T> The type of NBT tag.
     *
     * @return This tag.
     */
    @SuppressWarnings("unchecked")
    public final <T extends NBTTag> T read(ByteBuf buf) {
        if (hasName)
            setName(readString(buf));
        _read(buf);
        try {
            return (T) this;
        } catch (ClassCastException ex) {
            return null;
        }
    }

    /**
     * Writes data from this tag into the given buffer with the given byte order.
     *
     * @param buf The buffer to write to.
     * @param <T> The type of NBT tag.
     *
     * @return This tag.
     */
    @SuppressWarnings("unchecked")
    public final <T extends NBTTag> T write(ByteBuf buf) {
         Optional<String> name = getName();
         if (writeType)
             buf.writeByte(getTypeId());
         if (name.isPresent())
             writeString(buf, name.get());
         _write(buf);
         try {
             return (T) this;
         } catch (ClassCastException ex) {
             return null;
         }
    }

    /**
     * Prints this tag to the given print stream.
     *
     * @param stream The stream to print to.
     */
    public final void print(PrintStream stream) {
        _print(stream != null ? stream : System.out, "");
    }

    /**
     * Checks whether or not this tag represents a number.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isNumber() {
        return this instanceof NumberTag;
    }

    /**
     * Attempts to get this tag as a Number.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a Number.
     */
    public Number getAsNumber() {
        return isNumber() ? ((NumberTag) this).getAsNumber() : 0;
    }

    /**
     * Attempts to get this tag as a NumberTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a NumberTag.
     */
    public NumberTag getAsNumberTag() {
        return isNumber() ? (NumberTag) this : null;
    }

    /**
     * Checks whether or not this tag represents a String.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isString() {
        return this instanceof StringTag;
    }

    /**
     * Attempts to get this tag as a string.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a string.
     */
    public String getAsString() {
        if (isString())
            return ((StringTag) this).get();
        else if (isNumber())
            return getAsNumber().toString();
        else if (isByteArray())
            return Arrays.toString(getAsByteArray());
        else if (isIntArray())
            return Arrays.toString(getAsIntArray());
        else if (isListTag())
            return getAsListTag().list().toString();
        else if (isCompoundTag())
            return new LinkedList<>(getAsCompoundTag().map().entrySet()).toString();
        return super.toString();
    }

    /**
     * Attempts to get this tag as a StringTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a StringTag.
     */
    public StringTag getAsStringTag() {
        return isString() ? (StringTag) this : null;
    }

    /**
     * Checks whether or not this tag represents a byte array.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isByteArray() {
        return this instanceof ByteArrayTag;
    }

    /**
     * Attempts to get this tag as a byte array.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a byte array.
     */
    public byte[] getAsByteArray() {
        return isByteArray() ? ((ByteArrayTag) this).get() : null;
    }

    /**
     * Attempts to get this tag as a ByteArrayTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a ByteArrayTag.
     */
    public ByteArrayTag getAsByteArrayTag() {
        return isByteArray() ? (ByteArrayTag) this : null;
    }

    /**
     * Checks whether or not this tag represents an int array.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isIntArray() {
        return this instanceof IntArrayTag;
    }

    /**
     * Attempts to get this tag as an int array.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as an int array.
     */
    public int[] getAsIntArray() {
        return isIntArray() ? ((IntArrayTag) this).get() : null;
    }

    /**
     * Attempts to get this tag as a IntArrayTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a IntArrayTag.
     */
    public IntArrayTag getAsIntArrayTag() {
        return isIntArray() ? (IntArrayTag) this : null;
    }

    /**
     * Checks whether or not this tag represents a list tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isListTag() {
        return this instanceof ListTag;
    }

    /**
     * Attempts to get this tag as a list tag.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a list tag.
     */
    public ListTag getAsListTag() {
        return isListTag() ? (ListTag) this : null;
    }

    /**
     * Checks whether or not this tag represents a compound tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isCompoundTag() {
        return this instanceof CompoundTag;
    }

    /**
     * Attempts to get this tag as a compound tag.
     * A ClassCastException is thrown if it is not one.
     *
     * @return This tag as a compound tag.
     */
    public CompoundTag getAsCompoundTag() {
        return isCompoundTag() ? (CompoundTag) this : null;
    }

    protected abstract void _print(PrintStream stream, String indent);

    protected abstract void _read(ByteBuf buf);

    protected abstract void _write(ByteBuf buf);

    /**
     * Reads a string.
     *
     * @param buf The buffer to read from.
     *
     * @return The string.
     */
    public static String readString(ByteBuf buf) {
        byte[] bytes = new byte[buf.readUnsignedShort()];
        buf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Writes a string.
     *
     * @param buf The buffer to write to.
     * @param string The string.
     */
    public static void writeString(ByteBuf buf, String string) {
        buf.writeShort(string.length());
        buf.writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }

    protected void setHasName(boolean hasName) {
        this.hasName = hasName;
    }

    protected void setWriteType(boolean writeType) {
        this.writeType = writeType;
    }

    protected String name() {
        Optional<String> name = getName();
        return name.isPresent() ? String.format("'%s'", name.get()) : "None";
    }

    protected String indent(String indent) {
        return "   " + indent;
    }

    @Override
    public String toString() {
        return getAsString();
    }

}
