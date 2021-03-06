/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.toutatis.xvoid.creater.mybatisplus.generator;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.*;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.builder.ConfigBuilder;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.po.TableField;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.po.TableInfo;
import cn.toutatis.xvoid.creater.mybatisplus.generator.engine.AbstractTemplateEngine;
import cn.toutatis.xvoid.creater.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ็ๆๆไปถ
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
@Accessors(chain = true)
public class AutoGenerator {
    private static final Logger logger = Logger.getLogger(AutoGenerator.class);

    /**
     * ้็ฝฎไฟกๆฏ
     */
    protected ConfigBuilder config;
    /**
     * ๆณจๅฅ้็ฝฎ
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected InjectionConfig injectionConfig;
    /**
     * ๆฐๆฎๆบ้็ฝฎ
     */
    private DataSourceConfig dataSource;
    /**
     * ๆฐๆฎๅบ่กจ้็ฝฎ
     */
    private StrategyConfig strategy;
    /**
     * ๅ ็ธๅณ้็ฝฎ
     */
    private PackageConfig packageInfo;
    /**
     * ๆจกๆฟ ็ธๅณ้็ฝฎ
     */
    private TemplateConfig template;
    /**
     * ๅจๅฑ ็ธๅณ้็ฝฎ
     */
    private GlobalConfig globalConfig;
    /**
     * ๆจกๆฟๅผๆ
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * ็ๆไปฃ็?
     */
    public void execute() {
        logger.debug("[็ๆ]ๅๅค็ๆๆไปถ.");
        //TODO ็ๆ้กต้ข
        // ๅๅงๅ้็ฝฎ
        if (null == config) {
            config = new ConfigBuilder(packageInfo, dataSource, strategy, template, globalConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == templateEngine) {
            // ไธบไบๅผๅฎนไนๅ้ป่พ๏ผ้็จ Velocity ๅผๆ ใ ้ป่ฎค ใ
            // 2020-06 - 11 Toutatis_Gc ๆนๅไธบFreemarkerๅผๆ
            templateEngine = new FreemarkerTemplateEngine();
        }

        // ๆจกๆฟๅผๆๅๅงๅๆง่กๆไปถ่พๅบ
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("[็ๆ]ๆไปถ็ๆๅฎๆ.");
    }

    /**
     * ๅผๆพ่กจไฟกๆฏใ้ข็ๅญ็ฑป้ๅ
     *
     * @param config ้็ฝฎไฟกๆฏ
     * @return ignore
     */
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * ้ขๅค็้็ฝฎ
     *
     * @param config ๆป้็ฝฎไฟกๆฏ
     * @return ่งฃๆๆฐๆฎ็ปๆ้
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /*
         * ๆณจๅฅ่ชๅฎไน้็ฝฎ
         */
        if (null != injectionConfig) {
            injectionConfig.initMap();
            config.setInjectionConfig(injectionConfig);
        }
        /*
         * ่กจไฟกๆฏๅ่กจ
         */
        List<TableInfo> tableList = this.getAllTableInfoList(config);
        for (TableInfo tableInfo : tableList) {
            /* ---------- ๆทปๅ?ๅฏผๅฅๅ ---------- */
            if (config.getGlobalConfig().isActiveRecord()) {
                // ๅผๅฏ ActiveRecord ๆจกๅผ
                tableInfo.setImportPackages(Model.class.getCanonicalName());
            }
            if (tableInfo.isConvert()) {
                // ่กจๆณจ่งฃ
                tableInfo.setImportPackages(TableName.class.getCanonicalName());
            }
            if (config.getStrategyConfig().getLogicDeleteFieldName() != null && tableInfo.isLogicDelete(config.getStrategyConfig().getLogicDeleteFieldName())) {
                // ้ป่พๅ?้คๆณจ่งฃ
                tableInfo.setImportPackages(TableLogic.class.getCanonicalName());
            }
            if (StringUtils.isNotBlank(config.getStrategyConfig().getVersionFieldName())) {
                // ไน่ง้ๆณจ่งฃ
                tableInfo.setImportPackages(Version.class.getCanonicalName());
            }
            boolean importSerializable = true;
            if (StringUtils.isNotBlank(config.getSuperEntityClass())) {
                // ็ถๅฎไฝ
                tableInfo.setImportPackages(config.getSuperEntityClass());
                importSerializable = false;
            }
            if (config.getGlobalConfig().isActiveRecord()) {
                importSerializable = true;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
            // Boolean็ฑปๅisๅ็ผๅค็
            if (config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()
                && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
                List<TableField> tableFields = tableInfo.getFields().stream().filter(field -> "boolean".equalsIgnoreCase(field.getPropertyType()))
                    .filter(field -> field.getPropertyName().startsWith("is")).collect(Collectors.toList());
                tableFields.forEach(field -> {
                    //ไธป้ฎไธบis็ๆๅตๅบๆฌไธๆฏไธๅญๅจ็.
                    if (field.isKeyFlag()) {
                        tableInfo.setImportPackages(TableId.class.getCanonicalName());
                    } else {
                        tableInfo.setImportPackages(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                    }
                    field.setConvert(true);
                    field.setPropertyName(StringUtils.removePrefixAfterPrefixToLower(field.getPropertyName(), 2));
                });
            }
        }
        return config.setTableInfoList(tableList);
    }

    public InjectionConfig getCfg() {
        return injectionConfig;
    }

    public AutoGenerator setCfg(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }
}
