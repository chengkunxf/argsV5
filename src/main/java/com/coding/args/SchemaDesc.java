package com.coding.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SchemaDesc {
    private String schemaText;

    public static final Map<String, Object> defaultValueMap = new HashMap() {
        {
            put("boolean", false);
            put("integer", 0);
            put("string", "");
            put("string[]",new String[]{});
        }
    };

    public SchemaDesc(String schemaText) {
        this.schemaText = schemaText;
    }

    public int getSize() {
        return schemaText.split(" ").length;
    }

    public Object getDefaultValue(String flag) {
        // l:boolean
        String type = getType(flag);
        if (!defaultValueMap.containsKey(type)) {
            throw new IllegalArgumentException(String.format("This %s type is not support"));
        }
        return defaultValueMap.get(type);
    }

    public String getType(String flag) {
        // l:boolean p:integer
        String[] schemaArray = schemaText.split(" ");
        String oneSchema = Arrays.stream(schemaArray).filter(e -> e.startsWith(flag)).findAny().get();
        String type = oneSchema.split(":")[1];
        return type;
    }
}
