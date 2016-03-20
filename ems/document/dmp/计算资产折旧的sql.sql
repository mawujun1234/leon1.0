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


select * from (
select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
select ecode,max(id) from ems_repair
where  status in ('over')
group by id,ecode
) b
where a.ecode=b.ecode 
) where day_have is not null--排除历史中没有数据的


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


update ems_assetclean a set day_hava=(
select day_hava from (
  select * from (
  select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
  select ecode,max(id) from ems_repair
  where  status in ('over')
  group by id,ecode
  ) b
  where a.ecode=b.ecode 
  ) where day_have is not null--排除历史中没有数据的
) b where a.ecode=b.ecode and  b.day_have is not null
) 
where exists (select 1 from (
  select * from (
  select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
  select ecode,max(id) from ems_repair
  where  status in ('over')
  group by id,ecode
  ) b
  where a.ecode=b.ecode 
  ) where day_have is not null--排除历史中没有数据的
) b where a.ecode=b.ecode) and a.day_now=to_char(sysdate,'yyyymmdd')




select * from ems_equipmentcycle where operateType='task_install'

select * from ems_equipmentcycle where operateType='task_cancel'


select a.ecode,a.operatedate,b.operatedate from ems_equipmentcycle a,ems_equipmentcycle b
where a.ecode=b.ecode and a.operateType='task_install' and b.operateType='task_cancel'
and a.ecode='030101-**-1507310028'
order by a.ecode,a.operatedate

select * from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
and ecode='030101-**-1507310028'
order by ecode,operatedate

select ecode,count(ecode) from ems_equipmentcycle where operateType='task_install' or operateType='task_cancel'
group by ecode
having count(ecode)>3



