package com.hu.fypimplbackend.services

import java.io.InputStream
import java.util.*

interface IFileStore {
    fun upload(
        path: String,
        fileName: String,
        optionalMetadata: Optional<Map<String, String>>,
        inputStream: InputStream
    )

    fun download(path: String, key: String): ByteArray
}