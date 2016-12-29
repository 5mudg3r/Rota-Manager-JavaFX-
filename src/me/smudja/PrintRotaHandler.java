package me.smudja;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.StringTokenizer;

/**
 * This class handles printing of the current rota configuration.
 * 
 * @author smithl
 */
public class PrintRotaHandler implements Printable {

	
	/**
	 * The constructor for the class.
	 * A new instance of the class is created by {@link me.smudja.MenuHandler#handle(javafx.event.ActionEvent) MenuHandler}
	 * for each print job.
	 * 
	 * This constructor also displays the print dialog.
	 */
	public PrintRotaHandler() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } 
            catch (PrinterException ex) {
             /* The job did not successfully complete */
            	System.out.println("Unable to print");
            }
        }
   }
	
	/** 
	 * This method handles the printing of the document.
	 * It implements the {@code print()} function defined in the interface.
	 * 
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
		FontMetrics metrics = graphics.getFontMetrics(font);
		double height = pageFormat.getImageableHeight();
		double width = pageFormat.getImageableWidth();
		
		if(pageIndex > 0) {
			return NO_SUCH_PAGE;
		}
		
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		graphics.setFont(font);
		
		for(int i = 0; i < 9; i++) {
			g2d.draw(new Line2D.Double((i * width/8), 0, (i * width/8), height));
		}
		
		for(int j = 0; j < 5; j++) {
			g2d.draw(new Line2D.Double(0, (j * height/4), width, (j*height/4)));
		}
		
		graphics.drawString("Monday", (int) (width/8) + 20, (int) height/8);
		graphics.drawString("Tuesday", (int) (2 * width/8) + 20, (int) height/8);
		graphics.drawString("Wednesday", (int) (3 * width/8) + 20, (int) height/8);
		graphics.drawString("Thursday", (int) (4 * width/8) + 20, (int) height/8);
		graphics.drawString("Friday", (int) (5 * width/8) + 20, (int) height/8);
		graphics.drawString("Saturday", (int) (6 * width/8) + 20, (int) height/8);
		graphics.drawString("Sunday", (int) (7 * width/8) + 20, (int) height/8);
		
		graphics.drawString("Morning", 10, (int) (height/4) + 56);
		graphics.drawString("Afternoon", 10, (int) (2 * height/4) + 56);
		graphics.drawString("Evening", 10, (int) (3 * height/4) + 56);
		
		double cellWidth = width/8;
		double cellHeight = height/4;
		
		double shiftX;
		double shiftY;
		
		for(Shift shift : ShiftManager.INSTANCE.getShifts()) {
			shiftX = (shift.getDay().getLoc())*cellWidth;
			shiftY = (shift.getPeriod().getLoc())*cellHeight;
			
			graphics.drawString(shift.getPerson(), (int) (shiftX + (cellWidth - metrics.stringWidth(shift.getPerson()))/2), (int)shiftY + metrics.getHeight());
			graphics.drawString(shift.getPhone(), (int) (shiftX + (cellWidth - metrics.stringWidth(shift.getPhone()))/2), (int)shiftY + 2*metrics.getHeight());
			
			graphics.drawLine((int)(shiftX + 10), (int)(shiftY + 2.5*metrics.getHeight()), (int)(shiftX + cellWidth - 10),  (int)(shiftY + 2.5*metrics.getHeight()));
			
			drawMeal(graphics, shift.getMeal(), (int) shiftX, (int) shiftY, cellWidth, cellHeight, metrics);
		}
		
		
		
		return PAGE_EXISTS;
	}

	/**
	 * This utility method is called by {@link me.smudja.PrintRotaHandler#print(Graphics, PageFormat, int) print()}
	 * to handle displaying the meal on the page as it requires consideration of new lines.
	 * 
	 * @param graphics		The graphics being used in {@code print()}
	 * @param meal			The meal to be printed
	 * @param cellX			The leftmost x-coordinate of the cell to print the meal in
	 * @param cellY			The uppermost y-coordinate of the cell to print the meal in
	 * @param cellWidth		The width of the cell	
	 * @param cellHeight	The height of the cell
	 * @param metrics		The metrics of the font being used in {@code print()}
	 */
	private void drawMeal(Graphics graphics, String meal, int cellX, int cellY, double cellWidth, double cellHeight, FontMetrics metrics) {
		StringTokenizer strTknizr = new StringTokenizer(meal, " ");
		int Y = (int) ( cellY + 3.5*metrics.getHeight());
		int x = cellX;
		int y = Y;
		int spWidth = metrics.stringWidth(" ");
		String word, sp;
		String line = "";
		while(strTknizr.hasMoreTokens()) {
			word = strTknizr.nextToken();
			int wWidth = metrics.stringWidth(word);
			if((x + spWidth + wWidth) > cellX + cellWidth ) {
				if(y <= cellY + cellHeight) {
					graphics.drawString(line, (int)(cellX + ((cellWidth - metrics.stringWidth(line))/2)), y);
				}
				else {
					graphics.drawString("", (int)(cellX + ((cellWidth - metrics.stringWidth(line))/2)), y);
					break;
				}
				
				line = word;
				x = cellX + wWidth;
				y = y + metrics.getHeight();
			}
			else {
				if(x != cellX) {sp = " ";} else { sp = ""; }
				line = line + sp + word;
				x = x + spWidth + wWidth;
			}
		}
		if(y <= cellY + cellHeight) {
			graphics.drawString(line, (int) (cellX + ((cellWidth - metrics.stringWidth(line))/2)), (int) y);
		}
		else {
			graphics.drawString("...", (int) (cellX + ((cellWidth - metrics.stringWidth(line))/2)), (int) y);
		}
		
	}

}
