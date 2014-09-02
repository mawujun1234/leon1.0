package com.mawujun.cache;

import java.util.ArrayList;
import java.util.List;

import com.mawujun.baseinfo.EquipmentVO;

/**
 * 缓存的key，
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class EquipKey {
	//缓存的单据类型
	private EquipScanType type;
	//哪个仓库在操作
	private String store_id;
	
	private long checkDate;//用来检查判断，防止缓存冲突的，也就是说一次操作一个缓存，如果发现缓存不一致了就删除
	
	//private List<EquipmentVO> list=new ArrayList<EquipmentVO>();
	//private String user_id;
	
	public EquipKey(EquipScanType type, String store_id,long checkDate) {
		super();
		this.type = type;
		this.store_id = store_id;
		this.checkDate=checkDate;
	}
	
	public static EquipKey getInstance(EquipScanType type, String store_id,long checkDate){
		return new EquipKey(type,store_id,checkDate);
	}
	
	
	
	public EquipScanType getType() {
		return type;
	}
	public String getStore_id() {
		return store_id;
	}





//	public List<EquipmentVO> getList() {
//		return list;
//	}
//	
//	public void addEquipmentVO(EquipmentVO equipmentVO) {
//		this.list.add(equipmentVO);
//	}

	public long getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(long checkDate) {
		this.checkDate = checkDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (checkDate ^ (checkDate >>> 32));
		result = prime * result
				+ ((store_id == null) ? 0 : store_id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipKey other = (EquipKey) obj;
		if (checkDate != other.checkDate)
			return false;
		if (store_id == null) {
			if (other.store_id != null)
				return false;
		} else if (!store_id.equals(other.store_id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EquipKey [type=" + type + ", store_id=" + store_id
				+ ", checkDate=" + checkDate + "]";
	}
}
