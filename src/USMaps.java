


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;



public class USMaps{
	enum Version { ONE, TWO, THREE, FOUR, FIVE };
	static Version running = Version.ONE;
	private static MapPane mapPane;
	private static InteractionPane interactionPane;
	private static JFrame appFrame;
	private static final String FILENAME = "CenPop2010.txt";
	
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(1);
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {			
			public void run() {
				// Prepare and run separate process for solution code.
				int initX = 23; // initial X value
				int initY = 12; // initial Y value
				
				// Creates outermost frame
				appFrame = new JFrame("USA Population Density");
				appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// Resize window based on screen size
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				appFrame.setSize(screen.width * 7 / 8, screen.height * 7 / 8);
				appFrame.setLocation(screen.width / 16, screen.height / 16 - 20);
				// Display early because the wait for something to appear on-screen is unsettling
				// Note this necessitates a call to appFrame.validate later
				appFrame.setVisible(true);

				// Creates menu toolbar and adds to mainFrame
				JMenuBar menuToolbar = createToolbar();
				appFrame.setJMenuBar(menuToolbar);

				// The main frame should be laid out vertically
				Container mainContentPane = appFrame.getContentPane();
				BoxLayout mainLayout = new BoxLayout(mainContentPane, BoxLayout.Y_AXIS);
				mainContentPane.setLayout(mainLayout);
				
				// Creates Map Pane (map-viewing pane) and adds to mainFrame
				mapPane = new MapPane(appFrame);
				appFrame.add(mapPane);
				
				// Creates Interaction Pane and adds to mainFrame
				interactionPane = new InteractionPane(appFrame);
				interactionPane.initMapGrid(initY, initX, mapPane);
				appFrame.add(interactionPane, BorderLayout.SOUTH);

				// according to the swing documentation, one must call validate after
				// adding components to a visible item -- doesn't seem to matter on Windows,
				// but important on Mac to avoid needing to resize manually
				appFrame.validate();
								
				// Preprocess the solution
				pqPreprocess();
			}

			private JMenuBar createToolbar() {
				JMenuBar toolbar = new JMenuBar();
				
				// The File menu
				JMenu fileMenu = new JMenu("File");
				// Exit item with Ctrl+x shortcut
				JMenuItem exitItem = new JMenuItem("Exit");
				exitItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				fileMenu.add(exitItem);
				
				// The Run menu
				JMenu runMenu = new JMenu("Run");
				// "Change Run" item
				JMenu changeRunSubMenu = new JMenu("Change Run");
				// "Zoom" item
				JMenu zoomMenu = new JMenu("Zoom");
				// change run to v1
				final JMenuItem changeToV1 = new JMenuItem("V.1");
				changeToV1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, 
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
				changeToV1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						running = Version.ONE;
						interactionPane.deselectAllButtons();
						interactionPane.selectButton(1);
						pqPreprocess();
						//JOptionPane.showMessageDialog(changeToV1, "Changed to V1");
					}
				});
				// change run to v2
				final JMenuItem changeToV2 = new JMenuItem("V.2");
				changeToV2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
				changeToV2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						running = Version.TWO;
						interactionPane.deselectAllButtons();
						interactionPane.selectButton(2);
						pqPreprocess();
						//JOptionPane.showMessageDialog(changeToV2, "Changed to V2");
					}
				});
				// change run to v3
				final JMenuItem changeToV3 = new JMenuItem("V.3");
				changeToV3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
				changeToV3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						running = Version.THREE;
						interactionPane.deselectAllButtons();
						interactionPane.selectButton(3);
						pqPreprocess();
						//JOptionPane.showMessageDialog(changeToV3, "Changed to V3");
					}
				});
				// change run to v4
				final JMenuItem changeToV4 = new JMenuItem("V.4");
				changeToV4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, 
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
				changeToV4.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						running = Version.FOUR;
						interactionPane.deselectAllButtons();
						interactionPane.selectButton(4);
						pqPreprocess();
						//JOptionPane.showMessageDialog(changeToV4, "Changed to V4");
					}
				});
				// change run to v5
				final JMenuItem changeToV5 = new JMenuItem("V.5");
				changeToV5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, 
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
				changeToV5.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						running = Version.FIVE;
						interactionPane.deselectAllButtons();
						interactionPane.selectButton(5);
						pqPreprocess();
						//JOptionPane.showMessageDialog(changeToV5, "Changed to V5");
					}
				});
				// Run item
				final JMenuItem runItem = new JMenuItem("Run");
				runItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
				runItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						runProgram(appFrame);
					}
				});
				final JMenuItem noZoom = new JMenuItem("None");
				noZoom.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						mapPane.unzoom();
						//appFrame.validate();
					}					
				});
				final JMenuItem zoom = new JMenuItem("Continental U.S.");
				zoom.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						mapPane.zoom();
						//appFrame.validate();
					}					
				});
				changeRunSubMenu.add(changeToV1);
				changeRunSubMenu.add(changeToV2);
				changeRunSubMenu.add(changeToV3);
				changeRunSubMenu.add(changeToV4);
				changeRunSubMenu.add(changeToV5);
				runMenu.add(changeRunSubMenu);
				runMenu.addSeparator();
				runMenu.add(runItem);
				
				zoomMenu.add(noZoom);
				zoomMenu.add(zoom);

				toolbar.add(fileMenu);
				toolbar.add(runMenu);
				toolbar.add(zoomMenu);
				
				return toolbar;
			}
		});
	}
	
	static void runProgram(Component parent){
		int w = mapPane.getWest();
		int s = mapPane.getSouth();
		int e = mapPane.getEast();
		int n = mapPane.getNorth();
		//System.out.println(w + ", " + s + ", " + e + ", " + n);
		Pair<Integer,Float> result = PopulationQuery.singleInteraction(w, s, e, n);
		InteractionPane.displayCensusData(result.getElementA(), result.getElementB());
	}
	
	public static int getVersionNum(){
		switch(running){
		case ONE: return 1;
		case TWO: return 2;
		case THREE: return 3;
		case FOUR: return 4;
		case FIVE: return 5;
		default: return -1;
		}
	}
	
	public static void pqPreprocess(){
		PopulationQuery.preprocess(FILENAME, 
				mapPane.getColumns(), mapPane.getRows(), getVersionNum());
	}
}
