package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import xyz.nickr.nbt.NBTCodec;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing a list of unnamed NBTTags of the same type.
 *
 * @author Nick Robson
 */
@NBTTagType(9)
public class ListTag extends NBTTag implements Iterable<NBTTag> {

    private List<NBTTag> elements = new LinkedList<>();
    private byte type;

    ListTag() {}

    /**
     * Creates a ListTag containing the given {@link NBTTag}s.
     *
     * @param tags The tags.
     */
    public ListTag(NBTTag... tags) {
        Arrays.asList(tags).forEach(this::add);
    }

    @Override
    public Iterator<NBTTag> iterator() {
        return elements.iterator();
    }

    @Override
    public Spliterator<NBTTag> spliterator() {
        return Spliterators.spliterator(iterator(), size(), Spliterator.NONNULL | Spliterator.ORDERED);
    }

    /**
     * Gets a list representation of the {@link NBTTag}s in this list tag.
     *
     * @return A list representation of this compound tag.
     */
    public List<NBTTag> list() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * Gets the n-th {@link NBTTag} to be added to this tag.
     * If negative, gets the {@code Math.abs(n)}-th last.
     *
     * @param index The index, n.
     *
     * @return The tag.
     */
    public NBTTag get(int index) {
        int idx = index >= 0 ? index : size() + index;
        return elements.get(idx);
    }

    /**
     * Checks whether or not the given tag represents a number.
     *
     * @param n The index of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isNumber(int n) {
        return get(n).isNumber();
    }

    /**
     * Attempts to get the given tag as a Number.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a Number.
     */
    public Number getAsNumber(int n) {
        return get(n).getAsNumber();
    }

    /**
     * Attempts to get the given tag as a NumberTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a NumberTag.
     */
    public NumberTag getAsNumberTag(int n) {
        return get(n).getAsNumberTag();
    }

    /**
     * Checks whether or not the given tag represents a String.
     *
     * @param n The index of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isString(int n) {
        return get(n).isString();
    }

    /**
     * Attempts to get the given tag as a string.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a string.
     */
    public String getAsString(int n) {
        return get(n).getAsString();
    }

    /**
     * Attempts to get the given tag as a StringTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a StringTag.
     */
    public StringTag getAsStringTag(int n) {
        return get(n).getAsStringTag();
    }

    /**
     * Checks whether or not the given tag represents a byte array.
     *
     * @param n The index of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isByteArray(int n) {
        return get(n).isByteArray();
    }

    /**
     * Attempts to get the given tag as a byte array.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a byte array.
     */
    public byte[] getAsByteArray(int n) {
        return get(n).getAsByteArray();
    }

    /**
     * Attempts to get the given tag as a ByteArrayTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a ByteArrayTag.
     */
    public ByteArrayTag getAsByteArrayTag(int n) {
        return get(n).getAsByteArrayTag();
    }

    /**
     * Checks whether or not the given tag represents an int array.
     *
     * @param n The index of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isIntArray(int n) {
        return get(n).isIntArray();
    }

    /**
     * Attempts to get the given tag as an int array.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as an int array.
     */
    public int[] getAsIntArray(int n) {
        return get(n).getAsIntArray();
    }

    /**
     * Attempts to get the given tag as a IntArrayTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a IntArrayTag.
     */
    public IntArrayTag getAsIntArrayTag(int n) {
        return get(n).getAsIntArrayTag();
    }

    /**
     * Checks whether or not the given tag represents a list tag.
     *
     * @param n The index of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isListTag(int n) {
        return get(n).isListTag();
    }

    /**
     * Attempts to get the given tag as a list tag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a list tag.
     */
    public ListTag getAsListTag(int n) {
        return get(n).getAsListTag();
    }

    /**
     * Checks whether or not the given tag represents a compound tag.
     *
     * @param n The index of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isCompoundTag(int n) {
        return get(n).isCompoundTag();
    }

    /**
     * Attempts to get the given tag as a compound tag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param n The index of the tag.
     *
     * @return The given tag as a compound tag.
     */
    public CompoundTag getAsCompoundTag(int n) {
        return get(n).getAsCompoundTag();
    }

    /**
     * Gets the number of tags added to this list tag.
     *
     * @return The size.
     */
    public int size() {
        return elements.size();
    }

    /**
     * Adds a tag to this compound tag.
     *
     * @param tag The tag.
     */
    public void add(NBTTag tag) {
        Objects.requireNonNull(tag, "tag cannot be null");
        elements.add(tag);
    }

    /**
     * Removes the n-th tag from the compound tag.
     *
     * @param index The index, n.
     */
    public void remove(int index) {
        elements.remove(index);
    }

    /**
     * Removes all tags contained in this compound tag.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Sets the backing list of tags to be the given one.
     *
     * @param tags The tags.
     */
    public void set(List<NBTTag> tags) {
        this.elements = Objects.requireNonNull(tags, "tags list cannot be null");
    }

    @Override
    public void _read(ByteBuf buf, ByteOrder order) {
        elements.clear();
        type = buf.readByte();
        int length;
        if (order == ByteOrder.BIG_ENDIAN) {
            length = buf.readInt();
        } else {
            length = buf.readIntLE();
        }
        while (length-- > 0) {
            NBTTag tag = NBTCodec.createTag(type);
            tag.setWriteType(false);
            tag.setHasName(false);
            tag.read(buf, order);
            elements.add(tag);
        }
    }

    @Override
    public void _write(ByteBuf buf, ByteOrder order) {
        buf.writeByte(type);
        if (order == ByteOrder.BIG_ENDIAN) {
            buf.writeInt(elements.size());
        } else {
            buf.writeIntLE(elements.size());
        }
        for (NBTTag tag : elements) {
            tag.setWriteType(false);
            tag.setHasName(false);
            tag.write(buf, order);
        }
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_List(%s): %d entries", name(), size()));
        stream.println(indent + "{");
        String in = indent(indent);
        for (NBTTag tag : this)
            tag._print(stream, in);
        stream.println(indent + "}");
    }

}
