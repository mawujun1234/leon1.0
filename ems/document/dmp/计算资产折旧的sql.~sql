select * from ems_equipment
select * from ems_equipmentprod
--插入初始化数据
insert into report_assetclean(id,ecode,day_now,day_have) 
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




-- Create/Recreate indexes 
create index AssetClean_ecode on report_assetclean (ecode);
create index AssetClean_day_now on report_assetclean (day_now);
-- Create/Recreate indexes 
create index lifecycle on EMS_EQUIPMENTCYCLE (ecode);
--初始化所有设备，默认使用年限是5年
update ems_equipmentprod set depreci_year=5 where depreci_year is null;
--计算老旧订单的剩余使用年限
update report_assetclean a set day_have=(
select day_have from (
select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
where a.id=b.order_id and b.id=c.orderlist_id and a.ordertype='old_equipment'
) b where a.ecode=b.ecode
) 
where exists (select 1 from (
select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
where a.id=b.order_id and b.id=c.orderlist_id and a.ordertype='old_equipment'
) b where a.ecode=b.ecode) and a.day_now=to_char(sysdate,'yyyymmdd')



select distinct c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
where a.id=b.order_id and b.prod_id=c.prod_id and a.ordertype='old_equipment'

--计算折旧后重新设置的有效期
update report_assetclean a set day_have=(
select day_have from (
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
update report_assetclean a set day_used=(
select day_used from (
      select ecode,sum(ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))) day_used from (
      select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
      from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
      order by ecode,operatedate
      ) where operateType='task_install'
      group by ecode
) b where a.ecode=b.ecode
) where exists ( select 1 from ems_equipmentcycle c where a.ecode=c.ecode) and
  a.day_now=to_char(sysdate,'yyyymmdd')

--更新原值
update report_assetclean a set value_original=(
select unitPrice from (
select  b.ecode,a.unitPrice from ems_orderlist a,ems_equipment b
where a.id=b.orderlist_id
) b where a.ecode=b.ecode
) where exists(
select 1 from ems_equipment c where a.ecode=c.ecode
)



--计算净值和原值
update report_assetclean a set value_old=(value_original*0.95/day_have*day_used);
update report_assetclean a set value_net=(value_original-value_old);


delete report_assetclean
call proc_report_assetclean('20151101');
call proc_report_assetclean_init('20160404');
select * from report_assetclean where day_have is  null
select count(*) from report_assetclean 

select distinct day_key from report_assetclean 
select * from ems_equipment



UPDATE ems_pole SET STATUS='using' WHERE ID IN (
select A.ID from ems_pole a
where  a.status='hitch'
and not exists (select * from ems_task b where a.id=b.pole_id and b.status!='complete')
AND exists (select * from ems_task b WHERE a.id=b.pole_id and b.status='complete' AND B.TYPE='newInstall')
)

select * from ems_pole where id='2c9085494d4c5635014d551eefaf0020'

select * from ems_task where pole_id='2c9085494d4c5635014d551eefaf0020'
select * from  ems_handlemethod where id='82f9ecbd-9e09-422f-a3b5-b31c371cfd0f'

select * from ems_taskequipmentlist where task_id='20160314-0001'


select * from ems_pole where code='231001'
select * from ems_equipment_pole where pole_id='2c9085494d4c5635014d55115f740000'
select * from ems_task where pole_id='2c9085494d4c5635014d55115f740000'
01010Q-**-1507100001
select * from ems_taskequipmentlist where task_id='20160408-0002'


select * from report_assetclean where day_key='20160418'
update ems_equipment a set (a.value_original,a.value_net)=(select b.value_original,b.value_net from 
report_assetclean b where a.ecode=b.ecode and b.day_key='20160404')

select * from ems_equipment where 
ecode='04030P-**-1508030107'


last_pole_id like '%宁波%'



update ems_equipment set last_pole_id='2c9085494d4c5635014d5558a83c00b3'
where last_pole_id='浙江省宁波市鄞州区鄞州中学门口'

select last_pole_id,count(ecode) from ems_equipment where last_pole_id like '%宁波%'
group by last_pole_id
having count(ecode)>1

select * from ems_equipment where last_pole_id like '%宁波%'

