package com.zundrel.moneyworks.client;

import com.zundrel.moneyworks.CommonProxy;
import com.zundrel.moneyworks.init.CurrencyBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx);
    }

    @SubscribeEvent
    public void onModelRegister(ModelRegistryEvent event) {
        addModel(CurrencyBlocks.atm, 0, "inventory");
    }

    private void addModel(Block block, int meta, String name) {
        addModel(Item.getItemFromBlock(block), meta, name);
    }

    private void addModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), name));
    }

    private void addItemModel(Item item, int meta, String name) {
        ResourceLocation res = new ResourceLocation(item.getRegistryName().getResourceDomain(), name);
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(res, "inventory"));
    }

}
