package cn.toutatis.xvoid.creater;

import cn.toutatis.xvoid.creater.mapper.BasicMapper;
import cn.toutatis.xvoid.creater.tools.FreeMarkerConfiguration;
import cn.toutatis.xvoid.creater.tools.ManifestToolkit;
import cn.toutatis.xvoid.creater.tools.Parameter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.ibatis.session.SqlSession;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gc
 * ORM构建器
 */
public class ConstructionBuilder {

    private SqlSession sqlSession;
    private BasicMapper basicMapper;

    private ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();
    private FreeMarkerConfiguration freeMarkerConfiguration = FreeMarkerConfiguration.getInstance();

    private ConstructionBuilder(){}
    public ConstructionBuilder(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        basicMapper = sqlSession.getMapper(BasicMapper.class);
    }

    public void getTableField(String tableName) {
        Configuration configuration = freeMarkerConfiguration.getConfiguration();
        Parameter parameter = new Parameter();
        parameter.put("tableName",tableName);
        List<Map> tableInfo = basicMapper.getTableInfo(parameter);
        List<Map<String, Object>> maps = manifestToolkit.formatHumpNameForList(tableInfo);
        for (Map user : tableInfo) {
            System.err.println(user);
        }
        try {
            Template template = configuration.getTemplate("/entity/entity.ftlh");
            Map map = new HashMap();
            map.put("tableInfo",maps);
            FileWriter fileWriter = new FileWriter(new File(manifestToolkit.getConfigProperties("currentChoosePath")+"/"+tableName+".txt"));
            template.process(map,fileWriter);
            fileWriter.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }



}
