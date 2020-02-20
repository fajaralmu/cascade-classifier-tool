package com.fajar.classifierherlper.app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.fajar.classifierherlper.app.component.ComponentBuilder;
import com.fajar.classifierherlper.app.component.CustomLabel;
import com.fajar.classifierherlper.app.component.PanelRequest;

public class Application extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4814400448113515574L;

	private JButton buttonChooseOriginPath = new JButton("Origin Path");
	private JButton buttonChooseDestinationPath = new JButton("Destination Path");
	private JButton buttonResizeImage = new JButton("Resize Background");
	private JButton buttonPrintFiles = new JButton("Print Files");
	
	private JTextField labelOriginPath = new JTextField();
	private JTextField labelDesinationPath = new JTextField();
	private JTextField inputExtension = new JTextField("jpg");

	private JFileChooser fileChooser  = new JFileChooser(); 

	private String originPath = "";
	private String destinationPath = "";

	private JPanel parentPanel = new JPanel();

	public Application() {
		super();
		this.init();
		this.show();

	}

	public void init() {
		setBounds(0, 0, 600, 400);
		setName("Image Resizer");
		setTitle("Image Resizer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		parentPanel.setBackground(Color.WHITE);
		parentPanel.setLayout(null);
		parentPanel.setBounds(10, 10, 550, 550);
		parentPanel.setSize(550, 550);

		initComponent();
		setLocationByPlatform(true);

		initEvent();
	}

	private void initComponent() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { 
			e.printStackTrace();
		}

		parentPanel.removeAll();

		PanelRequest panelRequest = new PanelRequest(2, 150, 20, 15, Color.white, 30, 30, 0, 0, true);
		
		JPanel mainPanel = ComponentBuilder.buildPanel(panelRequest, 
				
				new CustomLabel("Origin Path"), buttonChooseOriginPath, 
				new CustomLabel("Destination Path"), buttonChooseDestinationPath,
				
				new CustomLabel("Origin Path"),		 labelOriginPath,
				new CustomLabel("Destination Path"), labelDesinationPath,
				
				new CustomLabel("Extension"),inputExtension,
				
				buttonResizeImage, buttonPrintFiles
				);

		parentPanel.add(mainPanel);

		this.setContentPane(parentPanel);

	}

	private void initEvent() {
		buttonChooseOriginPath.addActionListener(onChooseOriginClick());
		buttonChooseDestinationPath.addActionListener(onChooseDestinationClick());
		
		buttonResizeImage.addActionListener(resizeImageOnClick());
		buttonPrintFiles.addActionListener(printFilesOnClick());
		
		try {
			fileChooser.setCurrentDirectory(new File("C:\\Users\\Republic Of Gamers\\Pictures"));
		}catch (Exception e) {
			System.out.println("Error getting default directory: "+e);
		}
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
	}

	private ActionListener printFilesOnClick() {
		// TODO Auto-generated method stub
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageResizer.printActiveFile(originPath, inputExtension.getText());
			}
		};
	}

	private ActionListener resizeImageOnClick() {
		// TODO Auto-generated method stub
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				int result =JOptionPane.showConfirmDialog(rootPane, "Are you sure?");
				
				if(result == JOptionPane.YES_OPTION) { 
					ImageResizer.resizeBG(originPath, destinationPath, inputExtension.getText());
				}
				
			}
		};
	}

	private ActionListener onChooseOriginClick() {

		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { 
				int returnVal = fileChooser.showOpenDialog(parentPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					try {
						System.out.println("ORIGIN PATH:" + file.getCanonicalPath());
						originPath = file.getCanonicalPath();
						labelOriginPath.setText(originPath);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}
		};
	}
	
	private ActionListener onChooseDestinationClick() {

		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { 
				int returnVal = fileChooser.showOpenDialog(parentPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					try {
						System.out.println("Destination Path:" + file.getCanonicalPath());
						destinationPath = file.getCanonicalPath();
						labelDesinationPath.setText(destinationPath);
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}
		};
	}

}
