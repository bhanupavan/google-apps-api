package com.brs.gmail;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

import org.apache.commons.io.FileUtils;

import com.sun.mail.imap.IMAPFolder;


public class EmailReader {


    public static void main(String[] args) throws MessagingException, IOException {
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flag flag = null;
        try 
        {
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");

          Session session = Session.getDefaultInstance(props, null);

          store = session.getStore("imaps");
          store.connect("imap.googlemail.com","bhanupavansingh@gmail.com", "tryxiuevcfxbeldt");

          folder = (IMAPFolder) store.getFolder("inbox"); 


          if(!folder.isOpen())
          folder.open(Folder.READ_WRITE);
          Calendar cal = Calendar.getInstance();
          cal.add(Calendar.DAY_OF_MONTH, 0);
          ReceivedDateTerm term  = new ReceivedDateTerm(ComparisonTerm.EQ,new Date(cal.getTimeInMillis()));

          Message[] todayMmessages = folder.search(term);
          Message[] messages = folder.search(new SubjectStringSearchTerm(),todayMmessages);
          System.out.println("No of Messages : " + folder.getMessageCount());
          System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
          System.out.println(messages.length);
          for (int i=0; i < messages.length;i++) 
          {

            System.out.println("*****************************************************************************");
            System.out.println("MESSAGE " + (i + 1) + ":");
            Message msg =  messages[i];
            //System.out.println(msg.getMessageNumber());
            //Object String;
            //System.out.println(folder.getUID(msg)

            subject = msg.getSubject();

            System.out.println("Subject: " + subject);
            System.out.println("From: " + msg.getFrom()[0]);
           System.out.println("To: "+msg.getAllRecipients()[0]);
            System.out.println("Date: "+msg.getReceivedDate());
            System.out.println("Size: "+msg.getSize());
            System.out.println(msg.getFlags());
            System.out.println("Body: \n"+ msg.getContent());
            System.out.println(msg.getContentType());
           if(subject.equalsIgnoreCase("Test Attach and Read")){
        	   Object content = msg.getContent();
        	   if(content instanceof Multipart){
        		   Multipart multipart = (Multipart)content;
        		   InputStream source = MailConverters.toInputStream(multipart);
        		   System.out.println(source.toString());
        	   }
        	   break;
        	   
           }

          }
        }
        finally 
        {
          if (folder != null && folder.isOpen()) { folder.close(true); }
          if (store != null) { store.close(); }
        }

    }



}
