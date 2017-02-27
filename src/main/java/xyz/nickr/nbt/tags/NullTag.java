package xyz.nickr.nbt.tags;

import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.nio.ByteOrder;

final class NullTag extends NBTTag {

    public static final NullTag INSTANCE = new NullTag();

    private NullTag() {}

    @Override
    protected void _print(PrintStream stream, String indent) {}

    @Override
    protected void _read(ByteBuf buf, ByteOrder order) {}

    @Override
    protected void _write(ByteBuf buf, ByteOrder order) {}

}
