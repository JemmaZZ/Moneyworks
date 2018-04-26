package com.zundrel.moneyworks.handlers;

import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.capabilities.AccountCapability;
import com.zundrel.moneyworks.helpers.CurrencyHelper;
import com.zundrel.moneyworks.init.CurrencyItems;
import com.zundrel.moneyworks.items.ItemMoney;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Moneyworks.MOD_ID)
public class ModEventHandler {

    @SubscribeEvent
    public static void onLivingDropsEvent(LivingDropsEvent event) {
        if (ConfigHandler.dropMoney && !(event.getEntityLiving() instanceof EntityPlayer) && event.getEntityLiving() instanceof IMob && event.getEntityLiving().getEntityWorld().isRemote == false) {
            System.out.println("HEH");

            if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource().getTrueSource() instanceof FakePlayer)) {
                CurrencyHelper.dropChange(event.getEntityLiving(), event.getEntityLiving().getMaxHealth() / ConfigHandler.mobDivisionValue);
                return;
            }

            if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityArrow) {
                EntityArrow arrow = (EntityArrow) event.getSource().getTrueSource();
                if (arrow.shootingEntity instanceof EntityPlayer && !(arrow.shootingEntity instanceof FakePlayer)) {
                    CurrencyHelper.dropChange(event.getEntityLiving(), event.getEntityLiving().getMaxHealth() / ConfigHandler.mobDivisionValue);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRenderer;
        RenderItem renderItem = mc.getRenderItem();

        if (mc.player.isSneaking()) {
            if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
                return;
            }

            int position = ConfigHandler.position;

            GL11.glPushMatrix();

            ItemMoney moneyItem = null;

            for (ItemMoney money : CurrencyItems.money.values()) {
                moneyItem = money;
                break;
            }

            String physicalAmount = CurrencyHelper.formatValue(moneyItem.getCurrency(), CurrencyHelper.getInventoryAmount(mc.player));
            String accountAmount = mc.player.getCapability(Moneyworks.ACCOUNT_DATA, null).getFormattedAmount();

            if (position == 0 || position == 2) {
                if (position == 2) {
                    GL11.glTranslated(0, new ScaledResolution(mc).getScaledHeight() - (34), 0);
                }
                GL11.glPushMatrix();
                GL11.glScaled(3, 3, 0);
                ItemStack dollarBill = new ItemStack(moneyItem);
                GL11.glTranslated(1, -2.5, 0);
                renderItem.renderItemIntoGUI(dollarBill, 0, 0);
                GL11.glPopMatrix();

                fr.drawString("Physical: " + physicalAmount, (3 * 16) + 7, 8, 0x37A537);
                fr.drawString("Physical: " + physicalAmount, (3 * 16) + 6, 7, 0x55FF55);

                fr.drawString("Account: " + accountAmount, (3 * 16) + 7, 20, 0x37A537);
                fr.drawString("Account: " + accountAmount, (3 * 16) + 6, 19, 0x55FF55);
            } else if (position == 1 || position == 3) {
                if (position == 3) {
                    GL11.glTranslated(0, new ScaledResolution(mc).getScaledHeight() - (34), 0);
                }
                GL11.glTranslated(new ScaledResolution(mc).getScaledWidth() - 180, 0, 0);
                GL11.glPushMatrix();
                GL11.glScaled(3, 3, 0);
                ItemStack dollarBill = new ItemStack(moneyItem);
                GL11.glTranslated(1, -2.5, 0);
                renderItem.renderItemIntoGUI(dollarBill, 42, 0);
                GL11.glPopMatrix();

                fr.drawString("Physical: " + physicalAmount, (128 - fr.getStringWidth("Physical: " + physicalAmount)), 8, 0x37A537);
                fr.drawString("Physical: " + physicalAmount, (127 - fr.getStringWidth("Physical: " + physicalAmount)), 7, 0x55FF55);

                fr.drawString("Account: " + accountAmount, (128 - fr.getStringWidth("Account: " + accountAmount)), 20, 0x37A537);
                fr.drawString("Account: " + accountAmount, (127 - fr.getStringWidth("Account: " + accountAmount)), 19, 0x55FF55);
            }

            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Moneyworks.MOD_ID, "account"), new AccountCapability((EntityPlayer) event.getObject()));
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.getOriginal().hasCapability(Moneyworks.ACCOUNT_DATA, null)) {
            AccountCapability cap = event.getOriginal().getCapability(Moneyworks.ACCOUNT_DATA, null);
            AccountCapability newCap = event.getEntityPlayer().getCapability(Moneyworks.ACCOUNT_DATA, null);
            newCap.setAmount(cap.getAmount(), true);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoined(PlayerLoggedInEvent event) {
        if (event.player.hasCapability(Moneyworks.ACCOUNT_DATA, null)) {
            AccountCapability cap = event.player.getCapability(Moneyworks.ACCOUNT_DATA, null);
            cap.sendPacket();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        if (event.player.hasCapability(Moneyworks.ACCOUNT_DATA, null)) {
            AccountCapability cap = event.player.getCapability(Moneyworks.ACCOUNT_DATA, null);
            cap.sendPacket();
        }
    }

}
