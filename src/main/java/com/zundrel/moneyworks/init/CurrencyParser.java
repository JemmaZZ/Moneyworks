package com.zundrel.moneyworks.init;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.zundrel.moneyworks.Moneyworks;
import com.zundrel.moneyworks.api.Currency;
import com.zundrel.moneyworks.api.Denomination;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CurrencyParser {

    public static void initDir(FMLPreInitializationEvent event) throws IOException {
        File dir = new File(event.getModConfigurationDirectory(), "moneyworks");

        if (!dir.exists()) {
            dir = new File(event.getModConfigurationDirectory(), "moneyworks");
            dir.mkdir();
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
    }

    public static List<Currency> parse(FMLPreInitializationEvent event) throws IOException {
        List<Currency> currencies = new ArrayList<Currency>();

        initDir(event);

        File json = new File(event.getModConfigurationDirectory(), "moneyworks/pack.json");

        if (json.exists()) {
            String name = (json.getName().split("\\."))[0];
            String type = (json.getName().split("\\."))[1];

            if (name.equals("pack") && type.equals("json")) {
                System.out.println("This is a json file.");

                JsonReader reader = new JsonReader(new FileReader(json));
                JsonObject object = new JsonObject();
                Currency currency = new Gson().fromJson(reader, Currency.class);

                System.out.println(currency);
                if (currency != null) {
                    currencies.add(currency);
                }
            }
        }

        if (!currencies.isEmpty()) {
            return currencies;
        }
        return null;
    }

}
