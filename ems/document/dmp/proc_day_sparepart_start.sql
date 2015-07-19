--从某一天开始重新计算所有的仓库
create or replace procedure proc_day_sparepart_start(in_start_day in varchar2,in_store_id in varchar2)
as
begin
  for store in (
    select * from ems_store  where type in (1,3)
  ) loop
    proc_day_sparepart(store.id,in_todaykey);
  END LOOP;
end;
