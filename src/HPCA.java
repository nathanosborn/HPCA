import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;

public class HPCA {

	private JFrame frame;
	private JLabel lblDataFrameTitle;
	private JTextField txtExamplefilemdp;
	private String selectedMDP;
	private JLabel statusLabel;
	private JLabel phStatusLabel;
	JList<Object> leftlist;
	private JList<String> rightlist;
	private JButton movebutton;
	private JButton emptybutton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HPCA window = new HPCA();
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
	public HPCA() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblDataFrameTitle = new JLabel("Simulation file builder");
		lblDataFrameTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDataFrameTitle.setForeground(Color.BLACK);
		lblDataFrameTitle.setBackground(Color.WHITE);
		lblDataFrameTitle.setBounds(10, 11, 195, 34);
		frame.getContentPane().add(lblDataFrameTitle);
		
		
		String[] minerals = populateDirectoryList("mineral");
		final JComboBox comboBox_mineral = new JComboBox(minerals);
		comboBox_mineral.setBounds(30, 73, 127, 20);
		frame.getContentPane().add(comboBox_mineral);
		
		JLabel lblMineral = new JLabel("Mineral");
		lblMineral.setBounds(20, 49, 46, 14);
		frame.getContentPane().add(lblMineral);
		
		
		String[] solvents = populateDirectoryList("solvent");
		final JComboBox comboBox_solvent = new JComboBox(solvents);
		comboBox_solvent.setBounds(198, 73, 143, 20);
		frame.getContentPane().add(comboBox_solvent);
		
		JLabel lblSolvent = new JLabel("Solvent");
		lblSolvent.setBounds(198, 49, 46, 14);
		frame.getContentPane().add(lblSolvent);
		
		
		String[] salts = populateDirectoryList("salt");
		final JComboBox comboBox_salt = new JComboBox(salts);
		comboBox_salt.setBounds(198, 129, 143, 20);
		frame.getContentPane().add(comboBox_salt);
		
		JLabel lblSalts = new JLabel("Salts");
		lblSalts.setBounds(198, 104, 46, 14);
		frame.getContentPane().add(lblSalts);
		
