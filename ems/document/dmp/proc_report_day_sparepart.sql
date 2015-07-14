  --���㵱�յķ���������
create or replace procedure proc_report_day_sparepart(in_store_id in varchar2, in_todaykey in varchar2)
as
--�����key
in_yesterdaykey varchar2(8):=to_char(TRUNC(to_date(in_todaykey,'yyyymmdd'))-1,'yyyymmdd');
--����µ�key
in_monthkey varchar2(6):=to_char(TRUNC(to_date(in_todaykey,'yyyymmdd'),'month'),'yyyymm');
--�ϸ��µ�key
--in_lastmonthkey varchar2(6):=to_char(TRUNC(to_date('20150101','yyyymmdd'),'month')-1,'yyyymm');
begin
  --ɾ�������е�����,���¼���
  delete report_day_sparepart where daykey=in_todaykey and store_id=in_store_id;
  
  
 insert into report_day_sparepart(daykey,store_id,prod_id,monthkey,yesterdaynum, purchasenum, oldnum,installoutnum,repairinnum,scrapoutnum,repairoutnum,borrownum,borrowreturnnum)  
 select in_todaykey,in_store_id,prod_id,in_monthkey,sum(yesterdaynum) yesterdaynum, sum(purchasenum) purchasenum,sum(oldnum) oldnum,-sum(installoutnum) installoutnum,sum(repairinnum) repairinnum,sum(scrapoutnum) scrapoutnum,sum(repairoutnum) repairoutnum,-sum(borrownum) borrownum,sum(borrowreturnnum) borrowreturnnum from (
 --��ȡ����Ľ�������
  select prod_id,todaynum as yesterdaynum,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum
  from report_day_sparepart a
  where a.daykey=in_yesterdaykey and a.store_id=in_store_id
  union all
 ----�����������ЩӦ���ǵ��յ÷�����
 --�ɹ�����
  select prod_id,0 yesterdaynum,sum(purchasenum) purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from (
    select c.prod_id,count(b.ecode) as purchasenum from ems_instore a,ems_instorelist b,ems_equipment c
    where a.id=b.instore_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    group by c.prod_id
    union all
    select c.prod_id,count( b.ecode) as purchasenum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') =in_todaykey and a.str_in_id=in_store_id  
    and b.isnew='Y' and b.adjustListStatus='in'
    group by c.prod_id
  )
  group by prod_id
   union all
  --��Ʒ����
  select prod_id,0 yesterdaynum,0 purchasenum,sum(oldnum) oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from (
    select c.prod_id,count(b.ecode) as oldnum from ems_installin a,ems_installinlist b,ems_equipment c
    where a.id=b.installIn_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.isBad='N' and b.installInListType='other'
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as oldnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') =in_todaykey and a.str_in_id=in_store_id  
    and b.isnew='N' and b.adjustListStatus='in'
    group by c.prod_id    
  )
  group by prod_id
   union all
  --��������
  select prod_id,0 yesterdaynum,0 purchasenum,0 oldnum,sum(installoutnum),0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from (
    select c.prod_id,count(b.ecode) as installoutnum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.installOutListType='installout' and a.status='over'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as installoutnum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.borrowListType='installout'
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as installoutnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') =in_todaykey and a.str_out_id=in_store_id  
    and a.adjustType='installout'
    group by c.prod_id
  )
  group by prod_id
   union all
  --����ά�޷�����
  select c.prod_id,0 yesterdaynum,0 purchasenum,0 oldnum,0 installoutnum,count(a.ecode) as repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from ems_repair a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') =in_todaykey and a.str_in_id=in_store_id
  and a.status='over'
  group by c.prod_id
   union all
  --������
  select c.prod_id,0 yesterdaynum,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,count(a.ecode) as scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from ems_scrap a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.operateDate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
  and a.status='scrap'
  group by c.prod_id
   union all
  --ά�޳�������
   select c.prod_id,0 yesterdaynum,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,count(a.ecode) as repairoutnum,0 borrownum,0 borrowreturnnum from ems_repair a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') =in_todaykey and a.str_out_id=in_store_id
  group by c.prod_id
   union all
  --���ڽ���� 
  select prod_id,0 yesterdaynum,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,sum(borrownum) borrownum,0 borrowreturnnum from (
    select c.prod_id,count(b.ecode) as borrownum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.installOutListType='borrow' and a.status='over'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as borrownum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.borrowListType='borrow' and a.status in ('over','noreturn')
    group by c.prod_id  
    union all
    select c.prod_id,count( b.ecode) as borrownum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') =in_todaykey and a.str_out_id=in_store_id  
    and a.adjustType='borrow'
    group by c.prod_id
  )
  group by prod_id 
   union all
  --�黹��
  select prod_id,0 yesterdaynum,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,sum(borrowreturnnum) as borrowreturnnum from  (
   select c.prod_id,count(b.ecode) as borrowreturnnum from ems_installout a,ems_installoutlist b,ems_equipment c
    where a.id=b.installout_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.installOutListType='borrow' and a.status='over' and b.isreturn='Y'
    group by c.prod_id  
    union all
    select c.prod_id,count(b.ecode) as borrowreturnnum from ems_borrow a,ems_borrowlist b,ems_equipment c
    where a.id=b.borrow_id and b.ecode=c.ecode and to_char(a.operatedate,'yyyymmdd') =in_todaykey and a.store_id=in_store_id
    and b.borrowListType='borrow' and a.status in ('over','noreturn') and b.isreturn='Y'
    group by c.prod_id  
     union all
    select c.prod_id,count( b.ecode) as borrowreturnnum from ems_adjust a,ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') =in_todaykey and a.str_in_id=in_store_id 
    and a.adjustType='returnback' and b.adjustListStatus='in'
    group by c.prod_id 
  ) group by prod_id 
) group by prod_id ;
commit;

--��ʼ������յĽ�������
    --���ս���=�������+�ɹ�����+��Ʒ����+(-����������)+ά�޷�����+(-���ڽ�����)+���ڹ黹��
    update report_day_sparepart 
    set todaynum=yesterdaynum+purchasenum+oldnum+installoutnum+repairinnum+borrownum+borrowreturnnum
    where daykey=in_todaykey and store_id=in_store_id;
    commit;
end;  