package ru.morozov.utils.components.xls;

import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.formula.*;
import org.apache.poi.ss.formula.eval.AreaEvalBase;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.ptg.Area3DPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.utils.ParserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by km on 17.09.2015.
 */
public class XlsFile implements InitializingBean {

    private String fileName, root;
    private Workbook workbook;
    private boolean isValidConfig = false;
    private int defaultSheet = 0;
    private List<? extends DataValidation> dvRecords;
    private EvaluationWorkbook evalWorkbook;

    public void setFileName(String fileName) {this.fileName = fileName;}
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
            evalWorkbook = HSSFEvaluationWorkbook.create((HSSFWorkbook)workbook);
        }
        catch (OfficeXmlFileException ox) {
            try {
                workbook = new XSSFWorkbook(getClass().getClassLoader().getResourceAsStream(root + fileName));
                evalWorkbook = XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);
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

        try {
            return workbook.getSheetAt(sheet).getRow(cell.getRow()).getCell(cell.getCol());
        }
        catch(Exception ex) {
            return null;
        }
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

        if (cell == null)
            return null;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                Double _value = cell.getNumericCellValue();
                return _value == _value.longValue() ? String.format("%d", _value.longValue()) : String.format("%s", _value);
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                break;
        }

        return "";
    }

    private List<? extends DataValidation> getValidations() {
        if (dvRecords == null) {
            Sheet sheet = workbook.getSheetAt(defaultSheet);

            dvRecords = sheet.getDataValidations();
        }

        return dvRecords;
    }

    public List<String> getCellConstraints(Cell cell) {
        for (DataValidation _validation : getValidations())
            for (CellRangeAddress _region : _validation.getRegions().getCellRangeAddresses())
                if (_region.isInRange(cell.getRowIndex(), cell.getColumnIndex())) {
                    if (_validation.getValidationConstraint().getExplicitListValues() != null)
                        return Arrays.asList(_validation.getValidationConstraint().getExplicitListValues());
                    else if (_validation.getValidationConstraint().getFormula1() != null) {
                        String formula = _validation.getValidationConstraint().getFormula1().split("\"")[1];
                        String[] _names = formula.split("!");
                        String _sheetName = _names[0];
                        String _arrName = _names[1];
                        int sheetIndex = evalWorkbook.getSheetIndex(_sheetName);

                        EvaluationName nm = evalWorkbook.getName(_arrName, sheetIndex);
                        if(nm == null || !nm.isRange()){
                            throw new RuntimeException("Specified name '" + _arrName + "' is not a range as expected.");
                        }

                        OperationEvaluationContext ec = new OperationEvaluationContext(new WorkbookEvaluator(evalWorkbook, null, null), evalWorkbook,
                                defaultSheet, cell.getRowIndex(), cell.getColumnIndex(), null);

                        Ptg[] ptgs = nm.getNameDefinition();
                        if (ptgs.length == 1 && ptgs[0] instanceof Area3DPtg) {
                            ValueEval result = ec.getArea3DEval((Area3DPtg)ptgs[0]);

                            if (result instanceof AreaEvalBase) {
                                AreaEvalBase _area = (AreaEvalBase) result;
                                ArrayList<String> resultStrings = new ArrayList<>();
                                for (int i = _area.getFirstRow(); i <= _area.getLastRow(); i++) {
                                    String value = getStringValue(new CellCoord(_area.getFirstSheetIndex(), _area.getFirstColumn(), i));
                                    if (value != null && value.length() > 0)
                                        resultStrings.add(value);
                                }

                                return resultStrings;
                            }

                            return null;
                        }
                   }
                }

        return null;
    }
}
