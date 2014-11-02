create or replace procedure proc_monthinventory(month_in in varchar2,lastmonth_in in varchar2)
as

begin

--计算所有仓库的库存  
for store in (
    select * from ems_store  
) loop 
  
  --首先插入key的数据,防止当某个仓库，没有没有库存的时候，就查不出数据了，还有就是这个型号的刚好今天领完了，没有库存，那也不会显示了
  for rec in (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style
    from ems_equipment a
    where a.store_id=store.id
     and  a.status !=30
  ) loop
    insert into ems_monthinventory(monthkey,subtype_id,prod_id,brand_id,style,store_id,
    fixednum,lastnum,purchasenum,oldnum,installoutnum,repairinnum,scrapoutnum,repairoutnum,adjustoutnum,adjustinnum,nownum,supplementnum)
    values(month_in,rec.subtype_id,rec.prod_id,rec.brand_id,rec.style,rec.store_id,0,0,0,0,0,0,0,0,0,0,0,0);
  end loop;
  
  --上月结余数
  for rec in(select * from ems_monthinventory where monthkey=lastmonth_in)
  loop
    update ems_monthinventory a set a.lastnum=rec.nownum,a.nownum=a.nownum+rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=month_in;
  end loop;
  
   --本期采购新增
  for rec in(
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id,count(b.encode) as purchasenum from
    ems_instore a, ems_instorelist b,ems_equipment c
    where a.id=b.instore_id and b.encode=c.ecode and a.store_id=store_id_in and  to_char(a.operatedate,'yyyymm') =nowmonth_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id
  )
  loop
    update ems_monthinventory a set a.purchasenum=rec.purchasenum,a.nownum=a.nownum+rec.purchasenum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;

  --本期领用数量
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id,count(b.ecode) as installoutnum from
    ems_installout a, ems_installoutlist b,ems_equipment c
    where a.id=b.installOut_id and b.ecode=c.ecode and a.store_id=store_id_in and  to_char(a.operatedate,'yyyymm') =nowmonth_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id
  )
  loop
    update ems_monthinventory a set a.installoutnum=-rec.installoutnum,a.nownum=a.nownum-rec.installoutnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;

  --本期维修返库数,维修好后返库的数量
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_in_id as store_id,count(a.ecode) as repairinnum from
    ems_repair a,ems_equipment c
    where  a.ecode=c.ecode and a.str_in_id=store_id_in and  to_char(a.str_in_date,'yyyymm') =nowmonth_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_in_id
  ) loop
    update ems_monthinventory a set a.repairinnum=rec.repairinnum,a.nownum=a.nownum+rec.repairinnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;
  ----报废出库，只取报废确认(维修单状态为6)了的数量，并且这个维修单是从这个仓库出去的
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_out_id as store_id,count(a.ecode) as scrapoutnum from
    ems_repair a,ems_equipment c
    where  a.ecode=c.ecode and a.str_out_id=store_id_in and  to_char(a.scrapDate,'yyyymm') =nowmonth_in and a.status='6'
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_out_id
  ) loop
    update ems_monthinventory a set a.scrapoutnum=-rec.scrapoutnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;


  ----维修出库，不管维修出库的时间，还在维修中就算在里面,报废确认中的数量也算在这里
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_out_id as store_id,count(a.ecode) as repairoutnum from
    ems_repair a,ems_equipment c
    where  a.ecode=c.ecode and a.str_out_id=store_id_in  and a.status not in ('4','6')
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_out_id
  ) loop
    update ems_monthinventory a set a.repairoutnum=-rec.repairoutnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;

  ---本期借用数，即调拨出库的数量，从本仓库出去的数量
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_out_id as store_id,count(b.ecode) as adjustoutnum from
    ems_adjust a, ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and a.str_out_id=store_id_in and  to_char(a.str_out_date,'yyyymm') =nowmonth_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_out_id
  ) loop
    update ems_monthinventory a set a.adjustoutnum=-rec.adjustoutnum,a.nownum=a.nownum-rec.adjustoutnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;


  --本期归还数，调到这个仓库的数据
   for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_in_id as store_id,count(b.ecode) as adjustinnum from
    ems_adjust a, ems_adjustlist b,ems_equipment c
    where a.id=b.adjust_id and b.ecode=c.ecode and a.str_in_id=store_id_in and  to_char(a.str_in_date,'yyyymm') =nowmonth_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.str_in_id
  ) loop
    update ems_monthinventory a set a.adjustinnum=rec.adjustinnum,a.nownum=a.nownum-rec.adjustinnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;
  


  --本月结余数   获取当前计算的时候的库存就可以了 ,这个是查询的时候的库存，如果不在这个周期内重新计算过了的话，这个库存就不准了
  for rec in  (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style,count(a.ecode) as nownum_query
    from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    where a.store_id=store.id
     and a.status =1
    group by a.subtype_id,a.prod_id,a.brand_id,a.style,a.store_id,a.style
  ) loop
    update ems_monthinventory a set a.nownum_query=rec.nownum_query
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and a.monthkey=month_in;
  end loop;


end loop;  
  commit;
end;
/



----------------------------------------------------------------------------------------日结库存

create or replace procedure proc_dayinventory(day_in in varchar2,lastday_in in varchar2)
as

begin

--计算所有仓库的库存  
for store in (
    select * from ems_store  
) loop 
  
  --首先插入key的数据,防止当某个仓库，没有没有库存的时候，就查不出数据了，还有就是这个型号的刚好今天领完了，没有库存，那也不会显示了
  for rec in (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style
    from ems_equipment a
    where a.store_id=store.id
     and  a.status !=30
  ) loop
    insert into ems_dayinventory(daykey,subtype_id,prod_id,brand_id,style,store_id,nownum,lastnum)
    values(day_in,rec.subtype_id,rec.prod_id,rec.brand_id,rec.style,rec.store_id,0,0);
  end loop;
  --本月结余数   获取当前计算的时候的库存就可以了 
  for rec in  (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style,count(a.ecode) as nownum
    from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    where a.store_id=store.id
     and a.status =1
    group by a.subtype_id,a.prod_id,a.brand_id,a.style,a.store_id,a.style
  ) loop
    update ems_dayinventory a set a.nownum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and a.daykey=day_in;
  end loop;

  --上月结余数
  for rec in(select * from ems_dayinventory where daykey=lastday_in)
  loop
    update ems_dayinventory a set a.lastnum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and daykey=day_in;
  end loop;

end loop;  
  commit;
end;
/