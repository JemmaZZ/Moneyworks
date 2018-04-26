package com.zundrel.moneyworks.init;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.api.MoneyModel;
import com.zundrel.moneyworks.api.Currency;
import com.zundrel.moneyworks.api.Denomination;
import com.zundrel.moneyworks.api.Location;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.DosFileAttributes;
import java.util.*;

public class CurrencyParser {

    public static void initDir(FMLPreInitializationEvent event) throws IOException {
        File dir = new File(event.getModConfigurationDirectory(), "moneyworks");

        if (!dir.exists()) {
            dir.mkdir();
        }

        File dir2 = new File(dir, "resources");

        if (!dir2.exists()) {
            dir2.mkdir();
        }

        File dir3 = new File(dir2, "textures/items");

        if (!dir3.exists()) {
            dir3.mkdirs();
        }

        File dir4 = new File(dir2, "lang");

        if (!dir4.exists()) {
            dir4.mkdir();
        }

        File json = new File(dir, "pack.json");

        if (!json.exists()) {
            json = new File(dir, "pack.json");
            json.createNewFile();

            Map<String, Denomination> denominations = new HashMap<>();

            denominations.put("penny", new Denomination(0.01F));
            denominations.put("nickel", new Denomination(0.05F));
            denominations.put("dime", new Denomination(0.10F));
            denominations.put("quarter", new Denomination(0.25F));
            denominations.put("dollar", new Denomination(1.00F));
            denominations.put("dollar_five", new Denomination(5.00F));
            denominations.put("dollar_ten", new Denomination(10.00F));
            denominations.put("dollar_twenty", new Denomination(20.00F));
            denominations.put("dollar_fifty", new Denomination(50.00F));
            denominations.put("dollar_hundred", new Denomination(100.00F));

            Currency currency = new Currency("UMD", "Universal Minecraft Dollar", "$", denominations);

            String jsonString = new Gson().toJson(currency, Currency.class);

            JsonParser parser = new JsonParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonElement el = parser.parse(jsonString);
            jsonString = gson.toJson(el);

            FileWriter writer = new FileWriter(json);
            writer.write(jsonString);
            writer.flush();
            writer.close();
        }

        Moneyworks.proxy.addResourcePack(Moneyworks.preevent);
        Moneyworks.proxy.refreshResources();
    }

    public static Currency parse(FMLPreInitializationEvent event) throws IOException {
        Currency returnCurrency = null;

        initDir(event);

        File json = new File(event.getModConfigurationDirectory(), "moneyworks/pack.json");

        if (json.exists()) {
            String name = (json.getName().split("\\."))[0];
            String type = (json.getName().split("\\."))[1];

            if (name.equals("pack") && type.equals("json")) {
                JsonReader reader = new JsonReader(new FileReader(json));
                Currency currency = new Gson().fromJson(reader, Currency.class);

                if (currency != null) {
                    returnCurrency = currency;
                }
            }
        }

        File resources = new File(event.getModConfigurationDirectory(), "moneyworks/resources/models/item");

        if (resources.exists()) {
            resources.delete();
        }

        resources.mkdirs();

        if (returnCurrency != null) {
            for (String name : returnCurrency.getDenominations().keySet()) {
                json = new File(resources, name + ".json");
                json.createNewFile();

                MoneyModel model = new MoneyModel("item/generated", new Location("moneyworks:items/" + name));

                String jsonString = new Gson().toJson(model, MoneyModel.class);

                JsonParser parser = new JsonParser();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                JsonElement el = parser.parse(jsonString);
                jsonString = gson.toJson(el);

                FileWriter writer = new FileWriter(json);
                writer.write(jsonString);
                writer.flush();
                writer.close();
            }

            Moneyworks.proxy.refreshResources();

            return returnCurrency;
        }
        return null;
    }

}
