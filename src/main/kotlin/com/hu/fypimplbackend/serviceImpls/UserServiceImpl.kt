package com.hu.fypimplbackend.serviceImpls

import com.hu.fypimplbackend.config.ApplicationConfig
import com.hu.fypimplbackend.repositories.UserRepository
import com.hu.fypimplbackend.services.FileStore
import com.hu.fypimplbackend.services.UserService
import org.apache.http.entity.ContentType.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Service
class UserServiceImpl(
    @Autowired
    private val fileStore: FileStore,

    @Autowired
    private val applicationConfig: ApplicationConfig,

    @Autowired
    private val userRepository: UserRepository

) : UserService {

    private val logger: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun updateProfileImage(username: String, multipartFile: MultipartFile): Pair<String, String> {
        if (multipartFile.isEmpty) {
            throw IllegalStateException("Cannot upload empty file");
        }
        if (!listOf(
                IMAGE_PNG.mimeType,
                IMAGE_BMP.mimeType,
                IMAGE_GIF.mimeType,
                IMAGE_JPEG.mimeType
            ).contains(multipartFile.contentType)
        ) {
            throw IllegalStateException("FIle uploaded is not an image");
        }

        // getting the file metadata
        val fileMetadata = hashMapOf(
            "Content-Type" to multipartFile.contentType!!,
            "Content-Length" to multipartFile.size.toString()
        )
        val path = "${this.applicationConfig.profileImageBucket}/${UUID.randomUUID()}"
        val fileName = multipartFile.originalFilename!!

        try {
            this.fileStore.upload(path, fileName, Optional.of(fileMetadata), multipartFile.inputStream)
            logger.info("Image uploaded successfully")
            return Pair(path, fileName)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to upload file", e)
        }
    }

    override fun downloadImage(username: String): ByteArray {
        val user = this.userRepository.findByUsername(username)
        if (user.isPresent) {
            return this.fileStore.download(user.get().imagePath!!, user.get().imageFileName!!)
        }
        return ByteArray(0)
    }
}