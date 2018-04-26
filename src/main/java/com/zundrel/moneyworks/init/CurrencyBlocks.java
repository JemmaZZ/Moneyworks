package com.zundrel.moneyworks.init;

import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.blocks.BlockATM;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Moneyworks.MOD_ID)
public class CurrencyBlocks {

    public static final Block atm = new BlockATM();

    @SubscribeEvent
    public static void onBlockRegistration(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        registerBlock(registry, atm, "atm");
    }

    @SubscribeEvent
    public static void onItemRegistration(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registerItem(registry, atm, "atm");
    }

    private static ResourceLocation registerBlock(IForgeRegistry<Block> registry, Block block, String name) {
        ResourceLocation rl = new ResourceLocation(Moneyworks.MOD_ID, name);
        block.setRegistryName(rl);
        block.setUnlocalizedName(rl.toString());
        registry.register(block);
        return rl;
    }

    private static void registerBlock(IForgeRegistry<Block> registry, Block block, Class<? extends TileEntity> teClass, String name) {
        ResourceLocation rl = registerBlock(registry, block, name);
        GameRegistry.registerTileEntity(teClass, rl.toString());
    }

    private static ItemBlock registerItem(IForgeRegistry<Item> registry, Block block, String name) {
        ResourceLocation rl = new ResourceLocation(Moneyworks.MOD_ID, name);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(rl);
        item.setUnlocalizedName(rl.toString());
        registry.register(item);
        return item;
    }

}
