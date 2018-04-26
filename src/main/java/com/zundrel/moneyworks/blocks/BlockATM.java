package com.zundrel.moneyworks.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockATM extends BlockFacingBase {

    public BlockATM() {
        super(Material.ROCK);
    }

    @Override
    public boolean isFull(IBlockState state) {
        return false;
    }

}
