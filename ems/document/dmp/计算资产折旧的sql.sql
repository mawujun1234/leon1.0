select * from ems_equipment
select * from ems_equipmentprod

insert into ems_assetclean(id,ecode,day_now,day_hava)
select sys_guid(),ecode,to_char(sysdate,'yyyymmdd'),b.depreci_year*365 from ems_equipment a,ems_equipmentprod b
where a.prod_id=b.id

select b.ecode from ems_order a,ems_orderlist b
where a.id=b.order_id and a.ordertype='old_equipment'

4689fb10-b650-4bdc-bbe2-4a9dde9b4822

同个设备只取最新的
select * from ems_repair
where  status in ('back_store','over')

select a.* from ems_repair a,(
select ecode,max(id) from ems_repair
where  status in ('over')
group by id,ecode
) b
where a.ecode=b.ecode


select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_hava from ems_repair a,(
select ecode,max(id) from ems_repair
where  status in ('over')
group by id,ecode
) b
where a.ecode=b.ecode


update ems_assetclean a set day_hava=(
select day_hava from (
select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_hava from ems_order a,ems_orderlist b,ems_equipment c
where a.id=b.order_id and b.prod_id=c.prod_id and a.ordertype='old_equipment'
) b where a.ecode=b.ecode
) 
where exists (select 1 from (
select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_hava from ems_order a,ems_orderlist b,ems_equipment c
where a.id=b.order_id and b.prod_id=c.prod_id and a.ordertype='old_equipment'
) b where a.ecode=b.ecode) and a.day_now=to_char(sysdate,'yyyymmdd')



select distinct c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_hava from ems_order a,ems_orderlist b,ems_equipment c
where a.id=b.order_id and b.prod_id=c.prod_id and a.ordertype='old_equipment'
