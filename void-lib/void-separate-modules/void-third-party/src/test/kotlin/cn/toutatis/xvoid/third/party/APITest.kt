package cn.toutatis.xvoid.third.party

import cn.toutatis.xvoid.third.party.aMap.AMapWebAPI
import org.junit.Test

class APITest {

    @Test
    fun `test API Document`(): Unit {
        AMapWebAPI.WEATHER_INQUIRY.printAPI()

        AMapWebAPI.IP_LOCATION.printAPI()
    }

}