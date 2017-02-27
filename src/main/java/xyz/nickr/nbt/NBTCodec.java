package xyz.nickr.nbt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import xyz.nickr.nbt.tags.NBTTag;
import xyz.nickr.nbt.tags.NBTTag.NBTTagType;

/**
 * The main controlling class for the NBT API. <br>
 * This class creates, encodes, and decodes NBTTags.
 *
 * @author Nick Robson
 */
public class NBTCodec {

    private static final Map<Byte, Class<? extends NBTTag>> types = new HashMap<>();

    static {
        String pkg = NBTCodec.class.getPackage().getName();
        for (String s : Arrays.asList("End", "Byte", "Short", "Int", "Long", "Float", "Double", "ByteArray", "String", "List", "Compound", "IntArray")) {
            try {
                Class<? extends NBTTag> cl = Class.forName(pkg + ".tags." + s + "Tag").asSubclass(NBTTag.class);
                NBTTagType type = cl.getAnnotation(NBTTagType.class);
                if (type != null)
                    types.put(type.value(), cl);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attempts to create an NBTTag given a type ID.
     *
     * @param id The type ID.
     *
     * @return A newly constructed NBTTag.
     */
    public static NBTTag createTag(byte id) {
        Class<? extends NBTTag> cls = types.get(id);
        try {
            Constructor<? extends NBTTag> constr = cls.getDeclaredConstructor();
            constr.setAccessible(true);
            return constr.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException("no valid tag found for id=" + id, ex);
        }
    }

    private final ByteOrder order;

    /**
     * Creates a NBTCodec object with a given byte order.
     *
     * @param order The byte order.
     */
    public NBTCodec(ByteOrder order) {
        this.order = Objects.requireNonNull(order, "byte order cannot be null");
    }

    /**
     * Decodes a {@link NBTTag} from a given ByteBuf, using a specified {@link NBTCompression} method.
     *
     * @param buf The buffer to decode from.
     * @param compression The compression method used.
     *
     * @return The decoded NBTTag.
     */
    public NBTTag decode(ByteBuf buf, NBTCompression compression) {
        if (compression != null)
            buf = compression.extract(buf);
        byte type = buf.readByte();
        NBTTag tag = createTag(type);
        tag.read(buf, order);
        return tag;
    }

    /**
     * Decodes a {@link NBTTag} from a given InputStream, using a specified {@link NBTCompression} method.
     *
     * @param in The input stream to decode from.
     * @param compression The compression method used.
     *
     * @return The decoded NBTTag.
     */
    public NBTTag decode(InputStream in, NBTCompression compression) {
        ByteBuf buf = Unpooled.buffer();
        byte[] tmp = new byte[4096];
        try {
            while (true) {
                int c = in.read(tmp);
                if (c == -1)
                    break;
                buf.writeBytes(tmp, 0, c);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        return decode(buf, compression);
    }

    /**
     * Encodes an {@link NBTTag} to a given ByteBuf, using a specified {@link NBTCompression} method.
     *
     * @param buf The buffer to encode to.
     * @param tag The tag to be encoded.
     * @param compression The compression method to be used.
     */
    public void encode(ByteBuf buf, NBTTag tag, NBTCompression compression) {
        ByteBuf tmp = Unpooled.buffer();
        tag.write(tmp, order);
        if (compression != null)
            tmp = compression.compress(tmp);
        buf.writeBytes(tmp);
    }

    /**
     * Encodes an {@link NBTTag} to a given OutputStream, using a specified {@link NBTCompression} method.
     *
     * @param out The output stream to encode to.
     * @param tag The tag to be encoded.
     * @param compression The compression method to be used.
     */
    public void encode(OutputStream out, NBTTag tag, NBTCompression compression) {
        ByteBuf tmp = Unpooled.buffer();
        tag.write(tmp, order);
        if (compression != null)
            tmp = compression.compress(tmp);
        try {
            out.write(toByteArray(tmp));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * Gets a byte array representing the contents of a given ByteBuf.
     *
     * @param buf The ByteBuf.
     *
     * @return The byte array.
     */
    public static byte[] toByteArray(ByteBuf buf) {
        if (buf.hasArray())
            return buf.array();
        int ridx = buf.readerIndex();
        buf.resetReaderIndex();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        buf.readerIndex(ridx);
        return bytes;
    }

}
