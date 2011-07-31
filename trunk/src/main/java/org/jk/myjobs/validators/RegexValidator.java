/**
 * Copyright [2011] [JK]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package org.jk.myjobs.validators;

import org.jk.myjobs.utils.FacesUtils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * User: JK
 * Date: 29/07/11
 * email: jkumaranc@yahoo.com
 * Regex Validation for input fields
 */
@FacesValidator("org.myjobs.validator.Regex")
public class RegexValidator implements Validator, PartialStateHolder {
    private String regex;
    private String message = "Validation Failed";
    private boolean _transient = false;

    public void validate(final FacesContext context, final UIComponent component, final Object value) throws ValidatorException {
        final Pattern mask = Pattern.compile(regex);
        final Matcher matcher = mask.matcher(String.valueOf(value));
        if (!matcher.matches()) {
            FacesUtils.handleError(context, message);
        }
    }

   /* private void handleValidationError(FacesContext context) {
        String messageBundle = context.getApplication().getMessageBundle();
        Locale locale = context.getViewRoot().getLocale();
        final ResourceBundle bundle = ResourceBundle.getBundle(messageBundle, locale);
        final FacesMessage facesMessage = new FacesMessage();
        String dMessage = message;
        try {
            dMessage = bundle.getString(message);
        } catch (MissingResourceException e) {
            //do nothing
        }
        facesMessage.setDetail(dMessage);
        facesMessage.setSummary(dMessage);
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(facesMessage);
    }*/

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isTransient() {
        return _transient;
    }

    public void setTransient(boolean transientValue) {
        _transient = transientValue;
    }

    // RESTORE & SAVE STATE
    public Object saveState(FacesContext context) {
        if (!initialStateMarked()) {
            Object values[] = new Object[2];
            values[0] = regex;
            values[1] = message;
            return values;
        }
        return null;
    }

    public void restoreState(FacesContext context,
                             Object state) {
        if (state != null) {
            Object values[] = (Object[]) state;
            regex = (String) values[0];
            message = (String) values[1];
        }
    }

    // MISC
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegexValidator)) return false;

        final RegexValidator regexValidator = (RegexValidator) o;

        if (regex != null ? !regex.equals(regexValidator.regex) : regexValidator.regex != null) return false;
        if (message != null ? !message.equals(regexValidator.message) : regexValidator.message != null) return false;

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

    private Boolean isDisabled() {
        return null;
    }

    private String getFor() {
        return null;
    }
}