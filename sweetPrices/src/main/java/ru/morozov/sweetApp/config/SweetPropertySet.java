package ru.morozov.sweetApp.config;

import org.springframework.beans.factory.InitializingBean;
import ru.morozov.sweetApp.config.base.IPriceProducer;
import ru.morozov.utils.components.xls.XlsFile;

import java.util.List;

public class SweetPropertySet implements InitializingBean {
	
	private List<SweetProperty> properties;
    private XlsFile propertiesHolder;
	
	public List<SweetProperty> getProperties() {return properties;}
	public void setProperties(List<SweetProperty> properties) {this.properties = properties;}

    public XlsFile getPropertiesHolder() {return propertiesHolder;}
    public void setPropertiesHolder(XlsFile propertiesHolder) {this.propertiesHolder = propertiesHolder;}

    public SweetProperty getProperty(int index) {
		return index >= 0 && index < properties.size() ? properties.get(index) : null;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        for (SweetProperty property : properties) {
            property.setHolder(this);
            if (property instanceof IPriceProducer)
                ((IPriceProducer) property).refresh();
        }
    }
}
