package cn.toutatis.spring

import com.github.xiaoymin.knife4j.annotations.ApiSupport
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * @author Toutatis_Gc
 * @date 2022/5/19 23:31
 * 测试swagger注解
 */
@Api(tags = ["测试控制器","标签2测试"], description = "描述测试")
@RestController
@ApiSupport(order = 0, author = "Toutatis_Gc")
class TestController {


    @ApiOperation(value="获取用户信息",tags=["获取用户信息copy"],notes="注意问题点")
    @RequestMapping("/a",method=[RequestMethod.GET])

    fun test1():String{
        return "annotation"
    }

}