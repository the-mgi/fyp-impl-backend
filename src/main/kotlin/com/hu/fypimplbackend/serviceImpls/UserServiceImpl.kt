package com.hu.fypimplbackend.serviceImpls

import com.hu.fypimplbackend.config.ApplicationConfig
import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.user.UpdateUserDTO
import com.hu.fypimplbackend.repositories.UserRepository
import com.hu.fypimplbackend.services.IFileStore
import com.hu.fypimplbackend.services.IUserService
import org.apache.http.entity.ContentType.*
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class UserServiceImpl(
    @Autowired
    private val iFileStore: IFileStore,

    @Autowired
    private val applicationConfig: ApplicationConfig,

    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder,

    @Autowired
    private val loggerFactory: Logger

) : IUserService {
    @Throws(DataIntegrityViolationException::class)
    override fun saveUser(user: User): User {
        this.loggerFactory.info("saveUser in UserService")
        user.password = this.passwordEncoder.encode(user.password)
        val savedUser = this.userRepository.save(user)
        savedUser.password = null
        return savedUser
    }

    override fun getUser(username: String): User {
        this.loggerFactory.info("getUser in UserService")
        val optionalUser = this.userRepository.findByUsername(username);
        return if (optionalUser.isPresent) {
            optionalUser.get()
        } else {
            throw EntityNotFoundException()
        }
    }

    override fun deleteUser(username: String) {
        this.userRepository.deleteByUsername(username)
    }

    override fun updateProfileImage(username: String, multipartFile: MultipartFile): Pair<String, String> {
        if (multipartFile.isEmpty) {
            throw IllegalStateException("Cannot upload empty file")
        }
        if (!listOf(
                IMAGE_PNG.mimeType,
                IMAGE_BMP.mimeType,
                IMAGE_GIF.mimeType,
                IMAGE_JPEG.mimeType
            ).contains(multipartFile.contentType)
        ) {
            throw IllegalStateException("FIle uploaded is not an image")
        }

        // getting the file metadata
        val fileMetadata = hashMapOf(
            "Content-Type" to multipartFile.contentType!!,
            "Content-Length" to multipartFile.size.toString()
        )
        val path = "${this.applicationConfig.profileImageBucket}/${UUID.randomUUID()}"
        val fileName = multipartFile.originalFilename!!

        try {
            this.iFileStore.upload(path, fileName, Optional.of(fileMetadata), multipartFile.inputStream)
            loggerFactory.info("Image uploaded successfully")
            return Pair(path, fileName)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to upload file", e)
        }
    }

    override fun downloadImage(username: String): ByteArray {
        val user = this.userRepository.findByUsername(username)
        if (user.isPresent) {
            return this.iFileStore.download(user.get().imagePath!!, user.get().imageFileName!!)
        }
        return ByteArray(0)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = this.userRepository.findByUsername(username)
        this.loggerFactory.info("loadUserByUsername in UserService: ${user.isPresent}")
        if (user.isPresent) {
            val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
            user.get().roles.forEach { authorities.add(SimpleGrantedAuthority(it.roleName?.name)) }
            return org.springframework.security.core.userdetails.User(
                user.get().username,
                user.get().password,
                authorities
            )
        }
        throw UsernameNotFoundException("Username not found")
    }

    override fun updateUser(username: String, updateUserDTO: UpdateUserDTO): User {
        val oldUserOptional = this.userRepository.findByUsername(username)
        oldUserOptional.get().password = null
        return oldUserOptional.get()
//        if (oldUserOptional.isPresent) {
//            val oldUser = oldUserOptional.get()
//            this.userMapper.updateUserAsPerUpdateUserDTO(updateUserDTO, oldUser)
//            return oldUser
//        }
//        throw Exception("")
    }

}