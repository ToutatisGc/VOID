package cn.toutatis.xvoid.spring.business

import cn.toutatis.xvoid.data.common.result.ProxyResult
import cn.toutatis.xvoid.data.common.result.ResultCode
import cn.toutatis.xvoid.data.common.result.branch.DetailedResult
import cn.toutatis.xvoid.support.spring.annotations.VoidController
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.github.xiaoymin.knife4j.annotations.ApiSupport
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * @author Toutatis_Gc
 * @date 2022/5/19 23:31
 * 测试swagger注解
 */
@Api(tags = ["测试控制器"], description = "描述测试")
@ApiSupport(order = 0, author = "Toutatis_Gc")
@VoidController
@RequestMapping("/test")
class TestController {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var personMapper : PersonMapper

    @ApiOperation(value="获取用户信息",notes="注意问题点")
    @RequestMapping("/a",method=[RequestMethod.POST])
    fun test1():String{
        return "annotation"
    }

    @ApiOperation(value="测试返回Result",notes="注意问题点")
    @RequestMapping("/b",method=[RequestMethod.GET])
    fun test2(): ProxyResult {
        return ProxyResult(ResultCode.NORMAL_SUCCESS, "成功111")
    }

    @ApiOperation(value="测试jdbc",notes="注意问题点")
    @RequestMapping("/c",method=[RequestMethod.GET])
    fun test3(): ProxyResult {
        /*成功链接*/
        val queryForMap = jdbcTemplate.queryForMap("select * from vb_person")
        return ProxyResult(ResultCode.NORMAL_SUCCESS, "成功111")
    }

    @ApiOperation(value="测试mapper",notes="注意问题点")
    @RequestMapping("/d",method=[RequestMethod.GET])
    fun test4(): ProxyResult {
        val selectList = personMapper.selectList(null)
        val person = Person()
//        person.bindBaseProperties()
        person.name = "ced"
        person.insert()
        return ProxyResult(ResultCode.NORMAL_SUCCESS, "成功")
    }

    @ApiOperation(value="测试model",notes="注意问题点")
    @RequestMapping("/e",method=[RequestMethod.GET])
    fun test5(): Person {
        val selectOne = personMapper.selectOne(QueryWrapper<Person>().eq("id", "642a775f05c11ea6479283361feac711"))
        selectOne.name = "CCD"
        selectOne.updateById()
        return selectOne
    }

    @ApiOperation(value="测试error",notes="注意问题点")
    @RequestMapping("/f",method=[RequestMethod.GET])
    fun test6(): ProxyResult {
        val i = 1/0
        return ProxyResult(ResultCode.NORMAL_SUCCESS, "成功")
    }

    @RequestMapping("/idx",method=[RequestMethod.GET])
    fun test7(): ModelAndView {
        val modelAndView = ModelAndView("index")
        return modelAndView
    }

    @RequestMapping("/g",method=[RequestMethod.GET])
    fun test8(): ProxyResult {
        val result = ProxyResult(ResultCode.NORMAL_SUCCESS)
        result.useDetailedMode = false
        return result
    }

}