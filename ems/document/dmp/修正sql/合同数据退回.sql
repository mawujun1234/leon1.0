
---用于设备还没有被入库或领用出去的时候
SELECT * FROM EMS_equipmentprod where style='' 
--首先根据合同号找到相应的订单
select * from ems_order
      where orderno like '%2016-267%'
select * from ems_order
      where orderno like 'NBDW-2016-020%'
--根据订单id找到相应的订单明细
select * from ems_orderlist where order_id='9df49df2-e1a6-4d99-bc15-117a7ed00617'
and prod_id='050104'

--判断这些设备有没有被使用或被入库
select * from ems_equipment_workunit where ecode in (   
  select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
)
select * from ems_equipment_store where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
)
select * from ems_equipment_pole where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
)
select * from ems_equipment_repair where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
)

--要从该设备入库的时候开始重新算
begin
  for store in (
    select * from ems_store  where type in (1,3)
  ) loop
    proc_day_sparepart_init(store.id,'20150821');
  END LOOP;
end;

--请除这条明细订单
delete ems_orderlist where id='ba1f9984-177d-427c-b020-c499f3dd5355'
--更新已入库订单数,和上面这一条只能二选一
update ems_orderlist set totalnum=(select count(ecode) from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355') where id='ba1f9984-177d-427c-b020-c499f3dd5355'
delete ems_installout a where not exists(select 1 from ems_installoutlist b where a.id=b.installout_id );
delete ems_instore a where not exists(select 1 from ems_instorelist b where a.id=b.instore_id );
--delete ems_order where id='1d651eb4-50af-4530-9779-30d13b6566d8'
--delete ems_orderlist where ID='ba1f9984-177d-427c-b020-c499f3dd5355' --and order_id='1d651eb4-50af-4530-9779-30d13b6566d8'
delete ems_barcode where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355';-- and status='1'
delete ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355';
select * from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355';
delete ems_instorelist where ecode in(
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);
07012F-**-1608220001

select * from EMS_INSTALLINLIST where ecode='07012F-**-1608220001'
select * from EMS_INSTALLIN where id='20160902145542'

--删除领用返库的数据
delete EMS_INSTALLIN a where not exists(select 1 from EMS_INSTALLINLIST b where a.id=b.installin_id );
delete EMS_INSTALLINLIST where ecode in(
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);

--select * from ems_instorelist where instore_id='20150721134052'
--20150724140513还有删除没有入库记录明细的新品入库单
delete ems_installoutlist where ecode in(
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);
--select * from ems_installoutlist where installout_id='20150721135630'

delete ems_equipment_workunit where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);
delete ems_equipment_store where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);
delete ems_equipment_pole where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);
delete ems_equipment_repair where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
)
delete ems_equipmentcycle where ecode in (
       select ecode from ems_equipment where orderlist_id='ba1f9984-177d-427c-b020-c499f3dd5355'
);




