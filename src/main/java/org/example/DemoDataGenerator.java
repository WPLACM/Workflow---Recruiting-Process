/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ACCESS;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Permissions.READ;
import static org.camunda.bpm.engine.authorization.Permissions.UPDATE;
import static org.camunda.bpm.engine.authorization.Resources.APPLICATION;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;
import static org.camunda.bpm.engine.authorization.Resources.TASK;
import static org.camunda.bpm.engine.authorization.Resources.USER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationEntity;
import org.camunda.bpm.engine.task.TaskQuery;

/**
 * Creates demo credentials to be used in the invoice showcase.
 *
 * @author drobisch und Maxi
 */
public class DemoDataGenerator {

    private final static Logger LOGGER = Logger.getLogger(DemoDataGenerator.class.getName());

    public void createUsers(ProcessEngine engine) {

        final IdentityServiceImpl identityService = (IdentityServiceImpl) engine.getIdentityService();

        if(identityService.isReadOnly()) {
            LOGGER.info("Identity service provider is Read Only, not creating any demo users.");
            return;
        }

        //Not executed if demo user present
        User singleResult = identityService.createUserQuery().userId("nemo").singleResult();
        if (singleResult != null) {
            return;
        }

        LOGGER.info("Generating demo data");

        User user = identityService.newUser("nemo");
        user.setFirstName("Nemo");
        user.setLastName("Nemo");
        user.setPassword("nemo");
        user.setEmail("nemo@camunda.org");
        identityService.saveUser(user, true);

        User user2 = identityService.newUser("john");
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setPassword("john");
        user2.setEmail("john@camunda.org");
        identityService.saveUser(user2, true);

        User user3 = identityService.newUser("mary");
        user3.setFirstName("Mary");
        user3.setLastName("Anne");
        user3.setPassword("mary");
        user3.setEmail("mary@camunda.org");
        identityService.saveUser(user3, true);

        User user4 = identityService.newUser("peter");
        user4.setFirstName("Peter");
        user4.setLastName("Meter");
        user4.setPassword("peter");
        user4.setEmail("peter@camunda.org");
        identityService.saveUser(user4, true);

        Group publishingGroup = identityService.newGroup("publishing");
        publishingGroup.setName("Publishing");
        publishingGroup.setType("WORKFLOW");
        identityService.saveGroup(publishingGroup);

        Group applicationEvaluationGroup = identityService.newGroup("applicationEvaluation");
        applicationEvaluationGroup.setName("applicationEvaluation");
        applicationEvaluationGroup.setType("WORKFLOW");
        identityService.saveGroup(applicationEvaluationGroup);

        Group managementGroup = identityService.newGroup("management");
        managementGroup.setName("Management");
        managementGroup.setType("WORKFLOW");
        identityService.saveGroup(managementGroup);

        final AuthorizationService authorizationService = engine.getAuthorizationService();

        // create group
        if(identityService.createGroupQuery().groupId(Groups.CAMUNDA_ADMIN).count() == 0) {
            Group camundaAdminGroup = identityService.newGroup(Groups.CAMUNDA_ADMIN);
            camundaAdminGroup.setName("camunda BPM Administrators");
            camundaAdminGroup.setType(Groups.GROUP_TYPE_SYSTEM);
            identityService.saveGroup(camundaAdminGroup);
        }

        // create ADMIN authorizations on all built-in resources
        for (Resource resource : Resources.values()) {
            if(authorizationService.createAuthorizationQuery().groupIdIn(Groups.CAMUNDA_ADMIN).resourceType(resource).resourceId(ANY).count() == 0) {
                AuthorizationEntity userAdminAuth = new AuthorizationEntity(AUTH_TYPE_GRANT);
                userAdminAuth.setGroupId(Groups.CAMUNDA_ADMIN);
                userAdminAuth.setResource(resource);
                userAdminAuth.setResourceId(ANY);
                userAdminAuth.addPermission(ALL);
                authorizationService.saveAuthorization(userAdminAuth);
            }
        }

        identityService.createMembership("nemo", "publishing");
        identityService.createMembership("nemo", "applicationEvaluation");
        identityService.createMembership("nemo", "management");
        identityService.createMembership("nemo", "camunda-admin");

        identityService.createMembership("john", "publishing");
        identityService.createMembership("mary", "applicationEvaluation");
        identityService.createMembership("peter", "management");


        // authorize groups for tasklist only:

        Authorization publishingTasklistAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        publishingTasklistAuth.setGroupId("publishing");
        publishingTasklistAuth.addPermission(ACCESS);
        publishingTasklistAuth.setResourceId("tasklist");
        publishingTasklistAuth.setResource(APPLICATION);
        authorizationService.saveAuthorization(publishingTasklistAuth);

        Authorization publishingReadProcessDefinition = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        publishingReadProcessDefinition.setGroupId("publishing");
        publishingReadProcessDefinition.addPermission(Permissions.READ);
        publishingReadProcessDefinition.addPermission(Permissions.READ_HISTORY);
        publishingReadProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
        // restrict to invoice process definition only
        publishingReadProcessDefinition.setResourceId("invoice");
        authorizationService.saveAuthorization(publishingReadProcessDefinition);

        Authorization applicationEvaluationTasklistAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        applicationEvaluationTasklistAuth.setGroupId("applicationEvaluation");
        applicationEvaluationTasklistAuth.addPermission(ACCESS);
        applicationEvaluationTasklistAuth.setResourceId("tasklist");
        applicationEvaluationTasklistAuth.setResource(APPLICATION);
        authorizationService.saveAuthorization(applicationEvaluationTasklistAuth);

        Authorization applicationEvaluationReadProcessDefinition = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        applicationEvaluationReadProcessDefinition.setGroupId("applicationEvaluation");
        applicationEvaluationReadProcessDefinition.addPermission(Permissions.READ);
        applicationEvaluationReadProcessDefinition.addPermission(Permissions.READ_HISTORY);
        applicationEvaluationReadProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
        // restrict to invoice process definition only
        applicationEvaluationReadProcessDefinition.setResourceId("invoice");
        authorizationService.saveAuthorization(applicationEvaluationReadProcessDefinition);

        Authorization managementTasklistAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        managementTasklistAuth.setGroupId("management");
        managementTasklistAuth.addPermission(ACCESS);
        managementTasklistAuth.setResourceId("tasklist");
        managementTasklistAuth.setResource(APPLICATION);
        authorizationService.saveAuthorization(managementTasklistAuth);

        Authorization managementReadProcessDefinition = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        managementReadProcessDefinition.setGroupId("management");
        managementReadProcessDefinition.addPermission(Permissions.READ);
        managementReadProcessDefinition.addPermission(Permissions.READ_HISTORY);
        managementReadProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
        // restrict to invoice process definition only
        managementReadProcessDefinition.setResourceId("invoice");
        authorizationService.saveAuthorization(managementReadProcessDefinition);

        Authorization publishingDemoAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        publishingDemoAuth.setGroupId("publishing");
        publishingDemoAuth.setResource(USER);
        publishingDemoAuth.setResourceId("nemo");
        publishingDemoAuth.addPermission(READ);
        authorizationService.saveAuthorization(publishingDemoAuth);

        Authorization publishingJohnAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        publishingJohnAuth.setGroupId("publishing");
        publishingJohnAuth.setResource(USER);
        publishingJohnAuth.setResourceId("john");
        publishingJohnAuth.addPermission(READ);
        authorizationService.saveAuthorization(publishingJohnAuth);

        Authorization manDemoAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        manDemoAuth.setGroupId("management");
        manDemoAuth.setResource(USER);
        manDemoAuth.setResourceId("nemo");
        manDemoAuth.addPermission(READ);
        authorizationService.saveAuthorization(manDemoAuth);

        Authorization manPeterAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        manPeterAuth.setGroupId("management");
        manPeterAuth.setResource(USER);
        manPeterAuth.setResourceId("peter");
        manPeterAuth.addPermission(READ);
        authorizationService.saveAuthorization(manPeterAuth);

        Authorization accDemoAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        accDemoAuth.setGroupId("applicationEvaluation");
        accDemoAuth.setResource(USER);
        accDemoAuth.setResourceId("nemo");
        accDemoAuth.addPermission(READ);
        authorizationService.saveAuthorization(accDemoAuth);

        Authorization accMaryAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        accMaryAuth.setGroupId("applicationEvaluation");
        accMaryAuth.setResource(USER);
        accMaryAuth.setResourceId("mary");
        accMaryAuth.addPermission(READ);
        authorizationService.saveAuthorization(accMaryAuth);

        Authorization taskMaryAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        taskMaryAuth.setUserId("mary");
        taskMaryAuth.setResource(TASK);
        taskMaryAuth.setResourceId(ANY);
        taskMaryAuth.addPermission(READ);
        taskMaryAuth.addPermission(UPDATE);
        authorizationService.saveAuthorization(taskMaryAuth);

        // create default filters

        FilterService filterService = engine.getFilterService();

        Map<String, Object> filterProperties = new HashMap<String, Object>();
        filterProperties.put("description", "Tasks assigned to me");
        filterProperties.put("priority", -10);
        addVariables(filterProperties);
        TaskService taskService = engine.getTaskService();
        TaskQuery query = taskService.createTaskQuery().taskAssigneeExpression("${currentUser()}");
        Filter myTasksFilter = filterService.newTaskFilter().setName("My Tasks").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(myTasksFilter);

        filterProperties.clear();
        filterProperties.put("description", "Tasks assigned to my Groups");
        filterProperties.put("priority", -5);
        addVariables(filterProperties);
        query = taskService.createTaskQuery().taskCandidateGroupInExpression("${currentUserGroups()}").taskUnassigned();
        Filter groupTasksFilter = filterService.newTaskFilter().setName("My Group Tasks").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(groupTasksFilter);

        // global read authorizations for these filters

        Authorization globalMyTaskFilterRead = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
        globalMyTaskFilterRead.setResource(FILTER);
        globalMyTaskFilterRead.setResourceId(myTasksFilter.getId());
        globalMyTaskFilterRead.addPermission(READ);
        authorizationService.saveAuthorization(globalMyTaskFilterRead);

        Authorization globalGroupFilterRead = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
        globalGroupFilterRead.setResource(FILTER);
        globalGroupFilterRead.setResourceId(groupTasksFilter.getId());
        globalGroupFilterRead.addPermission(READ);
        authorizationService.saveAuthorization(globalGroupFilterRead);

        // management filter

        filterProperties.clear();
        filterProperties.put("description", "Tasks for Group applicationEvaluation");
        filterProperties.put("priority", -3);
        addVariables(filterProperties);
        query = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList("applicationEvaluation")).taskUnassigned();
        Filter candidateGroupTasksFilter = filterService.newTaskFilter().setName("applicationEvaluation").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(candidateGroupTasksFilter);

