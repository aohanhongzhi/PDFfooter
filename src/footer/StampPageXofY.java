package footer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StampPageXofY {

	public static final String SRC = "机器学习入门ppt.pdf";
	public static final String DEST = "机器学习入门ppt2.pdf";

	public static void main(String[] args) throws IOException, DocumentException {
		File file = new File(DEST);
		// file.getParentFile().mkdirs();
		new StampPageXofY().manipulatePdf(SRC, DEST);
	}

	public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(src);
		int n = reader.getNumberOfPages();
		// reader.getPageN(1);
		Rectangle pageSize = reader.getPageSize(1);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		PdfContentByte pagecontent;
		for (int i = 0; i < n;) {
			pagecontent = stamper.getOverContent(++i);

			// ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT, new
			// Phrase(String.format("page %s of %s", i, n)), 310, 50, 0);
			ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT, new Phrase(String.format("%s/%s", i, n)), pageSize.getWidth()/2,
					50, 0);

		}
		stamper.close();
		reader.close();
	}
}