package com.mawujun.mobile.geolocation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mawujun.baseinfo.CustomerController;
import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleRepository;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Controller
public class MapController {
	@Autowired
	private MapService mapService;
	@Autowired
	private PoleRepository poleRepository;
	@Autowired
	private CustomerController customerController;
//	/**
//	 * 初始化所有不存在经纬度的点位
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 */
//	@RequestMapping("/map/initAllPoleNoLngLat.do")
//	@ResponseBody
//	public String initAllPoleNoLngLat(){
//		String result=mapService.initAllPoleNoLngLat();
//		//return "".equals(result)?"nodata":result;
//		return result;
//	}
	
	@RequestMapping("/map/initAllPoleNoLngLat.do")
	@ResponseBody
	public String initAllPoleNoLngLat(@RequestParam(value = "excel") MultipartFile file,String customer_2, HttpServletRequest request) throws InvalidFormatException, IOException{
//		System.out.println("开始");  
//        String path = request.getSession().getServletContext().getRealPath("upload");  
//        String fileName = file.getOriginalFilename();  
//        System.out.println(path);  
//        File targetFile = new File(path, fileName);  
//        if(!targetFile.exists()){  
//            targetFile.mkdirs();  
//        }  
//  
//        
//        //保存  
//        try {  
//            file.transferTo(targetFile);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
        
		String fileName = file.getOriginalFilename();  
		Workbook wb=null;
		if (fileName.endsWith("xls")) {
			wb = new HSSFWorkbook(file.getInputStream());
//			Sheet sheet = wb.getSheetAt(0);
//	        int row_num=sheet.getLastRowNum();
//	        for(int i=2;i<row_num;i++){
//	        	Cell cell=sheet.getRow(i).getCell(1);
//	        	System.out.println(cell.getStringCellValue());
//	        }
		} else if (fileName.endsWith("xlsx")) {
			 //OPCPackage pkg = OPCPackage.open(file.getInputStream());
		     wb = new XSSFWorkbook(file.getInputStream());
		} else {
			//throw new BusinessException("文件类型不对!");
			return "文件类型不对!";
		}
		
       
        Sheet sheet = wb.getSheetAt(0);
        int row_num=sheet.getLastRowNum();
        for(int i=2;i<row_num;i++){
        	Row row=sheet.getRow(i);
        	Cell code=row.getCell(3);//点位编号
        	Cell lng=row.getCell(4);//经度
        	Cell lat=row.getCell(5);//纬度
        	if(lng==null || lat==null){
        		continue;
        	}
        	System.out.println(code.getStringCellValue());
        	
        	

        	poleRepository.updateOrginLngLatByPoleCode(code.getStringCellValue(), getCellValue(lng), getCellValue(lat),customer_2);
        	//取出所有的编码和经纬度，然后直接update	
        }
        //转换坐标
        mapService.transform();
        //pkg.close();
		
		return "已经成功初始化！"+fileName;
	}
	private String getCellValue(Cell cell) {  
        String cellValue = "";  
        //DecimalFormat df = new DecimalFormat("#");  
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_STRING:  
            cellValue = cell.getRichStringCellValue().getString().trim();  
            break;  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            cellValue =cell.getNumericCellValue()+"";  
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA:  
            cellValue = cell.getCellFormula();  
            break;  
        default:  
            cellValue = "";  
        }  
        return cellValue;  
    }  

	
	
	/**
	 * 查询某个客户下的下的杆位
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param area_id
	 * @return
	 */
	@RequestMapping("/map/queryPoles.do")
	@ResponseBody
	public Page queryPoles(Integer start,Integer limit,String customer_2_id,String[] customer_0or1_id,String workunit_id,Boolean queryNoLngLatPole,Boolean queryBrokenPoles) {	
		if(queryBrokenPoles!=null && queryBrokenPoles==true){
			List<Pole> list= queryBrokenPoles();
			Page page=new Page();
			page.setStart(start);
			page.setTotal(list.size());
			page.setPageSize(limit);
			page.setResult(list);
			return page;
		}
		Page page=Page.getInstance(start,limit);
		//查询所有没有设置过经纬度的数据
		if(queryNoLngLatPole==true){	
			return poleRepository.queryNoLngLatPole(page);
		}
		
		page.addParam("customer_2_id", customer_2_id);
		StringBuilder builder=new StringBuilder("");
		for(String aa:customer_0or1_id){
			builder.append(",'");
			builder.append(aa);
			builder.append("'");
		}
		page.addParam("customer_0or1_id", builder.substring(1).toString());
		page.addParam("workunit_id", workunit_id);
		page.addParam("novalue", "novalue");
		
		return poleRepository.queryPoles4Map(page);
		
//		List<Pole> poles=poleService.query(Cnd.where().andEquals(M.Pole.area_id, area_id).asc(M.Pole.code));
//		return poles;
	}
	
	/**
	 * 地图上显示的时候，查询所有的点位，要把所有点位显示出来 ，和/map/queryPoles.do是同步变化的
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param customer_2_id
	 * @param customer_0or1_id
	 * @param workunit_id
	 * @param queryBrokenPoles
	 * @return
	 */
	@RequestMapping("/map/queryPolesAll.do")
	@ResponseBody
	public List<Pole> queryPolesAll(Integer start,Integer limit,String customer_2_id,String[] customer_0or1_id,String workunit_id,Boolean queryBrokenPoles) {	
		if(queryBrokenPoles!=null && queryBrokenPoles==true){
			return queryBrokenPoles();
		}
		
//		if(customer_0or1_id==null || customer_0or1_id.length==0){
//			List<CustomerVO>  list=customerController.query(customer_2_id);
//			customer_0or1_id=new String[list.size()];
//			int i=0;
//			for(CustomerVO vo:list){
//				customer_0or1_id[i]=list.get(i).getId();
//				i++;
//			}
//		}

		Params param=Params.init();
		param.addIf("customer_2_id", customer_2_id);
		StringBuilder builder=new StringBuilder("");
		for(String aa:customer_0or1_id){
			builder.append(",'");
			builder.append(aa);
			builder.append("'");
		}
		param.addIf("customer_0or1_id", builder.substring(1));
		param.addIf("workunit_id", workunit_id);
		param.addIf("novalue", "novalue");
		
		List<Pole> list= poleRepository.queryPoles4Map(param);
//		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//		for(Pole pole:list){
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put(M.Pole.id, pole.getId());
//			map.put("fulladdress",pole.geetFullAddress());
//			map.put(M.Pole.longitude, pole.getLongitude());
//			map.put(M.Pole.latitude, pole.getLatitude());
//			result.add(map);
//		}
		return list;
	}
	
	@RequestMapping("/map/queryBrokenPoles.do")
	@ResponseBody
	public List<Pole> queryBrokenPoles() {
		List<Pole> list= poleRepository.queryBrokenPoles();
		return list;
	}
	/**
	 * 更新某个点位的经纬度
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param customer_2_id
	 * @param customer_0or1_id
	 * @param workunit_id
	 * @return
	 */
	@RequestMapping("/map/updatePoleLngLat.do")
	@ResponseBody
	public String updatePoleLngLat(String pole_id,String longitude,String latitude) {	
		poleRepository.updateCoordes(longitude, latitude, pole_id);
		return "success";
	}
//	/**
//	 * 查询所有还未设置了经纬度的点位
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param pole_id
//	 */
//	@RequestMapping("/map/initAllPoleNoLngLat.do")
//	public Page queryNoLngLatPole(Integer start,Integer limit){
//		Page page=Page.getInstance(start,limit);
//		
//		return poleRepository.queryNoLngLatPole(page);
//	}

}
