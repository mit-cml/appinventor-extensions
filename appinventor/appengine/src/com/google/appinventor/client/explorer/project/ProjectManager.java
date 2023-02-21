// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.explorer.project;

import com.google.appinventor.client.Ode;
import static com.google.appinventor.client.Ode.MESSAGES;
import com.google.appinventor.client.OdeAsyncCallback;
import com.google.appinventor.shared.rpc.project.ProjectNode;
import com.google.appinventor.shared.rpc.project.UserProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class manages projects.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public final class ProjectManager {
  // Map to find the project from a project ID.
  private final Map<Long, Project> projectsMap;

  // List of listeners for any project manager events.
  private final List<ProjectManagerEventListener> projectManagerEventListeners;

  /**
   * Flag indicating whether the project infos have all loaded.
   */
  private volatile boolean projectsLoaded = false;

  /**
   * Creates a new projects manager.
   */
  public ProjectManager() {
    projectsMap = new HashMap<Long, Project>();
    projectManagerEventListeners = new ArrayList<ProjectManagerEventListener>();
    Ode.getInstance().getProjectService().getProjectInfos(
      new OdeAsyncCallback<List<UserProject>>(
        MESSAGES.projectInformationRetrievalError()) {
        @Override
        public void onSuccess(List<UserProject> projectInfos) {
          for (UserProject projectInfo : projectInfos) {
            addProject(projectInfo);
          }
          fireProjectsLoaded();
        }
      });
  }

  /**
   * Returns a list of the projects with the given project name prefix.
   *
   * @param prefix  project name prefix
   * @return  a list of projects
   */
  public List<Project> getProjects(String prefix) {
    List<Project> projects = new ArrayList<Project>();

    for (Project project : projectsMap.values()) {
      if (project.getProjectName().startsWith(prefix)) {
        projects.add(project);
      }
    }

    return projects;
  }

  /**
   * Returns the project that belongs to a project node.
   *
   * @param node the project node for which we want to retrieve the project
   * @return the project of the node
   */
  public Project getProject(ProjectNode node) {
    return projectsMap.get(node.getProjectId());
  }

  /**
   * Returns the project for the given project name.
   *
   * <p>Note that in case of multiple projects with the same name, only the
   * first matching project will be returned.
   *
   * @param name  project name
   * @return  the corresponding project or {@code null}
   */
  public Project getProject(String name) {
    for (Project project : projectsMap.values()) {
      if (project.getProjectName().equals(name)) {
        return project;
      }
    }

    return null;
  }

  /**
   * Returns the project for the given project ID.
   *
   * @param projectId project ID
   * @return the corresponding project or {@code null}
   */
  public Project getProject(long projectId) {
    return projectsMap.get(projectId);
  }

  /**
   * Adds a new project to this project manager.
   *
   * @param projectInfo information about the project
   * @return new project
   */
  public Project addProject(UserProject projectInfo) {
    Project project = new Project(projectInfo);
    projectsMap.put(projectInfo.getProjectId(), project);
    fireProjectAdded(project);
    return project;
  }

  /**
   * Removes the project from trash permanently.
   *
   * @param projectId project ID
   */

  public void removeDeletedProject(long projectId) {
    Project project = projectsMap.remove(projectId);
    fireProjectDeleted(project);
  }

  /**
   * Restores the project from trash back to my projects.
   *
   * @param projectId project ID
   */

  public void restoreTrashProject(long projectId) {
    Project project = projectsMap.get(projectId);
    fireTrashProjectRestored(project);
  }

  public void trashProject(long projectId) {
    Project project = projectsMap.get(projectId);
    fireProjectTrashed(project);
  }

  /**
   * Adds a {@link ProjectManagerEventListener} to the listener list.
   *
   * @param listener  the {@code ProjectManagerEventListener} to be added
   */
  public void addProjectManagerEventListener(ProjectManagerEventListener listener) {
    projectManagerEventListeners.add(listener);
    if (projectsLoaded) {
      // inform the listener that projects have already been loaded
      listener.onProjectsLoaded();
    }
  }

  /**
   * Removes a {@link ProjectManagerEventListener} from the listener list.
   *
   * @param listener  the {@code ProjectManagerEventListener} to be removed
   */
  public void removeProjectManagerEventListener(ProjectManagerEventListener listener) {
    projectManagerEventListeners.remove(listener);
  }

  private List<ProjectManagerEventListener> copyProjectManagerEventListeners() {
    return new ArrayList<ProjectManagerEventListener>(projectManagerEventListeners);
  }

  /*
   * Triggers a 'project added' event to be sent to the listener on the listener list.
   */
  private void fireProjectAdded(Project project) {
    for (ProjectManagerEventListener listener : copyProjectManagerEventListeners()) {
      listener.onProjectAdded(project);
    }
  }

  /*
   * Triggers a 'project removed' event to be sent to the listener on the listener list.
   */
  private void fireTrashProjectRestored(Project project) {
    for (ProjectManagerEventListener listener : copyProjectManagerEventListeners()) {
      listener.onTrashProjectRestored(project);
    }
  }

  private void fireProjectTrashed(Project project) {
    for (ProjectManagerEventListener listener : copyProjectManagerEventListeners()) {
      listener.onProjectTrashed(project);
    }
  }

  private void fireProjectDeleted(Project project) {
    for (ProjectManagerEventListener listener : copyProjectManagerEventListeners()) {
      listener.onProjectDeleted(project);
    }
  }

  /*
   * Triggers a 'projects loaded' event to be sent to the listener on the listener list.
   */
  private void fireProjectsLoaded() {
    projectsLoaded = true;
    for (ProjectManagerEventListener listener : copyProjectManagerEventListeners()) {
      listener.onProjectsLoaded();
    }
  }
}
