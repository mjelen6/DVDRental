package engine;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParsePosition;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

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

import datatypes.DVD;
import datatypes.Movie;
import datatypes.Record;
import datatypes.RecordList;

public class PdfCreator {

	private String fileName;
	private String docTitle;
	private static String keyWords = "Java, PDF, iText, DVDRental";
	private static String author = System.getProperty("user.name");

	
	private BaseFont timesFont;
	private BaseFont arialFont;
	private Font catFont;
	private Font redFont;
	private Font subFont;
	private Font smallBold;
	private Font tableFont;

	
	public PdfCreator(String fileName, String docTitle) {
		this.fileName = fileName;
		this.docTitle = docTitle;
		
		try {
			timesFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
			arialFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		} 
			
		catFont = new Font(timesFont, 18, Font.BOLD);
		redFont = new Font(timesFont, 12, Font.NORMAL, BaseColor.RED);
		subFont = new Font(timesFont, 16, Font.BOLD);
		smallBold = new Font(timesFont, 12, Font.BOLD);
		tableFont = new Font(arialFont, 10, Font.NORMAL);
	}

	
	
	public void createRentalPdf(RecordList dvdList) {

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

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new FileNameExtensionFilter("PDF files (*.pdf)", "pdf"));
		
		if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

			File file = jFileChooser.getSelectedFile();
			String fname = file.getAbsolutePath();
			 
			if(!fname.endsWith(".pdf") ) 
                file = new File(fname + ".pdf");
					
			PdfCreator pdfCreator = new PdfCreator(file.getName(), "Tabela");
			DVD dvd1 = new DVD(1, 2, false, "janusz mistrz", new Date((new java.util.Date()).getTime()));
			Movie movie1 = new Movie(2, "Ogniem i mieczem", "Tutenhamon", "Horror");
			RecordList recordList = new RecordList();
			recordList.add(new Record(movie1, dvd1));
			
			DVD dvd2 = new DVD(3, 5, true, null, null);
			Movie movie2 = new Movie(5, "Titanic", "Cameron", "Dramat");
			recordList.add(new Record(movie2, dvd2));
			
			pdfCreator.createRentalPdf(recordList);
		}
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

		Paragraph paragraph = new Paragraph();
		// We add one empty line
		addEmptyLine(paragraph, 1);
		// Lets write a big header
		paragraph.add(new Paragraph(docTitle, catFont));

		addEmptyLine(paragraph, 1);
		// Will create: Report generated by: _name, _date
		paragraph.add(new Paragraph("Stan magazynu DVD na dzie�: " + new java.util.Date() + " wygenerowane przez: " + System.getProperty("user.name"), smallBold));
		addEmptyLine(paragraph, 3);
//		preface.add(new Paragraph("This document describes something which is very important ", smallBold));
//		addEmptyLine(preface, 1);

		document.add(paragraph);
	}

	private void addContent(Document document, RecordList dvdList) throws DocumentException {

		// add a table
		document.add(createTable(dvdList));
		
	}

	
	
	private PdfPTable createTable(RecordList dvdList) throws DocumentException {
		
		PdfPTable table = new PdfPTable(8);
		table.setWidths(new int[]{25,25,50,50,50,50,50,60});
		table.setWidthPercentage(100f);
		
		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);
		
//		{"dvdID",			false},
//		{"mid",				false},
//		{"Tytu�",			false},
//		{"Re�yser",			false},
//		{"Kategoria",		false},
//		{"Dost�pno��",		false},
//		{"Wypo�yczy�",		false},
//		{"Data po�yczki",	false},	};

		PdfPCell c1 = new PdfPCell(new Phrase("dvdID",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("mid",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Tytu�",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Re�yser",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Kategoria",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Dost�pno��",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Wypo�yczy�",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Data po�yczki",tableFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		table.setHeaderRows(1);

		
		for(Record dvd : dvdList){
			addRow(table, dvd);
		}
			
		return table;
	}


	private void addRow(PdfPTable table, Record record) {
		
//	   			{"dvdID",			false},
//				{"mid",				false},
//				{"Tytu�",			false},
//				{"Re�yser",			false},
//				{"Kategoria",		false},
//				{"Dost�pny",		false},
//				{"Wypo�yczy�",		false},
//				{"Data po�yczki",	false},	};

		
		DVD dvd = record.getDvd();
		Movie movie = record.getMovie();
		
		PdfPCell cell = new PdfPCell(new Phrase(Integer.toString(dvd.getDvdId()),tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(Integer.toString(movie.getMid()),tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
				
		cell = new PdfPCell(new Phrase(movie.getTitle(),tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(movie.getDirector(),tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
				
		cell = new PdfPCell(new Phrase(movie.getCategory(),tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		
		String available = dvd.isAvailable() ? "Dost�pny" : "Wypo�yczony";
		cell = new PdfPCell(new Phrase(available,tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
				
		String user = dvd.getUserName();
		if(user == null){
			user = new String("");
		}
		cell = new PdfPCell(new Phrase(user,tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		Date date = dvd.getLentDate();
		String dateStr;
		if(date == null){
			dateStr = new String("");
		}
		else {
			dateStr = date.toLocaleString();
		}
		
		
		
		cell = new PdfPCell(new Phrase(dateStr,tableFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
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