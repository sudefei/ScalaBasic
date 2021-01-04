package com.sdf.test;

import org.junit.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @Author defei.su
 * @Date 2020/8/17 10:10
 */
public class testUUID {

    @Test
    public void test01(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }


    /**
     * java 8 新特性，Stream的使用
     *
     */
    @Test
    public void test02(){
        //创建流
        Stream<String> streamNew = Stream.of("java", "spark", "hadoop", "sqoop", "pig");


    }


    @Test
    public void testString(){
        String s="oracle_MCMES \n" +
                "oracle_MCMES_ATH\n" +
                "oracle_MCMES_DC\n" +
                "oracle_MCMES_PLN\n" +
                "oracle_MCMES_PRM\n" +
                "oracle_MCMES_STATS\n" +
                "oracle_MCMES_STD\n" +
                "oracle_MCMES_WIP";
        String s1="channel_MCMES \n" +
                "channel_MCMES_ATH\n" +
                "channel_MCMES_DC\n" +
                "channel_MCMES_PLN\n" +
                "channel_MCMES_PRM\n" +
                "channel_MCMES_STATS\n" +
                "channel_MCMES_STD\n" +
                "channel_MCMES_WIP";
        String s2="phoenix_MCMES \n" +
                "phoenix_MCMES_ATH\n" +
                "phoenix_MCMES_DC\n" +
                "phoenix_MCMES_PLN\n" +
                "phoenix_MCMES_PRM\n" +
                "phoenix_MCMES_STATS\n" +
                "phoenix_MCMES_STD\n" +
                "phoenix_MCMES_WIP";
        String replace = s1.replace("\n", "\t");
        String replace_02 = s2.replace("\n", "\t");

        System.out.println(replace);
        System.out.println(replace_02);


    }


    @Test
    public void testReplace(){
        String s="{\"sql\":\"insert into \\\"MCMES_PLN\\\".\\\"KQ_PLN_MONPRODDET\\\"(\\\"ID\\\",\\\"PRODPLANID\\\",\\\"MATID\\\",\\\"MATCODE\\\",\\\"MATNAME\\\",\\\"PLANOUTPUT\\\",\\\"UOMID\\\",\\\"WORKSEQUENCE\\\",\\\"LINEAREAID\\\",\\\"PLANSTARTDATE\\\",\\\"PLANFINISHDATE\\\",\\\"TRADENAMEMTYPE\\\",\\\"CODESTATUS\\\",\\\"DATAVERSION\\\",\\\"REMARK\\\",\\\"CREATORCODE\\\",\\\"CREATEDTIME\\\",\\\"REDACTORCODE\\\",\\\"REDATETIME\\\",\\\"ORGCODE\\\",\\\"LINEAREACODE\\\",\\\"LINEAREANAME\\\",\\\"UOMCODE\\\",\\\"UOMNAME\\\",\\\"COLORFLAG\\\",\\\"ORDERNUMBER\\\",\\\"PRDPOINTCODE\\\",\\\"PRDPOINTNAME\\\",\\\"MATIDXN\\\",\\\"MATCODEXN\\\",\\\"MATNAMEXN\\\") values ('ceef887d-7334-41e2-a988-aba61aa6fb7c','a5133760-cc06-48a8-b4ae-81ebeb3aee5a','ZXDMCODE0000128226','0303024902',UNISTR('\\\\7EA2\\\\53CC\\\\559C(\\\\786C\\\\51FA\\\\53E3)K\\\\7248\\\\7F8E\\\\56FD\\\\7248-1\\\\6D66\\\\4E1C'),'100','abc68a09-9494-4131-85c4-6b6214ed3c4e','1976975','ZXDMCODE0000109155',TO_DATE('26-10-2020 00:00:00', 'DD-MM-YYYY HH24:MI:SS'),TO_DATE('26-10-2020 00:00:00', 'DD-MM-YYYY HH24:MI:SS'),'M','CREATED','0',NULL,'99999',TO_TIMESTAMP('2020-10-09 13:46:50.642169'),'99999',TO_TIMESTAMP('2020-10-09 13:46:50.642169'),'SHYCJT','Y7',UNISTR('Y7\\\\533A\\\\57DF'),'8',UNISTR('\\\\4E07\\\\652F'),'0','1976975','KJYQ',UNISTR('\\\\79D1\\\\6280\\\\56ED\\\\533A'),'ZXDMCODE0000128209','0303X05902','红双喜(硬出口)LIP版浦东')\"}";
        String replace = s.substring(8, s.length() - 2).replace("\\\"", "");
        System.out.println(replace);
        Pattern INSERT_PATTERN = Pattern.compile("^insert into (.*?)\\.(.*?)\\((.*?)\\) values \\((.*?)\\)$");
        String COLUMN_SPLIT = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        String COLUMN_VAL_SPLIT = ",(?=([^\\']*\\'[^\\']*\\')*[^\\']*$)(?![^(（]*\\))";

        Matcher matcher = INSERT_PATTERN.matcher(replace);
        if (matcher.find()){
            String[] split = matcher.group(3).split(COLUMN_SPLIT, -1);
            for (String str:split){
                System.out.println(str);
            }
        }



    }



}