create or replace procedure proc_equipment_unitprice()
as
begin
  /**���������ִ���豸�ĳ��ΰ�װʱ�䣬ֻ��Ҫִ��һ�ξ�����
  update ems_equipment a set first_install_date=(select operatedate from (
  select ecode,min(operatedate) operatedate from ems_equipmentcycle b
  where b.operatetype='task_install'
  group by ecode
  ) b
  where a.ecode=b.ecode)
  **/

  --����ÿ���豸��ԭֵ����Ϊԭֵ�ᷢ���仯������ÿ�θ��µ�ʱ�����һ��
  update ems_equipment a set unitprice=(
       select unitprice from ems_orderlist b where a.orderlist_id=b.id
  )
  commit;
  --���¾�ֵ
  update ems_equipment a 
  set unitprice_nav=unitprice-(unitprice*0.95/1825)*(ceil(TO_NUMBER(sysdate-trunc(first_install_date))))
 commit;
  

  
end;
