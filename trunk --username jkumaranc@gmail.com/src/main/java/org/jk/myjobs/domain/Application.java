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
package org.jk.myjobs.domain;

import org.metawidget.inspector.annotation.*;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: JK
 * Date: 27/07/11
 * email: jkumaranc@yahoo.com
 * Application is a domain object
 */

public class Application implements Serializable {
    private String id = System.currentTimeMillis() + "_" + Math.ceil(Math.random() * 1000);
    private String companyName;
    private String position;
    private String contactName;
    private String contactNumber;
    private String resume = "1.0";
    private String coverLetter = "1.0";
    private Date date = new Date();
    private List<Update> updates;

    public Application() {
        updates = new ArrayList<Update>();
    }

    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getResume() {
        return resume;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public Date getDate() {
        return date;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (!companyName.equals(that.companyName)) return false;
        if (!position.equals(that.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyName.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Application{" +
                "companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", resume='" + resume + '\'' +
                ", coverLetter='" + coverLetter + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", updates=" + updates +
                '}';
    }

    public void addUpdate(Update update) {
        updates.add(update);
    }

}
