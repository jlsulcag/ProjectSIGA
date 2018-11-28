package org.siga.bl.test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.siga.ds.DSConeccion;

/**
 *
 * @author JSULCAG
 */
public class reportTest {

    public static void main(String[] args) {
        try {
            Map<String, Object> parametro = new HashMap<>();
            File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0006-nota-entrada.jasper"));
            DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
            parametro.put("ID_ENTRADA", 1);
            byte[] documento = JasperRunManager.runReportToPdf(file.getPath(), parametro, ds.getConeccion());
            
            String fileType = "inline";
            String reportSetting = fileType + "; filename=Nota_Entrada.pdf";
            
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", reportSetting);
            response.setHeader("Cache-Control", "private");
            response.setContentLength(documento.length);
            
            ServletOutputStream stream = response.getOutputStream();
            stream.write(documento, 0, documento.length);
            stream.flush();
            stream.close();
            
            ds.getConeccion().close();
            
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException ex) {
            Logger.getLogger(reportTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reportTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(reportTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
