--��������洢���̣�����������е����ݿ�Ľ��ս�������
create or replace procedure proc_report_day_sparepart_all(in_todaykey in varchar2)
as
begin
  for store in (
    select * from ems_store  where type in (1,3)
  ) loop
    proc_report_day_sparepart(store.id,in_todaykey);
  END LOOP;
end;

