package com.zundrel.moneyworks.handlers;

import com.zundrel.moneyworks.Moneyworks;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = Moneyworks.MOD_ID)
public class ConfigHandler {
	@Comment("Changes whether hostile mobs drop money or not.")
	public static boolean dropMoney = true;

	@Comment({ "Changes the divisor for dropping money from hostile mobs.", "This value is useless if dropMoney is false.", "Dropped Money Equation: health / mobDivisionValue." })
	public static float mobDivisionValue = 8F;

	@Comment({ "Changes position of the money gui.", "0 = Top Left. 1 = Top Right. 2 = Bottom Left. 3 = Bottom Right." })
	@RangeInt(min = 0, max = 3)
	public static int position = 0;
}