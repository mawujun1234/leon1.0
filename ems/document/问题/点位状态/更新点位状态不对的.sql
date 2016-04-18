有一种情况就是，点位正常运行，但是状态显示为黄色，维修中等状态

UPDATE ems_pole SET STATUS='using' WHERE ID IN (
select A.ID from ems_pole a
where  a.status='hitch'
and not exists (select * from ems_task b where a.id=b.pole_id and b.status!='complete')
AND exists (select * from ems_task b WHERE a.id=b.pole_id and b.status='complete' AND B.TYPE='newInstall')
)

原因是因为
select last_pole_id,count(last_pole_id) from ems_equipment where last_pole_id like '%宁波%'
查出来很多中文的点位信息。


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
 