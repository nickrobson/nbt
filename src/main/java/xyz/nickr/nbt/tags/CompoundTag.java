package xyz.nickr.nbt.tags;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;

import io.netty.buffer.ByteBuf;
import xyz.nickr.nbt.NBTCodec;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * Represents a {@link NBTTag} containing multiple named tags {@code short}.
 *
 * @author Nick Robson
 */
@NBTTagType(10)
public class CompoundTag extends NBTTag implements Iterable<NBTTag> {

    private Map<String, NBTTag> elements = new HashMap<>();

    CompoundTag() {}

    /**
     * Creates a CompoundTag containing the given {@link NBTTag}s.
     *
     * @param tags The tags.
     */
    public CompoundTag(NBTTag... tags) {
        Arrays.asList(tags).forEach(this::add);
    }

    @Override
    public Iterator<NBTTag> iterator() {
        return elements.values().iterator();
    }

    @Override
    public Spliterator<NBTTag> spliterator() {
        return Spliterators.spliterator(iterator(), size(), Spliterator.NONNULL | Spliterator.ORDERED);
    }

    /**
     * Gets this compound tag as a collection of {@link NBTTag}s.
     *
     * @return A collection representation of this compound tag.
     */
    public Collection<NBTTag> values() {
        return Collections.unmodifiableCollection(elements.values());
    }

    /**
     * Gets this compound tag as a map of Strings to {@link NBTTag}s.
     *
     * @return A map representation of this compound tag.
     */
    public Map<String, NBTTag> map() {
        return Collections.unmodifiableMap(elements);
    }

    /**
     * Gets the tag contained in this compound tag with a given name.
     *
     * @param name The name.
     *
     * @return The tag, or null if no such tag.
     */
    public Optional<NBTTag> get(String name) {
        return Optional.ofNullable(elements.get(name));
    }

    /**
     * Gets whether or not there exists a tag in this compound tag with a given name.
     *
     * @param name The name.
     *
     * @return True if there exists one, false otherwise.
     */
    public boolean has(String name) {
        return elements.containsKey(name);
    }

    /**
     * Checks whether or not the given tag represents a number.
     *
     * @param name The name of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isNumber(String name) {
        return get(name).orElse(NullTag.INSTANCE).isNumber();
    }

    /**
     * Attempts to get the given tag as a Number.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a Number.
     */
    public Number getAsNumber(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsNumber();
    }

    /**
     * Attempts to get the given tag as a NumberTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a NumberTag.
     */
    public NumberTag getAsNumberTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsNumberTag();
    }

    /**
     * Checks whether or not the given tag represents a String.
     *
     * @param name The name of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isString(String name) {
        return get(name).orElse(NullTag.INSTANCE).isString();
    }

    /**
     * Attempts to get the given tag as a string.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a string.
     */
    public String getAsString(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsString();
    }

    /**
     * Attempts to get the given tag as a StringTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a StringTag.
     */
    public StringTag getAsStringTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsStringTag();
    }

    /**
     * Checks whether or not the given tag represents a byte array.
     *
     * @param name The name of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isByteArray(String name) {
        return get(name).orElse(NullTag.INSTANCE).isByteArray();
    }

    /**
     * Attempts to get the given tag as a byte array.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a byte array.
     */
    public byte[] getAsByteArray(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsByteArray();
    }

    /**
     * Attempts to get the given tag as a ByteArrayTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a ByteArrayTag.
     */
    public ByteArrayTag getAsByteArrayTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsByteArrayTag();
    }

    /**
     * Checks whether or not the given tag represents an int array.
     *
     * @param name The name of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isIntArray(String name) {
        return get(name).orElse(NullTag.INSTANCE).isIntArray();
    }

    /**
     * Attempts to get the given tag as an int array.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as an int array.
     */
    public int[] getAsIntArray(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsIntArray();
    }

    /**
     * Attempts to get the given tag as a IntArrayTag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a IntArrayTag.
     */
    public IntArrayTag getAsIntArrayTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsIntArrayTag();
    }

    /**
     * Checks whether or not the given tag represents a list tag.
     *
     * @param name The name of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isListTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).isListTag();
    }

    /**
     * Attempts to get the given tag as a list tag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a list tag.
     */
    public ListTag getAsListTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsListTag();
    }

    /**
     * Checks whether or not the given tag represents a compound tag.
     *
     * @param name The name of the tag.
     *
     * @return True if it does, false otherwise.
     */
    public boolean isCompoundTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).isCompoundTag();
    }

    /**
     * Attempts to get the given tag as a compound tag.
     * A ClassCastException is thrown if it is not one.
     *
     * @param name The name of the tag.
     *
     * @return The given tag as a compound tag.
     */
    public CompoundTag getAsCompoundTag(String name) {
        return get(name).orElse(NullTag.INSTANCE).getAsCompoundTag();
    }

    /**
     * Gets the number of tags added to this compound tag.
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
        Objects.requireNonNull(tag, "tag cannot be null").setHasName(true);
        elements.put(tag.getName().get(), tag);
    }

    /**
     * Removes the tag with the given name from the compound tag.
     *
     * @param name The name of the tag.
     */
    public void remove(String name) {
        elements.remove(name);
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
    public void set(Map<String, NBTTag> tags) {
        this.elements = Objects.requireNonNull(tags, "tags list cannot be null");
    }

    @Override
    public void _read(ByteBuf buf) {
        byte type;
        while ((type = buf.readByte()) != 0) {
            NBTTag tag = NBTCodec.createTag(type);
            tag.setWriteType(true);
            tag.setHasName(true);
            tag.read(buf);
            add(tag);
        }
    }

    @Override
    public void _write(ByteBuf buf) {
        for (NBTTag tag : this) {
            tag.setWriteType(true);
            tag.setHasName(true);
            tag.write(buf);
        }
        buf.writeByte(0);
    }

    @Override
    protected void _print(PrintStream stream, String indent) {
        stream.println(indent + String.format("TAG_Compound(%s): %d entries", name(), size()));
        stream.println(indent + "{");
        values().stream()
                .sorted((t1, t2) -> t1.name().compareToIgnoreCase(t2.name()))
                .forEach(tag -> tag._print(stream, indent(indent)));
        stream.println(indent + "}");
    }

}
