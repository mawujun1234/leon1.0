select a.id as "维修单号",decode(a.status,'to_repair','发往维修中心','repairing','维修中','back_store','返库途中','over','完成','scrap_confirm','报废确认中','scrap','报废','未知状态') as "状态"
,b.id as "报废单号"
,d.name as "作业单位",e.name as "维修人" ,f.name as "小类",f.style as "型号"
,1 as "数量",j.unitprice as "单价"
from ems_repair a 
left join ems_scrap b on a.id=b.repair_id
left join ems_workunit d on a.workunit_id=d.id
left join sys_user e on a.rpa_user_id=e.id
left join ems_equipmentprod f on a.prod_id=f.id
inner join ems_equipment g on a.ecode=g.ecode
inner join ems_orderlist j on g.orderlist_id=j.id
