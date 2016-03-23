select * from ems_equipment
select * from ems_equipmentprod
--插入初始化数据
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

--计算老旧订单的剩余使用年限
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

--计算折旧后重新设置的有效期
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

--获取一个安装卸载的时间
select ecode,ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))from (
select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
and ecode in ('04030P-**-1508030107')
--and ecode in ('030101-**-1507310028','04030P-**-1508030107','040403-**-1507100049','01010Q-**-1507100049')
order by ecode,operatedate
) where operateType='task_install'

select ecode,sum(ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))) from (
select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
and ecode in ('04030P-**-1508030107')
--and ecode in ('030101-**-1507310028','04030P-**-1508030107','040403-**-1507100049','01010Q-**-1507100049')
order by ecode,operatedate
) where operateType='task_install'
group by ecode
---更新已经使用天数的sql
update ems_assetclean a set day_used=(
select day_used from (
      select ecode,sum(ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))) day_used from (
      select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
      from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
      order by ecode,operatedate
      ) where operateType='task_install'
      group by ecode
) b where a.ecode=b.ecode
) where --exists ( select 1 from ems_equipmentcycle c where a.ecode=c.ecode) and
  a.day_now=to_char(sysdate,'yyyymmdd')




