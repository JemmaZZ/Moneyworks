package com.zundrel.moneyworks.network;

import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.capabilities.AccountCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncAccount implements IMessage {
	private int entityId;
	private float amount;

	public MessageSyncAccount() {
	}

	public MessageSyncAccount(EntityLivingBase entity, float amount) {
		this.entityId = entity.getEntityId();
		this.amount = amount;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeFloat(amount);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.amount = buf.readFloat();
	}

	public static class Handler extends AbstractClientMessageHandler<MessageSyncAccount> {
		@Override
		public IMessage handleClientMessage(EntityPlayer player, MessageSyncAccount message, MessageContext ctx) {
			if ((player != null) && (message != null) && (ctx != null)) {
				EntityLivingBase en = (EntityLivingBase) player.getEntityWorld().getEntityByID(message.entityId);
				if (en != null) {
					if (en.getEntityWorld() != null && en.hasCapability(Moneyworks.ACCOUNT_DATA, null)) {
						AccountCapability entityData = en.getCapability(Moneyworks.ACCOUNT_DATA, null);
						entityData.setAmount(message.amount, false);
					}
				}
			}
			return null;
		}
	}

}