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

package org.jk.myjobs.dao;

import org.jk.myjobs.RepositoryException;
import org.jk.myjobs.domain.User;

/**
 * User: JK
 * Date: 27/07/11
 * email: jkumaranc@yahoo.com
 * //TODO add the doc pls!
 */
public interface Repository {

   void createUser(User user) throws RepositoryException;

    void updateUser(User user) throws RepositoryException;

    User findUser(String userName);
}
