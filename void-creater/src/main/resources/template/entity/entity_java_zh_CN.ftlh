
public class UserPermission extends Model<UserPermission> {

    private static final long serialVersionUID = 1L;

    <#list tableInfo as map>
        <#list map?keys as itemKey>
            ${itemKey} : ${map[itemKey]}
        </#list>
    </#list>

    @TableId
    private Integer id;
    /**
    * 路由路径
    */
    private String url;

    /**
    * 注释
    */
    private String comments;
}