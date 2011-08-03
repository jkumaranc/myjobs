package org.jk.myjobs.validators;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.xml.bind.ValidationException;

import org.jk.myjobs.utils.FacesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.jk.myjobs.utils.FacesUtils.buildErrorFacesMessage;


/**
 * Validate two fields are equal
 *
 * @author pmuir
 * @author Daniel Roth
 *         <p/>
 *         Updated from SEAM, so referred them!
 */
@FacesValidator(value = "org.jboss.seam.ui.validator.Equality")
public class EqualityValidator implements Validator, PartialStateHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(EqualityValidator.class);

    private enum ValidOperation {
        EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL;
    }

    private String forId;
    private String message;
    private String messageId;
    private ValidOperation operator = ValidOperation.EQUAL; // Default

    public EqualityValidator() {
        this.message = "Value does not equal that in '#0'";
    }

    public EqualityValidator(String forId) {
        this();
        setFor(forId);
    }

    public EqualityValidator(String forId, String message, String messageId, String operator) {
        this(forId);
        if (message != null) {
            setMessage(message);
        }
        if (messageId != null) {
            setMessageId(messageId);
        }
        if (operator != null && !"".equals(operator)) {
            if (ValidOperation.valueOf(operator.toUpperCase()) != null)
                setOperator(ValidOperation.valueOf(operator.toUpperCase()));
            else
                throw new IllegalStateException("Illegal operator. " + "Supported are: " + validOperatorsAsString());
        }

    }

    private String validOperatorsAsString() {
        StringBuffer buff = new StringBuffer();
        for (ValidOperation op : ValidOperation.values()) {
            buff.append(op.name()).append(" ");
        }
        return buff.toString();
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String forId = getFor();
        String forIdWithForm = "form:" + forId;
        if (forId == null) {
            throw new FacesException("Must specify a component to validate equality against");
        }
        UIComponent otherComponent = component.findComponent(forId);
        if (otherComponent == null) {
            otherComponent = component.findComponent(forIdWithForm);
        }
        Object other = new OtherComponent(context, otherComponent).getValue();
        if (value == null && other == null) {
            // Thats fine
        } else if (value != null) {
            switch (operator) {
                case EQUAL:
                    if (!value.equals(other)) {
                        throw new ValidatorException(buildErrorFacesMessage(context, message));
                    }
                    break;
                case NOT_EQUAL:
                    if (value.equals(other)) {
                        throw new ValidatorException(buildErrorFacesMessage(context, message));
                    }
                    break;
                case GREATER:
                    if (!(compare(value, other) > 0)) {
                        throw new ValidatorException(buildErrorFacesMessage(context, message));
                    }
                    break;
                case GREATER_OR_EQUAL:
                    if (!(compare(value, other) >= 0)) {
                        throw new ValidatorException(buildErrorFacesMessage(context, message));
                    }
                    break;
                case LESS:
                    if (!(compare(value, other) < 0)) {
                        throw new ValidatorException(buildErrorFacesMessage(context, message));
                    }
                    break;
                case LESS_OR_EQUAL:
                    if (!(compare(value, other) <= 0)) {
                        throw new ValidatorException(buildErrorFacesMessage(context, message));
                    }
                    break;
            }
        }
    }

    private int compare(Object value, Object other) throws IllegalArgumentException {
        try {
            Comparable c1 = (Comparable) value;
            return c1.compareTo(other);
        } catch (Exception e) {
            throw new IllegalArgumentException("Values are not comparable", e);
        }

    }

    public String getFor() {
        return forId;
    }

    public void setFor(String forId) {
        this.forId = forId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public boolean isTransient() {
        return false;
    }

    //////////

    // RESTORE & SAVE STATE
    public Object saveState(FacesContext context) {
        if (!initialStateMarked()) {
            Object values[] = new Object[4];
            values[0] = forId;
            values[1] = message;
            values[2] = messageId;
            values[3] = operator.toString();
            return values;
        }
        return null;
    }

    public void restoreState(FacesContext context, Object state) {
        if (state != null) {
            Object fields[] = (Object[]) state;
            forId = (String) fields[0];
            message = (String) fields[1];
            messageId = (String) fields[2];
            operator = ValidOperation.valueOf((String) fields[3]);
        }
    }

    // MISC
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegexValidator)) return false;

        final EqualityValidator equalityValidator = (EqualityValidator) o;

        if (forId != null ? !forId.equals(equalityValidator.forId) : equalityValidator.forId != null) return false;
        if (message != null ? !message.equals(equalityValidator.message) : equalityValidator.message != null)
            return false;

        return true;
    }

    private boolean _initialStateMarked = false;

    public void clearInitialState() {
        _initialStateMarked = false;
    }

    public boolean initialStateMarked() {
        return _initialStateMarked;
    }

    public void markInitialState() {
        _initialStateMarked = true;
    }


    public void setTransient(boolean newTransientValue) {
        // No-op
    }

    /**
     * Simple data structure to hold info on the "other" component
     *
     * @author pmuir
     */
    private class OtherComponent {

        private FacesContext context;
        private UIComponent component;
        private EditableValueHolder editableValueHolder;

        private Renderer renderer;
        private Converter converter;

        public OtherComponent(FacesContext facesContext, UIComponent component) {
            this.component = component;
            this.context = facesContext;
            if (!(component instanceof EditableValueHolder)) {
                throw new IllegalStateException("forId must reference an EditableValueHolder (\"input\") component");
            }
            editableValueHolder = (EditableValueHolder) component;
            initRenderer();
            initConverter();
        }

        private void initRenderer() {
            if (renderer == null) {
                String rendererType = component.getRendererType();
                if (rendererType != null) {
                    renderer = context.getRenderKit().getRenderer(component.getFamily(), rendererType);
                    if (null == renderer) {
                        LOGGER.trace("Can't get Renderer for type " + rendererType);
                    }
                } else {
                    if (LOGGER.isTraceEnabled()) {
                        String id = component.getId();
                        id = (null != id) ? id : component.getClass().getName();
                        LOGGER.trace("No renderer-type for component " + id);
                    }
                }
            }
        }

        private void initConverter() {
            converter = editableValueHolder.getConverter();
            if (converter != null) {
                return;
            }

            ValueExpression valueExpression = component.getValueExpression("value");
            if (valueExpression == null) {
                return;
            }

            Class converterType;
            try {
                converterType = valueExpression.getType(context.getELContext());
            } catch (ELException e) {
                throw new FacesException(e);
            }

            // if converterType is null, String, or Object, assume
            // no conversion is needed
            if (converterType == null || converterType == String.class || converterType == Object.class) {
                return;
            }

            // if getType returns a type for which we support a default
            // conversion, acquire an appropriate converter instance.
            try {
                Application application = context.getApplication();
                converter = application.createConverter(converterType);
            } catch (Exception e) {
                throw new FacesException(e);
            }
        }

        private Object getConvertedValue(Object newSubmittedValue) throws ConverterException {

            Object newValue;

            if (renderer != null) {
                newValue = renderer.getConvertedValue(context, component, newSubmittedValue);
            } else if (newSubmittedValue instanceof String) {
                // If there's no Renderer, and we've got a String, run it
                // through
                // the Converter (if any)
                if (converter != null) {
                    newValue = converter.getAsObject(context, component, (String) newSubmittedValue);
                } else {
                    newValue = newSubmittedValue;
                }
            } else {
                newValue = newSubmittedValue;
            }
            return newValue;
        }

        public Object getValue() {
            /**
             * If conversion already is done, return value
             */
            if (editableValueHolder.isLocalValueSet()) {
                return editableValueHolder.getValue();
            }

            /**
             * Convert submittet value
             */
            Object submittedValue = editableValueHolder.getLocalValue();
            if (submittedValue == null) {
                return null;
            }

            Object newValue = null;

            try {
                newValue = getConvertedValue(submittedValue);
            } catch (ConverterException ce) {
                // Any errors will be attached by JSF
                return null;
            }

            return newValue;
        }

    }

    public ValidOperation getOperator() {
        return operator;
    }

    public void setOperator(ValidOperation operator) {
        this.operator = operator;
    }


}
