package com.zundrel.moneyworks.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.zundrel.moneyworks.api.Currency;
import com.zundrel.moneyworks.helpers.CurrencyHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMoney extends ItemBase {

    private Currency currency;
    private float value;

    public ItemMoney(Currency currency, float value) {
        super();
        this.currency = currency;
        this.value = value;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add("Name: " + currency.getName());
            tooltip.add("Code: " + currency.getCode());
        }

        tooltip.add("Value: " + CurrencyHelper.formatValue(currency, value));

        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add("Hold " + ChatFormatting.BOLD + ChatFormatting.AQUA + "SHIFT " + ChatFormatting.RESET + "for more information.");
        }
    }

    public Currency getCurrency() { return currency; }

    public float getValue() {
        return value;
    }

}
