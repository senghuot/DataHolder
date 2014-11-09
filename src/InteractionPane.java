import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class InteractionPane extends JComponent {
	private MapGrid mapGrid;
	private JRadioButton v1Radio, v2Radio, v3Radio, v4Radio, v5Radio;
	private ButtonGroup versionRadios;
	private JTextField numRowsEditorField, numColumnsEditorField;
	private static JTextField regPopDisplay, regPercDisplay;

	public InteractionPane(final JFrame appFrame){
		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		// Interaction Pane is laid out horizontally
		BoxLayout interactionPaneLayout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(interactionPaneLayout);
		
		// Interaction Pane includes a Version sub-panel laid out vertically
		JPanel programVersions = new JPanel();
		BoxLayout programVersionsLayout = new BoxLayout(programVersions, BoxLayout.Y_AXIS);
		programVersions.setLayout(programVersionsLayout);
		// create the radio buttons 
		v1Radio = new JRadioButton("V.1: Simple, Sequential", true);
		v1Radio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				USMaps.running = USMaps.Version.ONE;
				USMaps.pqPreprocess();
			}
		});
		v2Radio = new JRadioButton("V.2: Simple, Parallel", false);
		v2Radio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				USMaps.running = USMaps.Version.TWO;
				USMaps.pqPreprocess();
			}
		});
		v3Radio = new JRadioButton("V.3: Fancy, Sequential", false);
		v3Radio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				USMaps.running = USMaps.Version.THREE;
				USMaps.pqPreprocess();
			}
		});
		v4Radio = new JRadioButton("V.4: Fancy, Parallel", false);
		v4Radio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				USMaps.running = USMaps.Version.FOUR;
				USMaps.pqPreprocess();
			}
		});
		v5Radio = new JRadioButton("V.5: Fancy, Lock-Based", false);
		v5Radio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				USMaps.running = USMaps.Version.FIVE;
				USMaps.pqPreprocess();
			}
		});
		// And a button to run the thing
		final JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				USMaps.runProgram(appFrame);
			}
		});
		// add the radio buttons to a button group
		versionRadios = new ButtonGroup();
		versionRadios.add(v1Radio);
		versionRadios.add(v2Radio);
		versionRadios.add(v3Radio);
		versionRadios.add(v4Radio);
		versionRadios.add(v5Radio);
		// add the radio buttons and run button to the Versions sub-panel
		programVersions.add(v1Radio);
		programVersions.add(v2Radio);
		programVersions.add(v3Radio);
		programVersions.add(v4Radio);
		programVersions.add(v5Radio);
		programVersions.add(runButton);
		// at last, add the program Versions sub-panel to the Interaction Pane
		add(programVersions);
		
		// Create a sub-panel to change the line separation of the MapPane
		JPanel rowColPanel = new JPanel();
		// Add an editor (label + text field) for the number of rows
		JPanel numRowsEditor = new JPanel();
		JLabel numRowsEditorLabel = new JLabel("Number of Rows:");
		numRowsEditorField = new JTextField(3);
		numRowsEditorField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = numRowsEditorField.getText();
				int i;
				try{
					i = Integer.parseInt(s);
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(appFrame, 
							"Must enter a number in the \"Number of Rows\" field.", 
							"Number Entry Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				mapGrid.setRows(i);
				mapGrid.notifyObservers();
			}
		});
		numRowsEditorField.addFocusListener(new FocusListener(){
			private String text;
			public void focusGained(FocusEvent e) {
				text = numRowsEditorField.getText(); // save data that was already entered	
			}
			public void focusLost(FocusEvent e) {
				String s = numRowsEditorField.getText();
				if(!s.equals(text)){ 
					int i;
					try{
						i = Integer.parseInt(s);
					}catch(NumberFormatException nfe){
						JOptionPane.showMessageDialog(appFrame, 
								"Must enter a number in the \"Number of Rows\" field.", 
								"Number Entry Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					mapGrid.setRows(i);
					mapGrid.notifyObservers();
				}
			}
		});
		numRowsEditor.add(numRowsEditorLabel);
		numRowsEditor.add(numRowsEditorField);
		// Add an editor (label + text field) for the number of columns also!
		JPanel numColumnsEditor = new JPanel();
		JLabel numColumnsEditorLabel = new JLabel("Number of Columns");
		numColumnsEditorField = new JTextField(3);
		numColumnsEditorField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = numColumnsEditorField.getText();
				int i;
				try{
					i = Integer.parseInt(s);
				}catch(NumberFormatException nfe){
					JOptionPane.showMessageDialog(appFrame, 
							"Must enter a number in the \"Number of Columns\" field.", 
							"Number Entry Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				mapGrid.setColumns(i);
				mapGrid.notifyObservers();
			}
		});
		numColumnsEditorField.addFocusListener(new FocusListener(){
			private String text;
			public void focusGained(FocusEvent e) {
				text = numColumnsEditorField.getText(); // save data that was already entered	
			}
			public void focusLost(FocusEvent e) {
				String s = numColumnsEditorField.getText();
				if(!s.equals(text)){ 
					int i;
					try{
						i = Integer.parseInt(s);
					}catch(NumberFormatException nfe){
						JOptionPane.showMessageDialog(appFrame, 
								"Must enter a number in the \"Number of Columns\" field.", 
								"Number Entry Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					mapGrid.setColumns(i);
					mapGrid.notifyObservers();
				}
			}
		});
		numColumnsEditor.add(numColumnsEditorLabel);
		numColumnsEditor.add(numColumnsEditorField);
		rowColPanel.add(numRowsEditor);
		rowColPanel.add(numColumnsEditor);
		// Finally, add sub-panel to the Interaction Pane
		this.add(rowColPanel);
		
		// Create a sub-panel to display the data received from PopulationQuery
		JPanel dataDisplayPanel = new JPanel();
		JPanel popDisplaySubPanel = new JPanel();
		JLabel regPopLabel = new JLabel("Regional Population:");
		regPopDisplay = new JTextField(8);
		regPopDisplay.setEditable(false);
		popDisplaySubPanel.add(regPopLabel);
		popDisplaySubPanel.add(regPopDisplay);
		JPanel percDisplaySubPanel = new JPanel();
		JLabel regPercLabel = new JLabel("Percentage of total US:");
		regPercDisplay = new JTextField(8);
		regPercDisplay.setEditable(false);		
		percDisplaySubPanel.add(regPercLabel);
		percDisplaySubPanel.add(regPercDisplay);
		dataDisplayPanel.add(popDisplaySubPanel);
		dataDisplayPanel.add(percDisplaySubPanel);
		// add this data display sub-panel to the Interaction Pane
		this.add(dataDisplayPanel);
	}
	
	public void initMapGrid(int rows, int columns, Observer o){
		mapGrid = new MapGrid();
		mapGrid.addObserver(o);
		mapGrid.setRows(rows);
		mapGrid.setColumns(columns);
		numRowsEditorField.setText("" + rows);
		numColumnsEditorField.setText("" + columns);
		mapGrid.notifyObservers();
	}
	
	public static class MapGrid extends Observable{
		private int rows, columns;
		public void setRows(int rows){
			if(this.rows != rows){
				this.rows = rows;
				setChanged();
			}
		}
		public void setColumns(int columns){
			if(this.columns != columns){
				this.columns = columns;
				setChanged();
			}
		}
		public int getRows(){ return rows; }
		public int getColumns(){ return columns; }
	}
	
	public MapGrid getMapGrid(){ return mapGrid; }
	
	public void deselectAllButtons(){
		versionRadios.setSelected(v1Radio.getModel(), false);
		versionRadios.setSelected(v2Radio.getModel(), false);
		versionRadios.setSelected(v3Radio.getModel(), false);
		versionRadios.setSelected(v4Radio.getModel(), false);
		versionRadios.setSelected(v5Radio.getModel(), false);
	}
	
	public void selectButton(int i){
		switch(i){
		case 1: versionRadios.setSelected(v1Radio.getModel(), true); break; 
		case 2: versionRadios.setSelected(v2Radio.getModel(), true); break; 
		case 3: versionRadios.setSelected(v3Radio.getModel(), true); break; 
		case 4: versionRadios.setSelected(v4Radio.getModel(), true); break; 
		case 5: versionRadios.setSelected(v5Radio.getModel(), true); break; 
		default: break;
		}
	}
	
	public static void displayCensusData(int pop, double perc){
		//System.out.println(pop + " and " + perc);
		regPopDisplay.setText("" + pop);
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		regPercDisplay.setText(twoDecimals.format(perc) + "%");		
	}
	
}
