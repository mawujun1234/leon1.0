
// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) 
{ //author: meizz 
  var o = { 
    "M+" : this.getMonth()+1,                 //月份 
    "d+" : this.getDate(),                    //日 
    "h+" : this.getHours(),                   //小时 
    "m+" : this.getMinutes(),                 //分 
    "s+" : this.getSeconds(),                 //秒 
    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
    "S"  : this.getMilliseconds()             //毫秒 
  }; 
  if(/(y+)/.test(fmt)) 
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o) 
    if(new RegExp("("+ k +")").test(fmt)) 
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
  return fmt; 
}  

/*
  将String类型解析为Date类型.
  parseDate('2006-1-1') return new Date(2006,0,1)
  parseDate(' 2006-1-1 ') return new Date(2006,0,1)
  parseDate('2006-1-1 15:14:16') return new Date(2006,0,1,15,14,16)
  parseDate(' 2006-1-1 15:14:16 ') return new Date(2006,0,1,15,14,16);
  parseDate('2006-1-1 15:14:16.254') return new Date(2006,0,1,15,14,16,254)
  parseDate(' 2006-1-1 15:14:16.254 ') return new Date(2006,0,1,15,14,16,254)
  parseDate('不正确的格式') retrun null
*/
Date.prototype.parseDate=function(str){
  if(typeof str == 'string'){
    var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);
    if(results && results.length>3)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3])); 
    results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
    if(results && results.length>6)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6])); 
    results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);
    if(results && results.length>7)
      return new Date(parseInt(results[1]),parseInt(results[2]) -1,parseInt(results[3]),parseInt(results[4]),parseInt(results[5]),parseInt(results[6]),parseInt(results[7])); 
  }
  return null;
} 


