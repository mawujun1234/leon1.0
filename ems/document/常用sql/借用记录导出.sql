select a.id "���ñ��",a.operatedate "��������",b.name as "�ֿ�����",c.name as "��ҵ��λ",d.name as "��Ŀ"
,g.name as "С��",h.name as "Ʒ��",h.spec as "���",f.style as "�ͺ�"--,h.unit as prod_unit
,i.name as "Ʒ��",j.name as "��Ӧ��"
,1 as "����",l.unitprice as "����"
from ems_borrow a 
inner join ems_store b on a.store_id=b.id
inner join ems_workunit c on a.workunit_id=c.id
inner join ems_project d on a.project_id=d.id

inner join ems_borrowlist e on a.id=e.borrow_id
inner join ems_equipment f on e.ecode=f.ecode

inner join ems_equipmentsubtype g on f.subtype_id=g.id
inner join ems_equipmentprod h on f.prod_id=h.id
inner join ems_brand i on f.brand_id=i.id
inner join ems_supplier j on f.supplier_id=j.id
inner join ems_orderlist l on f.orderlist_id=l.id

--select * from ems_borrowlist
