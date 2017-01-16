select * from ems_equipmenttype
select * from ems_equipmentsubtype
select * from ems_equipmentprod


select a.id 大类编码,a.name 大类名称,b.id 小类编码,b.name 小类名称,c.id 品名编码,c.name 品名名称,c.style 型号 
from ems_equipmenttype a
inner join ems_equipmentsubtype b on a.id=b.parent_id
inner join ems_equipmentprod c on b.id=c.subtype_id 
order by a.id,b.id,c.id