package com.youngor.extention.swing.icons;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.youngor.extention.swing.SWingUtil;
import com.youngor.extention.swing.table.event.MouseAdapter;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IconBrowser extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPanel imagePanel;

	 
	public static void main(String[] args) {
		new IconBrowser().setVisible(true);
	}
	
	public void UpdateIcon(String s)
	{
		imagePanel.removeAll();
		
		final String pkg= IconUtil.class.getPackage().getName();
		String path=pkg.replace('.', '/');
		URL url=IconUtil.class.getClassLoader().getResource(path);

		File dir=new File(url.getFile());
		
		final File[] files=dir.listFiles();
 
		for (int i = 0; i < files.length; i++) {
			
			String name=files[i].getName();
			
			if(!(name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".gif") || name.toLowerCase().endsWith(".bmp")))
			{
				continue;
			}
			
			if(s!=null && s.length()>0)
			{
				if(name.indexOf(s)==-1)
				{
					continue;
				}
			}
			
			JButton btn = new JButton();
			btn.setContentAreaFilled(false);
			btn.setBorder(null);
			btn.setIcon(IconUtil.getIcon(pkg,files[i].getName()));
			btn.setName(i+"");
		
			
			
			btn.addMouseListener(new MouseAdapter(){
				
				@Override
				public void mouseClicked(MouseEvent e) {
					 if(e.getClickCount()==2)
					 {
						 JButton btn=(JButton)e.getSource();
							IconBrowser.this.actionPerformed(pkg,files[Integer.parseInt(btn.getName())].getName());
							
							IconBrowser.this.dispose();
					 }
				}
				
			});
		
			 
			imagePanel.add(btn);
			
			if(i%10==0)
			{
				imagePanel.updateUI();
			}
			
		}
		imagePanel.updateUI();
	}
	
	/**
	 * Create the dialog.
	 */
	public IconBrowser() {
		setModal(true);
		
		
	
		
		setBounds(100, 100, 661, 620);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		 
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);
		 
		imagePanel = new JPanel();
		scrollPane.setViewportView(imagePanel);
		imagePanel.setLayout(new GridLayout(0, 15, 2, 2));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPanel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("\u540D\u79F0\uFF1A");
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					queryIcon();
				}
			}
		});
		panel_1.add(textField);
		textField.setColumns(16);
		
		JButton button = new JButton("\u641C\u7D22");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryIcon();
			}
		});
		panel_1.add(button);
		
		
		
		SWingUtil.applyAsToolBar(panel_1);
		
		queryIcon();
		
	}
	
	
	private void queryIcon() {
		new Thread(){
			public void run() {
				
				UpdateIcon(textField.getText());
				
			};
		}.start();
	}
	
	

	public void actionPerformed(String pkg,String name) {
		 
		
	}

}
