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
import org.jk.myjobs.dao.Repository;
import org.jk.myjobs.domain.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;
import org.springframework.security.core.userdetails.memory.UserMap;

import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: JK
 * Date: 27/07/11
 * email: jkumaranc@yahoo.com
 * This is a mock repository which holds the user details
 * This will be replaced by a JPA/Hibernate based repository later!
 */
public class MockRepository implements Repository {
    private static final Logger LOGGER = getLogger(MockRepository.class);

    @Autowired(required = true)
    private InMemoryDaoImpl inMemoryDao;
    Map<String, User> users; //users map with user name as the key

    public MockRepository() {
        users = new HashMap<String, User>();
        users.put("roger", new User("Roger Federer", "roger", "roger", "roger@grasscourt.com", "Swiss"));
        users.put("rafael", new User("Rafael Nadal", "rafael", "rafael", "rafael@claycourt.com", "Spain"));
    }

    public void createUser(User user) throws RepositoryException {
        LOGGER.info("Adding the user : " + user);
        String userName = user.getUserName();
        if(isUserExists(userName)){
           throw new RepositoryException("The user " + userName + " already exists");
        }
        users.put(userName, user);
        inMemoryDao.getUserMap().addUser(user);
    }

    public void updateUser(User user) throws RepositoryException {
        LOGGER.info("Updating the user : " + user);
        String userName = user.getUserName();
        if(!isUserExists(userName)){
          throw new RepositoryException("No user with user name " + userName + " exists");
        }
        users.put(userName, user);//just replacing, not the best logic!
        inMemoryDao.getUserMap().addUser(user);//Spring does the same thing too!!
    }

    private boolean isUserExists(String userName) {
        User user = users.get(userName);
        if (user == null) {
            return false;
        }
        try {
           inMemoryDao.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException e) {
            return false;
        }
        return true;
    }

    public User findUser(String userName) {
        LOGGER.info("Searching for username : " + userName);
        return users.get(userName);
    }

    public InMemoryDaoImpl getInMemoryDao() {
        return inMemoryDao;
    }

    public void setInMemoryDao(InMemoryDaoImpl inMemoryDao) {
        this.inMemoryDao = inMemoryDao;
    }

    public void deleteUser(String username) {
        users.remove(username);
        UserMap userMap = inMemoryDao.getUserMap();
        UserMap newUserMap = new UserMap();
        for (String userKey : users.keySet()) {
            newUserMap.addUser(userMap.getUser(userKey));
        }
        inMemoryDao.setUserMap(newUserMap);
    }
}
