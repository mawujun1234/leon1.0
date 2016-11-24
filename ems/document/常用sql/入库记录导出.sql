select a.id||'' as "入库编号",a.operatedate as "操作者",b.name as "仓库名称",c.name as "操作者" 
,e.ecode  as "条码",f.name as "小类",g.name as "品名"，g.spec as "规格"
,h.name as "品牌",i.name as "供应商"
,1 as "数量",j.unitprice as "单价"
from ems_instore a 
inner  join ems_store b on a.store_id=b.id
inner join sys_user c on a.operater=c.id
inner join ems_instorelist d on d.inStore_id=a.id
inner join ems_equipment e on d.ecode=e.ecode
inner join ems_equipmentsubtype f on e.subtype_id=f.id
inner join ems_equipmentprod g on e.prod_id=g.id
inner join ems_brand h on e.brand_id=h.id
inner join ems_supplier i on e.supplier_id=i.id
inner join ems_orderlist j on e.orderlist_id=j.id
    


select count(*) from ems_instorelist

select * from ems_equipment
select * from ems_orderlist
