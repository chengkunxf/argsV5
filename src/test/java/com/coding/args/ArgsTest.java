package com.coding.args;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ArgsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    /***
     * 1.写伪代码,框定需求范围,实现后,可以 Igonre 掉
     * 2.解析 schema,l:boolean p:integer d:string ,获取标记的 size,获取 defaultValue,获取 Type
     * 3.schema当类型不一样的时候,抛出错误信息
     * 4.解析 args 的命令,此处只解析一个正常的 args -l true l:boolean ,可以通过 args.getValue("l") 获得正确的类型值
     * 5.解析 args 命令,此处是 -l l:boolean 的解析 ,args.getValue("l")
     * 6.解析多个 args 命令, -l -p 8080 -d /usr/log l:boolean p:integer d:string args.getValue("d")
     * 7.解析数组
     */
    @Test
//    @Ignore
    public void should_test_demo() {

        String argsText = "-l -p 8080 -d /usr/log";

        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string");

        Args args = new Args(argsText, schemaDesc);
        assertThat(args.getValue("l"), is(false));
        assertThat(args.getValue("p"), is(8080));
        assertThat(args.getValue("d"), is("/usr/log"));

    }

    @Test
//    @Ignore
    public void should_test_array_demo() {

        String argsText = "-l -p 8080 -d /usr/log -g this,is,all -f 90,95,100";

        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string g:string[] f:integer[]");

        Args args = new Args(argsText, schemaDesc);
        assertThat(args.getValue("l"), is(false));
        assertThat(args.getValue("p"), is(8080));
        assertThat(args.getValue("d"), is("/usr/log"));
        assertThat(args.getValue("g"), is(new String[]{"this", "is", "all"}));
        assertThat(args.getValue("f"), is(new Integer[]{90, 95, 100}));

    }

    @Test
    public void should_parse_schema() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string");

        assertThat(schemaDesc.getSize(), is(3));
        assertThat(schemaDesc.getDefaultValue("l"), is(false));
        assertThat(schemaDesc.getDefaultValue("p"), is(0));
        assertThat(schemaDesc.getDefaultValue("d"), is(""));
        assertThat(schemaDesc.getType("l"), is("boolean"));
        assertThat(schemaDesc.getType("p"), is("integer"));
        assertThat(schemaDesc.getType("d"), is("string"));


    }

    @Test
    public void should_trhow_illegalArgumentException() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean p:integer d:string w:byte");
        thrown.expect(IllegalArgumentException.class);
        assertThat(schemaDesc.getDefaultValue("w"), is(false));
    }


    @Test
    public void should_parse_one_args_and_schema() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean");
        Args args = new Args("-l true", schemaDesc);
        assertThat(args.getValue("l"), is(true));

        schemaDesc = new SchemaDesc("p:integer");
        args = new Args("-p 8080", schemaDesc);
        assertThat(args.getValue("p"), is(8080));


        schemaDesc = new SchemaDesc("d:string");
        args = new Args("-d /usr/log", schemaDesc);
        assertThat(args.getValue("d"), is("/usr/log"));

    }

    @Test
    public void should_parse_one_defalut_args_and_schema() {
        SchemaDesc schemaDesc = new SchemaDesc("l:boolean");
        Args args = new Args("-l", schemaDesc);
        assertThat(args.getValue("l"), is(false));

        schemaDesc = new SchemaDesc("p:integer");
        args = new Args("-p", schemaDesc);
        assertThat(args.getValue("p"), is(0));


        schemaDesc = new SchemaDesc("d:string");
        args = new Args("-d", schemaDesc);
        assertThat(args.getValue("d"), is(""));

    }


}