		final JSlider saltSlider = new JSlider(0,50);
		saltSlider.setPaintTicks(true);
		saltSlider.setPaintLabels(true);
		saltSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				statusLabel.setText("Value : " 
	            + ((JSlider)e.getSource()).getValue());
	         }
	      });
		saltSlider.createStandardLabels(10);
		saltSlider.setBounds(198, 198, 143, 44);
		frame.getContentPane().add(saltSlider);
		
		JLabel lblConcentration = new JLabel("Concentration");
		lblConcentration.setBounds(198, 173, 80, 14);
		frame.getContentPane().add(lblConcentration);
		
		statusLabel = new JLabel("Value : " + saltSlider.getValue());
		statusLabel.setBounds(198, 240, 80, 14);
		frame.getContentPane().add(statusLabel);
		
		
		JLabel lblSystemSize = new JLabel("System Size");
		lblSystemSize.setBounds(23, 129, 80, 14);
		frame.getContentPane().add(lblSystemSize);
		
		JRadioButton rdbtnX = new JRadioButton("X");
		rdbtnX.setBounds(30, 146, 59, 23);
		frame.getContentPane().add(rdbtnX);
		
		JRadioButton rdbtnY = new JRadioButton("Y");
		rdbtnY.setBounds(30, 172, 59, 23);
		frame.getContentPane().add(rdbtnY);
		
		JRadioButton rdbtnZ = new JRadioButton("Z");
		rdbtnZ.setBounds(30, 198, 59, 23);
		frame.getContentPane().add(rdbtnZ);
		
		final ButtonGroup bgSystemSize = new ButtonGroup();
		bgSystemSize.add(rdbtnX);
		bgSystemSize.add(rdbtnY);
		bgSystemSize.add(rdbtnZ);
		
		JLabel lblOrganic = new JLabel("Organic");
		lblOrganic.setBounds(426, 11, 46, 14);
		frame.getContentPane().add(lblOrganic);
		
		JLabel lblHeadingType = new JLabel("Type");
		lblHeadingType.setBounds(426, 31, 46, 14);
		frame.getContentPane().add(lblHeadingType);
		
		JLabel lblHeadingQuantity = new JLabel("Quantity");
		lblHeadingQuantity.setBounds(616, 31, 60, 14);
		frame.getContentPane().add(lblHeadingQuantity);
		
		final String[] organics = populateDirectoryList("organic");
		leftlist = new JList<Object>(organics);
		leftlist.setVisibleRowCount(3);
		leftlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		leftlist.setBounds(426, 48, 80, 100);
		frame.getContentPane().add(leftlist);
		
		movebutton = new JButton("Move -->");
		movebutton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						int[] selectedorgs = leftlist.getSelectedIndices();
						String[] orgstrings = new String[selectedorgs.length];
						for (int i = 0; i < selectedorgs.length; i++) {
							orgstrings[i] = organics[selectedorgs[i]];
						}
						rightlist.setListData(orgstrings);					
					}
			
				}
		);
		movebutton.setBounds(516, 71, 90, 25);
		frame.getContentPane().add(movebutton);
		
		rightlist = new JList<String>();
		rightlist.setVisibleRowCount(3);
		rightlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rightlist.setBounds(616, 49, 80, 100);
		frame.getContentPane().add(rightlist);
		
		emptybutton = new JButton("Empty");
		emptybutton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String[] emptystrlist = new String[0];
						rightlist.setListData(emptystrlist);					
					}
			
				}
		);
		emptybutton.setBounds(516, 104, 90, 25);
		frame.getContentPane().add(emptybutton);
		
		
		final JSlider slider_ph = new JSlider(0,14);
		slider_ph.setPaintTicks(true);
		slider_ph.setPaintLabels(true);
		slider_ph.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				phStatusLabel.setText("Value : " 
	            + ((JSlider)e.getSource()).getValue());
	         }
	      });
		slider_ph.createStandardLabels(10);
		slider_ph.setBounds(483, 171, 151, 44);
		frame.getContentPane().add(slider_ph);
		
		JLabel lblPh = new JLabel("pH");
		lblPh.setBounds(447, 187, 25, 14);
		frame.getContentPane().add(lblPh);
		
		phStatusLabel = new JLabel("Value : " + slider_ph.getValue());
		phStatusLabel.setBounds(447, 217, 80, 14);
		frame.getContentPane().add(phStatusLabel);
		
	    
		txtExamplefilemdp = new JTextField();
		txtExamplefilemdp.setText("example_file.mdp");
		txtExamplefilemdp.setBounds(30, 271, 189, 20);
		frame.getContentPane().add(txtExamplefilemdp);
		txtExamplefilemdp.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    //FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        //"JPG & GIF Images", "jpg", "gif"); // bring back in with .MDP filter only
			    //chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(frame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			       txtExamplefilemdp.setText(chooser.getSelectedFile().getName());
			       selectedMDP = chooser.getSelectedFile().getAbsolutePath();
			    }
			}
		});
		btnBrowse.setBounds(221, 270, 80, 23);
		frame.getContentPane().add(btnBrowse);
		
		JButton btnCreateFile = new JButton("Create file");
		btnCreateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get all the selected values
					// Get Mineral
					String mineralName = comboBox_mineral.getSelectedItem().toString();
					// Get Solvent
					String solventName = comboBox_solvent.getSelectedItem().toString();
					// Get Organics
					// Can be done better
					ListModel<String> organicList = rightlist.getModel();
					int organicSize = organicList.getSize();
					ArrayList<String> organicNames = new ArrayList<String>();
					for (int i = 0; i < organicSize; i++) {
						organicNames.add( organicList.getElementAt(i) );
					}
					String organicString = organicNames.toString();
					// Get System Size
					String systemSize = HPCA.getSelectedButton(bgSystemSize);
					// Get Salts & Concentration
					String saltName = comboBox_salt.getSelectedItem().toString();
					int concentration = saltSlider.getValue();
					// Get pH
					int phNum = slider_ph.getValue();
					// Get MDP info
						// Get filename
						String mdpFile = selectedMDP;
						// Open file
						// Parse file for relevant information
				
				// Create formatted strings for writing to text file
				
				// Write to new text file
				PrintWriter writer;
				try {
					writer = new PrintWriter("the-file-name.txt", "UTF-8");
					
					writer.println(mineralName);
					writer.println(solventName);
					writer.println(organicString);
					writer.println(systemSize);
					writer.println(saltName);
					writer.println(concentration);
					writer.println(phNum);
					writer.println(mdpFile);
					
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnCreateFile.setBounds(496, 256, 138, 35);
		frame.getContentPane().add(btnCreateFile);
		/*
		JSpinner spinner = new JSpinner();
		spinner.setBounds(465, 45, 40, 22);
		frame.getContentPane().add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(504, 60, 40, 20);
		frame.getContentPane().add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(465, 79, 40, 20);
		frame.getContentPane().add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(504, 96, 40, 20);
		frame.getContentPane().add(spinner_3);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setBounds(465, 110, 40, 20);
		frame.getContentPane().add(spinner_4);
		*/
	}

	protected static String getSelectedButton(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
	}

	private String[] populateDirectoryList(String directory) {
		// Get the sub-directory names within directory
		File file = new File("C:\\Users\\Nathan\\Documents\\Uni\\Geol\\Nathan\\Nathan\\share\\" + directory);
		String[] names = file.list();
		ArrayList<String> returnList = new ArrayList<String>();

		for(String name : names)
		{
		    if (new File("C:\\Users\\Nathan\\Documents\\Uni\\Geol\\Nathan\\Nathan\\share\\" + directory + "\\" + name).isDirectory())
		    {
		        returnList.add(name);
		    }
		}
		String[] returnArray = returnList.toArray(new String[returnList.size()]);
		return returnArray;
	}
	
}
