select * from ems_equipmentcycle
where ecode='030101-**-1507310037'
order by operatedate

select * from ems_scrap
where ecode='030101-**-1507310037'
select * from ems_equipment
where ecode='04020J-01-1507210010'
select * from ems_equipment_repair
where ecode='030101-**-1507310037'
select * from ems_repair where id='20160802095028008'
where ecode='030101-**-1507310037'

---�豸���˱������̺󣬷����豸�������ã���������
--ɾ�����ϵ�
delete ems_scrap where ecode='030101-**-1507310037'
--�޸��豸״̬
update ems_equipment set status='outside_repairing',place='repair' where ecode='030101-**-1507310037'
--���豸�ҵ�ά������
insert into ems_equipment_repair(ecode,repair_id,num,type,type_id,from_id,inDate)
values('030101-**-1507310037','2c9085494d1dba52014d21aeb25a0005',1,'repair','20160802095028008'
       ,'2c9085494d1dba52014d21ae5e000004',to_date('2016-08-05','yyyy-mm-dd'))
--�޸�ά�޵���״̬Ϊά����
update ems_repair set status='repairing' where  ecode='030101-**-1507310037' and id='20160802095028008'
--ɾ�����豸����������
delete ems_equipmentcycle where  ecode='030101-**-1507310037' and operatetype='scrap'

