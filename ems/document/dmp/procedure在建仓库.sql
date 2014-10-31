----计算所有在建仓库，当前时间的库存
--call proc_initbuildmonthreport_all('201410','201409')
create or replace procedure proc_initbuildmonthreport_all(nowmonth_in in varchar2,lastmonth_in in varchar2)
as
begin
  for rec in (
    select * from ems_store where type=1 and status='Y'
  ) LOOP
    proc_buildmonthreport(rec.id,nowmonth_in,lastmonth_in);
  END LOOP;
end;


--call proc_buildmonthreport('2c90838448b957570148b9675f460003','201410','201409')
--select * from report_buildmonthreport
--在建仓库的盘点月报表  存储过程
create or replace procedure proc_buildmonthreport(store_id_in in varchar2,nowmonth_in in varchar2,lastmonth_in in varchar2)
as
  store_name varchar2(30);
begin
  select name into store_name from ems_store where id=store_id_in;
  
  --清除要插入的月份的数据
  delete report_buildmonthreport where monthkey=nowmonth_in and store_id=store_id_in;
  
  --首先插入key的数据,防止当某个仓库，没有没有库存的时候，就查不出数据了，还有就是这个型号的刚好今天领完了，没有库存，那也不会显示了
  for rec in (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style,b.name as subtype_name,c.name as prod_name,c.unit,d.name as brand_name--,count(a.ecode) as nownum
    from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    where a.store_id=store_id_in
     and  a.status !=30
  ) loop
    insert into report_buildmonthreport(monthkey,subtype_id,subtype_name,prod_id,prod_name,brand_id,brand_name,style,store_id,store_name,unit,nownum)
    values(nowmonth_in,rec.subtype_id,rec.subtype_name,rec.prod_id,rec.prod_name,rec.brand_id,rec.brand_name,rec.style,rec.store_id,store_name,rec.unit,0);
  end loop;
  --本月结余数   获取当前计算的时候的库存就可以了 
  for rec in  (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style,count(a.ecode) as nownum
    from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    where a.store_id=store_id_in
     and a.status =1
    group by a.subtype_id,a.prod_id,a.brand_id,a.style,a.store_id,a.style
  ) loop
    update report_buildmonthreport a set a.nownum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and a.monthkey=nowmonth_in;
  end loop;

  --上月结余数
  for rec in(select * from report_buildmonthreport where monthkey=lastmonth_in)
  loop
    update report_buildmonthreport a set a.lastnum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;
  
  --入库数量
  for rec in(
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id,count(b.encode) as storeinnum from 
    ems_instore a, ems_instorelist b,ems_equipment c
    where a.id=b.instore_id and b.encode=c.ecode and a.store_id=store_id_in and  to_char(a.operatedate,'yyyymm') =nowmonth_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id
  )
  loop
    update report_buildmonthreport a set a.storeinnum=rec.storeinnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;
  
  --领用数量
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id,count(b.ecode) as installoutnum from 
    ems_installout a, ems_installoutlist b,ems_equipment c
    where a.id=b.installOut_id and b.ecode=c.ecode and a.store_id=store_id_in and  to_char(a.operatedate,'yyyymm') =nowmonth_in 
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id
  )
  loop
    update report_buildmonthreport a set a.installoutnum=-rec.installoutnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and monthkey=nowmonth_in;
  end loop;
  
  commit;
end;
/


---------------------------------------------------在建仓库，
--用来初始化化，历史没有录入的数据，录入开始日期和结束日期
--初始化所有在建仓库的日报表
--call proc_initbuilddayreport_all('201409','20141001','20141030');
create or replace procedure proc_initbuilddayreport_all(lastmonth_in in varchar2,startday_in in varchar2,endday_in in varchar2)
as
begin
  for rec in (
    select * from ems_store where type=1 and status='Y'
  ) LOOP
    proc_initbuilddayreport(rec.id,lastmonth_in,startday_in,endday_in);
  END LOOP;
