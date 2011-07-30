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
import org.jk.myjobs.domain.User;
import org.jk.myjobs.inspectors.annotations.CompId;
import org.jk.myjobs.inspectors.annotations.EqualField;
import org.jk.myjobs.inspectors.annotations.Regex;
import org.metawidget.inspector.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import java.util.Collection;
import java.util.List;

import static org.metawidget.inspector.InspectionResultConstants.MINIMUM_LENGTH;

/**
 * User: JK
 * Date: 28/07/11
 * email: jkumaranc@yahoo.com
 * User Delegated managed class
 * Used for creation too as its more of an entity operation
 */
@ManagedBean(name = "mUser")
@RequestScoped
public class ManagedUser extends User {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagedUser.class);

    @ManagedProperty(value = "#{repository}")
    private Repository repository;
    private transient String repeatPassword;

    public String createUser() {
        repository.createUser(this);
        LOGGER.info("New is created : " + getUserName());
        return "/public/login.jsf";
    }

    @UiRequired
    @UiLabel(value = "Full Name")
    public String getFullName() {
        return super.getFullName();
    }

    @UiRequired
    @UiLabel(value = "Preferred User Name")
    @UiComesAfter(value = "fullName")
    public String getUserName() {
        return super.getUserName();
    }

    @UiMasked
    @UiRequired
    @UiComesAfter(value = "userName")
    @UiAttribute(name = MINIMUM_LENGTH, value = "5")
    @CompId(name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    @UiMasked
    @UiRequired
    @UiComesAfter(value = "password")
    @UiAttribute(name = MINIMUM_LENGTH, value = "5")
    @EqualField(field = "password", message = "password_missmatching")
    public String getRepeatPassword() {
        return repeatPassword;
    }

    @UiRequired
    @UiComesAfter(value = "repeatPassword")
    @Regex(pattern = ".+@.+\\.[a-z]+", message = "validator_email_message")
    public String getEmail() {
        return super.getEmail();
    }

    @UiLarge
    @UiComesAfter(value = "email")
    public String getAddress() {
        return super.getAddress();
    }


    @UiHidden
    public Repository getRepository() {
        return repository;
    }

    @UiHidden
    public String getUsername() {
        return super.getUsername();
    }

    @UiHidden
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @UiHidden
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @UiHidden
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @UiHidden
    public boolean isEnabled() {
        return super.isEnabled();
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @UiHidden
    public List<Application> getApplications() {
        return super.getApplications();
    }

   @UiHidden
    public boolean isHasApplications() {
        return super.isHasApplications();
    }

   @UiHidden
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
