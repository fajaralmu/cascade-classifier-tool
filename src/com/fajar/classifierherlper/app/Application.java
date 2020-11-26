package com.fajar.classifierherlper.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.fajar.classifierherlper.app.component.BlankComponent;
import com.fajar.classifierherlper.app.component.ComponentBuilder;
import com.fajar.classifierherlper.app.component.CustomLabel;
import com.fajar.classifierherlper.app.component.PanelRequest;
import com.fajar.classifierherlper.app.component.ReservedFor;

public class Application extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4814400448113515574L;

	private JButton buttonChooseOriginPath = new JButton("Origin Path");
	private JButton buttonChooseDestinationPath = new JButton("Destination Path");
	private JButton buttonChooseFilePath = new JButton("Select File");

	private JButton buttonResizeImage = new JButton("Resize Background");
	private JButton buttonPrintFiles = new JButton("Print Files");
	private JButton buttonDoReplication = new JButton("Replicate");
	private JButton buttonDoFlip = new JButton("Flip Image");
	private JButton buttonDoGenerateHtml = new JButton("Generate Html");
	private JButton buttonConvertBlackWhite = new JButton("Convert Black & White");

	private JTextField labelOriginPath = new JTextField();
	private JTextField labelDesinationPath = new JTextField();
	private JTextField labelFilePath = new JTextField();

	private JTextField inputExtension = new JTextField("jpg");
	private JTextField inputReplication = new JTextField("0");

	private JTextField inputFlipMode = new JTextField("1");

	private JFileChooser folderChooser = new JFileChooser();
	private JFileChooser fileChooser = new JFileChooser();

	private String originPath = "";
	private String destinationPath = "";
	private String filePath = "";

	private JPanel parentPanel = new JPanel();

	public Application() {
		super();
		this.init();
		this.setVisible(true);

	}

	public void init() {
		setBounds(0, 0, 600, 700);
		setName("Image Manipulator");
		setTitle("Image Manipulator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		parentPanel.setBackground(Color.WHITE);
		parentPanel.setLayout(null);
		parentPanel.setBounds(10, 10, 550, 650);
		parentPanel.setSize(550, 650);

		initComponent();
		setLocationByPlatform(true);

		initEvent();
	}
	
	private Component title(String title) {
		
		JLabel label = new JLabel(title);
		Font font = new Font("Arial", Font.BOLD, 20);
		label.setFont(font );
		return label;
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
				
				title("Image Manipulator"), 		new BlankComponent(ReservedFor.BEFORE_HOR, 150, 20),
				
				customLabel("Origin Path"), 		buttonChooseOriginPath, 
				customLabel("Destination Path"), 	buttonChooseDestinationPath,
				
				customLabel("Origin Path"),		 	labelOriginPath,
				customLabel("Destination Path"), 	labelDesinationPath,
				
				customLabel("Extension"),			inputExtension,
				/**
				 * resize & Print images found in the directory
				 */
				buttonResizeImage, 					buttonPrintFiles,
 
				
				customLabel("Single Image Selection[00s]"),blankLabel(),
				labelFilePath, 						buttonChooseFilePath,
				/**
				 * replication
				 */
				customLabel("Image Replication"),	blankLabel(),
				inputReplication,  					buttonDoReplication,
				/**
				 * flip
				 */
				customLabel("Flip Mode"),			blankLabel(), 
				inputFlipMode, 						buttonDoFlip,
				/**
				 * generate html
				 */
				customLabel("Generate html"),			blankLabel(),
				customLabel("Select Image in menu [00s]"), buttonDoGenerateHtml,
				customLabel("Select Image in menu [00s]"), buttonConvertBlackWhite
				);

		parentPanel.add(mainPanel);

		this.setContentPane(parentPanel);

	}

	private Component customLabel(String string) { 
		return new CustomLabel(string);
	}

	private Component blankLabel() { 
		return new JLabel();
	}

	private void initEvent() {
		buttonChooseOriginPath.addActionListener(onChooseOriginClick());
		buttonChooseDestinationPath.addActionListener(onChooseDestinationClick());
		buttonChooseFilePath.addActionListener(onChooseFileClick());

		buttonResizeImage.addActionListener(resizeImageOnClick());
		buttonPrintFiles.addActionListener(printFilesOnClick());
		buttonDoReplication.addActionListener(replicateImage());
		buttonDoFlip.addActionListener(flipImage());
		buttonDoGenerateHtml.addActionListener(generateHtml());
		buttonConvertBlackWhite.addActionListener(convertBlackWhite());
		
		try {
			fileChooser.setCurrentDirectory(new File("C:\\Users\\Republic Of Gamers\\Pictures"));
			folderChooser.setCurrentDirectory(new File("C:\\Users\\Republic Of Gamers\\Pictures"));
		} catch (Exception e) {
			System.out.println("Error getting default directory: " + e);
		}
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	private ActionListener convertBlackWhite() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (null == filePath || filePath.isEmpty()) {
					return;
				}
				 
				ImageProcessor.process(filePath);
			}
		}; 
	}

	private ActionListener generateHtml() {
		
		  return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (null == filePath || filePath.isEmpty()) {
					return;
				}
				 
				HtmlBuilder builder =new HtmlBuilder (filePath, 1, false);

				builder.printHtml();
			}
		}; 
	}

	private ActionListener flipImage() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int mode = Integer.parseInt(inputFlipMode.getText());
				ImageResizer.flipImage(originPath, destinationPath, mode);
				
			}
		};
	}

	private ActionListener replicateImage() {

		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (null == filePath || filePath.isEmpty()) {
					return;
				}
				if (null == destinationPath || destinationPath.isEmpty()) {
					return;
				}

				int replication = Integer.parseInt(inputReplication.getText());
				ImageResizer.replicateImage(filePath, replication, destinationPath);

			}
		};
	}

	private ActionListener onChooseFileClick() {
		
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(parentPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();

					try {
						System.out.println("FILE PATH:" + file.getCanonicalPath());
						filePath = file.getCanonicalPath();
						labelFilePath.setText(filePath);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					System.out.println("Open command cancelled by user.");
				}

			}
		};
	}

	private ActionListener printFilesOnClick() {
		
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageResizer.printActiveFile(originPath, inputExtension.getText());
			}
		};
	}

	private ActionListener resizeImageOnClick() {
		
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int result = JOptionPane.showConfirmDialog(rootPane, "Are you sure?");

				if (result == JOptionPane.YES_OPTION) {
					ImageResizer.resizeBG(originPath, destinationPath, inputExtension.getText());
				}

			}
		};
	}

	private ActionListener onChooseOriginClick() {

		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = folderChooser.showOpenDialog(parentPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = folderChooser.getSelectedFile();

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
				int returnVal = folderChooser.showOpenDialog(parentPanel);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = folderChooser.getSelectedFile();

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
