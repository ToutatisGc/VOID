package cn.toutatis.xvoid.third.party.basic;

import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.third.party.openai.Cost;
import cn.toutatis.xvoid.toolkit.log.LogEnum;
import lombok.Data;

/**
 * @author Toutatis_Gc
 */
@Data
public class ApiDocumentInfo {

        private String name;

        private String description;

        private LogEnum logEnum;

        private APIDocument apiDocument;
    }