package org.demian.demibox.schedulers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.demian.demibox.dao.Event;
import org.demian.demibox.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;

public class EventScheduler implements MessageSourceAware {
	private MessageSource messageSource;
	private MailSender mailSender;
	private EventsService eventsService;

	@Scheduled(fixedRate = 60000)
	public void sendEventsEmails() {
		System.out.println("EventScheduler called!");
		List<Event> events = eventsService.getRecentEvents();
		for (Event e : events) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(messageSource.getMessage("demibox.email", null, Locale.ROOT));
			mail.setTo(e.getUsername());
			mail.setSubject(messageSource.getMessage("scheduler.emailTitle", null, Locale.ROOT));
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
			String emailText = messageSource.getMessage("scheduler.emailText", new Object[] { format.format(e.getDate()), e.getDetails() }, Locale.ROOT);
			System.out.println(emailText);
			mail.setText(emailText);
			try {
				mailSender.send(mail);
			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
			eventsService.setEmailSent(e.getId(), e.getUsername());
		}
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Autowired
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	public void setEventsService(EventsService eventsService) {
		this.eventsService = eventsService;
	}
}