        Authorization managementGroupFilterRead = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        managementGroupFilterRead.setResource(FILTER);
        managementGroupFilterRead.setResourceId(candidateGroupTasksFilter.getId());
        managementGroupFilterRead.addPermission(READ);
        managementGroupFilterRead.setGroupId("applicationEvaluation");
        authorizationService.saveAuthorization(managementGroupFilterRead);

        // john's tasks

        filterProperties.clear();
        filterProperties.put("description", "Tasks assigned to John");
        filterProperties.put("priority", -1);
        addVariables(filterProperties);
        query = taskService.createTaskQuery().taskAssignee("john");
        Filter johnsTasksFilter = filterService.newTaskFilter().setName("John's Tasks").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(johnsTasksFilter);

        // mary's tasks

        filterProperties.clear();
        filterProperties.put("description", "Tasks assigned to Mary");
        filterProperties.put("priority", -1);
        addVariables(filterProperties);
        query = taskService.createTaskQuery().taskAssignee("mary");
        Filter marysTasksFilter = filterService.newTaskFilter().setName("Mary's Tasks").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(marysTasksFilter);

        // peter's tasks

        filterProperties.clear();
        filterProperties.put("description", "Tasks assigned to Peter");
        filterProperties.put("priority", -1);
        addVariables(filterProperties);
        query = taskService.createTaskQuery().taskAssignee("peter");
        Filter petersTasksFilter = filterService.newTaskFilter().setName("Peter's Tasks").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(petersTasksFilter);

        // all tasks

        filterProperties.clear();
        filterProperties.put("description", "All Tasks - Not recommended to be used in production :)");
        filterProperties.put("priority", 10);
        addVariables(filterProperties);
        query = taskService.createTaskQuery();
        Filter allTasksFilter = filterService.newTaskFilter().setName("All Tasks").setProperties(filterProperties).setOwner("nemo").setQuery(query);
        filterService.saveFilter(allTasksFilter);

    }

    protected void addVariables(Map<String, Object> filterProperties) {
        List<Object> variables = new ArrayList<Object>();

        addVariable(variables, "amount", "Invoice Amount");
        addVariable(variables, "invoiceNumber", "Invoice Number");
        addVariable(variables, "creditor", "Creditor");
        addVariable(variables, "approver", "Approver");

        filterProperties.put("variables", variables);
    }

    protected void addVariable(List<Object> variables, String name, String label) {
        Map<String, String> variable = new HashMap<String, String>();
        variable.put("name", name);
        variable.put("label", label);
        variables.add(variable);
    }
}
