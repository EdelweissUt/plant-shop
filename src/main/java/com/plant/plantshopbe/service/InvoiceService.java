package com.plant.plantshopbe.service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.plant.plantshopbe.entity.Invoice;
import com.plant.plantshopbe.entity.Order;
import com.plant.plantshopbe.repository.InvoiceRepository;
import com.plant.plantshopbe.repository.OrderRepository;
import com.plant.plantshopbe.util.PDFInvoiceGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceService {
    InvoiceRepository invoiceRepository;
    OrderRepository orderRepository;
    PDFInvoiceGenerator pdfInvoiceGenerator;

    public void exportInvoicePdfByOrderId(String orderId, HttpServletResponse response) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        Invoice invoice = invoiceRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn cho đơn hàng"));

        // Cấu hình HTTP response
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + invoice.getInvoiceNumber() + ".pdf");

        // Gọi class xuất PDF
        pdfInvoiceGenerator.generateInvoicePdf(invoice, response.getOutputStream());
    }
}