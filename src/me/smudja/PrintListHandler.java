package me.smudja;

import java.awt.*;
import java.awt.print.*;

/**
 * This class handles printing of shopping lists.
 * 
 * @author smithl
 */
public class PrintListHandler implements Printable {
		
		/**
		 * This array stores the line numbers of each page break.
		 */
		int[] pageBreaks;
		/**
		 * The array storing the lines of text to be printed.
		 */
		String[] textLines;
		
		/**
		 * The constructor for the class.
		 * A new instance is created by {@link me.smudja.MenuHandler#handle(javafx.event.ActionEvent) MenuHandler}
		 * for each print job.
		 * 
		 * This constructor also displays the print dialog.
		 * 
		 * @param textLines	The shopping list to be printed
		 */
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
		
		/** 
		 * This method handles the actual printing of the shopping list.
		 * It implements the {@code print()} function defined in the interface.
		 * 
		 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
		 */
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
