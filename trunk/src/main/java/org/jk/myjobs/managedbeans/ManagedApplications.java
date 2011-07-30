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

import org.jk.myjobs.dao.Repository;
import org.jk.myjobs.domain.Application;
import org.jk.myjobs.domain.Update;
import org.jk.myjobs.domain.User;
import org.metawidget.inspector.annotation.UiHidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.jk.myjobs.utils.FacesUtils.getBundleKey;

/**
 * User: JK
 * Date: 27/07/11
 * email: jkumaranc@yahoo.com
 * <p/>
 * This is the collection managed beans to hold application controller, could have used inside the @ManagedApplication
 * but just separating the concerns!
 */
@ManagedBean(name = "mApplications")
@SessionScoped
public class ManagedApplications {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagedApplications.class);
    private static final String NEW_JOB_UPDATE_MSG = "new_job_update";
    private static final String NEW_JOB_UPDATE_TODO = "new_job_todo";
    @ManagedProperty(value = "#{repository}")
    private Repository repository;
    private ListDataModel<ManagedApplication> applicationTable;
    private ListDataModel<Update> updatesTable;
    private ManagedApplication mApplication;
    private Update update;
    private User user;

    public String addApplication() {
        Application application = (Application) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("mApplication");
        application.addUpdate(new Update(getBundleKey(NEW_JOB_UPDATE_MSG), getBundleKey(NEW_JOB_UPDATE_TODO)));
        getUser().getApplications().add(application);
        repository.updateUser(getUser());
        LOGGER.info("Applied a new application for " + application.getCompanyName());
        return "/secured/applications.jsf";
    }

    public String addUpdate() {
        update.setDate(new Date());
        mApplication.addUpdate(update);
        repository.updateUser(getUser());
        update = null;
        updatesTable = null;
        LOGGER.info("Applied a new update for " + mApplication.getCompanyName());
        return "/secured/edit_application.jsf";
    }

    public String deleteApplication() {
        Application application = applicationTable.getRowData();
        getUser().getApplications().remove(application);
        repository.updateUser(getUser());
        applicationTable = null;
        LOGGER.info("Deleted an application from " + mApplication.getCompanyName());
        return "/secured/applications.jsf";
    }

    public String editApplication() {
        mApplication = applicationTable.getRowData();
        repository.updateUser(getUser());
        applicationTable = null;
        LOGGER.info("Edited an application for "  + mApplication.getCompanyName());
        return "/secured/edit_application.jsf";
    }

    public String updateApplication() {
        repository.updateUser(getUser());
        LOGGER.info("Updated an application " + mApplication.getCompanyName());
        return "/secured/edit_application.jsf";
    }

    @UiHidden
    public Update getUpdate() {
        if (update == null) {
            update = new Update();
        }
        return update;
    }

    @UiHidden
    public ListDataModel<ManagedApplication> getApplicationTable() {
        if (applicationTable == null) {
            List<Application> applications = getUser().getApplications();
            List<ManagedApplication> mApplications = new ArrayList<ManagedApplication>();
            for (Application application : applications) {
                mApplications.add((ManagedApplication) application);
            }
            applicationTable = new ListDataModel<ManagedApplication>(mApplications);
        }
        return applicationTable;
    }

    @UiHidden
    public ListDataModel<Update> getUpdatesTable() {
        if (updatesTable == null && mApplication != null) {
            updatesTable = new ListDataModel<Update>(mApplication.getUpdates());
        }
        return updatesTable;
    }

    @UiHidden
    public User getUser() {
        if (user == null) {
            String userName = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            user = repository.findUser(userName);
        }
        return user;
    }

    @UiHidden
    public Repository getRepository() {
        return repository;
    }

    @UiHidden
    public Application getmApplication() {
        return mApplication;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setApplicationTable(ListDataModel<ManagedApplication> applicationTable) {
        this.applicationTable = applicationTable;
    }

    public void setUpdatesTable(ListDataModel<Update> updatesTable) {
        this.updatesTable = updatesTable;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void setmApplication(ManagedApplication mApplication) {
        this.mApplication = mApplication;
    }
}
