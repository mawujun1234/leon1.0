--计算资产用的
create or replace procedure proc_ems_assetclean()
as
begin
  for store in (
    select * from ems_store  where type in (1,3)
  ) loop
    proc_day_sparepart(store.id,in_todaykey);
  END LOOP;
end;