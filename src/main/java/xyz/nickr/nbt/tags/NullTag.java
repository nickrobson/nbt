package xyz.nickr.nbt.tags;

import java.io.PrintStream;

import io.netty.buffer.ByteBuf;

final class NullTag extends NBTTag {

    public static final NullTag INSTANCE = new NullTag();

    private NullTag() {}

    @Override
    protected void _print(PrintStream stream, String indent) {}

    @Override
    protected void _read(ByteBuf buf) {}

    @Override
    protected void _write(ByteBuf buf) {}

}
