/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
package com.ipn.mx.utillerias;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Isaac
 */
public class EnviarMail {
    
    public void send_mail(String to, String subject, String msg){
        Properties p = new Properties();
        p.setProperty("mail.smtp.host", "smtp.gmail.com");
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.setProperty("mail.smtp.port", "587");
        p.setProperty("mail.smtp.user", "isaac31120@gmail.com");
        p.setProperty("mail.smtp.auth", "true");
        
        Session s = Session.getDefaultInstance(p);
        
        MimeMessage message = new MimeMessage(s);
        
        try {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msg);
            
            Transport t = s.getTransport("smtp");
            t.connect(p.getProperty("mail.smtp.user"), "xhpvdixpgmzjaubc");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            
        } catch (AddressException ex) {
            Logger.getLogger(EnviarMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EnviarMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String[] args) {
//        EnviarMail m = new EnviarMail();
//        m.send_mail("isaac_31120@outlook.com", "My_Test", "Hola esta es una prueba");
//        
//    }
}