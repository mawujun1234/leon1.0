package com.mawujun.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;


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
	private Barcode_MaxNumRepository barcode_MaxNumRepository;
	@Autowired
	private BarcodeRepository barcodeRepository;
	
	@Override
	public OrderRepository getRepository() {
		return orderRepository;
	}
	
	public List<OrderVO> query(String orderNo) {	
		return orderRepository.query(orderNo);
	}
	
	
	
	SimpleDateFormat y2mdDateFormat=new SimpleDateFormat("yyMMdd");
	public List<String> getBarCodeList(OrderVO[] orderVOs) {
		String y2md=y2mdDateFormat.format(new Date());//年月日
		List<String> ecodes=new ArrayList<String>();
		for(OrderVO orderVO:orderVOs){
			int maxsd=getMaxsd(orderVO,y2md);
			
			int nums = orderVO.getPrintNum()+maxsd;
			for (int i = maxsd; i < nums; i++) {
				String ecode = generateBarcode(orderVO, i, y2md);
				ecodes.add(ecode);
				//保存这个订单明细下所有生成过的条码
				Barcode bar=new Barcode();
				bar.setEcode(ecode);
				bar.setOrder_id(orderVO.getId());
				bar.setYmd(y2md);
				barcodeRepository.create(bar);
			}
			//保存今天的这个订单号的最大致
			barcode_MaxNumRepository.update(Cnd.update().set(M.Barcode_MaxNum.num, nums)
					.andEquals(M.Barcode_MaxNum.subtype_id, orderVO.getSubtype_id())
					.andEquals(M.Barcode_MaxNum.prod_id, orderVO.getProd_id())
					.andEquals(M.Barcode_MaxNum.brand_id, orderVO.getBrand_id())
					.andEquals(M.Barcode_MaxNum.supplier_id, orderVO.getSupplier_id())
					.andEquals(M.Barcode_MaxNum.ymd,y2md)
					.andEquals(M.Barcode_MaxNum.num, maxsd));//用num做条件，是放置并发的时候，出现覆盖
		}

		return ecodes;
	}
	/**
	 * 流水码必须要4位，因为同个小类，品名，供应商，品牌，不同的仓库，同一天会进行同时入库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderVO
	 * @param serialNum
	 * @param y2md
	 * @return
	 */
	private String generateBarcode(OrderVO orderVO, Integer serialNum, String y2md) {
		StringBuilder code = new StringBuilder();
		//org.apache.commons.lang.StringUtils.leftPad(index+"", 4, "0");
		code.append(orderVO.getSubtype_id()+ orderVO.getProd_id()+"-"+ orderVO.getBrand_id()+orderVO.getSupplier_id()+"-"+y2md
				+StringUtils.leftPad(serialNum+"", 4, "0"));
		return code.toString();
	}
	private int getMaxsd(OrderVO orderVO,String y2md){
		Cnd cnd=Cnd.where().andEquals(M.Barcode_MaxNum.subtype_id, orderVO.getSubtype_id())
				.andEquals(M.Barcode_MaxNum.prod_id, orderVO.getProd_id())
				.andEquals(M.Barcode_MaxNum.brand_id, orderVO.getBrand_id())
				.andEquals(M.Barcode_MaxNum.supplier_id, orderVO.getSupplier_id())
				.andEquals(M.Barcode_MaxNum.ymd,y2md);
		
		Integer maxsd=(Integer)barcode_MaxNumRepository.queryMax(M.Barcode_MaxNum.num, cnd);
		if(maxsd==null){
			maxsd=0;
			Barcode_MaxNum maxnum=new Barcode_MaxNum();
			maxnum.setSubtype_id(orderVO.getSubtype_id());
			maxnum.setProd_id(orderVO.getProd_id());
			maxnum.setBrand_id(orderVO.getBrand_id());
			maxnum.setSupplier_id(orderVO.getSupplier_id());
			maxnum.setYmd(y2md);
			maxnum.setNum(maxsd);
			barcode_MaxNumRepository.create(maxnum);
		}
		return maxsd;
		
	}
	
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

}
