package com.mawujun.baseinfo;

/**
 * 品名的类型 
 * 套件:就是成套，成对的订购，但不打印条码，套件拆分后的设备就是单件设备。所有设备刚开始都是单件，当进行套件拆分后就变成了套件
 * 单件：就是不拆分成零件进行管理，也不按套拆分的，要打印条码。可以被拆分成零件，也可以被拆分成配件
 * 零件:就是把单件拆分成零件，主要是进行残值回收时使用的。作为零件的品名是不打印条码的，并且原单件 还是单件
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentProdType {
	TJ("套件"),DJ("单件"),LJ("零件");
	private String name;
	
	EquipmentProdType(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
}
