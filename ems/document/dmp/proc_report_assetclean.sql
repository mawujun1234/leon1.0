/**
* 调用方法call proc_report_assetclean('20151101');
计算截止某一个设备的 残值情况
*
**/
create or replace procedure proc_report_assetclean(in_day_now varchar2)
as
  --v_day_now varchar2(8):=to_char(sysdate,'yyyymmdd');
begin
  --第一次别忘记执行
  --update ems_equipmentprod set depreci_year=5 where depreci_year is null;
  --清理数据
  delete report_assetclean where day_key=in_day_now;
  --插入初始化数据,day_key=导数据的日期，可使用天数
  insert into report_assetclean(id,ecode,day_key,day_have)
  select sys_guid() AS id,ecode,in_day_now as day_key,b.depreci_year*365 AS day_have 
  from ems_equipment a,ems_equipmentprod b
  where a.prod_id=b.id 
  and to_char(a.fisData,'yyyymmdd')<=in_day_now;--只计算截止当前时间为止的已经入库了的设备
  commit;

  --计算老旧订单的剩余可使用天数,覆盖默认的可使用天数(上一条中读取的)
  --下面3行，为了清理不正常的数据否则，加出来为null
  update ems_orderlist set depreci_year=0 where  depreci_year is null;
  update ems_orderlist set depreci_month=0 where  depreci_month is null;
  update ems_orderlist set depreci_day=0 where  depreci_day is null;
  commit;
  update report_assetclean a set day_have=(
  select day_have from (
  select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
  where a.id=b.order_id and b.id=c.orderlist_id and a.ordertype='old_equipment' and (depreci_year is not null and depreci_month is not null and depreci_day is not null )
  ) b where a.ecode=b.ecode  and day_have!=0
  )
  where exists (select 1 from (
  select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
  where a.id=b.order_id and b.id=c.orderlist_id and a.ordertype='old_equipment'
  ) b where a.ecode=b.ecode  and day_have!=0) and a.day_key=in_day_now;
commit;
  --计算维修后重新设置的可使用天数，覆盖前面设置的可使用天数
  --下面3行，为了清理不正常的数据否则，加出来为null
  update ems_repair set depreci_year=0 where  depreci_year is null;
  update ems_repair set depreci_month=0 where  depreci_month is null;
  update ems_repair set depreci_day=0 where  depreci_day is null;
  commit;
  update report_assetclean a set day_have=(
  select day_have from (
    select * from (
    select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
    select ecode,max(id) from ems_repair
    where  status in ('over')
    and to_char(str_in_date,'yyyymmdd')<=in_day_now--只计算截止当前时间为止的维修好了的设备
    group by id,ecode
    ) b
    where a.ecode=b.ecode
    ) where day_have!=0--排除历史中没有数据的
  ) b where a.ecode=b.ecode and  b.day_have!=0
  )
  where exists (select 1 from (
    select * from (
    select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
    select ecode,max(id) from ems_repair
    where  status in ('over')
    and to_char(str_in_date,'yyyymmdd')<=in_day_now--只计算截止当前时间为止的维修好了的设备
    group by id,ecode
    ) b
    where a.ecode=b.ecode
    ) where day_have!=0--排除历史中没有数据的
  ) b where a.ecode=b.ecode) and a.day_key=in_day_now;
commit;
  ---更新已经使用天数的sql
update report_assetclean a set day_used=(
select day_used from (
      select ecode,sum(ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))) day_used from (
      select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
      from ems_equipmentcycle 
      where (operateType='task_install' or operateType='task_cancel')
      and to_char(operateDate,'yyyymmdd')<=in_day_now--截止指定时间的设备安装过的时间
      order by ecode,operatedate
      ) where operateType='task_install'
      group by ecode
) b where a.ecode=b.ecode
) where exists ( select 1 from ems_equipmentcycle c 
        where a.ecode=c.ecode 
        and to_char(c.operateDate,'yyyymmdd')<=in_day_now--截止指定时间的设备安装过的时间
  ) and a.day_key=in_day_now;
commit;
  --更新原值
  update report_assetclean a set value_original=(
  select unitPrice from (
  select  b.ecode,a.unitPrice from ems_orderlist a,ems_equipment b
  where a.id=b.orderlist_id
  ) b where a.ecode=b.ecode
  ) where exists(
  select 1 from ems_equipment c where a.ecode=c.ecode
  );

  commit;

  --计算净值和原值
  update report_assetclean a set value_old=(value_original*0.95/day_have*day_used);
  update report_assetclean a set value_net=(value_original-value_old);
  commit;
end;
