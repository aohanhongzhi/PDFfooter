package footer;
 
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 

 

public class ReplaceStream {
    public static final String SRC = "3天入门MySQL-01101519.pdf";
    public static final String DEST = "3天入门MySQL.pdf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
      //  file.getParentFile().mkdirs();
        new ReplaceStream().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfDictionary dict = reader.getPageN(2);
        PdfObject object = dict.getDirectObject(PdfName.TFOOT);
        if (object instanceof PRStream) {
            PRStream stream = (PRStream)object;
            byte[] data = PdfReader.getStreamBytes(stream);
            stream.setData(new String(data).replace("     本文档使用", "HELLO WORLD").getBytes());
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }
}