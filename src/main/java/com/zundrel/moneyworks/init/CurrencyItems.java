package com.zundrel.moneyworks.init;

import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.api.Currency;
import com.zundrel.moneyworks.api.Denomination;
import com.zundrel.moneyworks.items.ItemMoney;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

@Mod.EventBusSubscriber(modid = Moneyworks.MOD_ID)
public class CurrencyItems {

    public static TreeMap<Float, ItemMoney> money = new TreeMap<>();

    @SubscribeEvent
    public static void onItemRegistration(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        try {
            Currency currency = CurrencyParser.parse(Moneyworks.preevent);

            for (String name : currency.getDenominations().keySet()) {
                Denomination denomination = currency.getDenominations().get(name);
                registerItem(registry, new ItemMoney(currency, denomination.getValue()), name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ResourceLocation registerItem(IForgeRegistry<Item> registry, Item item, String name) {
        ResourceLocation rl = new ResourceLocation(Moneyworks.MOD_ID, name);
        item.setRegistryName(rl);
        item.setUnlocalizedName(rl.toString());
        registry.register(item);

        if (item instanceof ItemMoney) {
            money.put(((ItemMoney) item).getValue(), (ItemMoney) item);
        }

        return rl;
    }

}
