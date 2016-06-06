import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreator {

	private String fileName;
	private String docTitle;
	private static String keyWords = "Java, PDF, iText, DVDRental";
	private static String author = System.getProperty("user.name");

	
	private BaseFont baseFont;
	private Font catFont;
	private Font redFont;
	private Font subFont;
	private Font smallBold;

	
	public PdfCreator(String fileName, String docTitle) {
		this.fileName = fileName + ".pdf";
		this.docTitle = docTitle;
		
		try {
			baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		catFont = new Font(baseFont, 18, Font.BOLD);
		redFont = new Font(baseFont, 12, Font.NORMAL, BaseColor.RED);
		subFont = new Font(baseFont, 16, Font.BOLD);
		smallBold = new Font(baseFont, 12, Font.BOLD);
	}

	
	
	public void createRentalPdf(DVDList dvdList) {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			addMetaData(document);
			addDocHeader(document);
			addContent(document, dvdList);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		PdfCreator pdfCreator = new PdfCreator("kapucha", "Tabela");
		DVDList dvdList = new DVDList();
		dvdList.add(new DVD(1, 2, true, "janusz", "mistrz", new java.sql.Date(0)));
		pdfCreator.createRentalPdf(dvdList);

	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private void addMetaData(Document document) {
		document.addTitle(docTitle);
		document.addSubject("PDF generated by iText");
		document.addKeywords(keyWords);
		document.addAuthor(author);
		// document.addCreator(System.getProperty("user.name"));
	}

	private void addDocHeader(Document document) throws DocumentException {

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph(docTitle, catFont));

		addEmptyLine(preface, 1);
		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph("Stan magazynu DVD na dzie�: " + new Date() + " wygenerowane przez: " + System.getProperty("user.name"),			smallBold));
		addEmptyLine(preface, 3);
		preface.add(new Paragraph("This document describes something which is very important ", smallBold));

		addEmptyLine(preface, 1);


		document.add(preface);
	}

	private void addContent(Document document, DVDList dvdList) throws DocumentException {

		// add a table
		createTable(document, dvdList);
	}

	private void addRow(PdfPTable table, DVD dvd) {
		
		PdfPCell cell = new PdfPCell(new Phrase(Integer.toString(dvd.getDvdId())));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		
		cell = new PdfPCell(new Phrase(dvd.getUserName()));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase(dvd.getLentDate().toString()));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	
	}
	
	
	private void createTable(Document document, DVDList dvdList) throws DocumentException {
		
		PdfPTable table = new PdfPTable(3);

		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);

		PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 2"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 3"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		
		for(DVD dvd : dvdList){
			addRow(table, dvd);
		}
		
		document.add(table);
	}

	// private static void createList(Section subCatPart) {
	// List list = new List(true, false, 10);
	// list.add(new ListItem("First point"));
	// list.add(new ListItem("Second point"));
	// list.add(new ListItem("Third point"));
	// subCatPart.add(list);
	// }

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}