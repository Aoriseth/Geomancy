package com.aoriseth.geomancy.registry;

public enum MiningLevel {
    WOOD(0),
    STONE(1),
    IRON(2),
    DIAMOND(3),
    NETHERITE(4);

    private int level;

    MiningLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
