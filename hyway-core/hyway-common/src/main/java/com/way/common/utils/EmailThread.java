package com.way.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 
 * @类说明：发送邮件，线程安全
 */
public class EmailThread extends MyThread
{

    private static final String charset = "UTF-8";
    private static final String defaultMimetype = "text/plain";
    private static final String nickname = "零元素";

    // private static final String PORT =
    // PropertieUtils.gerResourceValue("MAIL_PORT");
    // private static final String SMTP =
    // PropertieUtils.gerResourceValue("MAIL_SMTP");
    // private static final String myEmailAddress =
    // PropertieUtils.gerResourceValue("MAIL_ADDRESS");
    // private static final String myEmailPWD =
    // PropertieUtils.gerResourceValue("MAIL_PWD ");

    private static final String PORT = "25";
    private static final String SMTP = "smtp.exmail.qq.com";
    private static final String myEmailAddress = "Samuel_way@163.com";
    private static final String myEmailPWD = "wanwei.com";
    private static final String isVerification = "true";

    // ======================= 初始化线程 =============================
    // 参数
    String[] receivers;
    File[] attachements;
    String subject, mailContent, mimetype, senders;

    /** 最多同时执行的任务数 */
    private static ExecutorService service = Executors
            .newFixedThreadPool(MyThread.getMaxThread());
    private static List<MyThread> threads = new ArrayList<MyThread>();

    static
    {
        for (int i = 0; i < MyThread.getMaxThread() + 1; i++)
        {
            threads.add(new EmailThread());
        }
    }

    // ======================= 初始化线程 =============================

    @Override
    public void runCode()
            throws MessagingException, UnsupportedEncodingException
    {
        Properties props = new Properties();
        props.put("mail.smtp.port", PORT); // 端口
        props.put("mail.smtp.host", SMTP); // smtp服务器地址
        props.put("mail.smtp.auth", isVerification); // 需要校验
        props.put("mail.debug", true);
        Session session = Session.getDefaultInstance(props, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myEmailAddress, myEmailPWD); // 登录用户名/密码
            }
        });
        session.setDebug(false);
        MimeMessage mimeMessage = new MimeMessage(session);

        InternetAddress sender = new InternetAddress(myEmailAddress);

        sender.setPersonal(nickname, charset); // 昵称（UTF-8编码）

        mimeMessage.setFrom(sender); // 发件人邮件

        InternetAddress[] toAddress = new InternetAddress[receivers.length];

        for (int i = 0; i < receivers.length; i++)
        {
            toAddress[i] = new InternetAddress(receivers[i]);
        }

        mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);// 收件人邮件
        mimeMessage.setSubject(subject, charset);

        Multipart multipart = new MimeMultipart();
        // 正文
        MimeBodyPart body = new MimeBodyPart();

        // body.setText(mailContent, charset);//不支持html
        body.setContent(mailContent, (mimetype != null && !"".equals(mimetype)
                ? mimetype : defaultMimetype) + ";charset=" + charset);
        multipart.addBodyPart(body);// 发件内容
        // 附件
        if (attachements != null)
        {
            for (File attachement : attachements)
            {
                MimeBodyPart attache = new MimeBodyPart();
                attache.setDataHandler(
                        new DataHandler(new FileDataSource(attachement)));
                String fileName = getLastName(attachement.getName());
                attache.setFileName(
                        MimeUtility.encodeText(fileName, charset, null));
                multipart.addBodyPart(attache);
            }
        }
        mimeMessage.setContent(multipart);
        mimeMessage.setSentDate(new Date());

        Transport.send(mimeMessage);
    }

    @Override
    public void setArgs(Object... args)
    {
        this.receivers = (String[]) args[0];
        this.subject = args[1].toString();
        this.mailContent = args[2].toString();
        this.attachements = (File[]) args[3];
        this.mimetype = args[4].toString();
    }

    /**
     * 发送邮件
     * 
     * @param receivers
     *            收件人
     * @param subject
     *            标题
     * @param mailContent
     *            邮件内容
     * @param attachements
     *            附件
     * @param mimetype
     *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void send(String[] receivers, String subject,
            String mailContent, File[] attachements, String mimetype)
            throws MessagingException, UnsupportedEncodingException
    {

        MyThread m = MyThread.getMyThread(threads, receivers, subject,
                mailContent, attachements, mimetype);
        service.execute(m);
    }

    /**
     * 发送邮件
     * 
     * @param receiver
     *            收件人
     * @param subject
     *            标题
     * @param mailContent
     *            邮件内容
     * @param mimetype
     *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void send(String receiver, String subject, String mailContent,
            String mimetype)
            throws UnsupportedEncodingException, MessagingException
    {
        send(new String[]
        { receiver }, subject, mailContent, mimetype);
    }

    /**
     * 发送邮件
     * 
     * @param receivers
     *            收件人
     * @param subject
     *            标题
     * @param mailContent
     *            邮件内容
     * @param mimetype
     *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void send(String[] receivers, String subject,
            String mailContent, String mimetype)
            throws UnsupportedEncodingException, MessagingException
    {
        send(receivers, subject, mailContent, null, mimetype);
    }

    private static String getLastName(String fileName)
    {
        int pos = fileName.lastIndexOf("\\");
        if (pos > -1)
        {
            fileName = fileName.substring(pos + 1);
        }
        pos = fileName.lastIndexOf("/");
        if (pos > -1)
        {
            fileName = fileName.substring(pos + 1);
        }
        return fileName;
    }
}
