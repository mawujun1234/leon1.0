create or replace procedure proc_report_day_sparepart(store_id_in in varchar2, daykey_in in varchar2)
as
lastdaykey_in varchar2(8):=to_char(TRUNC(to_date(daykey_in,'yyyymmdd'))-1,'yyyymmdd');
begin
  --计算当日的汇总数据
  
  --删掉现在有的数据,重新计算
  delete report_day_sparepart where daykey=day_in and store_id=store_id_in;
  
  
  select prod_id,sum(purchasenum) from (
    select c.prod_id,count(b.ecode) as purchasenum from ems_instore a,ems_instorelist b,ems_equipment c
    where a.id=b.instore_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150626' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    group by c.prod_id
    union all
    select c.prod_id,count( b.ecode) as purchasenum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150626' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003'  and b.isnew='Y'
    group by c.prod_id
  )
  group by prod_id
  

  select prod_id,sum(oldnum) from (
    select c.prod_id,count(b.ecode) as oldnum from ems_installin a,ems_installinlist b,ems_equipment c
    where a.id=b.installIn_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150626' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.isBad='N' and b.installInListType='other'
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as oldnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150626' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003'  and b.isnew='N'
    group by c.prod_id    
  )
  group by prod_id
  
  select prod_id,sum(installoutnum) from (
    select c.prod_id,count(b.ecode) as installoutnum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150626' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.installOutListType='installout'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as installoutnum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150626' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.borrowListType='installout'
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as installoutnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') ='20150626' and a.str_out_id='2c9085494d1dba52014d21ae15eb0003'  
    and a.adjustType='installout'
    group by c.prod_id
  )
  group by prod_id
  
  
  
  
  
end;  
