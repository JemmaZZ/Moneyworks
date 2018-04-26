package com.zundrel.moneyworks.api;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Map;

public class Currency {

    private String code;
    private String name;
    private String symbol;
    private Map<String, Denomination> denominations;

    public Currency (String code, String name, String symbol, Map<String,Denomination> denominations) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.denominations = denominations;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Map<String, Denomination> getDenominations() {
        return denominations;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDenominations(Map<String, Denomination> denominations) {
        this.denominations = denominations;
    }

    @Override
    public String toString() {
        return String.format("code:%s,name:%s,symbol:%s,denominations:%s", code, name, symbol, denominations);
    }
}
