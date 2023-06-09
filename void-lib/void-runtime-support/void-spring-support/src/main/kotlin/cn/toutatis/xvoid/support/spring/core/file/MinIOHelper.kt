package cn.toutatis.xvoid.support.spring.core.file

import cn.toutatis.xvoid.support.PkgInfo
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * MinIO助手
 */
@Component
open class MinIOHelper {

    companion object{
        const val XVOID_SYSTEM_RESOURCE_BUCKET = "xvoid-system-material"

        const val XVOID_USER_RESOURCE_BUCKET = FileToolkit.RESOURCE_FILE_DIR
    }

    @Autowired
    private lateinit var voidConfiguration:VoidConfiguration

    private val logger = LoggerToolkit.getLogger(this.javaClass)

    private var minioClient:MinioClient? = null

    /**
     * bucketBucket23333333
     */
    private val bucketBucket = HashMap<String,Boolean>(16)

    /**
     * 根据配置创建客户端
     */
    @OptIn(InternalCoroutinesApi::class)
    fun getClient():MinioClient{
        if (minioClient == null){
            synchronized(this) {
                val minIoConfig = voidConfiguration.minIoConfig
                minioClient = MinioClient.builder()
                    .endpoint(minIoConfig.endpoint)
                    .credentials(minIoConfig.accessKey, minIoConfig.accessSecret)
                    .region(minIoConfig.region)
                    .build()
                logger.info("[${PkgInfo.MODULE_NAME}-MinIO]已创建MinIO客户端,连接端为[${minIoConfig.endpoint}],Access-Key为:[${minIoConfig.accessKey}]")
            }
        }
        return minioClient!!
    }

    /**
     * 创建桶
     * @param bucket 桶名称
     */
    fun bucket(bucket:String):String {
        if (!bucketBucket.containsKey(bucket)) {
            val bucketExistsArgs = BucketExistsArgs.builder()
            bucketExistsArgs.bucket(bucket)
            val bucketExists = minioClient!!.bucketExists(bucketExistsArgs.build())
            if (!bucketExists){
                val makeBucketArgs = MakeBucketArgs.builder()
                makeBucketArgs.objectLock(false)
                makeBucketArgs.bucket(bucket)
                minioClient!!.makeBucket(makeBucketArgs.build())
                logger.info("[${PkgInfo.MODULE_NAME}-MinIO]已创建桶[${bucket}]")
            }else{
                bucketBucket[bucket] = true
            }
        }
        return bucket
    }

}