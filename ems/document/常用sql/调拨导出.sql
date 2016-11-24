select * from ems_adjust
select * from ems_adjustlist b



select g.id as "调拨单号",h.name as "出库仓库",i.name as "入库仓库"
,decode(g.status,'carry','在途','partin','未全入','over','完成','未知状态') "状态"
,decode(g.returnStatus,'neednot','不必归还','nonereturn','未归还','partreturn','部分归还','over','全部归还','未知状态') "归还状态"
,b.name as "小类",c.name as "品名",c.style as "型号",c.spec as "规格",d.name as "品牌",e.name as "供应商" 
,f.ecode "条码",1 as "数量",j.unitprice as "单价"
      from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    inner join ems_supplier e on a.supplier_id=e.id
    inner join ems_adjustlist f on a.ecode=f.ecode
    inner join ems_adjust  g on f.adjust_id=g.id
    inner join ems_store h on g.str_out_id=h.id
    inner join ems_store i on g.str_in_id =i.id
    inner join ems_orderlist j on a.orderlist_id=j.id
