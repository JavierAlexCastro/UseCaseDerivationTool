package business_objects;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

import business_objects.GenerateFVController;

public class SystemGUI {

	private static JFrame frame;
	private static JDialog dialog;
	private static JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SystemGUI window = new SystemGUI();
					SystemGUI.frame.setVisible(true);
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
		frame.setTitle("Use Case Derivation Tool");
		frame.setLayout(new FlowLayout());
		frame.setSize(500,400);
        frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
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
					File file = JFC.getSelectedFile();
					startThread("src/resources/"+file.getName());
					
					/*//create jdialog with jlabel to announce success
					JDialog dialog = new JDialog(frame, "Message");
		            JLabel label = new JLabel(msg); 
		            dialog.add(label);
		            dialog.setLocationRelativeTo(frame);
		            dialog.setSize(400, 120);
		            dialog.setVisible(true); 
					System.out.println(msg);*/
				}
			}
		});
		mnNewMenu.add(mntmGenerateFeatureVector);
	}
	
	private static void startThread(String filename)  
    { 
        SwingWorker<String, String> sw = new SwingWorker<String, String>()  
        { 
  
            @Override
            protected String doInBackground() throws Exception  
            { 
                // define what thread will do here
            	publish("Generating FV. Please wait . . .");
            	
            	GenerateFVController fv_controller = new GenerateFVController();
            	return fv_controller.generateFV(filename);
            } 
  
            @Override
            protected void process(List<String> chunks) 
            { 
                // define what the event dispatch thread  
                // will do with the intermediate results received 
                // while the thread is executing 
                String val = chunks.get(chunks.size()-1); 
                label.setText(val); 
            } 
  
            @Override
            protected void done()  
            { 
                // this method is called when the background  
                // thread finishes execution 
                try 
                { 
                    String statusMsg = get(); 
                    System.out.println(statusMsg); 
                    label.setText(statusMsg); 
                      
                }  
                catch (InterruptedException e)  
                { 
                    e.printStackTrace(); 
                }  
                catch (ExecutionException e)  
                { 
                    e.printStackTrace(); 
                } 
            } 
        }; 
        
        dialog = new JDialog(frame, "Message");
    	label = new JLabel();
    	dialog.setLocationRelativeTo(frame);
        dialog.setSize(400, 120);
        dialog.setVisible(true);
        dialog.add(label);
        
        // executes the swingworker on worker thread 
        sw.execute();  
    } 

}
