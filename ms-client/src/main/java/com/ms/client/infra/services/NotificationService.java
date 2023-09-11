package com.ms.client.infra.services;

import com.ms.client.infra.proxies.EmailProxy;
import com.ms.client.infra.proxies.requests.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private EmailProxy emailProxy;

	public void sendNotification(String to, String content, String ownerRef) {
		emailProxy.sendEmail(
		  new EmailRequest(
			to,
			"Invoice Manager - Support",
		    content,
		    ownerRef
		  )
		);
	}
}
