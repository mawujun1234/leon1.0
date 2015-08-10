package com.mawujun.mobile.task;
/**
 * 在扫描设备的时候的情况，
 * install:在这次任务中，这个设备室安装上去的
 * uninstall:在这次任务中这个设备是损坏，替换下来的
 * patrol:在这次任务中，是巡检的时候，扫描的设备。如果是维修任务，也可能是巡检，因为设备没坏
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum TaskListTypeEnum {
	install("安装"),uninstall("卸载"),patrol("巡检");
	
	private String name;
	TaskListTypeEnum(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
