create or replace procedure proc_report_assetclean
as
--�����key
v_day_key varchar2(8):=to_char(sysdate,'yyyymmdd');
begin
  --��ʼ�������豸��Ĭ��ʹ��������5��
  --update ems_equipmentprod set depreci_year=5 where depreci_year is null;
  -- Create/Recreate indexes 
  --create index AssetClean_ecode on report_assetclean (ecode);
  --create index AssetClean_day_key on report_assetclean (day_key); 
  --create index lifecycle on EMS_EQUIPMENTCYCLE (ecode);

  --ֻ�������µ����ݣ�����Ļ�����Ķ�Ҫ����where����
  delete report_assetclean;
  --�����ʼ�����ݣ�ecode+day_now,�����豸Ĭ�ϵ����޶���5�ˣ����豸�����и��ƹ���
  insert into report_assetclean(ecode,day_key,day_have,day_used,value_original,value_old,value_net) 
  select ecode,v_day_key,b.depreci_year*365,0,0,0,0
  from ems_equipment a,ems_equipmentprod b
  where a.prod_id=b.id;
 --�����Ͼɶ�����ʣ��ʹ������,���û�����þ�ʹ��Ĭ�ϵ���������
  update report_assetclean a set day_have=(
  select day_have from (
  select c.ecode,nvl(b.depreci_year,0)*365+nvl(b.depreci_month,0)*30+nvl(b.depreci_day,0) as day_have from ems_order a,ems_orderlist b,ems_equipment c
  where a.id=b.order_id and b.prod_id=c.prod_id  and c.orderlist_id=b.id and a.ordertype='old_equipment' 
  and ��b.depreci_year is not null or b. depreci_month is not null or b.depreci_day is not null��
  ) b where a.ecode=b.ecode and day_have is not null
  ) where  exists  (select 1 from (
      select c.ecode,nvl(b.depreci_year,0)*365+nvl(b.depreci_month,0)*30+nvl(b.depreci_day,0) as day_have from ems_order a,ems_orderlist b,ems_equipment c
      where a.id=b.order_id and b.prod_id=c.prod_id  and c.orderlist_id=b.id and a.ordertype='old_equipment' 
      and ��b.depreci_year is not null or b. depreci_month is not null or b.depreci_day is not null��  
      )  b where a.ecode=b.ecode
  ) and  a.day_key=v_day_key; 

  --����ά�޺��������õ���Ч��
  update report_assetclean a set day_have=(
    select day_have from (
      --select * from (
      select a.ecode,nvl(a.depreci_year,0)*365+nvl(a.depreci_month,0)*30+nvl(a.depreci_day,0) as day_have 
      from ems_repair a,(
        select ecode,max(id) id from ems_repair
        where  status in ('over')
        group by ecode
      ) b where a.ecode=b.ecode  and a.id=b.id
      and ��a.depreci_year is not null or a. depreci_month is not null or a.depreci_day is not null��
     -- ) where day_have is not null--�ų���ʷ��û�����ݵ�
    ) b where a.ecode=b.ecode and  b.day_have !=0
  ) where  exists  (select 1 from (
      select a.ecode,nvl(a.depreci_year,0)*365+nvl(a.depreci_month,0)*30+nvl(a.depreci_day,0) as day_have 
      from ems_repair a,(
        select ecode,max(id) id from ems_repair
        where  status in ('over')
        group by ecode
      ) b where a.ecode=b.ecode  and a.id=b.id
      and ��a.depreci_year is not null or a. depreci_month is not null or a.depreci_day is not null��
    ) b where a.ecode=b.ecode
  ) and a.day_key=v_day_key;

  --�����豸�Ѿ�ʹ�����������豸������������ͳ���豸�ڵ�λ�ϵ�ʱ��
  update report_assetclean a set day_used=(
  select day_used from (
        select ecode,sum(ROUND(TO_NUMBER((cancel_date+0)-(install_date+0) ))) day_used from (
        select ecode,operateType,operatedate install_date,lead(operatedate, 1, sysdate) over(partition by ecode order by ecode,operatedate) as cancel_date
        from ems_equipmentcycle where (operateType='task_install' or operateType='task_cancel')
        order by ecode,operatedate
        ) where operateType='task_install'
        group by ecode
  ) b where a.ecode=b.ecode
  ) where  exists ( select 1 from ems_equipmentcycle c where a.ecode=c.ecode and (operateType='task_install' or operateType='task_cancel'))
  and a.day_key=v_day_key;
    
   --�Ӷ����л�ȡ�豸��ԭֵ
   update report_assetclean a set value_original=(
    select unitPrice from (
    select  b.ecode,a.unitPrice from ems_orderlist a,ems_equipment b
    where a.id=b.orderlist_id
    ) b where a.ecode=b.ecode
    ) where exists(
    select 1 from ems_equipment c where a.ecode=c.ecode
    ) and  a.day_key=v_day_key;

   
   --�۾�=ԭֵ*95%/1825*N��
   --��ֵ=ԭֵ-�۾�
   update report_assetclean a set value_old=(value_original*0.95/day_have*day_used)
   where  a.day_key=v_day_key;
   update report_assetclean a set value_net=(value_original-value_old)
   where  a.day_key=v_day_key;
/** **/
  commit;
end;
