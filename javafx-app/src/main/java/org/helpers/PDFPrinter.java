package org.helpers;

import org.objects.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import javafx.collections.*;

public class PDFPrinter {

    public static String EVENT_SKILLS[][] = {{"1/2 Handstand", "Forward Roll", "Cartwheel", "Tripod", "Roundoff Rebound", "Backwards Roll", "HS Forward Roll", "2 Cartwheel Step in", "Press Handstand", "Back Handspring"},{"Walking Circle", "Dismount", "1/4 Circle","Bucket Russian", "1/2 Circle", "Bucket Circle", "3/4 Circle", "5 Bucket Circles", "3/2 Circle", "10 Bucket Circles"},{"Put Feet in Rings", "Tuck Hold", "Basket Hold", "5 Swings", "Basket to Skin the Cat", "Swing to Candle", "Pull up", "Candle, Basket, Skin the Cat", "Support Hold", "Lever"},{"Stick", "Jump into Pit", "Run, Hurdle, Jump", "Straight Jump Over Panel Mat", "Level 3 Vault", "Handstand Flatback", "HS Flatback", "Front Handspring", "Front Tuck", "Front Tuck into Pit"}, {"Tuck Hold", "Bear/Crab Walks", "Support Hold", "Straddle Walks", "Straddle L Hold", "5 Swings", "Support Walks", "Dismount", "Underbar Swing to Pits", "Swing to 45"}, {"Monkey Walks", "Support Hold", "1/2 Turn", "3 Casts", "Kick Over", "5 Swings", "Back Hip Circle", "Swings with Hop", "Pull Over", "Back Hip Circle Underswing"}};
    public static void print(String classname, ObservableList<Gymnast> gymnasts) {
        // Output PDF file path
        for (int r = 0; r < Helpers.EVENTS.length; r++){
            String event = Helpers.EVENTS[r];
            String pdfPath = "/Users/davidskocic/Desktop/EventPrints/" + event + ".pdf";

            try {
                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
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

                
                int[] widths = new int[3 + Helpers.NUM_SKILLS_TIM + Helpers.NUM_SKILLS_TIM / 2 - 1];
                PdfPTable progressTable = new PdfPTable(widths.length);
                widths[0] = 3;
                widths[1] = 3;
                widths[2] = 3;
                for (int i = 3; i < widths.length ; i++) widths[i] = 1;
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
                        if (c % 2 == 1) progressTable.addCell(emptyCell);
                    }
                }

                document.add(progressTable);
                
                PdfPTable footerTable = new PdfPTable(Helpers.NUM_SKILLS_TIM/2);
                for (int skill = 0; skill < EVENT_SKILLS[0].length; skill+=2){
                    PdfPCell cell = new PdfPCell(new Phrase((skill+1) + " - " + EVENT_SKILLS[r][skill], primaryFont));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    footerTable.addCell(cell);
                }
                for (int skill = 1; skill < EVENT_SKILLS[0].length; skill+=2){
                    PdfPCell cell = new PdfPCell(new Phrase((skill+1) + " - " + EVENT_SKILLS[r][skill], primaryFont));
                    cell.setBorder(PdfPCell.NO_BORDER);
                    footerTable.addCell(cell);
                }
                    
                footerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
                footerTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottom()+footerTable.getTotalHeight(), writer.getDirectContent());

                document.close();

                System.out.println("PDF created successfully at: " + pdfPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
