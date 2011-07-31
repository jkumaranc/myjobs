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
package org.jk.myjobs.dao.impl;

import org.jk.myjobs.RepositoryException;
import org.jk.myjobs.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;
import org.springframework.security.core.userdetails.memory.UserMap;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * User: JK
 * Date: 31/07/11
 * email: jkumaranc@yahoo.com
 * //TODO add the doc pls!
 */
public class MockRepositoryTest {
    private MockRepository repository;
    private InMemoryDaoImpl inMemoryDao;

    @BeforeClass
    public void setup() {
        repository = new MockRepository();
        inMemoryDao = new InMemoryDaoImpl();
        UserMap userMap = new UserMap();
        userMap.addUser(new User("Rafael Nadal", "rafael", "rafael", "email", "address"));
        userMap.addUser(new User("Roger Federer", "roger", "roger", "email", "address"));
        inMemoryDao.setUserMap(userMap);

        repository.setInMemoryDao(inMemoryDao);
    }

    @BeforeMethod()
    public void methodSetup() throws RepositoryException {
        User[] users = {
                new User("full name1", "username1", "password1", "email1", "address1"),
                new User("full name2", "username2", "password2", "email2", "address2"),
                new User("full name3", "username3", "password3", "email3", "address3"),
                new User("full name4", "username4", "password4", "email4", "address4"),
                new User("full name5", "username5", "password5", "email5", "address5")
        };
        for (User user : users) {
            repository.createUser(user);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void methodTeardown() {
        String[] users = {"username", "username1", "username2", "username3", "username4","username5"};
        for (String user : users) {
            repository.deleteUser(user);
        }
    }

    /*TODO just a dummy test case, nothing useful!*/
    @Test
    public void testCreateUser(){
        User newUser = new User("full name", "username", "password", "email", "address");
        try {
            repository.createUser(newUser);
        } catch (RepositoryException unexpected) {
            assertNull(unexpected, "user creation should be smooth!");
        }
        User user = repository.users.get("username");
        assertNotNull(user, "Asserting the created user not null");
        assertEquals("full name", user.getFullName(), "Asserting the full name");
        assertEquals("username", user.getUserName(), "Asserting the username");
        assertEquals("password", user.getPassword(), "Asserting the password");
        assertEquals("email", user.getEmail(), "Asserting the email");
        assertEquals("address", user.getAddress(), "Asserting the address");
        try {
            repository.createUser(newUser);
        } catch (RepositoryException expected) {
            assertNotNull(expected);
        }
    }

    @Test
    public void testUpdateUser() throws Exception {
        repository.createUser(new User("full name", "username", "password", "email", "address"));
        User user = repository.users.get("username");
        user.setFullName("Full Name Changed");
        user.setUserName("User Name Changed");
        user.setPassword("Password Changed");
        user.setEmail("Email Changed");
        user.setAddress("Address Changed");
        try {
            repository.updateUser(user);
            Assert.fail("Username can't be changed");
        } catch (RepositoryException expected) {
            Assert.assertNotNull(expected);
        }
        user.setUserName("username");
        user = repository.users.get("username");
        repository.updateUser(user);
        assertNotNull(user, "Asserting the created user not null");
        assertEquals("Full Name Changed", user.getFullName(), "Asserting the full name");
        assertEquals("username", user.getUserName(), "Asserting the username");
        assertEquals("Password Changed", user.getPassword(), "Asserting the password");
        assertEquals("Email Changed", user.getEmail(), "Asserting the email");
        assertEquals("Address Changed", user.getAddress(), "Asserting the address");
        UserDetails userDetails = null;
        try {
            userDetails = inMemoryDao.loadUserByUsername("username");
        } catch (Throwable unExpected) {
            assertNull(unExpected);
        }
        assertEquals("Password Changed", userDetails.getPassword(), "Asserting the password from user details");
    }

    @Test
    public void testFindUser() throws Exception {
        User user = repository.findUser("username1");
        assertNotNull(user);
    }
}
