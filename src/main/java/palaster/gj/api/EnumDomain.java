package palaster.gj.api;

import net.minecraft.util.IStringSerializable;

public enum EnumDomain implements IStringSerializable {
    NONE("none"),
    CREATION("creation"),
    COMMUNITY("community"),
    LIFE("life");

    private String name;

    EnumDomain(String name) { this.name = name; }

    @Override
    public String getName() { return name; }

    @Override
    public String toString() { return getName(); }
}
