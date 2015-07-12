create or replace procedure proc_report_day_sparepart(store_id_in in varchar2, daykey_in in varchar2)
as
lastdaykey_in varchar2(8):=to_char(TRUNC(to_date(daykey_in,'yyyymmdd'))-1,'yyyymmdd');
begin
  --���㵱�յĻ�������
  
  --ɾ�������е�����,���¼���
  delete report_day_sparepart where daykey=day_in and store_id=store_id_in;
  
  --�ɹ�����
  select prod_id,sum(purchasenum) from (
    select c.prod_id,count(b.ecode) as purchasenum from ems_instore a,ems_instorelist b,ems_equipment c
    where a.id=b.instore_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150710' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    group by c.prod_id
    union all
    select c.prod_id,count( b.ecode) as purchasenum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150712' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003'  
    and b.isnew='Y' and b.adjustListStatus='in'
    group by c.prod_id
  )
  group by prod_id
  
  --��Ʒ����
  select prod_id,sum(oldnum) from (
    select c.prod_id,count(b.ecode) as oldnum from ems_installin a,ems_installinlist b,ems_equipment c
    where a.id=b.installIn_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150712' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.isBad='N' and b.installInListType='other'
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as oldnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150712' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003'  
    and b.isnew='N' and b.adjustListStatus='in'
    group by c.prod_id    
  )
  group by prod_id
  
  --��������
  select prod_id,sum(installoutnum) from (
    select c.prod_id,count(b.ecode) as installoutnum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150711' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.installOutListType='installout' and a.status='over'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as installoutnum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150711' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.borrowListType='installout'
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as installoutnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') ='20150712' and a.str_out_id='2c9085494d1dba52014d21ae15eb0003'  
    and a.adjustType='installout'
    group by c.prod_id
  )
  group by prod_id
  
  --����ά�޷�����
  select c.prod_id,count(a.ecode) as repairinnum from ems_repair a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150712' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003'
  and a.status='over'
  group by c.prod_id
  
  
  --������
  select c.prod_id,count(a.ecode) as scrapoutnum from ems_scrap a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.operateDate,'yyyymmdd') ='20150712' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
  and a.status='scrap'
  group by c.prod_id
  
  --ά�޳�������
   select c.prod_id,count(a.ecode) as repairoutnum from ems_repair a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') ='20150712' and a.str_out_id='2c9085494d1dba52014d21ae15eb0003'
  group by c.prod_id
  
  --���ڽ���� 
  select prod_id,sum(borrownum) from (
    select c.prod_id,count(b.ecode) as borrownum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150711' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.installOutListType='borrow' and a.status='over'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as borrownum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150711' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.borrowListType='borrow' and a.status in ('over','noreturn')
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as borrownum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') ='20150712' and a.str_out_id='2c9085494d1dba52014d21ae15eb0003'  
    and a.adjustType='borrow'
    group by c.prod_id
  )
  group by prod_id 
  
  --�黹��
   select c.prod_id,count(b.ecode) as borrowreturnnum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150711' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.installOutListType='borrow' and a.status='over' and b.isreturn='Y'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as borrowreturnnum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') ='20150711' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
    and b.borrowListType='borrow' and a.status in ('over','noreturn') and b.isreturn='Y'
    group by c.prod_id  
     union all
    select c.prod_id,count( b.ecode) as borrowreturnnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150712' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003' 
    and a.adjustType='returnback' and b.adjustListStatus='in'
    group by c.prod_id   
    
end;  
