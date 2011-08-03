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
package org.jk.myjobs.utils.inspectors;

import org.jk.myjobs.utils.Constants;
import org.jk.myjobs.utils.inspectors.annotations.EqualField;
import org.jk.myjobs.utils.inspectors.annotations.CompId;
import org.jk.myjobs.utils.inspectors.annotations.Regex;
import org.metawidget.inspector.impl.BaseObjectInspector;
import org.metawidget.inspector.impl.propertystyle.Property;
import org.metawidget.util.CollectionUtils;

import java.util.Map;

import static org.jk.myjobs.utils.Constants.*;
import static org.jk.myjobs.utils.Constants.COMP_ID;


/**
 * User: JK
 * Date: 29/07/11
 * email: jkumaranc@yahoo.com
 * This is the inspector for the validators specified from MyJobs
 */
public class MyJobsValidatorInspector extends BaseObjectInspector {

    protected Map<String, String> inspectProperty(Property property) throws Exception {
        Map<String, String> attributes = CollectionUtils.newHashMap();

        // Regex
        // This is myjobs annotation so dont worry about the class pat ;)

        Regex regex = property.getAnnotation(Regex.class);
        if (regex != null) {
            String pattern = regex.pattern();
            String message = regex.message();
            attributes.put(PATTERN, pattern);
            attributes.put(REGEX_MESSAGE, message);
        }

        EqualField equals = property.getAnnotation(EqualField.class);
        if(equals != null){
            String field = equals.field();
            String message = equals.message();
            attributes.put(FIELD, field);
            attributes.put(EQUALS_MESSAGE, message);
        }

        CompId compId = property.getAnnotation(CompId.class);
        if(compId != null){
            String compIdValue = compId.name();
                attributes.put(COMP_ID, compIdValue);
        }
        return attributes;
    }

}

