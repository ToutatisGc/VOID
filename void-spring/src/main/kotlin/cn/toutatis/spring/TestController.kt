package cn.toutatis.spring

import cn.toutatis.data.common.ProxyResult
import cn.toutatis.data.common.ResultCode
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.github.xiaoymin.knife4j.annotations.ApiSupport
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * @author Toutatis_Gc
 * @date 2022/5/19 23:31
 * 测试swagger注解
 */
@Api(tags = ["测试控制器"], description = "描述测试")
@RestController
@ApiSupport(order = 0, author = "Toutatis_Gc")
class TestController {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var personMapper : PersonMapper

    @ApiOperation(value="获取用户信息",notes="注意问题点")
    @RequestMapping("/a",method=[RequestMethod.GET])
    fun test1():String{
        return "annotation"
    }

    @ApiOperation(value="测试返回Result",notes="注意问题点")
    @RequestMapping("/b",method=[RequestMethod.GET])
    fun test2(): ProxyResult {
        return ProxyResult(ResultCode.NORMAL_SUCCESS,"成功111",null)
    }

    @ApiOperation(value="测试jdbc",notes="注意问题点")
    @RequestMapping("/c",method=[RequestMethod.GET])
    fun test3(): ProxyResult {
        /*成功链接*/
        val queryForMap = jdbcTemplate.queryForMap("select * from person")
        return ProxyResult(ResultCode.NORMAL_SUCCESS,"成功111",null)
    }

    @ApiOperation(value="测试mapper",notes="注意问题点")
    @RequestMapping("/d",method=[RequestMethod.GET])
    fun test4(): ProxyResult {
        val selectList = personMapper.selectList(null)
        val person = Person()
        person.bindBaseProperties()
        person.name = "ABBC"
        person.insert()
        return ProxyResult(ResultCode.NORMAL_SUCCESS,"成功22",selectList)
    }

    @ApiOperation(value="测试model",notes="注意问题点")
    @RequestMapping("/e",method=[RequestMethod.GET])
    fun test5(): Person {
        val selectOne = personMapper.selectOne(QueryWrapper<Person>().eq("id", "de551ba2fa4510eb235da29b8d5d82f7"))
        selectOne.name = "CCD"
        selectOne.updateById()
        return selectOne
    }

}