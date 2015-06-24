package com.mawujun.install;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.install.Borrow;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface BorrowRepository extends IRepository<Borrow, String>{
	public Page queryMain(Page page);
	public List<BorrowListVO> queryList(@Param("borrow_id")String borrow_id) ;
	
	public BorrowListVO getBorrowListVO(@Param("borrow_id")String borrow_id,@Param("ecode")String ecode);
	public void returnBorrowList(@Param("borrowlist_id")String borrowlist_id);
	public void updateBorrowIsAllReturn(@Param("borrow_id")String borrow_id);
	
	
	public void changeBorrowListType2installout(@Param("borrow_id")String borrow_id,@Param("ecode")String ecode );

}
