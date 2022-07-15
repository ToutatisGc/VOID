<#assign projectPath=request.contextPath/>
<#import "/root/component/init/INDEX-INIT.ftlh" as INIT/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>测试</title>
    <@INIT.importScript openFlex=false hiddenVersion=true></@INIT.importScript>
    <link href="${projectPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${projectPath}/static/js/cropper/css/cropper.min.css" rel="stylesheet">
    <link href="${projectPath}/static/js/cropper/css/main.css" rel="stylesheet">

    <!-- Scripts -->
    <script src="${projectPath}/static/js/bootstrap.min.js"></script>
    <script src="${projectPath}/static/js/cropper/js/cropper.min.js"></script>
    <script src="${projectPath}/static/js/cropper/js/main.js"></script>
</head>
<body>
<div id="vueInstance" v-cloak>
    <img src="${projectPath}/resLoad/VOID_20200915_0/029ec76873ed4fecbecc62f4ab596e1d.jpg" style="height: 200px">
    <!-- Content -->
    <div class="container" id="container">
        <div style="display: flex;align-items: center;justify-content: center;border: 1px solid #ebebeb;width: 600px">
            <div class="img-container" id="img-container">
                <img src="" alt="选择图片">
            </div>
            <div class="docs-preview" style="width: 250px;height: 250px;margin-left: 5px;margin-top: 10px;">
                <div class="img-preview preview-lg" style="float: left"></div>
                <div class="img-preview preview-md" style="float: left"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <!-- <h3 class="page-header">Demo:</h3> -->

            </div>
            <div class="col-md-3">
                <!-- <h3 class="page-header">Preview:</h3> -->


                <!-- <h3 class="page-header">Data:</h3> -->
<#--                <div class="docs-data">-->
<#--                    <div class="input-group">-->
<#--                        <label class="input-group-addon" for="dataX">X</label>-->
<#--                        <input class="form-control" id="dataX" type="text" placeholder="x">-->
<#--                        <span class="input-group-addon">px</span>-->
<#--                    </div>-->
<#--                    <div class="input-group">-->
<#--                        <label class="input-group-addon" for="dataY">Y</label>-->
<#--                        <input class="form-control" id="dataY" type="text" placeholder="y">-->
<#--                        <span class="input-group-addon">px</span>-->
<#--                    </div>-->
<#--                    <div class="input-group">-->
<#--                        <label class="input-group-addon" for="dataWidth">Width</label>-->
<#--                        <input class="form-control" id="dataWidth" type="text" placeholder="width">-->
<#--                        <span class="input-group-addon">px</span>-->
<#--                    </div>-->
<#--                    <div class="input-group">-->
<#--                        <label class="input-group-addon" for="dataHeight">Height</label>-->
<#--                        <input class="form-control" id="dataHeight" type="text" placeholder="height">-->
<#--                        <span class="input-group-addon">px</span>-->
<#--                    </div>-->
<#--                    <div class="input-group">-->
<#--                        <label class="input-group-addon" for="dataRotate">Rotate</label>-->
<#--                        <input class="form-control" id="dataRotate" type="text" placeholder="rotate">-->
<#--                        <span class="input-group-addon">deg</span>-->
<#--                    </div>-->
<#--                </div>-->
            </div>
        </div>
        <div class="row">
            <div class="col-md-9 docs-buttons">
                <div class="btn-group">
                    <label class="btn btn-primary btn-upload" for="inputImage" title="Upload image file">
                        <input class="sr-only" id="inputImage" name="file" type="file" accept="image/*">
                        <span class="docs-tooltip" data-toggle="tooltip" title="点击按钮上传图片">
                            <span class="el-icon-upload2">上传图片</span>
                        </span>
                    </label>
                </div>
                <div class="btn-group">
<#--                    <button class="btn btn-primary" data-method="setDragMode" data-option="crop" type="button" title="Crop">-->
<#--                        <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setDragMode&quot;, &quot;crop&quot;)">-->
<#--                            <i class="el-icon-full-screen"></i>-->
<#--                        </span>-->
<#--                    </button>-->
                    <button class="btn btn-primary" data-method="zoom" data-option="0.1" type="button" title="Zoom In">
                        <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;zoom&quot;, 0.1)">
                            <i class="el-icon-zoom-in"></i>
                        </span>
                    </button>
                    <button class="btn btn-primary" data-method="zoom" data-option="-0.1" type="button" title="Zoom Out">
                        <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;zoom&quot;, -0.1)">
                            <i class="el-icon-zoom-out"></i>
                        </span>
                    </button>
                </div>



                <!-- Show the cropped image in modal -->
                <div class="modal fade docs-cropped" id="getCroppedCanvasModal" aria-hidden="true"
                     aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button class="close" data-dismiss="modal" type="button" aria-hidden="true">&times;
                                </button>
                                <h4 class="modal-title" id="getCroppedCanvasTitle">Cropped</h4>
                            </div>
                            <div class="modal-body"></div>
                            <!-- <div class="modal-footer">
                              <button class="btn btn-primary" data-dismiss="modal" type="button">Close</button>
                            </div> -->
                        </div>
                    </div>
                </div><!-- /.modal -->

                <button class="btn btn-primary" data-method="getData" data-option="" data-target="#putData"
                        type="button">
          <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;getData&quot;)">
            Get Data
          </span>
                </button>
                <button class="btn btn-primary" data-method="getImageData" data-option="" data-target="#putData"
                        type="button">
          <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;getImageData&quot;)">
            Get Image Data
          </span>
                </button>
                <button class="btn btn-primary" data-method="getCanvasData" data-option="" data-target="#putData"
                        type="button">
          <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;getCanvasData&quot;)">
            Get Canvas Data
          </span>
                </button>
                <button class="btn btn-primary" data-method="setCanvasData" data-target="#putData" type="button">
          <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setCanvasData&quot;, data)">
            Set Canvas Data
          </span>
                </button>
                <button class="btn btn-primary" data-method="getCropBoxData" data-option="" data-target="#putData"
                        type="button">
          <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;getCropBoxData&quot;)">
            Get Crop Box Data
          </span>
                </button>
                <button class="btn btn-primary" data-method="setCropBoxData" data-target="#putData" type="button">
          <span class="docs-tooltip" data-toggle="tooltip" title="$().cropper(&quot;setCropBoxData&quot;, data)">
            Set Crop Box Data
          </span>
                </button>
                <input class="form-control" id="putData" type="text"
                       placeholder="Get data to here or set data with this value">

            </div><!-- /.docs-buttons -->
        </div>
    </div>
    <el-button type="primary" @click="getCanvas">渲染canvas</el-button>
    <img src="" id="base64Img" style="width: 300px;height: 300px;border: 1px solid red">

    <!-- Alert -->
    <div class="docs-alert"><span class="warning message"></span></div>

</div>
</body>
<script>
    let vueInstance = new Vue({
        el: '#vueInstance',
        data: {},
        methods:{
            getCanvas:function () {
                let cropper = $('.img-container > img').cropper('getCroppedCanvas');
                if (cropper instanceof HTMLElement){
                    let base64url = cropper.toDataURL('image/jpeg');
                    console.log(base64url);
                    $("#base64Img").attr("src",base64url);
                    let parameter = {
                        imageBase64String : base64url
                    };
                    $.post("${projectPath}/uploadBase",parameter,function (response) {
                        console.log(response);
                    })
                }else {
                    this.$message.error('请选择一张图片');
                }
            }
        }
    })
</script>
</html>