package ru.morozov.utils.components;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

    private Integer minValue;
    private Integer maxValue;

    public NumberTextField() {this(0, Integer.MAX_VALUE, 1);}

    public NumberTextField(Integer minValue, Integer maxValue, Integer defaultValue) {
        super();
        this.minValue = minValue;
        this.maxValue = maxValue;

        if (defaultValue != null)
            setText(String.valueOf(defaultValue));
    }

	@Override
	public void replaceText(int start, int end, String text) {
        super.replaceText(start, end, text);
		if (!validate(text)) {
			undo();
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (validate(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean validate(String text) {
        if (!("".equals(text) || text.matches("[0-9]")))
                return false;

        String textValue = getText();

        if (textValue == null || textValue.length() == 0)
            return true;

        int value = Integer.valueOf(textValue);

        return ((minValue != null && value >= minValue) || minValue == null) &&
               ((maxValue != null && value <= maxValue) || maxValue == null);
    }
	
	public Double getDoubleValue() {return getText() == null || getText().isEmpty() ? 0 : Double.valueOf(getText());}
}
