function TimeControl() {
	this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	this.defaultOffset = new BMap.Size(150, 10);
}

TimeControl.prototype = new BMap.Control();

TimeControl.prototype.initialize = function(map) {
	// 创建一个DOM元素  
	var div = document.createElement("div");
	// 添加文字说明  
	div.setAttribute("title", "轨迹时间");
	div.setAttribute("id", "time_control");
	// 设置样式  
	div.style.cursor = "default";
	div.style.width = '150px';
	div.style.height = '20px';
	div.style.background = "rgba(0,0,0,0.5)";
	div.style.textAlign = "center";
	div.style.fontSize = "10px";
	div.style.color = "#ffffff";
	map.getContainer().appendChild(div);
	// 将DOM元素返回  
	return div;
}