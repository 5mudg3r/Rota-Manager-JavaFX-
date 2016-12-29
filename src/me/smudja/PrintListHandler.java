package me.smudja;

import java.awt.*;
import java.awt.print.*;

public class PrintListHandler implements Printable {
		
		int[] pageBreaks;	// array to store page break line positions
		String[] textLines;
		
		public PrintListHandler(String[] textLines) {
			this.textLines = textLines;
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
			
			if(pageBreaks == null) {
				int linesPerPage = (int)(pageFormat.getImageableHeight()/lineHeight);
				int numBreaks = (textLines.length -1)/linesPerPage;
				pageBreaks = new int[numBreaks];
				for(int b = 0; b < numBreaks; b++) {
					pageBreaks[b] = (b+1)*linesPerPage;
				}
			}
			
			if(pageIndex > pageBreaks.length) {
				return NO_SUCH_PAGE;
			}
						
			Graphics2D g2d = (Graphics2D) graphics;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			
			graphics.setFont(font);
			
			int y = 0;
			int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
			int end = (pageIndex == pageBreaks.length) ? textLines.length : pageBreaks[pageIndex];
			
			for(int line = start; line < end; line++) {
				y += lineHeight;
				graphics.drawString(textLines[line], 0, y);
			}
			
			return PAGE_EXISTS;
		}
}
