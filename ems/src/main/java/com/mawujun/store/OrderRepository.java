package com.mawujun.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.repository1.IRepository;
import com.mawujun.store.Order;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface OrderRepository extends IRepository<Order, String>{

	public Page queryMain(Page page);
	public List<OrderListVO> queryList(@Param("order_id")String order_id);
	
	
	public List<Order> queryUncompleteOrderno(@Param("user_id")String user_id,@Param("orderNo")String orderNo,@Param("project_id")String project_id);
	
	public List<OrderVO> query(@Param("orderNo")String orderNo);
	
	public EquipmentVO getEquipFromBarcode(@Param("ecode")String ecode);
	
	
	//public void updateTotalNum(@Param("orderlist_id")String orderlist_id,@Param("totalNum")String totalNum);
	
	public List<BarcodeVO> getBarcodesRange(Map<String,Object> params);
	public void deleteBarcodesRange(Map<String,Object> params);
	
	public String queryStatus(@Param("id")String id);
	
	public List<OrderListVO> queryList4Barcode(Params params);
	
	/**
	 * 当某个订单明细中的数量已经全部入库后，就删除在barcode中所有还没有入库的条码，因为这些条码已经失效了
	 * @author mawujun 16064988@qq.com
	 */
	public void deleteBarcodeWhenAllin(@Param("orderlist_id")String orderlist_id);
	//public List<OrderListVO> queryList4Barcode_tj_children(Params params);
	
	//public Order getMainInfo(@Param("orderNo")String orderNo);
}
