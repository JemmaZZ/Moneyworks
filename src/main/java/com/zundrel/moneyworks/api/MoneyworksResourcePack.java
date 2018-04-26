package com.zundrel.moneyworks.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MoneyworksResourcePack implements IResourcePack
{
    boolean debug = false;

    @Override
    public InputStream getInputStream(ResourceLocation rl) throws IOException
    {
        if (resourceExists(rl) && rl.getResourceDomain().equals("moneyworks")) {
            File file = new File(new File(Minecraft.getMinecraft().mcDataDir, "config/moneyworks/resources/"), rl.getResourcePath());

            return new FileInputStream(file);
        }
        return null;
    }

    @Override
    public boolean resourceExists(ResourceLocation rl)
    {
        File fileRequested = new File(new File(Minecraft.getMinecraft().mcDataDir, "config/moneyworks/resources/"), rl.getResourcePath());

        return fileRequested.isFile();
    }

    @Override
    public Set getResourceDomains()
    {
        File folder = new File(Minecraft.getMinecraft().mcDataDir, "config/moneyworks/resources");
        if (!folder.exists())
        {
            folder.mkdir();
        }
        HashSet<String> folders = new HashSet<String>();

        File[] resourceDomains = folder.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);

        for (File resourceFolder : resourceDomains)
        {
            if (resourceFolder.getName().equals("debug"))
            {
                debug = true;
            }
        }

        for (File resourceFolder : resourceDomains)
        {
            if (resourceFolder.getName().equals("models") || resourceFolder.getName().equals("textures")) {
                folders.add("moneyworks");
            }
            System.out.println(resourceFolder.getName());
        }

        return folders;
    }

    @Override
    public IMetadataSection getPackMetadata(MetadataSerializer p_135058_1_, String p_135058_2_) throws IOException
    {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException
    {
        return null;
    }

    @Override
    public String getPackName()
    {
        return "MoneyResources";
    }
}