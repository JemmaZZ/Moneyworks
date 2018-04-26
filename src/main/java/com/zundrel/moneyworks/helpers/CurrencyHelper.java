package com.zundrel.moneyworks.helpers;

import com.zundrel.moneyworks.api.Currency;
import com.zundrel.moneyworks.init.CurrencyItems;
import com.zundrel.moneyworks.items.ItemMoney;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.text.NumberFormat;
import java.util.*;

public class CurrencyHelper {

    public static NumberFormat getCurrencyInstance() {
        return NumberFormat.getCurrencyInstance(Locale.US);
    }

    public static String formatValue(Currency currency, float value) {
        return currency.getSymbol() + (formatValue(value).substring(1));
    }

    public static String formatValue(float value) {
        return getCurrencyInstance().format(value);
    }

    public static float getInventoryAmount(EntityPlayer player) {
        float value = 0;

        for (ItemStack stack : player.inventory.mainInventory) {
            if (!stack.isEmpty() && stack.getItem() instanceof ItemMoney) {
                value += ((ItemMoney) stack.getItem()).getValue();
            }
        }

        return value;
    }

    public static HashMap<Float, Integer> convertToChange(float value) {
        HashMap<Float, Integer> amounts = new HashMap();
        float totalValue = value;

        TreeMap<Float, ItemMoney> moneyList = new TreeMap<Float, ItemMoney>(Collections.reverseOrder());

        moneyList.putAll(CurrencyItems.money);

        for (float moneyValue : moneyList.keySet()) {
            ItemMoney money = moneyList.get(moneyValue);

            System.out.println(moneyValue);

            int amount = (int) (totalValue / moneyValue);
            totalValue = totalValue - moneyValue * amount;

            amounts.put(moneyValue, amount);
        }

        return amounts;
    }

    public static void dropChange(Entity entity, float value) {
        HashMap<Float, Integer> amount = convertToChange(value);

        for (float moneyValue : amount.keySet()) {
            for (float moneyValue2 : CurrencyItems.money.keySet()) {
                if (moneyValue == moneyValue2) {
                    ItemMoney money = CurrencyItems.money.get(moneyValue2);

                    System.out.println(amount.get(moneyValue));

                    EntityItem item = new EntityItem(entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ, new ItemStack(money, amount.get(moneyValue)));
                    entity.getEntityWorld().spawnEntity(item);
                }
            }
        }
    }

}
