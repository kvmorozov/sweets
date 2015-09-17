package ru.morozov.utils.components.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.utils.ParserUtils;

import java.io.IOException;

/**
 * Created by km on 17.09.2015.
 */
public class XlsFile implements InitializingBean {

    private String fileName, root;
    private Workbook workbook;
    private boolean isValidConfig = false;
    private int defaultSheet = 0;

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getRoot() {return root;}
    public void setRoot(String root) {this.root = root;}

    public boolean isValidConfig() {return isValidConfig;}

    public Workbook getWorkbook() {return workbook;}

    public int getDefaultSheet() {return defaultSheet;}
    public void setDefaultSheet(int defaultSheet) {this.defaultSheet = defaultSheet;}

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

    private int getSheet(CellCoord cell) {return cell.getSheet() >= 0 ? cell.getSheet() : defaultSheet;}

    public Cell getCell(CellCoord cell) {
        int sheet = getSheet(cell);

        return workbook.getSheetAt(sheet).getRow(cell.getRow()).getCell(cell.getCol());
    }

    public Double getDoubleValue(CellCoord cellCoord) {
        int sheet = getSheet(cellCoord);

        if (sheet < 0 || cellCoord.getCol() < 0 || cellCoord.getRow() < 0)
            return null;

        Double result = 0d;
        Cell cell = getCell(cellCoord);

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                return ParserUtils.getDoubleResult(cell.getStringCellValue());
            default:
                break;
        }

        return result;
    }

    public String getStringValue(CellCoord cellCoord) {
        int sheet = getSheet(cellCoord);

        if (sheet < 0 || cellCoord.getCol() < 0 || cellCoord.getRow() < 0)
            return null;

        Cell cell = getCell(cellCoord);

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                break;
        }

        return "";
    }
}
