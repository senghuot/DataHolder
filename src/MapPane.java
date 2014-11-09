import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class MapPane extends JPanel implements Observer{
	
	//large map/census data bounds
	private final double MAX_WEST = -173.033005;
	private final double MAX_EAST = -65.300858;
	private final double MAX_SOUTH1 = 17.941346;	
	private final double MAX_SOUTH = mercatorConversion(MAX_SOUTH1);
	private final double MAX_NORTH1 = 71.300949;
	private final double MAX_NORTH = mercatorConversion(MAX_NORTH1);
	
	//continental US map bounds
	private final double CONT_MAX_WEST = -126;
	private final double CONT_MAX_EAST = -66;
	private final double CONT_MAX_SOUTH1 = 24;
	private final double CONT_MAX_SOUTH = mercatorConversion(CONT_MAX_SOUTH1);
	private final double CONT_MAX_NORTH1 = 50;
	private final double CONT_MAX_NORTH = mercatorConversion(CONT_MAX_NORTH1);
	
	//for adjusting the continental zoom
	private final double H_ZOOM_FACTOR = (MAX_NORTH - MAX_SOUTH) / (CONT_MAX_NORTH - CONT_MAX_SOUTH);
	private final double W_ZOOM_FACTOR = (MAX_EAST - MAX_WEST) / (CONT_MAX_EAST - CONT_MAX_WEST);
	//the offset of the continental US w.r.t the entire US
	private final double DOWN_SHIFT = (MAX_NORTH - CONT_MAX_NORTH) / (MAX_NORTH - MAX_SOUTH);
	private final double RIGHT_SHIFT = (MAX_WEST - CONT_MAX_WEST) / (MAX_WEST - MAX_EAST);
	
	// for debugging: we placed these cities on the canvas and then lined the background image to match.	
	
	/*
	private final double MIAMI_N1 = 25.787778;
	private final double MIAMI_N = mercatorConversion(MIAMI_N1);
	private final double MIAMI_E = -80.224167;
	private final double SEATTLE_N1 = 47.61;
	private final double SEATTLE_N = mercatorConversion(SEATTLE_N1);
	private final double SEATTLE_E = -122.33;
	private final double BOSTON_N1 = 42.34711;
	private final double BOSTON_N = mercatorConversion(BOSTON_N1);
	private final double BOSTON_E = -71.0945;
	private final double SANDIEGO_N1 = 32.71528;
	private final double SANDIEGO_N = mercatorConversion(SANDIEGO_N1);
	private final double SANDIEGO_E = -117.15722; 
	private final double HONOLULU_N1 = 21.3069;
	private final double HONOLULU_N = mercatorConversion(HONOLULU_N1);
	private final double HONOLULU_E = -157.8583;
	*/
	
	// This is the actual Mercator Equation. Took a bloody long time to find.
	private double mercatorConversion(double lat){
		double latpi = lat * Math.PI / 180;
		double x = Math.log(Math.tan(latpi) + 1 / Math.cos(latpi));
		//System.out.println(lat + " -> " + x);
		return x;
	}
	
	private int rows, columns;
	private Image wholeUSImage, contUSImage, currentImage;
	private double sStartColumn, sStartRow;
	private double sEndColumn, sEndRow;
	private boolean selecting, zoomed;
	
	public int getRows(){
		return rows; 
	}
	public int getColumns(){
		return columns;
	}
	public int getNorth(){
		return rows - (int)Math.min(sStartRow, sEndRow);
	}
	public int getSouth(){
		return rows - (int)Math.max(sStartRow, sEndRow);
	}
	public int getEast(){
		return (int)Math.max(sStartColumn, sEndColumn) + 1;
	}
	public int getWest(){
		return (int)Math.min(sStartColumn, sEndColumn) + 1;
	}
	public void zoom(){
		zoomed = true;
		currentImage = contUSImage;
		repaint();
	}
	public void unzoom(){
		zoomed = false;
		currentImage = wholeUSImage;
		repaint();
	}
	public void paint(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int height = this.getHeight();
		int width = this.getWidth();
		//System.out.println(width + ", " + height);
		double rHeight = 1.0 * height / rows;
		double rWidth = 1.0 * width / columns;
		// draw map
		g2.drawImage(currentImage, 1, 1, width, height, null);
		if(zoomed){
			//expand the rectangles
			rHeight *= H_ZOOM_FACTOR;
			rWidth *= W_ZOOM_FACTOR;
			//shift their start point
			int startX = -(int)(width * W_ZOOM_FACTOR * RIGHT_SHIFT);
			int startY = -(int)(height * H_ZOOM_FACTOR * DOWN_SHIFT);
			// draw squares
			for(int x = 0; x < columns; x++){
				for(int y = 0; y < rows; y++){
					double rX = x * rWidth + startX;
					double rY = y * rHeight + startY;
					if((rX + rWidth > 0 && rY + rHeight > 0) ||
							(rX < width && rY < height)){
						//if the rectangle is within the viewing area
						Rectangle2D r = new Rectangle2D.Double(rX, rY, rWidth, rHeight);
						g2.draw(r);
					}					
				}
			}
		} else {
			// draw squares			
			//System.out.println(rWidth + ", " + rHeight);
			for(int x = 0; x < columns; x++){
				for(int y = 0; y < rows; y++){
					Rectangle2D r = new Rectangle2D.Double(x * rWidth, y * rHeight, rWidth, rHeight);
					g2.draw(r);
				}
			}
		}
				
		
		// For debugging: Draw some cities on top of the background image. Then line the image up with these cities.
		/*double nsRatio = 1.0 * height / (MAX_NORTH - MAX_SOUTH);
		double ewRatio = 1.0 * width / (MAX_EAST - MAX_WEST);
		Rectangle2D seattle = new Rectangle2D.Double(-1 * (MAX_WEST - SEATTLE_E) * ewRatio, (MAX_NORTH - SEATTLE_N) * nsRatio, 6, 6);
		Rectangle2D boston = new Rectangle2D.Double(-1 * (MAX_WEST - BOSTON_E) * ewRatio, (MAX_NORTH - BOSTON_N) * nsRatio,  6, 6);
		Rectangle2D sandiego = new Rectangle2D.Double(-1 * (MAX_WEST - SANDIEGO_E) * ewRatio, (MAX_NORTH - SANDIEGO_N) * nsRatio, 6, 6);
		Rectangle2D miami = new Rectangle2D.Double(-1 * (MAX_WEST - MIAMI_E) * ewRatio, (MAX_NORTH - MIAMI_N) * nsRatio, 6, 6);
		Rectangle2D honolulu = new Rectangle2D.Double(-1 * (MAX_WEST - HONOLULU_E) * ewRatio, (MAX_NORTH - HONOLULU_N) * nsRatio, 6, 6);
		g2.setColor(Color.RED); 
		g2.fill(seattle);
		g2.fill(sandiego);
		g2.fill(boston);
		g2.fill(miami);
		g2.fill(honolulu);
		g2.setColor(Color.BLACK);
		*/
		
		// color squares based on selection
		if(selecting){
			int trueStartColumn = (int)Math.min(sStartColumn, sEndColumn);
			int trueStartRow = (int)Math.min(sStartRow, sEndRow);
			int trueEndColumn = (int)Math.max(sStartColumn, sEndColumn);
			int trueEndRow = (int)Math.max(sStartRow, sEndRow);
			int trueHeight = trueEndRow - trueStartRow + 1;
			int trueWidth = trueEndColumn - trueStartColumn + 1;
			double recX = trueStartColumn * rWidth;
			double recY = trueStartRow * rHeight;
			double recWidth = trueWidth * rWidth;
			double recHeight = trueHeight * rHeight;
			if(zoomed){
				//translate and expand the selection rectangle for zoom
				int startX = -(int)(width * W_ZOOM_FACTOR * RIGHT_SHIFT);
				int startY = -(int)(height * H_ZOOM_FACTOR * DOWN_SHIFT);
				recX += startX;
				recY += startY;
				//recWidth *= W_ZOOM_FACTOR;
				//recHeight *= H_ZOOM_FACTOR;
			}
			Rectangle2D r = new Rectangle2D.Double(recX, recY, recWidth, recHeight);
			Color shade = Color.YELLOW;
			g2.setColor(shade);
			g2.setColor(new Color(shade.getRed(), shade.getGreen(), shade.getBlue(), 120));
			g2.fill(r);
			g2.setColor(Color.BLACK);
		}
		
		selecting = false;
		//System.out.println(rows + "X" + columns);
	}
	
	public MapPane(final JFrame appFrame){
		super(new BorderLayout(0,1));
		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		selecting = false;
		zoomed = false;
		// Add image
		MediaTracker mt = new MediaTracker(this);
		wholeUSImage = Toolkit.getDefaultToolkit().getImage("USMap.jpg");
		contUSImage = Toolkit.getDefaultToolkit().getImage("contUSmap.jpg");
		currentImage = wholeUSImage;
		mt.addImage(wholeUSImage, 0);
		mt.addImage(contUSImage, 1);
		try{
			mt.waitForAll();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) { }
			public void mouseEntered(MouseEvent e) { }

			public void mouseExited(MouseEvent e) {
				if(selecting){
					Component mapPane = e.getComponent();
					int w = mapPane.getWidth();
					int h = mapPane.getHeight();
					int x = e.getX();
					int y = e.getY();
					if(zoomed){
						//adjust to reflect the position on the overall map
						w *= W_ZOOM_FACTOR;
						h *= H_ZOOM_FACTOR;
						x = (int)(x + w * RIGHT_SHIFT);
						y = (int)(y + h * DOWN_SHIFT);
					}
					// must make sure we aren't extending beyond height/width bounds
					sEndColumn = Math.min(Math.max(x / (1.0 * w / columns), 0), columns - 1);
					sEndRow = Math.min(Math.max(y / (1.0 * h / rows), 0), rows - 1);
					//System.out.println("(" + sEndRow + "," + sEndColumn + ")");
					mapPane.repaint();
				}
			}

			public void mousePressed(MouseEvent e) {
				Component mapPane = e.getComponent();
				int w = mapPane.getWidth();
				int h = mapPane.getHeight();
				int x = e.getX();
				int y = e.getY();
				if(zoomed){
					//adjust to reflect the position on the overall map
					w *= W_ZOOM_FACTOR;
					h *= H_ZOOM_FACTOR;
					x = (int)(x + w * RIGHT_SHIFT);
					y = (int)(y + h * DOWN_SHIFT);
				}
				selecting = true;
				sStartColumn = x / (1.0 * w / columns);
				sStartRow = y / (1.0 * h / rows);
				//System.out.println("(" + sStartRow + "," + sStartColumn + ")");
			}

			public void mouseReleased(MouseEvent e) {
				if(selecting){
					Component mapPane = e.getComponent();
					int w = mapPane.getWidth();
					int h = mapPane.getHeight();
					int x = e.getX();
					int y = e.getY();
					if(zoomed){
						//adjust to reflect the position on the overall map
						w *= W_ZOOM_FACTOR;
						h *= H_ZOOM_FACTOR;
						x = (int)(x + w * RIGHT_SHIFT);
						y = (int)(y + h * DOWN_SHIFT);
					}
					sEndColumn = x / (1.0 * w / columns);
					sEndRow = y / (1.0 * h / rows);
					mapPane.repaint();
				}
			}
		});
	}

	public void update(Observable o, Object arg) {
		if(o instanceof InteractionPane.MapGrid){
			InteractionPane.MapGrid mg = (InteractionPane.MapGrid)o;
			rows = mg.getRows();
			columns = mg.getColumns();
			USMaps.pqPreprocess();
			this.repaint();
		}
	}
}
