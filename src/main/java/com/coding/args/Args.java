package com.coding.args;

import java.util.HashMap;
import java.util.Map;

public class Args {
    private final String argsText;
    private final SchemaDesc schemaDesc;

    public static Map<String, Object> argsMap = new HashMap<>();

    public Args(String argsText, SchemaDesc schemaDesc) {
        this.argsText = argsText;
        this.schemaDesc = schemaDesc;
        parse();
    }

    public Object getValue(String flag) {
        return argsMap.get(flag);
    }

    public void parse() {
        // -l -p 8080 -d /usr/log l:boolean p:integer d:string
        String[] argsTextArray = argsText.split("-");
        for (int i = 1; i < argsTextArray.length; i++) {
            System.out.println("oneArg is argsTextArray[" + i + "]===" + argsTextArray[i]);
            String[] oneArgsArray = argsTextArray[i].split(" ");
            String flag = oneArgsArray[0];
            String flagValue = oneArgsArray.length > 1 ? oneArgsArray[1] : schemaDesc.getDefaultValue(flag).toString();
            String type = schemaDesc.getType(flag);

            Object realValue = ArgsValueServiceFactory.getArgsValueServiceIFByStrategy(type).getArgsValue(flagValue);

            argsMap.put(flag, realValue);
        }
    }
}
