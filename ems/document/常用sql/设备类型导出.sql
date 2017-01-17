select * from ems_equipmenttype
select * from ems_equipmentsubtype
select * from ems_equipmentprod
select * from ems_brand

select a.id 大类编码,a.name 大类名称,b.id 小类编码,b.name 小类名称,c.id 品名编码,c.name 品名名称,c.style 型号 
,c.unit 单位,C.SPEC 规格,d.name 品牌,c.quality_month 质保_月,c.depreci_year 折旧年限
from ems_equipmenttype a
inner join ems_equipmentsubtype b on a.id=b.parent_id
inner join ems_equipmentprod c on b.id=c.subtype_id 
inner join ems_brand d on c.brand_id=d.id
order by a.id,b.id,c.id
