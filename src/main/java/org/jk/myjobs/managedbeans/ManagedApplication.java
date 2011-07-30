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
package org.jk.myjobs.managedbeans;

import org.jk.myjobs.domain.Application;
import org.jk.myjobs.domain.Update;
import org.metawidget.inspector.annotation.*;

import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;

/**
 * User: JK
 * Date: 30/07/11
 * email: jkumaranc@yahoo.com
 *
 * Managed bean for Application .. Domain is not confused with UI portions, yet delegated methods to decorate!
 */
@ManagedBean(name = "mApplication")
public class ManagedApplication extends Application{

    @UiLabel(value = "Company Name")
    @UiRequired
    public String getCompanyName() {
        return super.getCompanyName();
    }

    @UiLabel(value = "Position")
    @UiComesAfter(value = "companyName")
    @UiRequired
    public String getPosition() {
        return super.getPosition();
    }

    @UiLabel(value = "Contact Name")
    @UiComesAfter(value = "position")
    public String getContactName() {
        return super.getContactName();
    }

    @UiLabel(value = "Contact Number")
    @UiComesAfter(value = "contactName")
    public String getContactNumber() {
        return super.getContactNumber();
    }

    @UiLabel(value = "Resume Version")
    @UiComesAfter(value = "contactNumber")
    public String getResume() {
        return super.getResume();
    }

    @UiLabel(value = "Cover Letter Version")
    @UiComesAfter(value = "resume")
    public String getCoverLetter() {
        return super.getCoverLetter();
    }

    @UiLabel(value = "Date Applied")
    @UiComesAfter(value = "coverLetter")
    @UiAttribute(name = "type", value = "java.util.Date")
    public Date getDate() {
        return super.getDate();
    }

    @UiHidden
    public List<Update> getUpdates() {
        return super.getUpdates();
    }

    @UiHidden
    public String getId() {
        return super.getId();
    }

}
