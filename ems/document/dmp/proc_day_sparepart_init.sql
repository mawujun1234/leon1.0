--从某一天开始重新计算某个或所有仓库的日结数据
--如果in_store_id=all就表示对所有的仓库库进行重新计算
--call proc_day_sparepart_init('all','20150710')
--call proc_day_sparepart_init('2c9085494d1dba52014d21ae5e000004','20150710')
create or replace procedure proc_day_sparepart_init(in_store_id in varchar2,in_start_day in varchar2)
as
next_date date:= to_date(in_start_day,'yyyymmdd');
begin
  while (next_date<sysdate) loop
    if in_store_id='all' then
      proc_day_sparepart_all(to_char(next_date,'yyyymmdd'));
      
    else
      proc_day_sparepart(in_store_id,to_char(next_date,'yyyymmdd'));
    end if;
    next_date:=next_date+1;
  end loop;
end;
