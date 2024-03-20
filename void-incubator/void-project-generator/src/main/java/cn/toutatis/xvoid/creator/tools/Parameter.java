package cn.toutatis.xvoid.creator.tools;

import java.util.HashMap;

/**
 * @author Toutatis_Gc
 */
public class Parameter extends HashMap<String,Object> {

    private ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    private String dataSchema = manifestToolkit.getConfigProperties("dataSchema");

    public Parameter(){
        this.put("dataSchema",dataSchema);
    }

    public String getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(String dataSchema) {
        this.dataSchema = dataSchema;
    }
}