end;

--call proc_initbuilddayreport('2c90838a48f27b350148f2a91b81000d','201409','20141001','20141030');
--lastmonth_in上个月，startday_in这个月的开始日期，endday_in这个月的结束日期
create or replace procedure proc_initbuilddayreport(store_id_in in varchar2,lastmonth_in in varchar2,startday_in in varchar2,endday_in in varchar2)
as
begin
  for v_date in startday_in .. endday_in LOOP
    proc_builddayreport(store_id_in,v_date,lastmonth_in);
  END LOOP;
end;


--第三个参数是上个月的月份,用来获取上期数据
create or replace procedure proc_builddayreport(store_id_in in varchar2,nowday_in in varchar2,lastmonth_in in varchar2)
as
  store_name varchar2(30);
begin
  select name into store_name from ems_store where id=store_id_in;

  --清除要插入的日期的数据
  delete report_builddayreport where daykey=nowday_in and store_id=store_id_in;
  --首先插入key的数据,防止当某个仓库，没有没有库存的时候，就查不出数据了，还有就是这个型号的刚好今天领完了，没有库存，那也不会显示了
  for rec in (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style,b.name as subtype_name,c.name as prod_name,c.unit,d.name as brand_name--,count(a.ecode) as nownum
    from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    where a.store_id=store_id_in
     and  a.status !=30
  ) loop
    insert into report_builddayreport(daykey,subtype_id,subtype_name,prod_id,prod_name,brand_id,brand_name,style,store_id,store_name,unit,nownum)
    values(nowday_in,rec.subtype_id,rec.subtype_name,rec.prod_id,rec.prod_name,rec.brand_id,rec.brand_name,rec.style,rec.store_id,store_name,rec.unit,0);
  end loop;
  
  --本日结余数   获取当前计算的时候的库存就可以了
  for rec in  (
    select distinct a.subtype_id,a.prod_id,a.brand_id,a.store_id,a.style,count(a.ecode) as nownum
    from ems_equipment a
    inner join ems_equipmentsubtype b on a.subtype_id=b.id
    inner join ems_equipmentprod c on a.prod_id=c.id
    inner join ems_brand d on a.brand_id=d.id
    where a.store_id=store_id_in
     and a.status =1
    group by a.subtype_id,a.prod_id,a.brand_id,a.style,a.store_id,a.style
  ) loop
    update report_builddayreport a set a.nownum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and a.daykey=nowday_in;
  end loop;

  --上期结余数,获取的是上个月的结余数量，从月报表中获取
  for rec in(select * from report_buildmonthreport where monthkey=lastmonth_in)
  loop
    update report_builddayreport a set a.lastnum=rec.nownum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and daykey=nowday_in;
  end loop;

  --今日入库数量
  for rec in(
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id,count(b.encode) as storeinnum from
    ems_instore a, ems_instorelist b,ems_equipment c
    where a.id=b.instore_id and b.encode=c.ecode and a.store_id=store_id_in and  to_char(a.operatedate,'yyyymmdd') =nowday_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id
  )
  loop
    update report_builddayreport a set a.storeinnum=rec.storeinnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and daykey=nowday_in;
  end loop;

  --今日领用数量
  for rec in (
    select c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id,count(b.ecode) as installoutnum from
    ems_installout a, ems_installoutlist b,ems_equipment c
    where a.id=b.installOut_id and b.ecode=c.ecode and a.store_id=store_id_in and  to_char(a.operatedate,'yyyymmdd') =nowday_in
    group by c.subtype_id,c.prod_id,c.brand_id,c.style,a.store_id
  )
  loop
    update report_builddayreport a set a.installoutnum=-rec.installoutnum
    where a.subtype_id=rec.subtype_id and a.prod_id=rec.prod_id and a.brand_id=rec.brand_id and a.store_id=rec.store_id and a.style=rec.style and daykey=nowday_in;
  end loop;

  commit;
end;