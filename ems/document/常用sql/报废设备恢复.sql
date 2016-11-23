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

---设备走了报废流程后，发现设备还可以用，重新启用
--删除报废单
delete ems_scrap where ecode='030101-**-1507310037'
--修改设备状态
update ems_equipment set status='outside_repairing',place='repair' where ecode='030101-**-1507310037'
--把设备挂到维修中心
insert into ems_equipment_repair(ecode,repair_id,num,type,type_id,from_id,inDate)
values('030101-**-1507310037','2c9085494d1dba52014d21aeb25a0005',1,'repair','20160802095028008'
       ,'2c9085494d1dba52014d21ae5e000004',to_date('2016-08-05','yyyy-mm-dd'))
--修改维修单的状态为维修中
update ems_repair set status='repairing' where  ecode='030101-**-1507310037' and id='20160802095028008'
--删除该设备的生命周期
delete ems_equipmentcycle where  ecode='030101-**-1507310037' and operatetype='scrap'

