package ru.morozov.utils.components.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * Created by km on 17.09.2015.
 */
public class XlsFile implements InitializingBean {

    private String fileName, root;
    private Workbook workbook;
    private boolean isValidConfig = false;

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getRoot() {return root;}
    public void setRoot(String root) {this.root = root;}

    public boolean isValidConfig() {return isValidConfig;}

    public Workbook getWorkbook() {return workbook;}

    @Override
    public void afterPropertiesSet() throws Exception {
        if (fileName == null || fileName.isEmpty())
            return;

        try {
            workbook = new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream(root + fileName));
        }
        catch (OfficeXmlFileException ox) {
            try {
                workbook = new XSSFWorkbook(getClass().getClassLoader().getResourceAsStream(root + fileName));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        isValidConfig = true;
    }
}
