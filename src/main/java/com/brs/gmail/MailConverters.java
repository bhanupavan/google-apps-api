package com.brs.gmail;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.apache.commons.io.IOUtils;

public class MailConverters {

	public static String toString(Multipart multipart)
			throws MessagingException, IOException {
		int size = multipart.getCount();
		for (int i = 0; i < size; i++) {
			BodyPart part = multipart.getBodyPart(i);
			System.out.println(part.getContentType());
			if (part.getContentType().contains("TEXT")) {
				return part.getContent().toString();
			}
		}
		return null;
	}

	public static InputStream toInputStream(Multipart multipart)
			throws IOException, MessagingException {
		String s = toString(multipart);
		if (s == null) {
			return null;
		}
		return IOUtils.toInputStream(s);
	}

}
