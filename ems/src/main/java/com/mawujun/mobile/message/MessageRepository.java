package com.mawujun.mobile.message;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.mobile.message.Message;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface MessageRepository extends IRepository<Message, String>{
	public Page mobile_queryPage(Page page) ;

}
