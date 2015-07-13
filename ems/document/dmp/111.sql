select prod_id,sum(purchasenum) purchasenum,sum(oldnum) oldnum,sum(installoutnum) installoutnum,sum(repairinnum) repairinnum,sum(scrapoutnum) scrapoutnum,sum(repairoutnum) repairoutnum,sum(borrownum) borrownum,sum(borrowreturnnum) borrowreturnnum from (
 --采购新增
  select prod_id,sum(purchasenum) purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from (
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
   union all
  --旧品新增
  select prod_id,0 purchasenum,sum(oldnum) oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from (
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
   union all
  --本期领用
  select prod_id,0 purchasenum,0 oldnum,sum(installoutnum),0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from (
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
   union all
  --本期维修返还数
  select c.prod_id,0 purchasenum,0 oldnum,0 installoutnum,count(a.ecode) as repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from ems_repair a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.str_in_date,'yyyymmdd') ='20150712' and a.str_in_id='2c9085494d1dba52014d21ae15eb0003'
  and a.status='over'
  group by c.prod_id
   union all
  --报废数
  select c.prod_id,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,count(a.ecode) as scrapoutnum,0 repairoutnum,0 borrownum,0 borrowreturnnum from ems_scrap a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.operateDate,'yyyymmdd') ='20150712' and a.store_id='2c9085494d1dba52014d21ae15eb0003'
  and a.status='scrap'
  group by c.prod_id
   union all
  --维修出库数量
   select c.prod_id,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,count(a.ecode) as repairoutnum,0 borrownum,0 borrowreturnnum from ems_repair a,ems_equipment c
  where a.ecode=c.ecode and to_char(a.str_out_date,'yyyymmdd') ='20150712' and a.str_out_id='2c9085494d1dba52014d21ae15eb0003'
  group by c.prod_id
   union all
  --本期借出数 
  select prod_id,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,sum(borrownum) borrownum,0 borrowreturnnum from (
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
   union all
  --归还数
  select prod_id,0 purchasenum,0 oldnum,0 installoutnum,0 repairinnum,0 scrapoutnum,0 repairoutnum,0 borrownum,sum(borrowreturnnum) as borrowreturnnum from  (
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
  ) group by prod_id 
) group by prod_id 
