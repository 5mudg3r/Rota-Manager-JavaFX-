package me.smudja;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		
		pageFormat.setOrientation(PageFormat.LANDSCAPE);
		
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		graphics.setFont(font);
		
		for(int i = 0, i < 9; i++) {
			g2d.drawLine(x1, y1, x2, y2);
		}
		
		
		
		return PAGE_EXISTS;
	}

}
