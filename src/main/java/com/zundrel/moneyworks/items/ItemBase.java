package com.zundrel.moneyworks.items;

import com.zundrel.moneyworks.creativetabs.CreativeTabCurrency;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase() {
        super();
        setCreativeTab(CreativeTabCurrency.instance);
    }

}
