package com.galygious.galysentials;

import net.minecraft.util.math.BlockPos;


public class Home {
    private final String name;
    private final int dimension;
    private final BlockPos pos;

    public Home(String name, int dimension, BlockPos pos) {
        this.name = name;
        this.dimension = dimension;
        this.pos = pos;
    }

    // Add getters here
}
