create or replace procedure changBaseData(in_prod_id in varchar2)
as

begin
  for orderlist in (
    --查询订单明细
    select b.* from ems_orderlist b
    where b.prod_id =in_prod_id
    --select b.* from ems_order a,ems_orderlist b
    --where  a.id=b.order_id and a.orderno=in_orderno and prod_id =in_prod_id
  ) loop
    --往订单明细中插入新的二级数据
    insert into ems_orderlist(id,brand_id,prod_id,subtype_id,type_id,order_id,ordernum,unitprice,quality_month)
    select sys_guid(),a.brand_id,a.id,orderlist.subtype_id,orderlist.type_id,orderlist.order_id,orderlist.ordernum,orderlist.unitprice,orderlist.quality_month
    from ems_equipmentprod a where parent_id=in_prod_id;
    
    --在订单明细中把原来的一级数据取消掉，加了个]符号，让他们不进行关联
    update ems_orderlist set order_id=order_id||']' where id=orderlist.id;
    --commit;
    --获取新插入的两条订单明细数据
    for list11 in (
      select b.* from ems_orderlist b
      where b.order_id=orderlist.order_id and b.prod_id like in_prod_id||'-%' and id!=orderlist.id||']'
    ) loop
      --更新条码的数据的orderlist_id
      update ems_barcode a 
      set orderlist_id=list11.id
      where a.prod_id=list11.prod_id and a.orderlist_id=orderlist.id;
      --更新ems_equipment中的orderlist_id
       update ems_equipment a 
      set orderlist_id=list11.id
      where a.prod_id=list11.prod_id and a.orderlist_id=orderlist.id;
      --更新订单明细中的已入库数量
      update ems_orderlist a set totalNum=
      (select count(ecode) from ems_equipment b where a.id=b.orderlist_id and b.orderlist_id=list11.id)
      where a.id=list11.id;
      --更新规格
      update ems_equipmentprod  a set spec=
      (select b.spec from ems_equipmentprod b where b.id=in_prod_id)||spec
      where a.id=list11.prod_id;
       --更新保质期
      update ems_equipmentprod  a set quality_month=
      (select b.quality_month from ems_equipmentprod b where b.id=in_prod_id)
      where a.id=list11.prod_id;
      
    end loop;
    --取消套件的
    update ems_equipmentprod set status='N' where id=in_prod_id;
  END LOOP;
end;
/
