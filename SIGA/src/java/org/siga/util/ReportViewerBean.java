package org.siga.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperRunManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;

public class ReportViewerBean implements Serializable {

    public String file_path = "/resources/t/";
    public String rsc_report_path = "/org/edessco/sva/rpt/";
    private String report = "";
    private String existe;

    @ManagedProperty(value = "#{sessionFactory}")
    private SessionFactory sessionFactory;

    public void generarReporte(String dir, String rpt, String... srptn) {        
        generarReporte(dir, rpt,new String[]{}, new Object[]{}, srptn);
    }

    public void generarReporte(String dir, String rpt, String[] k, Object[] v, String... srptn) {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        if (k != null && v != null && k.length == v.length) {
            String real_path = "";
            for (String srn : srptn) {
                real_path = extractResource(dir, srn + ".jasper");
            }
            Map p = new HashMap();
            p.put("p_real_path", real_path);
            for (int i = 0; i < v.length; i++) {
                p.put(k[i], v[i]);
            }
            File f = reportMK(rpt, p);
            setExiste(f != null ? "true" : "false");
        } else {
            setExiste("false");
        }
        //</editor-fold>
    }

    public String pathFile() {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        File f = new File(getRealPath(report));
        if (f.exists()) {
            return "?file=.." + file_path + report;
        } else {
            return "";
        }
        //</editor-fold>
    }

    private File reportMK(String rpt, String[] k, Object[] v) {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        Map p = new HashMap();
        if (k != null && v != null && k.length == v.length) {
            for (int i = 0; i < v.length; i++) {
                p.put(k[i], v[i]);
            }
            return reportMK(rpt, p);
        } else {
            return null;
        }
        //</editor-fold>
    }

    public File reportMK(String rpt, Map p) {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        try {
            Session s = getSession();
            byte[] d = JasperRunManager.runReportToPdf(getClass().getResourceAsStream(rsc_report_path + rpt + ".jasper"), p, ((SessionImpl) s).connection());

            if (d != null) {
                File f = new File(getRealPath());
                if (!f.exists()) {
                    f.mkdir();
                }

                report = rpt + "_" + getSessionID() + ".pdf";
                f = new File(getRealPath(report));

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(d);
                fos.flush();
                fos.close();

                return f.exists() ? f : null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //</editor-fold>
    }

    public String extractResource(String dir, String rsc) {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String realPath = servletContext.getRealPath("/");
            File f = new File(realPath + File.separator + dir);
            if (!f.exists()) {
                f.mkdir();
            }
            String path = f.getAbsolutePath() + File.separator;
            InputStream fis = getClass().getResourceAsStream(rsc_report_path + rsc);
            f = new File(path + File.separator + rsc);
            FileOutputStream fos = new FileOutputStream(f);
            int sz;
            while ((sz = fis.available()) > 0) {
                byte[] data = new byte[sz];
                fis.read(data);
                fos.write(data);
            }
            fis.close();
            fos.close();

            return path;
        } catch (Exception e) {
            return "";
        }
        //</editor-fold>
    }

    public String getRealPath(String... fn) {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">      
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext
                = (ServletContext) facesContext.getExternalContext().getContext();
        String realPath = servletContext.getRealPath("/");
        File f = new File(realPath + file_path + ((fn.length > 0) ? fn[0] : ""));
        return f.getAbsolutePath();
        //</editor-fold>
    }

    public String getSessionID() {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session
                = (HttpSession) facesContext.getExternalContext().getSession(true);
        return session.getId();
        //</editor-fold>
    }

    @Deprecated
    public Session getSession() {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        try {
            return getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            return getSessionFactory().openSession();
        }
        //</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="GET'S y SET'S">
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String getExiste() {
        return existe;
    }

    public void setExiste(String existe) {
        this.existe = existe;
    }
    //</editor-fold>
}
