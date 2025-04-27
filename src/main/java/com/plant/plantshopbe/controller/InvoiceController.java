package com.plant.plantshopbe.controller;

import com.plant.plantshopbe.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceController {

    InvoiceService invoiceService;

    // API xuất hóa đơn từ orderId
    @GetMapping
    public String export(){
        return "dsdfs";
    }
    @GetMapping("/export-by-order/{orderId}")
    public void exportInvoiceByOrderId(@PathVariable("orderId") String orderId, HttpServletResponse response) throws Exception {
        invoiceService.exportInvoicePdfByOrderId(orderId, response);
    }
}