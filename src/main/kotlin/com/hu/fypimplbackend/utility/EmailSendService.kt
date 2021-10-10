package com.hu.fypimplbackend.utility

import com.hu.fypimplbackend.config.ApplicationConfig
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class EmailSendService(
    @Autowired
    private val applicationConfig: ApplicationConfig,

    @Autowired
    private val loggerFactory: Logger
) {

    @Throws(RuntimeException::class)
    suspend fun sendEmail(to: String, otpCode: String) {
        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.gmail.com" // smtp protocol required to communicate with the gmail server
        properties["mail.smtp.socketFactory.port"] = "465"
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.port"] = "465"

        val session: Session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(applicationConfig.fromEmail, applicationConfig.fromPassword)
            }
        })
        try {
            val mimeMessage = MimeMessage(session)
            val internetAddress = InternetAddress(to)
            mimeMessage.addRecipient(Message.RecipientType.TO, internetAddress)
            val subject = "Password Reset"
            mimeMessage.subject = subject
            val message = "Your new password to login to your account is $otpCode thanks for using our services"
            mimeMessage.setText(message)
            Transport.send(mimeMessage)
            this.loggerFactory.info("An email has been sent to you with instructions...")
        } catch (e: MessagingException) {
            throw RuntimeException(e)
        }
    }
}