package com.zundrel.moneyworks.network;

import com.zundrel.moneyworks.Moneyworks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {
	@SideOnly(Side.CLIENT)
	public abstract IMessage handleClientMessage(EntityPlayer paramEntityPlayer, T paramT, MessageContext paramMessageContext);

	public abstract IMessage handleServerMessage(EntityPlayer paramEntityPlayer, T paramT, MessageContext paramMessageContext);

	@Override
	public IMessage onMessage(T message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			return handleClientMessage(Moneyworks.proxy.getPlayerEntity(ctx), message, ctx);
		}
		return handleServerMessage(Moneyworks.proxy.getPlayerEntity(ctx), message, ctx);
	}
}
