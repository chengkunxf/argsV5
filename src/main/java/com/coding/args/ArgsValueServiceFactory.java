package com.coding.args;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgsValueServiceFactory {

    public static final Map<String, ArgsValueServiceIF> argsValueServiceIFMap = new HashMap<>();

    static {
        argsValueServiceIFMap.put("boolean", (flagValue) -> Boolean.valueOf(flagValue));
        argsValueServiceIFMap.put("integer", (flagValue) -> Integer.valueOf(flagValue));
        argsValueServiceIFMap.put("string", (flagValue) -> flagValue);
        argsValueServiceIFMap.put("string[]", (flagValue) -> flagValue.split(","));
        argsValueServiceIFMap.put("integer[]", (flagValue) -> {
            String[] flagValueArray = flagValue.split(",");
            int[] ints = Arrays.stream(flagValueArray).mapToInt(Integer::parseInt).toArray();
            return ArrayUtils.toObject(ints);
        });
    }

    public static ArgsValueServiceIF getArgsValueServiceIFByStrategy(String type) {
        return argsValueServiceIFMap.get(type);
    }
}
