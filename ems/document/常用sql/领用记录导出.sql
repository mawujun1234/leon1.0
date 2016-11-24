select a.id "领用编号",a.operatedate "领用日期",b.name as "仓库名称",c.name as "作业单位",d.name as "项目"
,g.name as "小类",h.name as "品名",h.spec as "规格",f.style as "型号"--,h.unit as prod_unit
,i.name as "品牌",j.name as "供应商",k.name as "领用类型",e.installouttype_content as "领用类型二级"
,1 as "数量",l.unitprice as "单价"
from ems_installout a 
inner join ems_store b on a.store_id=b.id
inner join ems_workunit c on a.workunit_id=c.id
inner join ems_project d on a.project_id=d.id

inner join ems_installoutlist e on a.id=e.installout_id
inner join ems_equipment f on e.ecode=f.ecode

inner join ems_equipmentsubtype g on f.subtype_id=g.id
inner join ems_equipmentprod h on f.prod_id=h.id
inner join ems_brand i on f.brand_id=i.id
inner join ems_supplier j on f.supplier_id=j.id
inner join ems_installouttype k on e.installOutType_id=k.id
inner join ems_orderlist l on f.orderlist_id=l.id

--select * from ems_installoutlist
