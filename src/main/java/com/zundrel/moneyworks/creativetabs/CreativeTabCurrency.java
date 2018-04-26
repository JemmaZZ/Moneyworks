package com.zundrel.moneyworks.creativetabs;

import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.init.CurrencyBlocks;
import com.zundrel.moneyworks.init.CurrencyItems;
import com.zundrel.moneyworks.items.ItemMoney;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CreativeTabCurrency extends CreativeTabs {

    public static final CreativeTabs instance = new CreativeTabCurrency();
    NonNullList<ItemStack> list;

    private CreativeTabCurrency() {
        super(Moneyworks.MOD_ID);
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(CurrencyBlocks.atm);
    }

    @Override
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        this.addBlock(CurrencyBlocks.atm);

        for (Float value : CurrencyItems.money.keySet()) {
            ItemMoney money = CurrencyItems.money.get(value);

            this.addItem(money);
        }
    }

    private void addItem(Item item) {
        item.getSubItems(this, list);
    }

    private void addBlock(Block block) {
        block.getSubBlocks(this, list);
    }

}
