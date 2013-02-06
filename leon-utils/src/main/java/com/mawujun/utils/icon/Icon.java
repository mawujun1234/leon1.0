package com.mawujun.utils.icon;

/**
 * icon文件的信息
 * @author mawujun
 *
 */
public class Icon {
	private String filePath;
	private String fileName;

	/**
	 * icon文件名
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取icon图片的路径，包括程序上下文，及icon文件夹+icon文件名称
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
