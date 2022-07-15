<#assign projectPath=request.contextPath/>

<#--
图片上传组件 需要搭配imageUploadMethod使用
参数:
ref 元素引用(必须唯一而且和imageUploadMethod 的ref一致)
actionPath 上传地址
listType 主题 text/picture/picture-card
accept 设置可上传的文件类型
data 可以引用 vue data元素 也可以为JSON对象
autoUpload 是否为自动上传
openClear 开启清空文件列表按钮
limitNum 限制上传数量
limitSize 限制上传大小(Mb)
customTip 自定义提示
-->
<#macro imageUploadElement ref="upload" actionPath="/upload" listType="picture-card" accept=".jpg,.jpeg,.png"
        formData="{}" autoUpload="false" openClear=true limitNum=5 limitSize="1" customTip="只能上传jpg/png文件" showTip="true">
    <el-upload
               ref="${ref}" action="${projectPath}${actionPath}"
               list-type="${listType}" :data="${formData}" :headers="{limitSize:'${limitSize}'}"
               :auto-upload="${autoUpload}" :on-success="${ref}Success"
               accept="${accept}" :limit="${limitNum}"
               :on-success="${ref}Success"
               multiple>
        <i class="el-icon-plus"></i>
        <#if showTip == 'true'>
            <div slot="tip" class="el-upload__tip">${customTip},数量限制为 ${limitNum} 张,且每张不超过 ${limitSize}Mb</div>
        </#if>
    </el-upload>
    <#if autoUpload == 'false'>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="${ref}UploadFile">上传到服务器</el-button>
    </#if>
    <#if openClear>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="${ref}ClearFiles">清空</el-button>
    </#if>
</#macro>

<#--
图片上传组件的执行方法
需要搭配imageUploadElement使用
ref 需要和imageUploadElement中的ref一致
nested 上传成功后的回调
-->
<#macro imageUploadMethod ref="upload">
    ${ref}UploadFile:function () {
        this.$refs.${ref}.submit();
    },
    ${ref}Success:function (response,file,fileList) {
        <#nested>
    },
    ${ref}ClearFiles:function () {
        this.$refs.${ref}.clearFiles();
    },
</#macro>

