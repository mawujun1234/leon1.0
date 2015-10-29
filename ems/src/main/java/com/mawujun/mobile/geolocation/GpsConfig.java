package com.mawujun.mobile.geolocation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 配置gps上传的时间间隔
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_gpsconfig")
public class GpsConfig  implements IdEntity<Integer>{
	@Id
	private Integer id;
	private int interval=60;//请求定位数据的事件间隔，秒为单位

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