select * from ems_pole where code='QG0044'
select * from ems_equipment where ecode='04030P-**-1508030107'
select * from ems_pole where address='瞻歧镇东城村关兴度路口 '

select a.last_pole_id,b.id from ems_equipment a,ems_pole b
where a.last_pole_id like '%宁波%' and a.last_pole_id=b.province||b.city||b.area||b.address


select c.ecode from (
select a.ecode,a.last_pole_id,count(b.id) as pole_id from ems_equipment a,ems_pole b
where a.last_pole_id like '%宁波%' and a.last_pole_id=b.province||b.city||b.area||b.address
group by a.ecode,a.last_pole_id
having count(b.id)=1
) c --where d.last_pole_id=c.last_pole_id --and d.ecode=c.ecode



select a.ecode,count(b.id) as pole_id from ems_equipment a,ems_pole b
where a.last_pole_id like '%宁波%' and trim(a.last_pole_id)=b.province||b.city||b.area||b.address
group by a.ecode,b.id
having count(b.id)=1



update ems_equipment d set d.last_pole_id=(
select c.pole_id from (
select a.ecode,a.last_pole_id,b.id as pole_id from ems_equipment a,ems_pole b
where a.last_pole_id like '%宁波%' and a.last_pole_id=b.province||b.city||b.area||b.address
and a.ecode in (select c.ecode from (
select a.ecode,a.last_pole_id,count(b.id) as pole_id from ems_equipment a,ems_pole b
where a.last_pole_id like '%宁波%' and a.last_pole_id=b.province||b.city||b.area||b.address
group by a.ecode,a.last_pole_id
having count(b.id)=1
) c)
) c where d.last_pole_id=c.last_pole_id and d.ecode=c.ecode
) where d.ecode in (

  select c.ecode from (
select a.ecode,a.last_pole_id,count(b.id) as pole_id from ems_equipment a,ems_pole b
where a.last_pole_id like '%宁波%' and a.last_pole_id=b.province||b.city||b.area||b.address
group by a.ecode,a.last_pole_id
having count(b.id)=1
) c
)

select * from ems_equipment where last_pole_id like '%宁波%'
select * from ems_equipment where ecode='04030P-**-1508030107'
select * from ems_pole where id='2c9085494d4158a4014d4165ab130005'
select * from ems_equipmentcycle where ecode='04030P-**-1508030107' order by operatedate

select a.ecode,count(a.ecode) from ems_equipmentcycle a,ems_equipmentcycle b
where  a.ecode=b.ecode
and a.operatetype='task_install' and a.operatetype=b.operatetype
 and a.operatedate>b.operatedate --and a.ecode!='04030P-**-1508030107'
 group by a.ecode
 having count(a.ecode)>1
 
 
 select a.* from ems_equipmentcycle a,( select ecode,max(operatedate) max_operatedate from ems_equipmentcycle
 where operatetype='task_install'  --and ecode='04030P-**-1508030107'
 group by ecode) b
where  a.ecode=b.ecode
and a.operatetype='task_install' 
 and a.operatedate=b.max_operatedate 
 
 select ecode,max(operatedate) from ems_equipmentcycle
 where operatetype='task_install'  --and ecode='04030P-**-1508030107'
 group by ecode
 
 
 
 
 
  update ems_equipment a set last_pole_id='2c9085494d4158a4014d4165ab130005' where ecode='04030P-**-1508030107'
 
 update ems_equipment a set last_pole_id=(
 select b.target_id from (
 select a.* from ems_equipmentcycle a,( select ecode,max(operatedate) max_operatedate from ems_equipmentcycle
 where operatetype='task_install'  --and ecode='04030P-**-1508030107'
 group by ecode) b
where  a.ecode=b.ecode
and a.operatetype='task_install' 
 and a.operatedate=b.max_operatedate 
 ) b where a.ecode=b.ecode
 ) 
 where exists (
       select  1 from 
       (
 select a.* from ems_equipmentcycle a,( select ecode,max(operatedate) max_operatedate from ems_equipmentcycle
 where operatetype='task_install'  --and ecode='04030P-**-1508030107'
 group by ecode) b
where  a.ecode=b.ecode
and a.operatetype='task_install' 
 and a.operatedate=b.max_operatedate 
 ) d where a.ecode=d.ecode
 )
 and a.last_pole_id like '%宁波%'



