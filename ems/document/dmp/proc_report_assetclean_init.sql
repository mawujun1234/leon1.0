/**
 * 从某天开始，初始化从这天开始每天的资产残值
 * call proc_report_assetclean_init('20160404');
**/
create or replace procedure proc_report_assetclean_init(in_start_day in varchar2)
as
next_date date:= to_date(in_start_day,'yyyymmdd');
begin
  while (next_date<sysdate) loop
      proc_report_assetclean(to_char(next_date,'yyyymmdd'));
    next_date:=next_date+1;
  end loop;
end;