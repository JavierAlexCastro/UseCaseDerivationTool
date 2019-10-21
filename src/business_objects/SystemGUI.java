package business_objects;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

import business_objects.GenerateFVController;

public class SystemGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SystemGUI window = new SystemGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SystemGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmGenerateFeatureVector = new JMenuItem("Generate Feature Vector");
		mntmGenerateFeatureVector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File cwd = new File(System.getProperty("user.dir"));
				JFileChooser JFC =  new JFileChooser(FileSystemView.getFileSystemView());
				JFC.setCurrentDirectory(cwd);
				int returnValue = JFC.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					GenerateFVController fv_controller = new GenerateFVController();
					String msg = "";
					File file = JFC.getSelectedFile();
					msg = fv_controller.generateFV("src/resources/"+file.getName());
					System.out.println(msg);
				}
			}
		});
		mnNewMenu.add(mntmGenerateFeatureVector);
	}

}
