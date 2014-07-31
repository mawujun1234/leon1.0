package com.mawujun.store;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

















import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;


import com.mawujun.store.Barcode;
import com.mawujun.store.BarcodeRepository;
import com.mawujun.store.Barcode.BarcodeKey;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.exception.BusinessException;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BarcodeService extends AbstractService<Barcode, String>{

	@Autowired
	private BarcodeRepository barcodeRepository;
	
	//SimpleDateFormat y4mdDateFormat=new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat y2mdDateFormat=new SimpleDateFormat("yyMMdd");
	//SimpleDateFormat ymdHmsDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public BarcodeRepository getRepository() {
		return barcodeRepository;
	}
	
	
	public List<Barcode> getBarCodeList(Barcode[] barcodes) {
		String y2md=y2mdDateFormat.format(new Date());//年月日
		//对条码进行分组
		Map<BarcodeKey,List<Barcode>> keyMaps=new HashMap<BarcodeKey,List<Barcode>>();
		for (Barcode equip : barcodes) {
			equip.setYmd(y2md);
			BarcodeKey key=equip.getKey();
			if(!keyMaps.containsKey(key)){
				keyMaps.put(key, new ArrayList<Barcode>());
			}
			keyMaps.get(key).add(equip);
		}
		
		
//		//现获取这次要打印的设备总数
//		int nums_sum=0;
//		for (Barcode equip : barcodes) {
//			nums_sum+= equip.getSerialNum();
//		}
//		//获取今天的最大流水号，并且更新
//		注意这里要按照 小类 品名 品牌 供应商 年月日 来获取最大的流水号
//		
//		int maxsd=getMaxsd(nums_sum);
		List<Barcode> list = new ArrayList<Barcode>();
		
		
		for(Entry<BarcodeKey,List<Barcode>> entry:keyMaps.entrySet()){
			int maxsd=getMaxsd(entry.getKey());//获取最大的流水号
			for (Barcode equip : entry.getValue()) {
				//要打印的设备的个数
				int nums = equip.getSerialNum();
				//为每个设备生成一个条码，同种类型的设备，一个设备一个条码
				for (int i = 0; i < nums; i++) {
					//按规则生成二维码
					int serialNum=++maxsd;
					String code = generateBarcode(equip, serialNum, y2md);
					
					Barcode barcodeClone=BeanUtils.copyOrCast(equip, Barcode.class);
					barcodeClone.setEcode(code);
					barcodeClone.setSerialNum(serialNum);
					barcodeClone.setStatus(true);//表示已经导出了
					list.add(barcodeClone);
					//保存该设备信息
					
					barcodeRepository.create(barcodeClone);
					
				}
			}
		}
		
		

		
//		Map<String,Object> result=new HashMap<String,Object>();
//		result.put("maxsd", maxsd);
//		result.put("list", list);
		return list;
	}
	
	public int getMaxsd(BarcodeKey key){
		Cnd cnd=Cnd.where().andEquals(M.Barcode.subtype_id, key.getSubtype_id())
				.andEquals(M.Barcode.prod_id, key.getProd_id())
				.andEquals(M.Barcode.brand_id, key.getBrand_id())
				.andEquals(M.Barcode.supplier_id, key.getSupplier_id())
				.andEquals(M.Barcode.ymd, key.getYmd());
		
		Integer maxsd=(Integer)barcodeRepository.queryMax(M.Barcode.serialNum, cnd);
		if(maxsd==null){
			maxsd=0;
		}
		return maxsd;
		
	}
	private String generateBarcode(Barcode barcode, Integer serialNum, String y2md) {
		StringBuilder code = new StringBuilder();
		//org.apache.commons.lang.StringUtils.leftPad(index+"", 4, "0");
		code.append(barcode.getSubtype_id()+ barcode.getProd_id()+ barcode.getBrand_id()+barcode.getSupplier_id()+y2md
				+StringUtils.leftPad(serialNum+"", 3, "0"));
		return code.toString();
	}

}
