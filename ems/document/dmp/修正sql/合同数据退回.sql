
---用于设备还没有被入库或领用出去的时候
SELECT * FROM EMS_equipmentprod where style='' 
--首先根据合同号找到相应的订单
select * from ems_order
      where orderno='NBDW-2016-020（06-BL）'
select * from ems_order
      where orderno like 'NBDW-2016-020%'
--根据订单id找到相应的订单明细
select * from ems_orderlist where order_id='b498c8ec-7d40-4851-a0c1-90bcc2d746b7'
and prod_id='050104'

--判断这些设备有没有被使用或被入库
select * from ems_equipment_workunit where ecode in (   
  select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
)
select * from ems_equipment_store where ecode in (
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
)
select * from ems_equipment_pole where ecode in (
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
)


begin
  for store in (
    select * from ems_store  where type in (1,3)
  ) loop
    proc_day_sparepart_init(store.id,'20150710');
  END LOOP;
end;

update ems_orderlist set totalnum=(select count(ecode) from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051') where id='bc6faed3-fd68-4a87-8e43-122c69271051'
delete ems_installout a where not exists(select 1 from ems_installoutlist b where a.id=b.installout_id );
delete ems_instore a where not exists(select 1 from ems_instorelist b where a.id=b.instore_id );
--delete ems_order where id='1d651eb4-50af-4530-9779-30d13b6566d8'
--delete ems_orderlist where ID='bc6faed3-fd68-4a87-8e43-122c69271051' --and order_id='1d651eb4-50af-4530-9779-30d13b6566d8'
delete ems_barcode where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051';-- and status='1'
delete ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051';
delete ems_instorelist where ecode in(
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
);
--select * from ems_instorelist where instore_id='20150721134052'
--20150724140513还有删除没有入库记录明细的新品入库单
delete ems_installoutlist where ecode in(
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
);
--select * from ems_installoutlist where installout_id='20150721135630'

delete ems_equipment_workunit where ecode in (
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
);
delete ems_equipment_store where ecode in (
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
);
delete ems_equipment_pole where ecode in (
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
)

delete ems_equipmentcycle where ecode in (
       select ecode from ems_equipment where orderlist_id='bc6faed3-fd68-4a87-8e43-122c69271051'
);




