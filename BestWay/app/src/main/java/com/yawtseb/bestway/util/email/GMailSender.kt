package com.airetefacruo.myapplication.util.email

import com.yawtseb.bestway.util.ConstData.GMAIL_ID
import com.yawtseb.bestway.util.ConstData.GMAIL_PW
import com.yawtseb.bestway.util.email.AuthenticationTemplate.Companion.koreanTemplate
import java.util.*
import javax.activation.CommandMap
import javax.activation.MailcapCommandMap
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class GMailSender() : javax.mail.Authenticator(){
    private val mailHost = "smtp.gmail.com"
    private var props: Properties = Properties()
    private lateinit var session: Session

    init {
        props.setProperty("mail.transport.protocol", "smtp")
        props.setProperty("mail.host", mailHost)
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.socketFactory.fallback"] = "false"
        props.setProperty("mail.smtp.quitwait", "false")

        val mc = CommandMap.getDefaultCommandMap() as MailcapCommandMap
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html")
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml")
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain")
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed")
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822")

        session = Session.getDefaultInstance(props, this)
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(GMAIL_ID, GMAIL_PW)
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendAuthEmail(subject: String, recipients: String, url: String, local: String) {

        val message: Message = MimeMessage(session)
        message.setFrom(InternetAddress(GMAIL_ID))
        message.subject = subject
        message.sentDate = Date()
        message.setFrom()

        if (recipients.indexOf(',') > 0) message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients))
        else message.setRecipient(Message.RecipientType.TO, InternetAddress(recipients))

        val messageBodyPart = MimeBodyPart()

        /// TODO: 2021-02-01 나중에 언어에 따라 전송하는 데이터 변경해줘야한다.
        messageBodyPart.setContent(koreanTemplate(url), "text/html; charset=utf-8")

        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(messageBodyPart)
        message.setContent(multipart)
        Transport.send(message)
    }

}