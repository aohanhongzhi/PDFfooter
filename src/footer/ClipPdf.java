package footer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;



public class ClipPdf {

    public static final String SRC = "3天入门MySQL-01101519.pdf";
    public static final String DEST = "3天入门MySQL.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
      //  file.getParentFile().mkdirs();
        new ClipPdf().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        int n = reader.getNumberOfPages();
        PdfDictionary page;
        PdfArray media;
        for (int p = 1; p <= n; p++) {
            page = reader.getPageN(p);
            media = page.getAsArray(PdfName.CROPBOX);
            if (media == null) {
                media = page.getAsArray(PdfName.MEDIABOX);
            }
            
            float llx = media.getAsNumber(0).floatValue()+300;
       
            System.out.println(media.getAsNumber(0));//原点
            System.out.println(media.getAsNumber(1));//原点左标
            System.out.println(media.getAsNumber(2));//宽
            System.out.println(media.getAsNumber(3));//高
            float lly = media.getAsNumber(1).floatValue()+300;
            float w = media.getAsNumber(0).floatValue() ;
            float h = media.getAsNumber(1).floatValue() ;
            
            System.out.format("%s\t%s\t%s\t%s",llx,lly,w,h);
           
            
            
            String command = String.format(Locale.ROOT,
                    "\nq %.2f %.2f %.2f %.2f re W n\nq\n",
                    0.0, 0.0, 60.0, 60.0);
            stamper.getUnderContent(p).setLiteral(command);
//            stamper.getOverContent(p).setLiteral(command);
           // stamper.getUnderContent(p).setLiteral("\nQ\nQ\n");
            stamper.getOverContent(p).setLiteral("\nQ\nQ\n");
        }
        stamper.close();
        reader.close();
    }
}