
package com.mawujun.generator;

import java.io.IOException;

import com.mawujun.generator.MT.GeneratorMT;

/**
 * 生成M类。这个类的作用是用来在编码的时候引用的，而不需要写列名，这样在修改javabean中的列名后，就会自动修改修改
 * @author mawujun 16064988@qq.com  
 *
 */
public class GeneratorM {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GeneratorMT generatorMT=new GeneratorMT();
		generatorMT.generateM("com.mawujun","/Users/mawujun/git/leon/ems/src/main/java","com.mawujun.utils");
	}

}


