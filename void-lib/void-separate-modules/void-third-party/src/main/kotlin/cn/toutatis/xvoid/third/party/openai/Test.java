package cn.toutatis.xvoid.third.party.openai;

import cn.toutatis.xvoid.third.party.aMap.AMapWebAPI;

public class Test {
    //完成对象的解析
    public static void main(String[] args) {
        //info对象

//        ApiDocumentInfo resolve = AMapAPI.CHECK_WEATHER_FORECASTS.resolve();
//        System.err.println(resolve);

        AMapWebAPI.CHECK_WEATHER_FORECASTS.printAPI();

//        StudyEnum.StudyEnumInfo info = StudyEnum.PHP.resolve();
//        System.out.println(info.getCostAnno().max());
//        System.out.println(info.getLogEnumAnno().open());
    }
}