alter table EMS_ORDERLIST modify prod_id VARCHAR2(10 CHAR);
create table EMS_ORDERLIST_0716 as select * from EMS_ORDERLIST;
create table ems_barcode_0716 as select * from ems_barcode;
create table ems_equipment_0716 as select * from ems_barcode;
create table ems_equipmentprod_0716 as select * from ems_equipmentprod;



--call changBaseData('04021Q','NBDW-2014-058(05-YZSN)');--NBDW-2014-058(05-YZSN)
call changBaseData('04021Q');
 --���һ��Ʒ��
update ems_equipmentprod set parent_id=null where parent_id='04021Q';
--NBDW-2014-058(05-YZSN) NBDW-2014-058(YZ-01YJ)
call changBaseData('04020J');
 --���һ��Ʒ��
update ems_equipmentprod set parent_id=null where parent_id='04020J';
--call changBaseData('04020J','NBDW-2014-058(05-YZSN)');
--call changBaseData('04020J','NBDW-2014-058(YZ-01YJ)');
--NBDW-2014-058(05-YZSN) NBDW-2014-058(YZ-01YJ)
call changBaseData('04020K');
 --���һ��Ʒ��
update ems_equipmentprod set parent_id=null where parent_id='04020K';
--call changBaseData('04020K','NBDW-2014-058(05-YZSN)');
--call changBaseData('04020K','NBDW-2014-058(YZ-01YJ)');
--
--������Ʒ����ʵ�������Ƕ���
select b.style, count(a.ecode) 
from ems_equipment a,ems_equipmentprod b
where a.prod_id=b.id and a.prod_id in ('04021Q-01','04021Q-02' )
group by b.style

select b.style, count(a.ecode) 
from ems_equipment a,ems_equipmentprod b
where a.prod_id=b.id and a.prod_id in ('04020J-01','04020J-02' )
group by b.style

select b.style, count(a.ecode) 
from ems_equipment a,ems_equipmentprod b
where a.prod_id=b.id and a.prod_id in ('04020K-01','04020K-02' )
group by b.style

  
--��������е�����������Ƿ�һ��
select b.style, count(a.ecode) 
from ems_barcode a,ems_equipmentprod b
where a.prod_id=b.id and a.prod_id in ('04021Q-01','04021Q-02' ) and a.status=1
group by b.style 

select b.style, count(a.ecode) 
from ems_barcode a,ems_equipmentprod b
where a.prod_id=b.id and a.prod_id in ('04020J-01','04020J-02' ) and a.status=1
group by b.style 

select b.style, count(a.ecode) 
from ems_barcode a,ems_equipmentprod b
where a.prod_id=b.id and a.prod_id in ('04020K-01','04020K-02' ) and a.status=1
group by b.style 

--��鶩�������������equipment�е��������Ƿ�һ��
select * from ems_order where orderno='NBDW-2014-058(05-YZSN)'
select sum(totalnum) from ems_order a,ems_orderlist b
where a.id=b.order_id and a.orderno='NBDW-2014-058(05-YZSN)'
select count(c.ecode) from 
ems_orderlist b,ems_equipment c 
where   b.id=c.orderlist_id and b.order_id='53B8A43437D64A359BD29F7BF99E2040'

select * from ems_order where orderno='NBDW-2014-058(YZ-01YJ)'
select sum(totalnum) from ems_order a,ems_orderlist b
where a.id=b.order_id and a.orderno='NBDW-2014-058(YZ-01YJ)'
select count(c.ecode) from 
ems_orderlist b,ems_equipment c 
where   b.id=c.orderlist_id and b.order_id='c9c3badb-4234-47bf-8411-7e2dc6498ee2'

--����ܵĶ������������ʵ���������������
select count(ecode) from ems_equipment
select sum(totalnum) from ems_orderlist where order_id not like '%]'

--����µĺ��ϵ����ݲ���
select * from ems_equipment a,ems_equipment_0716 b
where a.ecode=b.ecode and a.orderlist_id!=b.orderlist_id

--���orderlist�е���Ч����
delete ems_orderlist where order_id like '%]'





select b.* from ems_orderlist b
    where  prod_id like '04021Q-%'
select * from ems_orderlist where prod_id like '04021Q%'   

select * from ems_barcode where orderlist_id='694CB63E275E4718B696C3CE5CCE94E2' 
and status=1
select * from ems_barcode where orderlist_id='700A15528D584822B30C084FC5F7D4D5'    
and status=1

select * from  ems_equipmentprod where id like '04021Q%' 


select * from ems_orderlist where order_id='c9c3badb-4234-47bf-8411-7e2dc6498ee2'
select * from ems_orderlist where prod_id like '04020K%'

--����һ�����к󣬼�����������



select * from ems_order where orderno='NBDW-2014-058(YZ-01YJ)'
select * from ems_order where id='53B8A43437D64A359BD29F7BF99E2040'

