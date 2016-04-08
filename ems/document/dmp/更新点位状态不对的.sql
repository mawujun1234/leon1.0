有一种情况就是，点位正常运行，但是状态显示为黄色，维修中等状态

UPDATE ems_pole SET STATUS='using' WHERE ID IN (
select A.ID from ems_pole a
where  a.status='hitch'
and not exists (select * from ems_task b where a.id=b.pole_id and b.status!='complete')
AND exists (select * from ems_task b WHERE a.id=b.pole_id and b.status='complete' AND B.TYPE='newInstall')
)