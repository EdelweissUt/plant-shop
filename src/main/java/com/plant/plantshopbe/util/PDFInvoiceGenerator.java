package com.plant.plantshopbe.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.plant.plantshopbe.entity.Invoice;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class PDFInvoiceGenerator {

    public void generateInvoicePdf(Invoice invoice, OutputStream out) throws Exception {
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        var font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // Tiêu đề trung tâm
        Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN")
                .setFont(font)
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(title);

        // Thông tin hóa đơn
        Table invoiceInfo = new Table(2).useAllAvailableWidth();
        invoiceInfo.addCell(new Cell().add(new Paragraph("Mã hóa đơn:").setBold()).setBorder(Border.NO_BORDER));
        invoiceInfo.addCell(new Cell().add(new Paragraph(invoice.getInvoiceNumber())).setBorder(Border.NO_BORDER));

        invoiceInfo.addCell(new Cell().add(new Paragraph("Ngày phát hành:").setBold()).setBorder(Border.NO_BORDER));
        invoiceInfo.addCell(new Cell().add(new Paragraph(invoice.getIssuedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))).setBorder(Border.NO_BORDER));

        document.add(invoiceInfo);
        document.add(new Paragraph("\n"));

        // Thông tin công ty
        Paragraph companyHeader = new Paragraph("THÔNG TIN CÔNG TY")
                .setFont(font)
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT)
                .setUnderline();
        document.add(companyHeader);

        Table companyInfo = new Table(2).useAllAvailableWidth();
        companyInfo.addCell(new Cell().add(new Paragraph("Tên công ty:").setBold()).setBorder(Border.NO_BORDER));
        companyInfo.addCell(new Cell().add(new Paragraph(invoice.getCompanyName())).setBorder(Border.NO_BORDER));

        companyInfo.addCell(new Cell().add(new Paragraph("Mã số thuế:").setBold()).setBorder(Border.NO_BORDER));
        companyInfo.addCell(new Cell().add(new Paragraph(invoice.getTaxCode())).setBorder(Border.NO_BORDER));

        companyInfo.addCell(new Cell().add(new Paragraph("Địa chỉ:").setBold()).setBorder(Border.NO_BORDER));
        companyInfo.addCell(new Cell().add(new Paragraph(invoice.getCompanyAddress())).setBorder(Border.NO_BORDER));

        document.add(companyInfo);
        document.add(new Paragraph("\n"));

        // Tổng tiền
        Paragraph total = new Paragraph("Tổng tiền: " + String.format("%,.0f VNĐ", invoice.getOrder().getTotalPrice()))
                .setFont(font)
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(20);
        document.add(total);

        // Ký tên
        Paragraph sign = new Paragraph("\nNgười lập hóa đơn\n\n(Ký tên)")
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(40);
        document.add(sign);

        document.close();
    }

}