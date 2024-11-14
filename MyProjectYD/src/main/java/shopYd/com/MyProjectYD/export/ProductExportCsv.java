package shopYd.com.MyProjectYD.export;

import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import shopYd.com.MyProjectYD.entity.Product;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductExportCsv {

    public void setResponseHeader(HttpServletResponse response, String contentType,
                                  String extension, String prefix) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormatter.format(new Date());
        String fileName = prefix + timestamp + extension;

        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
    }

    public void export(List<Product> listProducts, HttpServletResponse response) throws IOException {
        setResponseHeader(response, "text/csv", ".csv", "products_");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Product ID", "Name", "Price", "Quantity", "Category", "Enabled"};
        String[] fieldMapping = {"id", "name", "price", "quantity", "category.name", "enabled"};

        csvWriter.writeHeader(csvHeader);

        for (Product product : listProducts) {
            csvWriter.write(product, fieldMapping);
        }

        csvWriter.close();
    }
}
