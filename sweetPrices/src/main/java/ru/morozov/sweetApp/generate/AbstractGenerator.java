package ru.morozov.sweetApp.generate;

import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.templates.SweetTemplate;
import ru.morozov.utils.components.xls.XlsFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by km on 21.09.2015.
 */
public abstract class AbstractGenerator implements IGenerator {

    protected final static SimpleDateFormat subDirDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    protected SystemConfigs systemConfig;
    protected SweetTemplate template;
    protected ParametersHolder parametersHolder;
    protected Double amount;

    protected static final Double DEFAULT_AMOUNT = 10000d;

    public SystemConfigs getSystemConfig() {return systemConfig;}

    protected boolean writeOut(PropertyValueSet params, XlsFile generatedTemplate) {
        String outputSubDirName = subDirDateFormat.format(new Date());
        Path outputPath = systemConfig.createSubdirectory(outputSubDirName);

        if (outputPath != null) {
            Path newPath = Paths.get(outputPath.toString(), params.getShortDesc() + ".xls");

            try {
                Path newFilePath = Files.createFile(newPath);
                FileOutputStream out = new FileOutputStream(newFilePath.toString());
                generatedTemplate.getWorkbook().write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            }
        }

        return true;
    }
}
