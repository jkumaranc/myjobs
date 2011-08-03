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
package org.jk.myjobs.processors;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.jk.myjobs.validators.EqualityValidator;
import org.jk.myjobs.validators.RegexValidator;
import org.metawidget.faces.component.UIMetawidget;
import org.metawidget.widgetprocessor.iface.WidgetProcessor;

import static org.jk.myjobs.utils.Constants.*;

/**
 * User: JK
 * Date: 29/07/11
 * email: jkumaranc@yahoo.com
 * This is the processor adding the validator or respective ops on the inspection results specific to MyJobs specific
 */
public class MyJobsValidationProcessor implements WidgetProcessor<UIComponent, UIMetawidget> {

    public UIComponent processWidget(UIComponent component, String elementName, Map<String, String> attributes, UIMetawidget metawidget) {
        // Only validate EditableValueHolders (ie. no labels, no Stubs)
        if (!(component instanceof EditableValueHolder)) {
            return component;
        }

        EditableValueHolder editableValueHolder = (EditableValueHolder) component;

        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();

        // Regex
        String regex = attributes.get(PATTERN);
        String message = attributes.get(REGEX_MESSAGE);
        if (regex != null) {
            RegexValidator validator = (RegexValidator) application.createValidator(REGEX_VALIDATOR);
            validator.setMessage(message);
            validator.setRegex(regex);
            editableValueHolder.addValidator(validator);
        }
        String field = attributes.get(FIELD);
        if (field != null) {
            if (!hasExistingValidator(editableValueHolder, EqualityValidator.class)) {
                EqualityValidator equalsValidator = (EqualityValidator) application.createValidator(EQUALITY_VALIDATOR);
                equalsValidator.setFor(field);
                String messageId = attributes.get(EQUALS_MESSAGE);
                equalsValidator.setMessageId(messageId);
                equalsValidator.setMessage(messageId);
                editableValueHolder.addValidator(equalsValidator);
            }
        }

        String id = attributes.get(COMP_ID);
        if (id != null) {
            component.setId(id);
        }
        return component;
    }

    private boolean hasExistingValidator(EditableValueHolder editableValueHolder, Class<? extends Validator> validatorClass) {
        Validator[] validators = editableValueHolder.getValidators();
        if (validators == null) {
            return false;
        }
        for (Validator validator : validators) {
            if (validatorClass.isAssignableFrom(validator.getClass())) {
                return true;
            }
        }
        return false;
    }


}
