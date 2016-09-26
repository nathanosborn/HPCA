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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
	private ArrayList<JSpinner> spinnerlist;
	
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
		frame = new JFrame("Simulation File Builder");
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblDataFrameTitle = new JLabel("Simulation file builder");
		lblDataFrameTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDataFrameTitle.setForeground(Color.BLACK);
		lblDataFrameTitle.setBackground(Color.WHITE);
		lblDataFrameTitle.setBounds(10, 11, 195, 34);
		frame.getContentPane().add(lblDataFrameTitle);
		
		
		String[] minerals = populateDirectoryList("mineral");
		final JComboBox<Object> comboBox_mineral = new JComboBox<Object>(minerals);
		comboBox_mineral.setBackground(Color.WHITE);
		comboBox_mineral.setBounds(30, 73, 127, 20);
		frame.getContentPane().add(comboBox_mineral);
		
		JLabel lblMineral = new JLabel("Mineral");
		lblMineral.setBounds(20, 49, 46, 14);
		frame.getContentPane().add(lblMineral);
		
		
		String[] solvents = populateDirectoryList("solvent");
		final JComboBox<Object> comboBox_solvent = new JComboBox<Object>(solvents);
		comboBox_solvent.setBackground(Color.WHITE);
		comboBox_solvent.setBounds(198, 73, 143, 20);
		frame.getContentPane().add(comboBox_solvent);
		
		JLabel lblSolvent = new JLabel("Solvent");
		lblSolvent.setBounds(198, 49, 46, 14);
		frame.getContentPane().add(lblSolvent);
		
		
		String[] salts = populateDirectoryList("salt");
		final JComboBox<Object> comboBox_salt = new JComboBox<Object>(salts);
		comboBox_salt.setBackground(Color.WHITE);
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
				statusLabel.setText("" + ((JSlider)e.getSource()).getValue());
			}
	      });
		saltSlider.createStandardLabels(10);
		saltSlider.setBounds(198, 198, 143, 44);
		frame.getContentPane().add(saltSlider);
		
		JLabel lblConcentration = new JLabel("Concentration");
		lblConcentration.setBounds(198, 173, 80, 14);
		frame.getContentPane().add(lblConcentration);
		
		statusLabel = new JLabel("" + saltSlider.getValue());
		statusLabel.setBounds(345, 207, 25, 14);
		frame.getContentPane().add(statusLabel);
		
		
		JLabel lblSystemSize = new JLabel("System Size");
		lblSystemSize.setBounds(23, 129, 80, 14);
		frame.getContentPane().add(lblSystemSize);
		
		JRadioButton rdbtnX = new JRadioButton("1");
		rdbtnX.setBounds(30, 146, 59, 23);
		frame.getContentPane().add(rdbtnX);
		
		JRadioButton rdbtnY = new JRadioButton("2");
		rdbtnY.setBounds(30, 172, 59, 23);
		frame.getContentPane().add(rdbtnY);
		
		JRadioButton rdbtnZ = new JRadioButton("3");
		rdbtnZ.setBounds(30, 198, 59, 23);
		frame.getContentPane().add(rdbtnZ);
		
		final ButtonGroup bgSystemSize = new ButtonGroup();
		bgSystemSize.add(rdbtnX);
		bgSystemSize.add(rdbtnY);
		bgSystemSize.add(rdbtnZ);
		
		JLabel lblOrganic = new JLabel("Organic");
		lblOrganic.setBounds(406, 11, 46, 14);
		frame.getContentPane().add(lblOrganic);
		
		JLabel lblHeadingType = new JLabel("Type");
		lblHeadingType.setBounds(406, 31, 46, 14);
		frame.getContentPane().add(lblHeadingType);
		
		JLabel lblHeadingSelected = new JLabel("Selected");
		lblHeadingSelected.setBounds(616, 31, 60, 14);
		frame.getContentPane().add(lblHeadingSelected);
		
		final String[] organics = populateDirectoryList("organic");
		leftlist = new JList<Object>(organics);
		leftlist.setVisibleRowCount(3);
		leftlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		leftlist.setBounds(406, 48, 100, 100);
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
						
						emptySpinners();
						addSpinners(orgstrings.length);
					}
			
				}
		);
		movebutton.setBounds(516, 71, 90, 25);
		frame.getContentPane().add(movebutton);
		
		rightlist = new JList<String>();
		rightlist.setVisibleRowCount(3);
		rightlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		rightlist.setBounds(616, 49, 100, 100);
		frame.getContentPane().add(rightlist);
		
		emptybutton = new JButton("Empty");
		emptybutton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String[] emptystrlist = new String[0];
						rightlist.setListData(emptystrlist);
						
						emptySpinners();
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
				phStatusLabel.setText("" + ((JSlider)e.getSource()).getValue());
			}
	      });
		slider_ph.createStandardLabels(10);
		slider_ph.setBounds(483, 171, 151, 44);
		frame.getContentPane().add(slider_ph);
		
		JLabel lblPh = new JLabel("pH");
		lblPh.setBounds(447, 187, 25, 14);
		frame.getContentPane().add(lblPh);
		
		phStatusLabel = new JLabel("" + slider_ph.getValue());
		phStatusLabel.setBounds(636, 176, 25, 14);
		frame.getContentPane().add(phStatusLabel);
		
		JLabel lblMDP = new JLabel("Select MDP file");
		lblMDP.setBounds(30, 250, 189, 20);
		frame.getContentPane().add(lblMDP);
		
		txtExamplefilemdp = new JTextField();
		txtExamplefilemdp.setBounds(30, 271, 189, 20);
		frame.getContentPane().add(txtExamplefilemdp);
		txtExamplefilemdp.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "MDP Files", "mdp"); // .MDP files only
			    chooser.setFileFilter(filter);
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
				// Perform validations
				// Check system size button group has selected value
				if (bgSystemSize.getSelection() == null){
					System.out.println("System size unselected");
				}
				// Check Organics selected group non-null
				if (rightlist.getModel().getSize() == 0){
					System.out.println("Organics selection is empty");
				}
				// Check spinners are all > 0
				
				// Check MDP file selected
				if (txtExamplefilemdp.getText().equals("")){
					System.out.println("No MDP file selected");
				}
				// Get all the selected values
					// Get Mineral
					String mineralName = comboBox_mineral.getSelectedItem().toString();
					// Get Solvent
					String solventName = comboBox_solvent.getSelectedItem().toString();
					// Get Organics
					// Can be done better
					ListModel<String> organicList = rightlist.getModel();
					int organicSize = organicList.getSize();
					// Names
					ArrayList<String> organicNames = new ArrayList<String>();
					ArrayList<Integer> organicQuants = new ArrayList<Integer>();
					for (int i = 0; i < organicSize; i++) {
						organicNames.add( organicList.getElementAt(i) );
						organicQuants.add( (Integer) spinnerlist.get(i).getValue() );
					}
					String organicString = organicNames.toString();
					String organicQString = organicQuants.toString();
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
						// Open & Parse file for relevant information instead ?
				
				// Create formatted strings for writing to text file
				
				// Write to new text file
				PrintWriter writer;
				try {
					writer = new PrintWriter("the-file-name.txt", "UTF-8");
					
					writer.println("Mineral:\t" + mineralName + "\n");
					writer.println("Solvent:\t" + solventName + "\n");
					writer.println("Organics:\t" + organicString + "\n");
					writer.println("Organics #:\t" + organicQString + "\n");
					writer.println("System Size:\t" + systemSize + "\n");
					writer.println("Salt:\t\t" + saltName + "\n");
					writer.println("Concentration:\t" + concentration + "\n");
					writer.println("pH:\t\t" + phNum + "\n");
					writer.println("MDP File:\t" + mdpFile + "\n");
					
					writer.close();
					
					JOptionPane.showMessageDialog(null, "Information added to configuration file");
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
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(720, 49, 40, 20);
		//frame.getContentPane().add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(720, 68, 40, 20);
		//frame.getContentPane().add(spinner_2);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(720, 87, 40, 20);
		//frame.getContentPane().add(spinner_3);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setBounds(720, 106, 40, 20);
		//frame.getContentPane().add(spinner_4);
		
		JSpinner spinner_5 = new JSpinner();
		spinner_5.setBounds(720, 125, 40, 20);
		//frame.getContentPane().add(spinner_5);
		
		JSpinner spinner_6 = new JSpinner();
		spinner_6.setBounds(720, 144, 40, 20);
		//frame.getContentPane().add(spinner_6);
		spinnerlist = new ArrayList<JSpinner>();
		spinnerlist.add(spinner_1);
		spinnerlist.add(spinner_2);
		spinnerlist.add(spinner_3);
		spinnerlist.add(spinner_4);
		spinnerlist.add(spinner_5);
		spinnerlist.add(spinner_6);
	}
	
	private void addSpinners(int spinner_num) {
		for (int i = 0; i < spinner_num; i++) {
			//JSpinner spinner = new JSpinner();
			//spinner.setBounds(720, 49+(19*i), 40, 20);
			frame.getContentPane().add(spinnerlist.get(i));
		}
	}
	
	private void emptySpinners() {
		for (int i = 0; i < 6; i++) {
			frame.getContentPane().remove(spinnerlist.get(i));
		}
		frame.getContentPane().repaint();
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
