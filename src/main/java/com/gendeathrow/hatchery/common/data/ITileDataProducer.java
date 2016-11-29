package com.gendeathrow.hatchery.common.data;

import net.minecraft.tileentity.TileEntity;

public interface ITileDataProducer<T, E extends TileEntity> {
    T getValue(E tile);
}
