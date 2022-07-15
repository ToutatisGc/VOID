<#import "/root/component/init/INDEX-INIT.ftlh" as INIT>
<#assign projectPath=request.contextPath/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>视频上传测试</title>
    <@INIT.importScript openFlex=true/>
</head>
<body>
<form id="uploadForm" enctype="multipart/form-data">
    <input type="file" accept="video/mp4,image/jpeg" name="file" id="file" multiple>
</form>
<button id="button">获取内容</button>
</body>
<script>
    $('#button').click(() => {
        // let files = $('#file')[0].files;
        console.log($('#uploadForm')[0])
        let formData = new FormData($('#uploadForm')[0]);
        formData.append("limitSize","100");
        console.log(formData.getAll("file"));
        <#--$.ajax({-->
        <#--    url:'${projectPath}/test/multiUpload',-->
        <#--    type:'POST',-->
        <#--    async: false,-->
        <#--    data: formData,-->
        <#--    processData : false,-->
        <#--    contentType : false,-->
        <#--    success: function(data){-->
        <#--        alert('上传成功！');-->
        <#--    },-->
        <#--    error:function(response){-->
        <#--        console.log(response);-->
        <#--    }-->
        <#--});-->
    })
</script>
</html>