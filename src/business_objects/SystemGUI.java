package business_objects;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

import business_objects.GenerateFVController;

public class SystemGUI {

	private static JFrame frame;
	private static JDialog dialog;
	private static JLabel label;
	private static JButton button;
	private static JTextArea textArea;
	
	////Singleton and Creator pattern
	private static GenerateFVController fv_controller = GenerateFVController.getInstance();

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
		frame.setTitle("Use Case Derivation Tool");
		frame.setLayout(new FlowLayout());
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null); //makes it centered on screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File"); //menu option
		menuBar.add(mnNewMenu);
		
		textArea = new JTextArea();
		textArea.setRows(30);
		textArea.setColumns(45);
		textArea.setEditable(false); //make it non-editable by the user
		
		JScrollPane js = new JScrollPane(textArea);
		js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(js);
		frame.pack();
		
		JMenuItem mntmGenerateFeatureVector = new JMenuItem("Generate Feature Vector");
		mntmGenerateFeatureVector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File cwd = new File(System.getProperty("user.dir")); //establish current working directory
				JFileChooser JFC =  new JFileChooser(FileSystemView.getFileSystemView()); //create a file chooser
				JFC.setCurrentDirectory(cwd); //set it to open on the current working directory
				int returnValue = JFC.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) { //if file selected and opened
					File file = JFC.getSelectedFile();
					
					//create jdialog with jlabel to announce success
					dialog = new JDialog(frame, "Message");
					label = new JLabel(BorderLayout.CENTER);
					button = new JButton("OK");
					
					dialog.getContentPane().setLayout(new BorderLayout());
					dialog.getContentPane().add(label);
					
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.setVisible(false);
						}
					});
					
					button.setBounds(10,10,40,40);
			        button.setEnabled(false);
			        dialog.pack();
					dialog.getContentPane().add(button, BorderLayout.SOUTH);
			        dialog.setSize(400, 120);
			        dialog.setLocationRelativeTo(frame); //center on parent window
			        dialog.setVisible(true);
			        
					generateFVThread(file.getName()); //create background thread
					
				}
			}
		});
		mnNewMenu.add(mntmGenerateFeatureVector);
		
		JMenuItem mntm_derive_uc = new JMenuItem("Derive Usecases");
		mntm_derive_uc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File cwd = new File(System.getProperty("user.dir"));  //get current working directory	
				JFileChooser JFC =  new JFileChooser(FileSystemView.getFileSystemView()); //generate file chooser
				JFC.setCurrentDirectory(cwd); //open it on current working directory
				int returnValue = JFC.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) { //if a file was selected and opened
					File file = JFC.getSelectedFile();
					
					//create jdialog with jlabel to announce success
					dialog = new JDialog(frame, "Message");
					label = new JLabel(BorderLayout.CENTER);
					button = new JButton("OK");
					
					//selection gui for strategy pattern
					String[] algorithms = {"AutoWEKA", "IBk", "DecisionStump", "Decision Table", "REPTree", "ZeroR"};
					String alg = (String)JOptionPane.showInputDialog(null, "Please Choose a classification algorithm", "Algorithm Chooser", JOptionPane.QUESTION_MESSAGE, null, algorithms, algorithms[0]);
					
					dialog.getContentPane().setLayout(new BorderLayout());
					dialog.getContentPane().add(label);
					
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.setVisible(false);
						}
					});
					
					button.setBounds(10,10,40,40);
			        button.setEnabled(false);
			        dialog.pack();
					dialog.getContentPane().add(button, BorderLayout.SOUTH);
			        dialog.setSize(400, 120);
			        dialog.setLocationRelativeTo(frame); //center on parent window
			        dialog.setVisible(true);
			        
			        if(!alg.equals("")) {
			        	generateUCThread(file.getName(), alg); //create background thread
			        }
				}
			}
		});
		mnNewMenu.add(mntm_derive_uc);
		
	}
	//For making a prediction
	private static void generateUCThread(String filename, String alg)  
    { 
        SwingWorker<String, String> sw = new SwingWorker<String, String>()  
        { 
  
            @Override
            protected String doInBackground() throws Exception  
            { 
                // define what thread will do here
            	
            	GenerateUCController generate_controller = GenerateUCController.getInstance(); //singleton pattern
            	publish(" Preparing Requirements. Please wait . . .");
            	fv_controller.generateFV(filename); //generate fv for requirements
            	publish(" Making a prediction. May take some time. Please wait . . .");
            	String message = generate_controller.labelRequirements(filename, alg); //generate use cases from requirements
            	String fname = filename.split("\\.")[0]; //remove file extension
            	if(message.contains("Usecase generated successfully!")) { //if it generated use cases successfully
            		FileReader useCaseFile = new FileReader("src/outputs/USECASES_"+fname+".txt");
            		BufferedReader br = new BufferedReader(useCaseFile);
            		textArea.read(br, null); //open generated use cases document to display for the user
            		br.close();
            		textArea.requestFocus();
            	}
            	return message;
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
                    button.setEnabled(true);
                      
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
        sw.execute();  
    } 
	
	//For generating FV
	private static void generateFVThread(String filename)  
    { 
        SwingWorker<String, String> sw = new SwingWorker<String, String>()  
        { 
  
            @Override
            protected String doInBackground() throws Exception  
            { 
                // define what thread will do here
            	publish(" Generating FV. Please wait . . .");
            	return fv_controller.generateFV(filename); //generate feature vector from requirements and return success message
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
                    button.setEnabled(true);
                      
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
        sw.execute();  
    } 
}
