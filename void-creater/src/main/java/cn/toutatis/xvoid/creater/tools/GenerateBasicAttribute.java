package cn.toutatis.xvoid.creater.tools;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Toutatis_Gc
 */
public class GenerateBasicAttribute extends HashMap<String,Object> implements Serializable {

    private ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    public GenerateBasicAttribute(){
        this.put("author",ConfigurationTable.AUTHOR_NAME.getInfo());
        this.put("createTime", manifestToolkit.getCurrentTime());
    }
}
