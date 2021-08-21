package com.hu.fypimplbackend.serviceImpls

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.util.IOUtils
import com.hu.fypimplbackend.services.FileStore
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class FileStoreImpl(
    @Autowired
    private val amazonS3: AmazonS3

) : FileStore {

    private val logger: Logger = LoggerFactory.getLogger(FileStoreImpl::class.java)

    /**
     * Function responsible to upload the images to specified bucket in S3Bucket
     *
     * @param path path on the Amazon S3 Bucket where the file will be stored
     * @param fileName fileName is the actual name of the file being uploaded. It will ve used as the key when downloading the file from S3
     * @param optionalMetadata map contains the details of the file i.e. file type and the file size
     * @param inputStream contains the actual file that should be saved to the Amazon S3
     *
     * @return Unit
     *
     * @throws IllegalArgumentException
     */
    @Throws(IllegalArgumentException::class)
    override fun upload(
        path: String,
        fileName: String,
        optionalMetadata: Optional<Map<String, String>>,
        inputStream: InputStream
    ) {
        val objectMetadata = ObjectMetadata()

        optionalMetadata.ifPresent { map ->
            if (map.isNotEmpty()) {
                map.forEach { (key: String?, value: String?) -> objectMetadata.addUserMetadata(key, value) }
            }
        }
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata)
            logger.info("Image uploaded Successfully")
        } catch (e: AmazonServiceException) {
            logger.error("Image failed to upload")
            throw IllegalArgumentException("Failed to upload the file", e)
        }
    }

    /**
     * Function responsible to download the images from the S3Bucket
     *
     * @param path is the bucket name
     * @param key is the actual fileName
     *
     * @return ByteArray
     *
     * @throws IllegalStateException
     */
    @Throws(IllegalStateException::class)
    override fun download(path: String, key: String): ByteArray {
        try {
            val s3Object: S3Object = amazonS3.getObject(path, key)
            val objectContent: S3ObjectInputStream = s3Object.objectContent
            logger.info("Image downloaded Successfully")
            return IOUtils.toByteArray(objectContent)
        } catch (e: Exception) {
            logger.error("Image failed to download")
            throw IllegalStateException("Failed to download the file", e)
        }
    }
}