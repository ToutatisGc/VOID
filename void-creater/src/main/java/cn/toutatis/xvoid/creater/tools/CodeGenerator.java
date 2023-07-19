package cn.toutatis.xvoid.creater.tools;


import cn.toutatis.xvoid.creater.mybatisplus.generator.AutoGenerator;
import cn.toutatis.xvoid.creater.mybatisplus.generator.InjectionConfig;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.*;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.rules.NamingStrategy;
import cn.toutatis.xvoid.creater.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.spring.support.enhance.orm.mybatisplus.support.VoidMybatisService;
import cn.toutatis.xvoid.spring.support.enhance.orm.mybatisplus.support.VoidMybatisServiceImpl;

/**
 * @author Toutaits_Gc
 */
public class CodeGenerator {

    private static ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    public static void tableGenerate(String[] tableListArrayString) {
        // 代码生成器 执行生成execute()
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setActiveRecord(true);
        String currentChoosePath = manifestToolkit.getConfigProperties("currentChoosePath");
        String devLang = manifestToolkit.getConfigProperties("devLang");
        globalConfig.setOutputDir(currentChoosePath + String.format("/src/main/%s/",devLang));
        globalConfig.setAuthor(manifestToolkit.getConfigProperties("author"));
        globalConfig.setOpen(false);

//        文件覆盖
        globalConfig.setFileOverride(true);
        globalConfig.setSwagger2(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useSwagger")));
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = manifestToolkit.getDataSourceConfig();
        autoGenerator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        String packagePath = manifestToolkit.getConfigProperties("packagePath");
        int lastIndexOfPoint = packagePath.lastIndexOf(".");
        String endWith = packagePath.substring(lastIndexOfPoint+1);
        String packagePathTemp = packagePath.substring(0, lastIndexOfPoint);
        packageConfig.setModuleName(endWith);
        packageConfig.setParent(packagePathTemp);
        packageConfig.setMapper("persistence");
//        packageConfig.setXml("");
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
//                Map generateBasicAttribute = new GenerateBasicAttribute();
//                setMap(generateBasicAttribute);
            }
        };

        // 自定义输出配置
        //freemarker
//        String templatePath = "/templates/mapper.xml_zh_CN.ftl";
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return currentChoosePath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
//        勾选不生成controller
        if (!Boolean.parseBoolean(manifestToolkit.getConfigProperties("useGenerateController"))){
            templateConfig.disable(TemplateType.CONTROLLER);
        }
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategyConfig.setEntityLombokModel(Boolean.parseBoolean(manifestToolkit.getConfigProperties("useLombok")));
        strategyConfig.setRestControllerStyle(true);


        strategyConfig.setSuperServiceClass(VoidMybatisService.class);
        strategyConfig.setSuperServiceImplClass(VoidMybatisServiceImpl.class);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategyConfig.setSuperEntityClass(EntityBasicAttribute.class);
        strategyConfig.setSuperEntityColumns(
                "rInt","rStr","createTime","status","logicDeleted",
                "createBy","lastUpdateTime","updateBy","version","remark","belongTo");
        strategyConfig.setInclude(tableListArrayString);
        strategyConfig.setControllerMappingHyphenStyle(false);
        strategyConfig.setTablePrefix(manifestToolkit.getConfigProperties("tablePrefix"));
        autoGenerator.setStrategy(strategyConfig);

        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

}