package com.mawujun.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.dozer.Mapping;
import org.junit.Test;

/**
 * 演示Dozer如何只要属性名相同，可以罔顾属性类型是基础类型<->String的差别，Array转为List，甚至完全另一种DTO的差别。
 * 并且能完美解决循环依赖问题。
 * 使用@Mapping能解决属性名不匹配的情况.
 */
public class BeanPropertiesCopyTest {
	
	@Test  
	 public void copyOrCast() throws Exception {
		
		
		Integer bb= BeanPropertiesCopy.copyOrCast(1, Integer.class);
		assertEquals((Integer)1, bb);
		
		String aa= BeanPropertiesCopy.copyOrCast("aa", String.class);
		assertEquals("aa", aa);
	}

	@Test  
	 public void testBeanToMap() throws Exception {
		 
		//ProductDTO productDTO = new ProductDTO();
		
		//ProductDTO->Product
		Product product = new Product();
		product.setProductName("car");
		

		Map aa=BeanPropertiesCopy.copyOrCast(product, Map.class);
		
		assertEquals("car", aa.get("productName"));
		//assertEquals((Double)1000d, product.getPrice());
	 }

	 @Test  
	 public void testCopyExcludeNull() throws Exception {
		 
		ProductDTO productDTO = new ProductDTO();
		
		//ProductDTO->Product
		Product product = new Product();
		product.setProductName("car");
		

		BeanPropertiesCopy.copyExcludeNull(productDTO, product);
		
		assertEquals("car", product.getProductName());
		//assertEquals((Double)1000d, product.getPrice());
	 }
	 @Test
	 public void testconvertToObject(){
		 String sdf=(String)BeanPropertiesCopy.convert("sdf", String.class);
		 assertEquals("sdf", sdf);
		 Integer integer=(Integer)BeanPropertiesCopy.convert(new BigDecimal(10.125), Integer.class);
		 assertEquals(new Integer(10), integer);
		 //Integer.parseInt("10.125");
		 Integer integer1=(Integer)BeanPropertiesCopy.convert("10.125", Integer.class);
		 assertEquals(new Integer(0), integer1);//
		 Integer integer2=(Integer)BeanPropertiesCopy.convert(new BigDecimal(10.125), Integer.class);
		 assertEquals(new Integer(10), integer2);//
		 
		 
		 
		 
		 
		 Double doubl=(Double)BeanPropertiesCopy.convert(new BigDecimal(10.125), Double.class);
		 assertEquals(new Double(10.125), doubl); 
		 Double doubl1=(Double)BeanPropertiesCopy.convert("10.125", Double.class);
		 assertEquals(new Double(10.125), doubl1);
		 
		 BigDecimal bigDecimal=(BigDecimal)BeanPropertiesCopy.convert(10.125, BigDecimal.class);
		 assertEquals(new BigDecimal(10.125), bigDecimal);
		 BigDecimal bigDecimal1=(BigDecimal)BeanPropertiesCopy.convert("10.125", BigDecimal.class);
		 assertEquals(new BigDecimal(10.125), bigDecimal1);
	 }
	 
	 @Test
	 public void testconvertToArray(){
		 String[] strArray={"1","2","3"};
		 Object obj=BeanPropertiesCopy.convert(strArray, Integer.class);
		 assertEquals(new Integer(1), ((Object[])obj)[0]);
		 assertEquals(new Integer(2), ((Object[])obj)[1]);
		 assertEquals(new Integer(3), ((Object[])obj)[2]);	 

	 }
	/**
	 * 从一个ProductDTO实例，创建出一个新的Product实例。
	 */
	@Test
	public void map() {

		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("car");
		productDTO.setPrice("200");

		PartDTO partDTO = new PartDTO();
		partDTO.setName("door");
		partDTO.setProduct(productDTO);

		productDTO.setParts(new PartDTO[] { partDTO });

		//ProductDTO->Product
		Product product = BeanPropertiesCopy.copyOrCast(productDTO, Product.class);

		assertEquals("car", product.getProductName());
		//原来的字符串被Map成Double。
		assertEquals(Double.valueOf(200), product.getPrice());
		//原来的PartDTO同样被Map成Part ,Array被Map成List
		assertEquals("door", product.getParts().get(0).getName());
		//Part中循环依赖的Product同样被赋值。
		assertEquals("car", product.getParts().get(0).getProduct().getProductName());

		//再反向从Product->ProductDTO
		ProductDTO productDTO2 = BeanPropertiesCopy.copyOrCast(product, ProductDTO.class);
		assertEquals("car", productDTO2.getName());
		assertEquals("200.0", productDTO2.getPrice());
		assertEquals("door", productDTO2.getParts()[0].getName());
	}

	/**
	 * 演示将一个ProductDTO实例的内容，Copy到另一个已存在的Product实例.
	 */
	@Test
	public void copy() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("car");
		productDTO.setPrice("200");

		PartDTO partDTO = new PartDTO();
		partDTO.setName("door");
		partDTO.setProduct(productDTO);

		productDTO.setParts(new PartDTO[] { partDTO });

		//已存在的Product实例
		Product product = new Product();
		product.setProductName("horse");
		product.setWeight(new Double(20));

		BeanPropertiesCopy.copyOrCast(productDTO, product);

		//原来的horse，被替换成car
		assertEquals("car", product.getProductName());
		//原来的20的属性被覆盖成200，同样被从字符串被专为Double。
		assertEquals(Double.valueOf(200), product.getPrice());
		//DTO中没有的属性值,在Product中被保留
		assertEquals(Double.valueOf(20), product.getWeight());
		//Part中循环依赖的Product同样被赋值。
		assertEquals("car", product.getParts().get(0).getProduct().getProductName());
	}

	public static class Product {
		private String productName;
		private Double price;
		private List<Part> parts;
		//DTO中没有的属性
		private Double weight;

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public List<Part> getParts() {
			return parts;
		}

		public void setParts(List<Part> parts) {
			this.parts = parts;
		}

		public Double getWeight() {
			return weight;
		}

		public void setWeight(Double weight) {
			this.weight = weight;
		}

	}

	public static class Part {
		//反向依赖Product
		private Product product;

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}
	}

	public static class ProductDTO {
		//定义到Product中的productName,只要在一边定义，双向转换都可以使用.
		@Mapping("productName")
		private String name;
		//类型为String 而非 Double
		private String price;
		//类型为Array而非List, PartDTO而非Part
		private PartDTO[] parts;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public PartDTO[] getParts() {
			return parts;
		}

		public void setParts(PartDTO[] parts) {
			this.parts = parts;
		}
	}

	public static class PartDTO {
		//反向依赖ProductDTO
		private ProductDTO product;

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ProductDTO getProduct() {
			return product;
		}

		public void setProduct(ProductDTO product) {
			this.product = product;
		}

	}
}
