package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controllers.NPV;
import javafx.collections.ObservableList;
import models.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Martín Ruíz on 4/27/2017.
 */
public class PDFMaker {

    public static void makePDFMatrix(String methodName,String primaryWeighting, String primaryWeighted
                                    ,String secondaryWeighting, String secondaryWeighted
                                    ,String otherWeighting, String otherWeighted,
                                     String totalWeighting, String totalWeghted, String result) throws DocumentException, IOException {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(Session.getInstance().getProjectID()+"_"+methodName+".pdf"));//como quieres que se llame tu archivo
            document.open();//a partir de aquí se va llenando el pdf, recuerda poner el .close
            Image image = Image.getInstance("resources\\images\\logoTecLowRes.png");
            image.scalePercent(40);//como agregar imagen

            //document.add(new Paragraph("Image"));
            document.add(image);
            document.add(new Paragraph("Project Name: Evaluation Tool"  ));
            document.add(new Paragraph("Project Number:" +Session.getInstance().getProjectID()));
            document.add(new Paragraph("Evaluator name: "+Session.getInstance().getEvaluatorName()));
            document.add(new Paragraph("Date: " + new Date().toString()));
            document.add(new Paragraph(methodName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18)));//puedes agregar texto estático y ponerle font y tamaño
            //document.add(new Paragraph("------------------------------------------------------------"));

            //document.add(new Paragraph("Principal:"+principal));
            //document.add(new Paragraph("Salvage Value (if applicable):"+salvageValue));
            document.add(new Paragraph("------------------------------------------------------------"));
            document.add(new Paragraph(""));
            PdfPTable table = new PdfPTable(3);
            PdfPCell cell = new PdfPCell(new Paragraph("Matrix calculated table"));
            cell.setColspan(3);//cuantas columnas en tu table
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell);

            table.addCell("Consideration");//titulos de las columnas
            table.addCell("Weighting");
            table.addCell("Weighted Value");
            table.addCell("Primary considerations");
            table.addCell(primaryWeighting);
            table.addCell(primaryWeighted);
            table.addCell("Secondary considerations");
            table.addCell(secondaryWeighting);
            table.addCell(secondaryWeighted);
            table.addCell("Other considerations");
            table.addCell(otherWeighting);
            table.addCell(otherWeighted);
            table.addCell("Grand Total");
            table.addCell(totalWeighting);
            table.addCell(totalWeghted);
            table.addCell("DECISION");
            table.addCell("");
            table.addCell(result);
            document.add(table);
            //agregar imagen al reporte
            document.add(new Paragraph("The result was: "+result));
            //JOptionPane.showMessageDialog(null, "Document saved");
            document.add(new Paragraph(""));
            document.add(new Paragraph("------------------------------------------------------------"));
            document.add(new Paragraph(""));

            PdfPTable table2 = new PdfPTable(3);
            PdfPCell cell2 = new PdfPCell(new Paragraph("Decision Range"));
            cell2.setColspan(3);//cuantas columnas en tu table
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.BLUE);
            table.addCell(cell2);
            table2.addCell("From");
            table2.addCell("To");
            table2.addCell("D/B Applicability");
            table2.addCell("0");
            table2.addCell("50");
            table2.addCell("No");
            table2.addCell("50");
            table2.addCell("65");
            table2.addCell("Can consider");
            table2.addCell("65");
            table2.addCell("100");
            table2.addCell("Yes");
            document.add(table2);
        document.close();

    }

    public static void makePDFDepreciation(String methodName,int periods, double principal,double taxRate, double salvage, String category, int salvagePeriod
                                            ,ObservableList<DepreciationRow> data) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Session.getInstance().getProjectID()+"_"+methodName+".pdf"));//como quieres que se llame tu archivo
        document.open();//a partir de aquí se va llenando el pdf, recuerda poner el .close
        Image image = Image.getInstance("resources\\images\\logoTecLowRes.png");
        image.scalePercent(40);//como agregar imagen

        //document.add(new Paragraph("Image"));
        document.add(image);
        document.add(new Paragraph("Project Name: Evaluation Tool"  ));
        document.add(new Paragraph("Project Number:" +Session.getInstance().getProjectID()));
        document.add(new Paragraph("Evaluator name: "+Session.getInstance().getEvaluatorName()));
        document.add(new Paragraph("Date: " + new Date().toString()));
        document.add(new Paragraph(methodName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18)));//puedes agregar texto estático y ponerle font y tamaño
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph("Principal:"+principal));
        document.add(new Paragraph("Tax Rate:"+taxRate));
        document.add(new Paragraph("Salvage Value"+salvage));
        document.add(new Paragraph("Salvage Period"+salvagePeriod));
        document.add(new Paragraph("Periods:"+periods));
        document.add(new Paragraph("Type/Category:"+category));
        //document.add(new Paragraph("Principal:"+principal));
        //document.add(new Paragraph("Salvage Value (if applicable):"+salvageValue));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        PdfPTable table = new PdfPTable(6);
        PdfPCell cell = new PdfPCell(new Paragraph("Detailed calculated table"));
        cell.setColspan(6);//cuantas columnas en tu table
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.BLUE);
        table.addCell(cell);

        table.addCell("Year");//titulos de las columnas
        table.addCell("Depreciation Rate");
        table.addCell("Annual Depreciation");
        table.addCell("Cumulative Depreciation");
        table.addCell("Value in ledgers");
        table.addCell("Tax per year");
        /**/
        for (int i = 0; i < data.size(); i++) {
            DepreciationRow temp = data.get(i);
            table.addCell(String.valueOf(temp.getYear()));
            table.addCell(String.valueOf(temp.getDepreciatonRate()));
            table.addCell(String.valueOf(temp.getAnnualDepreciation()));
            table.addCell(String.valueOf(temp.getCumulativeDepreciation()));
            table.addCell(String.valueOf(temp.getValueInLedgers()));
            table.addCell(String .valueOf(temp.getTaxPerYear()));
        }
        document.add(table);
        //agregar imagen al reporte
        document.add(new Paragraph("The result was: "+data.get(data.size()-1).getTaxPerYear()));
        //JOptionPane.showMessageDialog(null, "Document saved");
        document.add(new Paragraph(""));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        Image image2 = Image.getInstance("DepreciationChart.png");
        //image2.scalePercent(40);//como agregar imagen
        image2.scaleToFit(480,480);
        //document.add(new Paragraph("Image"));
        document.add(image2);
        document.close();
    }

    public static void makePDFChecklist(String methodName, ObservableList<ChecklistRow> data) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Session.getInstance().getProjectID()+"_"+methodName+".pdf"));//como quieres que se llame tu archivo
        document.open();//a partir de aquí se va llenando el pdf, recuerda poner el .close
        Image image = Image.getInstance("resources\\images\\logoTecLowRes.png");
        image.scalePercent(40);//como agregar imagen

        //document.add(new Paragraph("Image"));
        document.add(image);
        document.add(new Paragraph("Project Name: Evaluation Tool"  ));
        document.add(new Paragraph("Project Number:" +Session.getInstance().getProjectID()));
        document.add(new Paragraph("Evaluator name: "+Session.getInstance().getEvaluatorName()));
        document.add(new Paragraph("Date: " + new Date().toString()));
        document.add(new Paragraph(methodName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18)));//puedes agregar texto estático y ponerle font y tamaño
        document.add(new Paragraph("------------------------------------------------------------"));
        //document.add(new Paragraph("Interest rate:"+interestRate));
        //document.add(new Paragraph("Periods:"+periods));
        //document.add(new Paragraph("Tax Rate (if applicable):"+taxRate));
        //document.add(new Paragraph("Principal:"+principal));
        //document.add(new Paragraph("Salvage Value (if applicable):"+salvageValue));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell = new PdfPCell(new Paragraph("Detailed calculated table"));
        cell.setColspan(3);//cuantas columnas en tu table
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.BLUE);
        table.addCell(cell);

        table.addCell("Topic");//titulos de las columnas
        //table.setWidthPercentage(25);
        table.addCell("Question");
        //table.setWidthPercentage(37.5f);
        table.addCell("Answer");
        //table.setWidthPercentage(37.5f);
        /**/
        for (int i = 0; i < data.size(); i++) {
            ChecklistRow temp = data.get(i);
            table.addCell(temp.getTopic());
            table.addCell(temp.getQuestion());
            table.addCell(temp.getAnswer());
        }
        document.add(table);
        //agregar imagen al reporte
        //document.add(new Paragraph("The result was: "+result));
        //JOptionPane.showMessageDialog(null, "Document saved");
        document.add(new Paragraph(""));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        //Image image2 = Image.getInstance("NPVChart.png");
        //image2.scalePercent(40);//como agregar imagen
        //image2.scaleToFit(480,480);
        //document.add(new Paragraph("Image"));
        //document.add(image2);
        document.close();
    }



    public static void makePDFNPV(String methodName, double interestRate, int periods, double taxRate,
                                  double result, ObservableList<NPVRow> data) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Session.getInstance().getProjectID()+"_"+methodName+".pdf"));//como quieres que se llame tu archivo
        document.open();//a partir de aquí se va llenando el pdf, recuerda poner el .close
        Image image = Image.getInstance("resources\\images\\logoTecLowRes.png");
        image.scalePercent(40);//como agregar imagen

        //document.add(new Paragraph("Image"));
        document.add(image);
        document.add(new Paragraph("Project Name: Evaluation Tool"  ));
        document.add(new Paragraph("Project Number:" +Session.getInstance().getProjectID()));
        document.add(new Paragraph("Evaluator name: "+Session.getInstance().getEvaluatorName()));
        document.add(new Paragraph("Date: " + new Date().toString()));
        document.add(new Paragraph(methodName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18)));//puedes agregar texto estático y ponerle font y tamaño
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph("Interest rate:"+interestRate));
        document.add(new Paragraph("Periods:"+periods));
        document.add(new Paragraph("Tax Rate (if applicable):"+taxRate));
        //document.add(new Paragraph("Principal:"+principal));
        //document.add(new Paragraph("Salvage Value (if applicable):"+salvageValue));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        PdfPTable table = new PdfPTable(6);
        PdfPCell cell = new PdfPCell(new Paragraph("Detailed calculated table"));
        cell.setColspan(6);//cuantas columnas en tu table
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.BLUE);
        table.addCell(cell);

        table.addCell("Period");//titulos de las columnas
        table.addCell("Outflow");
        table.addCell("Inflow");
        table.addCell("Net Cash Flow");
        table.addCell("Net Present Value");
        table.addCell("Cumulative Net Present");
        /**/
        for (int i = 0; i < data.size(); i++) {
            NPVRow temp = data.get(i);
            table.addCell(String.valueOf(temp.getPeriod()));
            table.addCell(String.valueOf(temp.getOutflow()));
            table.addCell(String.valueOf(temp.getInflow()));
            table.addCell(String.valueOf(temp.getNetCashFlow()));
            table.addCell(String.valueOf(temp.getNetPresentValue()));
            table.addCell(String .valueOf(temp.getCumulativeNPV()));
        }
        document.add(table);
        //agregar imagen al reporte
        document.add(new Paragraph("The result was: "+result));
        //JOptionPane.showMessageDialog(null, "Document saved");
        document.add(new Paragraph(""));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        Image image2 = Image.getInstance("NPVChart.png");
        //image2.scalePercent(40);//como agregar imagen
        image2.scaleToFit(480,480);
        //document.add(new Paragraph("Image"));
        document.add(image2);

        document.close();
    }

    public static void makePDFPayback(String methodName, String []columns, double interestRate, int periods, double taxRate, double principal, double salvageValue,
                                      double result, ObservableList<PaybackRow> data) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(Session.getInstance().getProjectID()+"_"+methodName+".pdf"));//como quieres que se llame tu archivo
        document.open();//a partir de aquí se va llenando el pdf, recuerda poner el .close
        Image image = Image.getInstance("resources\\images\\logoTecLowRes.png");
        image.scalePercent(40);//como agregar imagen

        //document.add(new Paragraph("Image"));
        document.add(image);
        document.add(new Paragraph("Project Name: Evaluation Tool"  ));
        document.add(new Paragraph("Project Number:" +Session.getInstance().getProjectID()));
        document.add(new Paragraph("Evaluator name: "+Session.getInstance().getEvaluatorName()));
        document.add(new Paragraph("Date: " + new Date().toString()));
        document.add(new Paragraph(methodName, FontFactory.getFont(FontFactory.TIMES_BOLD, 18)));//puedes agregar texto estático y ponerle font y tamaño
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph("Interest rate:"+interestRate));
        document.add(new Paragraph("Periods:"+periods));
        //document.add(new Paragraph("Tax Rate (if applicable):"+taxRate));
        document.add(new Paragraph("Principal:"+principal));
        //document.add(new Paragraph("Salvage Value (if applicable):"+salvageValue));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        PdfPTable table = new PdfPTable(columns.length);
        PdfPCell cell = new PdfPCell(new Paragraph("Detailed calculated table"));
        cell.setColspan(columns.length);//cuantas columnas en tu table
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.BLUE);
        table.addCell(cell);
        for (int i = 0; i < columns.length; i++) {
            table.addCell(columns[i]);
        }
        /*table.addCell("Period");//titulos de las columnas
        table.addCell("Outflow");
        table.addCell("Inflow");
        table.addCell("Net Cash Flow");
        table.addCell("Cummulative cash flow");
        */
        for (int i = 0; i < data.size(); i++) {
            PaybackRow temp = data.get(i);
            table.addCell(String.valueOf(temp.getPeriod()));
            table.addCell(String.valueOf(temp.getOutflow()));
            table.addCell(String.valueOf(temp.getInflow()));
            table.addCell(String.valueOf(temp.getNetCashFlow()));
            table.addCell(String.valueOf(temp.getCumulativeCashFlow()));
        }
        document.add(table);
        //agregar imagen al reporte
        document.add(new Paragraph("The result was: "+result));
        //JOptionPane.showMessageDialog(null, "Document saved");
        document.add(new Paragraph(""));
        document.add(new Paragraph("------------------------------------------------------------"));
        document.add(new Paragraph(""));
        Image image2 = Image.getInstance("PaybackChart.png");
        //image2.scalePercent(40);//como agregar imagen
        image2.scaleToFit(480,480);
        //document.add(new Paragraph("Image"));
        document.add(image2);

        document.close();

    }


}
