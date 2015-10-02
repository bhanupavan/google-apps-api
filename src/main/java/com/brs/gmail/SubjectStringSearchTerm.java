package com.brs.gmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

public class SubjectStringSearchTerm extends SearchTerm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean match(Message paramMessage) {
		try {
			return paramMessage == null || paramMessage.getSubject() == null 
					? false
					: paramMessage.getSubject().equalsIgnoreCase("Test Attach and Read");
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

}
