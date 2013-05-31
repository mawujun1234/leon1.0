package com.youngor.extention.swing.icons;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class IconUtil {

	public static ImageIcon getIcon(String img) {
		return getIcon("com.youngor.extention.swing.icons", img);
	}

	public static ImageIcon getIcon(String pkg, String img) {
		if(pkg==null || img==null) return null;
		pkg = pkg.replace('.', '/');
		try {
			ImageIcon icon = new ImageIcon(IconUtil.class.getClassLoader()
					.getResource(pkg + "/" + img));
			return icon;
		} catch (Exception e) {
			return null;
		}
	}
	
	 

	public static BufferedImage getImage(String pkg, String img) {
		return toBufferedImage(getIcon(pkg, img).getImage());
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		int type = BufferedImage.TYPE_INT_ARGB;

		bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				type);
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}
}
