package com.mawujun.store;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.adjust.AdjustVO;
import com.mawujun.baseinfo.EquipmentProd;
import com.mawujun.baseinfo.EquipmentProdRepository;
import com.mawujun.baseinfo.EquipmentProdType;
import com.mawujun.baseinfo.EquipmentRepository;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreRepository;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrderService extends AbstractService<Order, String>{

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderListRepository  orderListRepository ;
	@Autowired
	private Barcode_MaxNumRepository barcode_MaxNumRepository;
	@Autowired
	private BarcodeRepository barcodeRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private EquipmentProdRepository equipmentProdRepository;
	
	@Override
	public OrderRepository getRepository() {
		return orderRepository;
	}
	
	public Page queryMain(Page page) {
		Page results=orderRepository.queryMain(page);
		List<Store> stores=storeRepository.queryAll();
		
		List<OrderVO> list=results.getResult();
		for(OrderVO orderVO:list){
			for(Store store:stores){
				if(store.getId().equals(orderVO.getStore_id())){
					orderVO.setStore_name(store.getName());
				}
			}
		}
		
		return results;
	}
	public  List<OrderListVO> queryList(String order_id) {	
		
		return orderRepository.queryList(order_id);
	}
	
	public List<OrderListVO> queryList4Barcode(Params params) {	
		
		return orderRepository.queryList4Barcode(params);
	}
	public List<OrderListVO> queryList4Barcode_tj_children(Params params) {	
		
		return orderRepository.queryList4Barcode_tj_children(params);
	}
	
	SimpleDateFormat y2mdDateFormat=new SimpleDateFormat("yyMMdd");
	
	public List<Order> queryUncompleteOrderno(String orderNo,String project_id) {
		List<Order> list=orderRepository.queryUncompleteOrderno(ShiroUtils.getAuthenticationInfo().getId(),orderNo,project_id);
		
//		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
//		for(String str:list){
//			Map<String,String> map=new HashMap<String,String>();
//			map.put("id", str);
//			map.put("name", str);
//			result.add(map);
//		}
		return list;
	}
	/**
	 * 创建订单，同时生成条码
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderes
	 * @return
	 */
	public void create(Order order,OrderList[] orderListes) {
		Long count=orderRepository.queryCount(Cnd.count(M.Order.orderNo).andEquals(M.Order.orderNo, order.getOrderNo()));
		if(count!=null && count>0){
			throw new BusinessException("该订单号已经存在");
		}
		Date createDate=new Date();
		order.setCreateDate(createDate);
		orderRepository.create(order);
//		String y2md=y2mdDateFormat.format(new Date());//年月日
		
		for(OrderList orderList:orderListes){	
			orderList.setOrder_id(order.getId());
			//创建订单
			orderListRepository.create(orderList);
			
		}
//		Long count=orderRepository.queryCount(Cnd.count(M.Order.orderNo).andEquals(M.Order.orderNo, orderes[0].getOrderNo()));
//		if(count!=null && count>0){
//			throw new BusinessException("该订单号已经存在");
//		}
////		String y2md=y2mdDateFormat.format(new Date());//年月日
//		Date createDate=new Date();
//		for(Order order:orderes){	
//			order.setCreateDate(createDate);
//			//创建订单
//			orderRepository.create(order);
//			
//		}
	}
	/**
	 * 把订单确认为 订单编辑完成了
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderNo
	 */
	public void editover(String id) {
		//更新订单状态
		//orderRepository.update(Cnd.update().set(M.Order.status, OrderStatus.editover).andEquals(M.Order.orderNo, orderNo));
		Order order=orderRepository.get(id);
		if(order.getStatus()==OrderStatus.editover){
			return;
		}
		if(!StringUtils.hasText(order.getProject_id())){
			throw new BusinessException("该订单还没有添加项目信息，不能确认!");
		}
		order.setStatus(OrderStatus.editover);
		orderRepository.update(order);
		
		
//		//
//		List<OrderList> orderLists=orderListRepository.query(Cnd.select().andEquals(M.OrderList.order_id, order.getId()));
//		
//		String y2md=y2mdDateFormat.format(new Date());//年月日
//		//同时生成条码
//		//创建该订单的条码号
//		for(OrderList orderList:orderLists){
//			int maxsd=getMaxsd(orderList,y2md);	
//			int nums = orderList.getOrderNum();
//			if(maxsd+nums>9999){
//				throw new BusinessException("同一天同个小类的的设备数量不能超过9999件,请明天再录入!");
//			}
//			for (int i = 1; i <= nums; i++) {
//				String ecode = generateBarcode(orderList, i+maxsd, y2md);
//				//ecodes.add(ecode);
//				//保存这个订单明细下所有生成过的条码
//				Barcode bar=new Barcode();
//				bar.setEcode(ecode);
//				bar.setOrderlist_id(orderList.getId());
//				bar.setYmd(y2md);
//				bar.setSeqNum(i);
//				barcodeRepository.create(bar);
//			}
//			
//			//保存今天的这个订单号的最大值，因为同一天同个型号的可能会录入多次，因为有多个仓库
//			barcode_MaxNumRepository.update(Cnd.update().set(M.Barcode_MaxNum.num, nums+maxsd)
//					.andEquals(M.Barcode_MaxNum.subtype_id, orderList.getSubtype_id())
//					.andEquals(M.Barcode_MaxNum.prod_id, orderList.getProd_id())
//					//.andEquals(M.Barcode_MaxNum.brand_id, order.getBrand_id())
//					//.andEquals(M.Barcode_MaxNum.supplier_id, order.getSupplier_id())
//					.andEquals(M.Barcode_MaxNum.ymd,y2md)
//					.andEquals(M.Barcode_MaxNum.num,maxsd));//用num做条件，是放置并发的时候，出现覆盖
//		}

	}
	
	public void delete(String id) {
		String status=orderRepository.queryStatus(id);
		if(OrderStatus.editover.toString().equalsIgnoreCase(status)){
			throw new BusinessException("订单已确认，不能删除!");
		}
		orderRepository.deleteBatch(Cnd.delete().andEquals(M.Order.id, id));
	}
	/**
	 * 强制退回，把订单状态改成可编辑
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param id
	 */
	public void forceBack(String id) {
//		Order order=orderRepository.get(id);
//		if(order.get){
//			
//		}
//		if(OrderStatus.editover.toString().equalsIgnoreCase(status)){
//			throw new BusinessException("订单已确认，不能删除!");
//		}
		orderRepository.update(Cnd.update().set(M.Order.status, OrderStatus.edit).andEquals(M.Order.id, id));
	}
	/**
	 * 如果订单进行修改，同个品名有很多不同的订单，条码的序号不是从1开始的，如果重复导出，将会生成不同的条码，序号递增
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	public void insertBarcode(Order order,OrderList orderList,String randomStr){
		//
		//List<OrderList> orderLists=orderListRepository.query(Cnd.select().andEquals(M.OrderList.order_id, order.getId()));
		String y2md=y2mdDateFormat.format(new Date());//年月日
		Date createDate=new Date();
		
		//Order order=orderRepository.get(orderList.getOrder_id());
		//String y2md=y2mdDateFormat.format(order.getOrderDate());//订单年月日
		
		//equipmentProd可能是件，也可能是套
		EquipmentProd equipmentProd=equipmentProdRepository.get(orderList.getProd_id());
		
		List<EquipmentProd> equipmentProdListes=null;
		//如果是套件，就取套件下面的拆分的零件进行打印
		boolean isTj=false;
		if(equipmentProd.getType()==EquipmentProdType.TJ){
			isTj=true;
			equipmentProdListes=equipmentProdRepository.queryProd_tj_children(equipmentProd.getId());
		} else {
			equipmentProdListes=new ArrayList<EquipmentProd>();
			equipmentProdListes.add(equipmentProd);
		}
		//同时生成条码
		//创建该订单的条码号
		for(EquipmentProd temp:equipmentProdListes){
			int lasted_maxsd=getMaxsd(temp,y2md);//这个必须要，因为不同的仓库，同一天可能会打印同一个品名下的设备
			int startNum=orderList.getTotalNum()+lasted_maxsd;
			int printnum = orderList.getPrintNum();
//			if(maxsd+nums>9999){
//				throw new BusinessException("同一天同个小类的的设备数量不能超过9999件,请明天再录入!");
//			}
			
			//
			String equipmentProd_id=temp.getId();
			//当打印的是普通条码的时候，就用**作为占位符
			if(!isTj){
				equipmentProd_id+="-**";
			}
			for (int i = 1; i <= printnum; i++) {
				String ecode = generateBarcode(equipmentProd_id, i+startNum, y2md);
				//ecodes.add(ecode);
				//保存这个订单明细下所有生成过的条码
				Barcode bar=new Barcode();
				bar.setEcode(ecode);
				bar.setOrderlist_id(orderList.getId());
				bar.setYmd(y2md);
				
				
				bar.setType_id(orderList.getType_id());
				bar.setSubtype_id(temp.getSubtype_id());
				bar.setProd_id(temp.getId());
				if(StringUtils.hasText(temp.getStyle())){
					bar.setStyle(temp.getStyle());
				} else {
					bar.setStyle(orderList.getStyle());
				}
				
				bar.setBrand_id(temp.getBrand_id());
				bar.setSupplier_id(order.getSupplier_id());
				bar.setStore_id(order.getStore_id());
				
				
				//bar.setSeqNum(i+startNum);
				bar.setRandomStr(randomStr);
				bar.setCreateDate(createDate);
				barcodeRepository.create(bar);
			}
			
			//保存今天的这个订单号的最大值，因为同一天同个品名的可能会录入多次，因为有多个仓库，多个订单存在
			barcode_MaxNumRepository.update(Cnd.update().set(M.Barcode_MaxNum.num, printnum+startNum)
					.andEquals(M.Barcode_MaxNum.subtype_id, temp.getSubtype_id())
					.andEquals(M.Barcode_MaxNum.prod_id, temp.getId())
					//.andEquals(M.Barcode_MaxNum.brand_id, order.getBrand_id())
					//.andEquals(M.Barcode_MaxNum.supplier_id, order.getSupplier_id())
					.andEquals(M.Barcode_MaxNum.ymd,y2md));
					//.andEquals(M.Barcode_MaxNum.num,maxsd));//用num做条件，是放置并发的时候，出现覆盖
		}
	}
	private int getMaxsd(EquipmentProd equipmentProd,String y2md){
		Cnd cnd=Cnd.where().andEquals(M.Barcode_MaxNum.subtype_id, equipmentProd.getSubtype_id())
				.andEquals(M.Barcode_MaxNum.prod_id, equipmentProd.getId())
				//.andEquals(M.Barcode_MaxNum.brand_id, orderVO.getBrand_id())
				//.andEquals(M.Barcode_MaxNum.supplier_id, orderVO.getSupplier_id())
				.andEquals(M.Barcode_MaxNum.ymd,y2md);
		
		Integer maxsd=(Integer)barcode_MaxNumRepository.queryMax(M.Barcode_MaxNum.num, cnd);
		if(maxsd==null){
			maxsd=0;
			Barcode_MaxNum maxnum=new Barcode_MaxNum();
			maxnum.setSubtype_id(equipmentProd.getSubtype_id());
			maxnum.setProd_id(equipmentProd.getId());
			//maxnum.setBrand_id(orderVO.getBrand_id());
			//maxnum.setSupplier_id(orderVO.getSupplier_id());
			maxnum.setYmd(y2md);
			maxnum.setNum(maxsd);
			barcode_MaxNumRepository.create(maxnum);
		}
		return maxsd;

		
	}
	private String generateBarcode(String equipmentProd_id, int serialNum, String y2md) {
		StringBuilder code = new StringBuilder();
		
		code.append(equipmentProd_id+"-"+y2md
				+StringUtils.leftPad(serialNum+"", 4, "0"));
		return code.toString();

	}
	/**
	 * 返回条码和型号,导出的时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderVOs 都是订单明细的数据
	 * @return
	 */
	public List<BarcodeVO> getBarCodeList(OrderList[] orderLists) {
		//本次导出条码的标识
		String randomStr=UUID.randomUUID().toString();
		 List<BarcodeVO> result=new ArrayList<BarcodeVO>();
		 Order order=orderRepository.get(orderLists[0].getOrder_id());
			//首先获取这个订单明细中的当前值
			for(OrderList orderList:orderLists){
				
				if(orderList.getPrintNum()==0){
					continue;
				}
				////在导之前先删掉，这个范围内的条码，然后再插入，这样就不怕他重复导了，并且是在equipment表中不存在的条码
				//orderRepository.deleteBarcodesRange(params);
				
				//先把条码存储在数据库中
				insertBarcode(order,orderList,randomStr);
				//获取当前要打印的条码范围
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("orderlist_id", orderList.getId());
				params.put("randomStr", randomStr);
				//params.put("startNum", orderList.getTotalNum());
				//params.put("endNum", orderList.getTotalNum()+orderList.getPrintNum());
				List<BarcodeVO> list= orderRepository.getBarcodesRange(params);
				result.addAll(list);
			}
			return result;
	}
	

	
	

//	/**
//	 * 流水码必须要4位，因为同个小类，品名，供应商，品牌，不同的仓库，同一天会进行同时入库
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param orderVO
//	 * @param serialNum
//	 * @param y2md
//	 * @return
//	 */
//	private String generateBarcode(OrderList orderList, int serialNum, String y2md) {
//		StringBuilder code = new StringBuilder();
//		
//		code.append(orderList.getProd_id()+"-"+y2md
//				+StringUtils.leftPad(serialNum+"", 4, "0"));
//		return code.toString();
//
//	}
//	private int getMaxsd(OrderList orderList,String y2md){
//		Cnd cnd=Cnd.where().andEquals(M.Barcode_MaxNum.subtype_id, orderList.getSubtype_id())
//				.andEquals(M.Barcode_MaxNum.prod_id, orderList.getProd_id())
//				//.andEquals(M.Barcode_MaxNum.brand_id, orderVO.getBrand_id())
//				//.andEquals(M.Barcode_MaxNum.supplier_id, orderVO.getSupplier_id())
//				.andEquals(M.Barcode_MaxNum.ymd,y2md);
//		
//		Integer maxsd=(Integer)barcode_MaxNumRepository.queryMax(M.Barcode_MaxNum.num, cnd);
//		if(maxsd==null){
//			maxsd=0;
//			Barcode_MaxNum maxnum=new Barcode_MaxNum();
//			maxnum.setSubtype_id(orderList.getSubtype_id());
//			maxnum.setProd_id(orderList.getProd_id());
//			//maxnum.setBrand_id(orderVO.getBrand_id());
//			//maxnum.setSupplier_id(orderVO.getSupplier_id());
//			maxnum.setYmd(y2md);
//			maxnum.setNum(maxsd);
//			barcode_MaxNumRepository.create(maxnum);
//		}
//		return maxsd;
//
//		
//	}

	
	/**
	 * 新品入库的时候，扫描设备获取设备的相关信息
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	public EquipmentVO getEquipFromBarcode(String ecode) {
		EquipmentVO vo=orderRepository.getEquipFromBarcode(ecode);
		return vo;
	}
	
	
	/**
	 * 添加明细数据
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderNo
	 * @return
	 * @throws IOException
	 */
	public void addList(OrderList orderList) throws  IOException{
		//获取主订单的信息
		Order main=orderRepository.get(orderList.getOrder_id());
		if(main.getStatus().equals(OrderStatus.editover)){
			throw new BusinessException("该订单已经不能编辑!");
		}
		
		orderListRepository.create(orderList);
	}
	
	/**
	 * 更新明细数据
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderNo
	 * @return
	 * @throws IOException
	 */
	public void updateList(OrderList orderList) throws IOException {
		// 获取主订单的信息
		Order main = orderRepository.get(orderList.getOrder_id());
		if (main.getStatus().equals(OrderStatus.editover)) {
			throw new BusinessException("该订单已经不能编辑!");
		}
		//如果已入库数量大于要订单的数量，也不准编辑
		if(orderList.getTotalNum()!=null &&orderList.getTotalNum()>orderList.getOrderNum()){
			throw new BusinessException("以入库数量大于订单数量，不准备修改!");
		}

		orderListRepository.update(orderList);
				
//		// 获取主订单的信息
//		Order main = orderRepository.get(order.getId());
//		if(main.getStatus().equals(OrderStatus.editover)){
//			throw new BusinessException("该订单已经不能编辑!");
//		}
//
//		main.setProd_id(order.getProd_id());
//		main.setBrand_id(order.getBrand_id());
//		main.setSupplier_id(order.getSupplier_id());
//		main.setStyle(order.getStyle());
//		main.setUnitPrice(order.getUnitPrice());
//		main.setOrderNum(order.getOrderNum());
//		
//
//		orderRepository.update(main);
	}
	
	/**
	 * 删除明细数据
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderNo
	 * @return
	 * @throws IOException
	 */
	public void deleteList(String id) throws  IOException{
		OrderList orderList = orderListRepository.get(id);
		Order main = orderRepository.get(orderList.getOrder_id());
		if(main.getStatus().equals(OrderStatus.editover)){
			throw new BusinessException("该订单已经结束编辑，不能删除!");
		}
		orderListRepository.delete(orderList);
	}

}
