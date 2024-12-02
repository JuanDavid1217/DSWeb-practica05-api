/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.reports;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import net.sf.jasperreports.engine.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author David
 */
public class Report {
    
    public static String crateReport(String url, String user, String password, String reportSchema, String finalReportPath, long saleId){ 
        try {
            Connection connection=null;
            try{
                connection = DriverManager.getConnection(
                    url,
                    user,
                    password
                );
            }catch( Exception e ){
                return "";
            }
            
            // Carga el archivo .jasper
            File report = new File(reportSchema);
            
            InputStream reportStream = new BufferedInputStream(new FileInputStream(report.getAbsoluteFile()));
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SALE_ID", (saleId));
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            String path = finalReportPath + saleId +".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, path);
            return path;
        } catch (IOException | JRException e) {
            return null;
        }
    }
    
}
