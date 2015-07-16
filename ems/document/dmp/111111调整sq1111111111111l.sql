select * from ems_equipmentprod
where id='04020J'
select * from ems_equipmentprod
where id='04021Q'

select * from ems_orderlist where prod_id in ('04021Q')
select * from ems_equipmentprod where parent_id='04021Q' 
------------04021Q
alter table EMS_ORDERLIST modify prod_id VARCHAR2(10 CHAR);
create table EMS_ORDERLIST_0716 as select * from EMS_ORDERLIST;
create table ems_barcode_0716 as select * from ems_barcode;

--查询订单明细
select b.* from ems_order a,ems_orderlist b
where a.id=b.order_id and prod_id in ('04021Q')

--往订单明细中插入新的二级数据
insert into ems_orderlist(id,brand_id,prod_id,subtype_id,type_id,order_id,ordernum,unitprice,quality_month)
select sys_guid(),a.brand_id,a.id,'0402','04','53B8A43437D64A359BD29F7BF99E2040' as order_id,21 as ordernum,605 as unitprice,36 as quality_month
from ems_equipmentprod a where parent_id='04021Q'

--在订单明细中把原来的一级数据取消掉，加了个]符号，让他们不进行关联
update ems_orderlist set order_id=order_id||']' where prod_id='04021Q' and order_id='53B8A43437D64A359BD29F7BF99E2040'

--更新条码的数据的orderlist_id
update ems_barcode a 
set orderlist_id=
(select b.id from ems_orderlist b where a.prod_id=b.prod_id and b.prod_id='04021Q-01')
where a.prod_id='04021Q-01'
update ems_barcode a 
set orderlist_id=
(select b.id from ems_orderlist b where a.prod_id=b.prod_id and b.prod_id='04021Q-01')
where a.prod_id='04021Q-02'
select * from ems_equipment where prod_id='04021Q-02'
update ems_equipment a set orderlist_id=(select b.orderlist_id from ems_barcode b where a.ecode=b.ecode)
where a.prod_id='04021Q-01'
update ems_equipment a set orderlist_id=(select b.orderlist_id from ems_barcode b where a.ecode=b.ecode)
where a.prod_id='04021Q-02'
--更新订单明细中的已入库数量
update ems_orderlist a set totalNum=
(select count(ecode) from ems_equipment b where a.id=b.orderlist_id )
where a.prod_id='04021Q-01'
update ems_orderlist a set totalNum=
(select count(ecode) from ems_equipment b where a.id=b.orderlist_id )
where a.prod_id='04021Q-02'


update ems_equipmentprod set parent_id=null where id='04021Q-01';
update ems_equipmentprod set parent_id=null where id='04021Q-02';
update ems_equipmentprod set status='N' where id='04021Q';
--更新规格
update ems_equipmentprod  a set spec=
(select b.spec from ems_equipmentprod b where b.id='04021Q')||spec
where a.id='04021Q-01'
update ems_equipmentprod  a set spec=
(select b.spec from ems_equipmentprod b where b.id='04021Q')||spec
where a.id='04021Q-02'



select * from ems_equipmentprod
where id='04021Q-02'
select * from ems_equipment where prod_id='04021Q-01'
delete ems_orderlist where subtype_id='02'
select * from ems_orderlist where prod_id='04021Q-02'

select b.id from ems_orderlist b where  b.prod_id='04021Q-01'
