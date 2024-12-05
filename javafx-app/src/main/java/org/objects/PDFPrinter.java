package org.objects;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import org.example.*;
import javafx.collections.*;

public class PDFPrinter {

    public static void print(String classname, ObservableList<Gymnast> gymnasts) {
        // Output PDF file path
        for (int r = 0; r < HelperFunctions.EVENTS.length; r++){
            String event = HelperFunctions.EVENTS[r];
            String pdfPath = "/Users/davidskocic/Desktop/EventPrints/" + event + ".pdf";

            try {
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
                document.open();

                // Set up fonts for the document
                Font boldFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                Font dateFont = new Font(Font.FontFamily.HELVETICA, 10);
                Font primaryFont = new Font(Font.FontFamily.HELVETICA, 7);

                // Create a table with 2 columns (one for the class name, one for the bold text)
                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);  // Make the table take up the full width of the page
                headerTable.setWidths(new int[]{1, 5});  // Control column widths (1 for class name, 4 for the title)

                // Add the class name to the first column (top-left)
                PdfPCell classCell = new PdfPCell(new Phrase(classname + '\n' + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), dateFont));
                classCell.setBorder(PdfPCell.NO_BORDER);
                headerTable.addCell(classCell);

                // Add the bold text ("RAINBOW PROGRAM" and "GROUP TESTING FORM") to the second column (centered)
                PdfPCell headerCell = new PdfPCell(new Phrase("RAINBOW PROGRAM GROUP TESTING FORM", boldFont));
                headerCell.setBorder(PdfPCell.NO_BORDER);
                headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                headerTable.addCell(headerCell);

                PdfPCell emptyCell = new PdfPCell(new Phrase(" ", primaryFont));
                emptyCell.setBorder(PdfPCell.NO_BORDER);
                headerTable.addCell(emptyCell);

                PdfPCell eventCell = new PdfPCell(new Phrase(event.toUpperCase(), boldFont));
                eventCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                eventCell.setBorder(PdfPCell.NO_BORDER);
                headerTable.addCell(eventCell);
                // Add the table to the document (this places both rows in the first row of the table)
                document.add(headerTable);
                document.add(new Paragraph("\n"));

                
                PdfPTable progressTable = new PdfPTable(39);
                int[] widths = new int[39];
                widths[0] = 3;
                widths[1] = 3;
                widths[2] = 3;
                for (int i = 3; i < 39; i++) widths[i] = 1;
                progressTable.setWidths(widths);
                progressTable.setWidthPercentage(100);

            
                for (Gymnast g: gymnasts){
                    String age = "" + g.getAge();
                    LocalDate[][] progress = g.getProgress();

                    PdfPCell fnameCell = new PdfPCell(new Phrase(g.getFirstName(), primaryFont));
                    fnameCell.setBorder(PdfPCell.NO_BORDER);
                    progressTable.addCell(fnameCell);

                    PdfPCell lnameCell = new PdfPCell(new Phrase(g.getLastName(), primaryFont));
                    lnameCell.setBorder(PdfPCell.NO_BORDER);
                    progressTable.addCell(lnameCell);

                    PdfPCell ageCell = new PdfPCell(new Phrase(age, primaryFont));
                    ageCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                    ageCell.setBorder(PdfPCell.NO_BORDER);
                    progressTable.addCell(ageCell);

                    for (int c = 0; c < progress[r].length; c++){
                        if (progress[r][c] != null)
                            progressTable.addCell(new Phrase("X", primaryFont));
                        else 
                            progressTable.addCell(new Phrase(" ", primaryFont));
                        if (c % 3 == 2) progressTable.addCell(emptyCell);
                    }
                }

                document.add(progressTable);


                // Close the document to finalize the PDF
                document.close();

                System.out.println("PDF created successfully at: " + pdfPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
