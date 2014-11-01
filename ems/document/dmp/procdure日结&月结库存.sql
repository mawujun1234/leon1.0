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
    insert into ems_monthinventory(monthkey,subtype_id,prod_id,brand_id,style,store_id,nownum,lastnum)
    values(month_in,rec.subtype_id,rec.prod_id,rec.brand_id,rec.style,rec.store_id,0,0);
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
    update ems_monthinventory a set a.nownum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and a.monthkey=month_in;
  end loop;

  --上月结余数
  for rec in(select * from ems_monthinventory where monthkey=lastmonth_in)
  loop
    update ems_monthinventory a set a.lastnum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=month_in;
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