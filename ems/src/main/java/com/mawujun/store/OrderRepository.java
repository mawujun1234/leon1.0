package com.mawujun.store;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.repository1.IRepository;
import com.mawujun.store.Order;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrderRepository extends IRepository<Order, String>{

	public List<OrderVO> query(@Param("orderNo")String orderNo);
	
	public EquipmentVO getEquipFromBarcode(@Param("ecode")String ecode);
	
	public void updateTotalNum(@Param("order_id")String order_id,@Param("totalNum")String totalNum);
}