package com.mawujun.mobile.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.mawujun.service.AbstractService;


import com.mawujun.mobile.message.Message;
import com.mawujun.mobile.message.MessageRepository;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MessageService extends AbstractService<Message, String>{

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public MessageRepository getRepository() {
		return messageRepository;
	}

}
