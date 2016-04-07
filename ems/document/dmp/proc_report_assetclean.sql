/**
***call proc_report_assetclean();
**
**/
create or replace procedure proc_report_assetclean
as
  v_day_now varchar2(8):=to_char(sysdate,'yyyymmdd');
begin
  --��һ�α����ִ��
  --update ems_equipmentprod set depreci_year=5 where depreci_year is null;
  --�������
  delete report_assetclean where day_now=v_day_now;
  --�����ʼ�����,day_now=����ݵ����ڣ���ʹ������
  insert into report_assetclean(id,ecode,day_now,day_have)
  select sys_guid() AS id,ecode,v_day_now as day_now,b.depreci_year*365 AS day_have from ems_equipment a,ems_equipmentprod b
  where a.prod_id=b.id;
  commit;
  
  --�����Ͼɶ�����ʣ���ʹ������,����Ĭ�ϵĿ�ʹ������(��һ���ж�ȡ��)
  update report_assetclean a set day_have=(
  select day_have from (
  select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
  where a.id=b.order_id and b.id=c.orderlist_id and a.ordertype='old_equipment'
  ) b where a.ecode=b.ecode
  )
  where exists (select 1 from (
  select c.ecode,b.depreci_year*365+b.depreci_month*30+b.depreci_day as day_have from ems_order a,ems_orderlist b,ems_equipment c
  where a.id=b.order_id and b.id=c.orderlist_id and a.ordertype='old_equipment'
  ) b where a.ecode=b.ecode) and a.day_now=v_day_now;

  --����ά�޺��������õĿ�ʹ�������ǰ�����õĿ�ʹ������
  update report_assetclean a set day_have=(
  select day_have from (
    select * from (
    select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
    select ecode,max(id) from ems_repair
    where  status in ('over')
    group by id,ecode
    ) b
    where a.ecode=b.ecode
    ) where day_have is not null--�ų���ʷ��û����ݵ�
  ) b where a.ecode=b.ecode and  b.day_have is not null
  )
  where exists (select 1 from (
    select * from (
    select a.ecode,a.depreci_year*365+a.depreci_month*30+a.depreci_day as day_have from ems_repair a,(
    select ecode,max(id) from ems_repair
    where  status in ('over')
    group by id,ecode
    ) b
    where a.ecode=b.ecode
    ) where day_have is not null--�ų���ʷ��û����ݵ�
  ) b where a.ecode=b.ecode) and a.day_now=v_day_now;

  ---�����Ѿ�ʹ�������sql
update report_assetclean a set day_used=(
select day_used from (
      select ecode,sum(ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))) day_used from (
      select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
      from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
      order by ecode,operatedate
      ) where operateType='task_install'
      group by ecode
) b where a.ecode=b.ecode
) where exists ( select 1 from ems_equipmentcycle c where a.ecode=c.ecode) and a.day_now=v_day_now;

  --����ԭֵ
  update report_assetclean a set value_original=(
  select unitPrice from (
  select  b.ecode,a.unitPrice from ems_orderlist a,ems_equipment b
  where a.id=b.orderlist_id
  ) b where a.ecode=b.ecode
  ) where exists(
  select 1 from ems_equipment c where a.ecode=c.ecode
  );



  --���㾻ֵ��ԭֵ
  update report_assetclean a set value_depreciation=(value_original*0.95/day_have*day_used);
  update report_assetclean a set value_net=(value_original-value_depreciation);

end;
