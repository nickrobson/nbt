package xyz.nickr.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

/**
 * Represents the different compression options supported by NBT.
 *
 * @author Nick Robson
 */
public enum NBTCompression {

    /**
     * Represents an uncompressed data stream.
     */
    UNCOMPRESSED {
        @Override
        public ByteBuf extract(ByteBuf buf) {
            return Unpooled.copiedBuffer(buf);
        }

        @Override
        public ByteBuf compress(ByteBuf buf) {
            return Unpooled.copiedBuffer(buf);
        }
    },

    /**
     * Represents a data stream compressed using GZip.
     */
    GZIP {
        @Override
        public ByteBuf extract(ByteBuf buf) {
            byte[] buffer = new byte[1024];
            ByteBuf out = Unpooled.buffer();
            try {
                GZIPInputStream gz = new GZIPInputStream(new ByteBufInputStream(buf));
                int len;
                while ((len = gz.read(buffer)) > 0) {
                    out.writeBytes(buffer, 0, len);
                }
                gz.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return out;
        }

        @Override
        public ByteBuf compress(ByteBuf buf) {
            ByteBuf out = Unpooled.buffer();
            try {
                GZIPOutputStream gz = new GZIPOutputStream(new ByteBufOutputStream(out));
                int readerIndex = buf.readerIndex();
                byte[] bytes = new byte[buf.resetReaderIndex().readableBytes()];
                buf.readBytes(bytes);
                buf.readerIndex(readerIndex);
                gz.write(bytes);
                gz.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return out;
        }
    },

    /**
     * Represents a data stream compressed using ZLib.
     */
    ZLIB {
        @Override
        public ByteBuf extract(ByteBuf buf) {
            int readerIndex = buf.readerIndex();
            byte[] bytes = new byte[buf.resetReaderIndex().readableBytes()];
            buf.readBytes(bytes);
            buf.readerIndex(readerIndex);

            Inflater inflater = new Inflater();
            inflater.setInput(bytes);
            byte[] buffer = new byte[1024];
            ByteBuf out = Unpooled.buffer();

            try {
                while (!inflater.finished()) {
                    int len = inflater.inflate(buffer);
                    out.writeBytes(buffer, 0, len);
                }
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
            return out;
        }

        @Override
        public ByteBuf compress(ByteBuf buf) {
            int readerIndex = buf.readerIndex();
            byte[] bytes = new byte[buf.resetReaderIndex().readableBytes()];
            buf.readBytes(bytes);
            buf.readerIndex(readerIndex);

            Deflater deflater = new Deflater();
            deflater.setInput(bytes);
            deflater.finish();

            byte[] buffer = new byte[1024];
            ByteBuf out = Unpooled.buffer();

            while (!deflater.finished()) {
                int len = deflater.deflate(buffer);
                out.writeBytes(buffer, 0, len);
            }
            return out;
        }
    },

    /**
     * Detects what compression the data stream uses, then
     * extracts and compresses using it.
     */
    DETECTED {
        @Override
        public ByteBuf extract(ByteBuf buf) {
            return detect(buf).extract(buf);
        }

        @Override
        public ByteBuf compress(ByteBuf buf) {
            return detect(buf).compress(buf);
        }
    };

    /**
     * Extracts a given ByteBuf.
     *
     * @param buf The buffer to be extracted.
     *
     * @return The extracted buffer.
     */
    public abstract ByteBuf extract(ByteBuf buf);

    /**
     * Compresses a given ByteBuf.
     *
     * @param buf The buffer to be compressed.
     *
     * @return The compressed buffer.
     */
    public abstract ByteBuf compress(ByteBuf buf);

    private static NBTCompression detect(byte a, byte b) {
        if (a == (byte) 0x1F && b == (byte) 0x8B)
            return GZIP;
        if (a == (byte) 0x78 && (b == (byte) 0x01 || b == (byte) 0x9C || b == (byte) 0xDA))
            return ZLIB;
        return UNCOMPRESSED;
    }

    /**
     * Detects and retrieves the compression used on the given ByteBuf.
     *
     * @param buf The buffer to check the compression of.
     *
     * @return The compression stream, or {@link #UNCOMPRESSED} if not recognized.
     */
    public static NBTCompression detect(ByteBuf buf) {
        if (buf.readableBytes() < 2)
            return UNCOMPRESSED;
        byte a = buf.getByte(0), b = buf.getByte(1);
        return detect(a, b);
    }

    /**
     * Detects and retrieves the compression used on the given InputStream.
     *
     * @param buf The buffer to check the compression of.
     *
     * @return The compression stream, or {@link #UNCOMPRESSED} if not recognized.
     */
    public static NBTCompression detect(InputStream is) {
        try {
            if (is.available() < 2)
                return UNCOMPRESSED;
            if (is.markSupported())
                is.mark(3);
            int a = is.read(), b = is.read();
            if (is.markSupported())
                is.reset();
            if (a == -1 || b == -1)
                return UNCOMPRESSED;
            return detect((byte) a, (byte) b);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
