package destory;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.baseinfo.Equipment;
import com.mawujun.repository1.IRepository;

import destory.Barcode;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
//@Repository
public interface BarcodeRepository extends IRepository<Barcode, String>{
	public Barcode getBarcodeByEcode(@Param("ecode")String ecode);

}
