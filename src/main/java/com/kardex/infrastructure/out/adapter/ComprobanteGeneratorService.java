package com.kardex.infrastructure.out.adapter;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.kardex.domain.spi.IComprobanteGeneratorPersistencePort;
import com.itextpdf.layout.element.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ComprobanteGeneratorService implements IComprobanteGeneratorPersistencePort {

    @Override
    public void generarComprobante(String nombre, String monto, String idTransaccion, String fecha, String archivoDestino) {
        try{
            File file = new File(archivoDestino);
            file.getParentFile().mkdirs();

            PdfWriter writer = new PdfWriter(archivoDestino);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            InputStream imageStream = getClass().getClassLoader().getResourceAsStream("static/img/Icono-K.png");

            if (imageStream == null) {
                throw new FileNotFoundException("No se encontró la imagen en el classpath");
            }

            ImageData imageData = ImageDataFactory.create(imageStream.readAllBytes());
            Image watermark = new Image(imageData);
            watermark.setFixedPosition(150, 400);
            watermark.setOpacity(0.07f);
            watermark.setMaxHeight(500);
            watermark.setMaxWidth(400);

            document.add(watermark);

            Paragraph titulo = new Paragraph("Factura")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(ColorConstants.CYAN);
            document.add(titulo);

            document.add(new LineSeparator(new SolidLine()));
            document.add(new Paragraph("ID Transacción: " + idTransaccion));
            document.add(new Paragraph("Nombre del comprador: " + nombre));
            document.add(new Paragraph("Monto: $" + monto));
            document.add(new Paragraph("Fecha: " + fecha));
            document.add(new Paragraph("Estado: Completado"));

            document.add(new Paragraph("\n"));

            // Total
            Paragraph total = new Paragraph("Total: $" + monto)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(14)
                    .setBold();
            document.add(total);

            // Mensaje final
            Paragraph gracias = new Paragraph("¡Gracias por su compra! 🌟")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30);
            document.add(gracias);

            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
