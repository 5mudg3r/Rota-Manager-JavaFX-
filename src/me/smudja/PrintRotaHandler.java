package me.smudja;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintRotaHandler implements Printable {

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
	
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
		FontMetrics metrics = graphics.getFontMetrics(font);
		int lineHeight = metrics.getHeight();
		
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
		
		
		
		return PAGE_EXISTS;
	}

}
