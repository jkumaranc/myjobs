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
package org.jk.myjobs.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 * User: JK
 * Date: 27/07/11
 * email: jkumaranc@yahoo.com
 */
public abstract class FacesUtils {
    public static String getBundleKey(String key) {
        String messageBundle = getCurrentInstance().getApplication().getMessageBundle();
        Locale locale = getCurrentInstance().getViewRoot().getLocale();
        return getBundle(messageBundle, locale).getString(key);
    }

    public static void handleError(FacesContext context, String message) {
        handleError(context, message, null);
    }

    public static void handleError(String message) {
        handleError(getCurrentInstance(), message, null);
    }

    public static void handleError(FacesContext context, String message, String id) {
        String messageBundle = context.getApplication().getMessageBundle();
        Locale locale = context.getViewRoot().getLocale();
        final ResourceBundle bundle = getBundle(messageBundle, locale);
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
        context.addMessage(id, facesMessage);
    }
}
