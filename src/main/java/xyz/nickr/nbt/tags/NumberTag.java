package xyz.nickr.nbt.tags;

public abstract class NumberTag extends NBTTag {

    @Override
    public abstract Number getAsNumber();

    /**
     * Sets the value of this NumberTag.
     *
     * @param number The new value.
     */
    public abstract void set(Number number);

}
