package com.payment.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.payment.dto.ProductRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void generateInvoiceAndSendEmail(ProductRequest product) {
        String paymentId = UUID.randomUUID().toString();
        String directoryPath = "invoices";
        String pdfPath = directoryPath + "/invoice_" + paymentId + ".pdf";

        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            document.add(new Paragraph("Invoice"));
            document.add(new Paragraph("Payment ID: " + paymentId));
            document.add(new Paragraph("Product: " + product.getName()));
            document.add(new Paragraph("Quantity: " + product.getQuantity()));
            document.add(new Paragraph("Amount Paid: " + product.getAmount() + " " + product.getCurrency()));
            document.close();

            sendEmailWithInvoice(product.getEmail(), pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailWithInvoice(String email, String pdfPath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Payment Invoice");
            helper.setText("Thank you for your payment. Attached is your invoice.");
            helper.addAttachment("Invoice.pdf", new File(pdfPath));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
