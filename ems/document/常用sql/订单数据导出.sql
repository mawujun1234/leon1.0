select a.orderno as "订单号",c.name as "仓库",decode(a.status,'edit','编辑中','已确认') as "状态"
,d.name as "项目",f.name as "操作者",e.name as "供应商",decode(a.orderType,'old_equipment','旧品订单','新品订单') as "订单类型"
,a.orderDate as "订购日期",h.name as "设备类型",g.name as "品名",i.name as "品牌"
,g.style as "型号",g.quality_month as "质保",g.spec as "规格",g.unit as "单位"
,b.ordernum as "订购数量",b.unitPrice as "单价"
,TO_CHAR(A.CREATEDATE,'YYYY-mm-dd') "订单创建日期"
from 
ems_order a 
inner join ems_orderlist b on a.id=b.order_id
inner join ems_store c on a.store_id=c.id
inner join ems_project d on a.project_id=d.id
inner join ems_supplier e on a.supplier_id=e.id
inner join sys_user f on a.operater=f.id
inner join ems_equipmentprod g on b.prod_id=g.id
inner join ems_equipmentsubtype h on b.subtype_id=h.id
inner join ems_brand i on g.brand_id=i.id
order by a.orderDate,a.orderno