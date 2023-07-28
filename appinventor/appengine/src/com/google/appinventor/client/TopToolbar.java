// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2013 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client;

import com.google.appinventor.client.boxes.ProjectListBox;
import com.google.appinventor.client.editor.youngandroid.BlocklyPanel;
import com.google.appinventor.client.editor.youngandroid.YaBlocksEditor;
import com.google.appinventor.client.explorer.commands.BuildCommand;
import com.google.appinventor.client.explorer.commands.ChainableCommand;
import com.google.appinventor.client.explorer.commands.CopyYoungAndroidProjectCommand;
import com.google.appinventor.client.explorer.commands.DownloadProjectOutputCommand;
import com.google.appinventor.client.explorer.commands.GenerateYailCommand;
import com.google.appinventor.client.explorer.commands.SaveAllEditorsCommand;
import com.google.appinventor.client.explorer.commands.ShowBarcodeCommand;
import com.google.appinventor.client.explorer.commands.ShowProgressBarCommand;
import com.google.appinventor.client.explorer.commands.WaitForBuildResultCommand;
import com.google.appinventor.client.explorer.commands.WarningDialogCommand;
import com.google.appinventor.client.explorer.project.Project;
import com.google.appinventor.client.output.OdeLog;
import com.google.appinventor.client.tracking.Tracking;
import com.google.appinventor.client.utils.Downloader;
import com.google.appinventor.client.widgets.DropDownButton;
import com.google.appinventor.client.widgets.DropDownButton.DropDownItem;
import com.google.appinventor.client.wizards.DownloadUserSourceWizard;
import com.google.appinventor.client.wizards.KeystoreUploadWizard;
import com.google.appinventor.client.wizards.ProjectUploadWizard;
import com.google.appinventor.client.wizards.TemplateUploadWizard;
import com.google.appinventor.client.wizards.ComponentImportWizard;
import com.google.appinventor.client.wizards.ComponentUploadWizard;
import com.google.appinventor.client.wizards.youngandroid.NewYoungAndroidProjectWizard;
import com.google.appinventor.common.version.AppInventorFeatures;
import com.google.appinventor.common.version.GitBuildId;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.shared.rpc.ServerLayout;
import com.google.appinventor.shared.rpc.project.ProjectRootNode;
import com.google.appinventor.shared.rpc.project.youngandroid.YoungAndroidProjectNode;
import com.google.appinventor.shared.rpc.user.Config;
import com.google.appinventor.shared.storage.StorageUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

import static com.google.appinventor.client.Ode.MESSAGES;


/**
 * TopToolbar lives in the TopPanel, to create functionality in the designer.
 */
public class TopToolbar extends Composite {
  private static final String WIDGET_NAME_NEW = "New";
  private static final String WIDGET_NAME_DELETE = "Delete";
  private static final String WIDGET_NAME_DELETE_TRASH = "Delete Trash";
  private static final String WIDGET_NAME_DOWNLOAD_KEYSTORE = "DownloadKeystore";
  private static final String WIDGET_NAME_UPLOAD_KEYSTORE = "UploadKeystore";
  private static final String WIDGET_NAME_DELETE_KEYSTORE = "DeleteKeystore";
  private static final String WIDGET_NAME_SAVE = "Save";
  private static final String WIDGET_NAME_SAVE_AS = "SaveAs";
  private static final String WIDGET_NAME_CHECKPOINT = "Checkpoint";
  private static final String WIDGET_NAME_MY_PROJECTS = "MyProjects";
  private static final String WIDGET_NAME_BUILD = "Build";
  private static final String WIDGET_NAME_BUILD_ANDROID_APK = "BuildApk";
  private static final String WIDGET_NAME_BUILD_ANDROID_AAB = "BuildAab";
  private static final String WIDGET_NAME_BUILD_ANDROID_APK2 = "BuildApk2";
  private static final String WIDGET_NAME_BUILD_ANDROID_AAB2 = "BuildAab2";
  private static final String WIDGET_NAME_BUILD_YAIL = "Yail";
  private static final String WIDGET_NAME_CONNECT_TO = "ConnectTo";
  private static final String WIDGET_NAME_WIRELESS_BUTTON = "Wireless";
  private static final String WIDGET_NAME_CHROMEBOOK = "Chromebook";
  private static final String WIDGET_NAME_EMULATOR_BUTTON = "Emulator";
  private static final String WIDGET_NAME_USB_BUTTON = "Usb";
  private static final String WIDGET_NAME_RESET_BUTTON = "Reset";
  private static final String WIDGET_NAME_HARDRESET_BUTTON = "HardReset";
  private static final String WIDGET_NAME_REFRESHCOMPANION_BUTTON = "RefreshCompanion";
  private static final String WIDGET_NAME_PROJECT = "Project";
  private static final String WIDGET_NAME_SETTINGS = "Settings";
  private static final String WIDGET_NAME_AUTOLOAD = "Autoload Last Project";
  private static final String WIDGET_NAME_DYSLEXIC_FONT = "DyslexicFont";
  private static final String WIDGET_NAME_HELP = "Help";
  private static final String WIDGET_NAME_ABOUT = "About";
  private static final String WIDGET_NAME_LIBRARY = "Library";
  private static final String WIDGET_NAME_GETSTARTED = "GetStarted";
  private static final String WIDGET_NAME_TUTORIALS = "Tutorials";
  private static final String WIDGET_NAME_EXTENSIONS = "Extensions";
  private static final String WIDGET_NAME_SHOWSPLASH = "ShowSplash";
  private static final String WIDGET_NAME_TROUBLESHOOTING = "Troubleshooting";
  private static final String WIDGET_NAME_FORUMS = "Forums";
  private static final String WIDGET_NAME_FEEDBACK = "ReportIssue";
  private static final String WIDGET_NAME_COMPANIONINFO = "CompanionInformation";
  private static final String WIDGET_NAME_COMPANIONUPDATE = "CompanionUpdate";
  private static final String WIDGET_NAME_IMPORTPROJECT = "ImportProject";
  private static final String WIDGET_NAME_IMPORTTEMPLATE = "ImportTemplate";
  private static final String WIDGET_NAME_EXPORTALLPROJECTS = "ExportAllProjects";
  private static final String WIDGET_NAME_EXPORTPROJECT = "ExportProject";

  private static final String WIDGET_NAME_ADMIN = "Admin";
  private static final String WIDGET_NAME_USER_ADMIN = "UserAdmin";
  private static final String WIDGET_NAME_DOWNLOAD_USER_SOURCE = "DownloadUserSource";
  private static final String WIDGET_NAME_SWITCH_TO_DEBUG = "SwitchToDebugPane";
  private static final String WINDOW_OPEN_FEATURES = "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes";
  private static final String WINDOW_OPEN_LOCATION = "_blank";

  private static final boolean iamChromebook = isChromeBook();

  private DropDownButton fileDropDown;
  private DropDownButton connectDropDown;
  private DropDownButton buildDropDown;
  private DropDownButton helpDropDown;
  private DropDownButton adminDropDown;
  private DropDownButton settingsDropDown;

  private boolean isReadOnly;
  /**
   * This flag is set to true when a check for the android.keystore file is in progress.
   */
  private volatile boolean isKeystoreCheckPending = false;
  /**
   * This flag is set to true when a call to {@link #updateKeystoreFileMenuButtons(boolean)} has
   * returned and the value was cached.
   */
  private volatile boolean isKeystoreCached = false;
  /**
   * This flag is the cached result of an earlier check for android.keystore.
   */
  private volatile boolean isKeystorePresent = false;

  public TopToolbar() {
    /*
     * Layout is as follows:
     * +----------------------------------------------------------------+
     * | Project ▾ | Connect ▾ | Build ▾ | Settings ▾ | Help ▾| Admin ▾ |
     * +----------------------------------------------------------------+
     */
    HorizontalPanel toolbar = new HorizontalPanel();
    toolbar.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);

    // Should the UI be in read only mode?
    isReadOnly = Ode.getInstance().isReadOnly();

    // Create the TopToolbar drop down menus.
    fileDropDown = makeButton(WIDGET_NAME_PROJECT, MESSAGES.projectsTabName());
    connectDropDown = makeButton(WIDGET_NAME_CONNECT_TO, MESSAGES.connectTabName());
    buildDropDown = makeButton(WIDGET_NAME_BUILD, MESSAGES.buildTabName());
    settingsDropDown = makeButton(WIDGET_NAME_SETTINGS, MESSAGES.settingsTabName());
    helpDropDown = makeButton(WIDGET_NAME_HELP, MESSAGES.helpTabName());

    createProjectsMenu();
    createConnectMenu();
    createBuildMenu();
    createSettingsMenu();
    createHelpMenu();

    // Add the Buttons to the Toolbar.
    toolbar.add(fileDropDown);
    toolbar.add(connectDropDown);
    toolbar.add(buildDropDown);
    toolbar.add(settingsDropDown);
    toolbar.add(helpDropDown);

    //Only if logged in as an admin, add the Admin Button
    if (Ode.getInstance().getUser().getIsAdmin()) {
      adminDropDown = makeButton(WIDGET_NAME_ADMIN, MESSAGES.adminTabName());
      createAdminMenu();
      toolbar.add(adminDropDown);
    }

    initWidget(toolbar);
  }

  public void updateMoveToTrash(String menu_item){
    if(menu_item.equals("Move To Trash")){
      fileDropDown.setItemVisible(MESSAGES.trashProjectMenuItem(), true);
      fileDropDown.setItemVisible(MESSAGES.deleteFromTrashButton(), false);
    }
    else{
      fileDropDown.setItemVisible(MESSAGES.trashProjectMenuItem(), false);
      fileDropDown.setItemVisible(MESSAGES.deleteFromTrashButton(), true);
    }
  }

  public void updateMenuState(int numSelectedProjects, int numProjects) {
    boolean allowDelete = !isReadOnly && numSelectedProjects > 0;
    boolean allowExport = numSelectedProjects > 0;
    boolean allowExportAll = numProjects > 0;
    fileDropDown.setItemEnabled(MESSAGES.trashProjectMenuItem(), allowDelete);
    fileDropDown.setItemEnabled(MESSAGES.deleteFromTrashButton(), allowDelete);
    String exportProjectLabel = numSelectedProjects > 1 ?
        MESSAGES.exportSelectedProjectsMenuItem(numSelectedProjects) : MESSAGES.exportProjectMenuItem();
    fileDropDown.setItemHtmlById(WIDGET_NAME_EXPORTPROJECT, exportProjectLabel);
    fileDropDown.setItemEnabledById(WIDGET_NAME_EXPORTPROJECT, allowExport);
    fileDropDown.setItemEnabled(MESSAGES.exportAllProjectsMenuItem(), allowExportAll);
  }

  private DropDownButton makeButton(String id, String text) {
    DropDownButton button = new DropDownButton(id, text, new ArrayList<DropDownItem>(), false);
    button.setStyleName("ode-TopPanelButton");
    return button;
  }

  private void refreshMenu(DropDownButton menu, List<DropDownItem> items) {
    menu.clearAllItems();  // ensure we start with a clean slate
    for (DropDownItem i : items) {
      menu.addItem(i);
    }
    fileDropDown.setItemVisible(MESSAGES.deleteFromTrashButton(), false);
  }

  private void createProjectsMenu() {
    List<DropDownItem> fileItems = Lists.newArrayList();
    fileItems.add(new DropDownItem(WIDGET_NAME_MY_PROJECTS, MESSAGES.projectMenuItem(),
        new SwitchToProjectAction()));
    fileItems.add(null);
    if (!isReadOnly) {
      fileItems.add(new DropDownItem(WIDGET_NAME_NEW, MESSAGES.newProjectMenuItem(),
          new NewAction()));
      fileItems.add(new DropDownItem(WIDGET_NAME_IMPORTPROJECT, MESSAGES.importProjectMenuItem(),
          new ImportProjectAction()));
      fileItems.add(new DropDownItem(WIDGET_NAME_IMPORTTEMPLATE, MESSAGES.importTemplateButton(),
          new ImportTemplateAction()));
      fileItems.add(new DropDownItem(WIDGET_NAME_DELETE, MESSAGES.deleteProjectButton(),
          new DeleteAction()));
      fileItems.add(new DropDownItem(WIDGET_NAME_DELETE_TRASH, MESSAGES.deleteFromTrashButton(),
          new DeleteForeverProjectAction()));
      fileItems.add(null);
      fileItems.add(new DropDownItem(WIDGET_NAME_SAVE, MESSAGES.saveMenuItem(),
          new SaveAction()));
      fileItems.add(new DropDownItem(WIDGET_NAME_SAVE_AS, MESSAGES.saveAsMenuItem(),
          new SaveAsAction()));
      fileItems.add(new DropDownItem(WIDGET_NAME_CHECKPOINT, MESSAGES.checkpointMenuItem(),
          new CheckpointAction()));
      fileItems.add(null);
    }
    fileItems.add(new DropDownItem(WIDGET_NAME_EXPORTPROJECT, MESSAGES.exportProjectMenuItem(),
        new ExportProjectAction()));
    fileItems.add(new DropDownItem(WIDGET_NAME_EXPORTALLPROJECTS, MESSAGES.exportAllProjectsMenuItem(),
        new ExportAllProjectsAction()));
    fileItems.add(null);
    if (!isReadOnly) {
      fileItems.add(new DropDownItem(WIDGET_NAME_UPLOAD_KEYSTORE, MESSAGES.uploadKeystoreMenuItem(),
          new UploadKeystoreAction()));
    }
    fileItems.add(new DropDownItem(WIDGET_NAME_DOWNLOAD_KEYSTORE, MESSAGES.downloadKeystoreMenuItem(),
        new DownloadKeystoreAction()));
    if (!isReadOnly) {
      fileItems.add(new DropDownItem(WIDGET_NAME_DELETE_KEYSTORE, MESSAGES.deleteKeystoreMenuItem(),
          new DeleteKeystoreAction()));
    }
    refreshMenu(fileDropDown, fileItems);
  }

  private void createConnectMenu() {
    List<DropDownItem> connectItems = Lists.newArrayList();
    connectItems.add(new DropDownItem(WIDGET_NAME_WIRELESS_BUTTON,
        MESSAGES.AICompanionMenuItem(), new WirelessAction()));
    if (iamChromebook) {
      connectItems.add(new DropDownItem(WIDGET_NAME_CHROMEBOOK,
          MESSAGES.chromebookMenuItem(), new ChromebookAction()));
    } else {
      connectItems.add(new DropDownItem(WIDGET_NAME_EMULATOR_BUTTON,
          MESSAGES.emulatorMenuItem(), new EmulatorAction()));
      connectItems.add(new DropDownItem(WIDGET_NAME_USB_BUTTON, MESSAGES.usbMenuItem(),
          new UsbAction()));
    }
    connectItems.add(null);
    connectItems.add(new DropDownItem(WIDGET_NAME_REFRESHCOMPANION_BUTTON, MESSAGES.refreshCompanionMenuItem(),
            new RefreshCompanionAction()));
    connectItems.add(null);
    connectItems.add(new DropDownItem(WIDGET_NAME_RESET_BUTTON, MESSAGES.resetConnectionsMenuItem(),
        new ResetAction()));
    if (!iamChromebook) {
      connectItems.add(new DropDownItem(WIDGET_NAME_HARDRESET_BUTTON, MESSAGES.hardResetConnectionsMenuItem(),
          new HardResetAction()));
    }
    refreshMenu(connectDropDown, connectItems);
  }

  private void createBuildMenu() {
    List<DropDownItem> buildItems = Lists.newArrayList();
    buildItems.add(new DropDownItem(WIDGET_NAME_BUILD_ANDROID_APK, MESSAGES.showExportAndroidApk(),
        new BarcodeAction(false, false)));
    buildItems.add(new DropDownItem(WIDGET_NAME_BUILD_ANDROID_AAB, MESSAGES.showExportAndroidAab(),
        new BarcodeAction(false, true)));

    // Second Buildserver Menu Items
    //
    // We may have a second buildserver which if present permits us to build applications
    // using different components. This was added primarily to support the "target 26 SDK"
    // effort where we needed a way for people to package applications against SDK 26 in
    // order for them to be available in Google's Play Store (Google Requirement as of
    // 8/1/2018). However such applications have a minSdk of 14 (Ice Cream Sandwich).
    //
    // To support the creation of packages for older devices, we leave the buildserver
    // (as of 8/1/2018) generating minSdk 7 packages (no target SDK) which will run on
    // much older devices. The second buildserver will package applications with a target
    // SDK of 26 for those MIT App Inventor users who wish to put their applications in
    // the Play Store after 8/1/2018.

    if (Ode.getInstance().hasSecondBuildserver()) {
      buildItems.add(null);
      buildItems.add(new DropDownItem(WIDGET_NAME_BUILD_ANDROID_APK2, MESSAGES.showExportAndroidApk2(),
          new BarcodeAction(true, false)));
      buildItems.add(new DropDownItem(WIDGET_NAME_BUILD_ANDROID_AAB2, MESSAGES.showExportAndroidAab2(),
          new BarcodeAction(true, true)));
    }

    if (AppInventorFeatures.hasYailGenerationOption() && Ode.getInstance().getUser().getIsAdmin()) {
      buildItems.add(null);
      buildItems.add(new DropDownItem(WIDGET_NAME_BUILD_YAIL, MESSAGES.generateYailMenuItem(),
          new GenerateYailAction()));
    }
    refreshMenu(buildDropDown, buildItems);
  }

  private void createSettingsMenu() {
    List<DropDownItem> settingsItems = Lists.newArrayList();
    if (Ode.getUserAutoloadProject()) {
      settingsItems.add(new DropDownItem(WIDGET_NAME_AUTOLOAD, MESSAGES.disableAutoload(),
          new DisableAutoloadAction()));
    } else {
      settingsItems.add(new DropDownItem(WIDGET_NAME_AUTOLOAD, MESSAGES.enableAutoload(),
          new EnableAutoloadAction()));
    }
    if (Ode.getUserDyslexicFont()) {
      settingsItems.add(new DropDownItem(WIDGET_NAME_DYSLEXIC_FONT, MESSAGES.disableOpenDyslexic(),
          new SetFontRegularAction()));
    } else {
      settingsItems.add(new DropDownItem(WIDGET_NAME_DYSLEXIC_FONT,  MESSAGES.enableOpenDyslexic(),
          new SetFontDyslexicAction()));
    }
    refreshMenu(settingsDropDown, settingsItems);
  }

  private void createHelpMenu() {
    List<DropDownItem> helpItems = Lists.newArrayList();
    helpItems.add(new DropDownItem(WIDGET_NAME_ABOUT, MESSAGES.aboutMenuItem(),
        new AboutAction()));
    helpItems.add(null);
    Config config = Ode.getSystemConfig();
    String libraryUrl = config.getLibraryUrl();
    if (!Strings.isNullOrEmpty(libraryUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_LIBRARY, MESSAGES.libraryMenuItem(),
          new WindowOpenAction(libraryUrl)));
    }
    String getStartedUrl = config.getGetStartedUrl();
    if (!Strings.isNullOrEmpty(getStartedUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_GETSTARTED, MESSAGES.getStartedMenuItem(),
          new WindowOpenAction(getStartedUrl)));
    }
    String extensionsUrl = config.getExtensionsUrl();
    if (!Strings.isNullOrEmpty(extensionsUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_EXTENSIONS, MESSAGES.extensionsMenuItem(),
          new WindowOpenAction(extensionsUrl)));
    }
    String tutorialsUrl = config.getTutorialsUrl();
    if (!Strings.isNullOrEmpty(tutorialsUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_TUTORIALS, MESSAGES.tutorialsMenuItem(),
          new WindowOpenAction(tutorialsUrl)));
    }
    String troubleshootingUrl = config.getTroubleshootingUrl();
    if (!Strings.isNullOrEmpty(troubleshootingUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_TROUBLESHOOTING, MESSAGES.troubleshootingMenuItem(),
          new WindowOpenAction(troubleshootingUrl)));
    }
    String forumsUrl = config.getForumsUrl();
    if (!Strings.isNullOrEmpty(forumsUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_FORUMS, MESSAGES.forumsMenuItem(),
          new WindowOpenAction(forumsUrl)));
    }
    helpItems.add(null);
    String feedbackUrl = config.getFeedbackUrl();
    if (!Strings.isNullOrEmpty(feedbackUrl)) {
      helpItems.add(new DropDownItem(WIDGET_NAME_FEEDBACK, MESSAGES.feedbackMenuItem(),
          new WindowOpenAction(feedbackUrl)));
      helpItems.add(null);
    }
    helpItems.add(new DropDownItem(WIDGET_NAME_COMPANIONINFO, MESSAGES.companionInformation(),
        new AboutCompanionAction()));
    helpItems.add(new DropDownItem(WIDGET_NAME_COMPANIONUPDATE, MESSAGES.companionUpdate(),
        new CompanionUpdateAction()));
    helpItems.add(new DropDownItem(WIDGET_NAME_SHOWSPLASH, MESSAGES.showSplashMenuItem(),
        new ShowSplashAction()));
    refreshMenu(helpDropDown, helpItems);
  }

  private void createAdminMenu() {
    if (adminDropDown == null) {
      return;  // the button won't exist if the user isn't an admin
    }
    List<DropDownItem> adminItems = Lists.newArrayList();
    adminItems.add(new DropDownItem(WIDGET_NAME_DOWNLOAD_USER_SOURCE,
        MESSAGES.downloadUserSourceMenuItem(), new DownloadUserSourceAction()));
    adminItems.add(new DropDownItem(WIDGET_NAME_SWITCH_TO_DEBUG,
        MESSAGES.switchToDebugMenuItem(), new SwitchToDebugAction()));
    adminItems.add(new DropDownItem(WIDGET_NAME_USER_ADMIN,
        "User Admin", new SwitchToUserAdminAction()));
    refreshMenu(adminDropDown, adminItems);
  }

  // -----------------------------
  // List of Commands for use in Drop-Down Menus
  // -----------------------------

  private static class NewAction implements Command {
    @Override
    public void execute() {
      new NewYoungAndroidProjectWizard(null).center();
      // The wizard will switch to the design view when the new
      // project is created.
    }
  }

  private class SaveAction implements Command {
    @Override
    public void execute() {
      ProjectRootNode projectRootNode = Ode.getInstance().getCurrentYoungAndroidProjectRootNode();
      if (projectRootNode != null) {
        ChainableCommand cmd = new SaveAllEditorsCommand(null);
        cmd.startExecuteChain(Tracking.PROJECT_ACTION_SAVE_YA, projectRootNode);
      }
    }
  }

  private class SaveAsAction implements Command {
    @Override
    public void execute() {
      ProjectRootNode projectRootNode = Ode.getInstance().getCurrentYoungAndroidProjectRootNode();
      if (projectRootNode != null) {
        ChainableCommand cmd = new SaveAllEditorsCommand(
            new CopyYoungAndroidProjectCommand(false));
        cmd.startExecuteChain(Tracking.PROJECT_ACTION_SAVE_AS_YA, projectRootNode);
      }
    }
  }

  private class CheckpointAction implements Command {
    @Override
    public void execute() {
      ProjectRootNode projectRootNode = Ode.getInstance().getCurrentYoungAndroidProjectRootNode();
      if (projectRootNode != null) {
        ChainableCommand cmd = new SaveAllEditorsCommand(
            new CopyYoungAndroidProjectCommand(true));
        cmd.startExecuteChain(Tracking.PROJECT_ACTION_CHECKPOINT_YA, projectRootNode);
      }
    }
  }

  private static class SwitchToProjectAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().getTopToolbar().updateMoveToTrash("Move To Trash");
      Ode.getInstance().switchToProjectsView();
      Ode.getInstance().getTopToolbar().updateFileMenuButtons(0);
    }
  }

  private class WirelessAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        startRepl(true, false, false, false); // false means we are
                                              // *not* the emulator
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_WIFI);
      }
    }
  }

  private class ChromebookAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        startRepl(true, true, false, false);
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_CHROMEBOOK);
      }
    }
  }

  private class EmulatorAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        startRepl(true, false, true, false); // true means we are the
                                             // emulator
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_EMULATOR);
      }
    }
  }

  private class UsbAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        startRepl(true, false, false, true);
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_USB);
      }
    }
  }

  private class ResetAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        startRepl(false, false, false, false); // We are really stopping the repl here
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_RESET);
      }
    }
  }

  private class HardResetAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        replHardReset();
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_HARD_RESET);
      }
    }
  }

  private class RefreshCompanionAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().okToConnect()) {
        replUpdate();
        Tracking.trackEvent(Tracking.CONNECT_EVENT, Tracking.CONNECT_ACTION_RESEND);
      }
    }
  }

  private class BarcodeAction implements Command {

    private boolean secondBuildserver = false;
    private boolean isAab;

    public BarcodeAction(boolean secondBuildserver, boolean isAab) {
      this.secondBuildserver = secondBuildserver;
      this.isAab = isAab;
    }

    @Override
    public void execute() {
      ProjectRootNode projectRootNode = Ode.getInstance().getCurrentYoungAndroidProjectRootNode();
      if (projectRootNode != null) {
        String target = YoungAndroidProjectNode.YOUNG_ANDROID_TARGET_ANDROID;
        ChainableCommand cmd = new SaveAllEditorsCommand(
            new GenerateYailCommand(
                new BuildCommand(target, secondBuildserver, isAab,
                  new ShowProgressBarCommand(target,
                    new WaitForBuildResultCommand(target,
                      new ShowBarcodeCommand(target, isAab)), "BarcodeAction"))));
        if (!Ode.getInstance().getWarnBuild(secondBuildserver)) {
          cmd = new WarningDialogCommand(target, secondBuildserver, cmd);
          Ode.getInstance().setWarnBuild(secondBuildserver, true);
        }
        cmd.startExecuteChain(Tracking.PROJECT_ACTION_BUILD_BARCODE_YA, projectRootNode,
            new Command() {
              @Override
              public void execute() {
              }
            });
      }
    }
  }

  private static class ExportProjectAction implements Command {
    @Override
    public void execute() {
      if (Ode.getInstance().getCurrentView() == Ode.PROJECTS) {
        List<Project> selectedProjects =
          ProjectListBox.getProjectListBox().getProjectList().getSelectedProjects();
        //If we are in the projects view
        if (selectedProjects.size() == 1) {
          exportProject(selectedProjects.get(0));
        } else if (selectedProjects.size() > 1) {
          exportSelectedProjects(selectedProjects);
        } else {
          // The user needs to select only one project.
          ErrorReporter.reportInfo(MESSAGES.wrongNumberProjectsSelected());
        }
      } else {
        //If we are in the designer view.
        Downloader.getInstance().download(ServerLayout.DOWNLOAD_SERVLET_BASE + ServerLayout.DOWNLOAD_PROJECT_SOURCE + "/" + Ode.getInstance().getCurrentYoungAndroidProjectId());
      }
    }

    private void exportProject(Project project) {
      Tracking.trackEvent(Tracking.PROJECT_EVENT,
        Tracking.PROJECT_ACTION_DOWNLOAD_PROJECT_SOURCE_YA, project.getProjectName());

      Downloader.getInstance().download(ServerLayout.DOWNLOAD_SERVLET_BASE +
        ServerLayout.DOWNLOAD_PROJECT_SOURCE + "/" + project.getProjectId());
    }

    private void exportSelectedProjects(List<Project> projects) {
      Tracking.trackEvent(Tracking.PROJECT_EVENT,
              Tracking.PROJECT_ACTION_DOWNLOAD_SELECTED_PROJECTS_SOURCE_YA);

      String selectedProjPath = ServerLayout.DOWNLOAD_SERVLET_BASE +
              ServerLayout.DOWNLOAD_SELECTED_PROJECTS_SOURCE + "/";

      for (Project project : projects) {
        selectedProjPath += project.getProjectId() + "-";
      }

      Downloader.getInstance().download(selectedProjPath);
    }
  }

  private static class ExportAllProjectsAction implements Command {
    @Override
    public void execute() {
      Tracking.trackEvent(Tracking.PROJECT_EVENT,
          Tracking.PROJECT_ACTION_DOWNLOAD_ALL_PROJECTS_SOURCE_YA);

      // Is there a way to disable the Download All button until this completes?
      if (Window.confirm(MESSAGES.downloadAllAlert())) {

        Downloader.getInstance().download(ServerLayout.DOWNLOAD_SERVLET_BASE +
            ServerLayout.DOWNLOAD_ALL_PROJECTS_SOURCE);
      }
    }
  }

  private static class ImportProjectAction implements Command {
    @Override
    public void execute() {
      new ProjectUploadWizard().center();
    }
  }

  private static class ImportTemplateAction implements Command {
    @Override
    public void execute() {
      new TemplateUploadWizard().center();
    }
  }

  private static class DeleteAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().getEditorManager().saveDirtyEditors(new Command() {
        @Override
        public void execute() {
          if (Ode.getInstance().getCurrentView() == Ode.PROJECTS) {
            List<Project> selectedProjects =
                ProjectListBox.getProjectListBox().getProjectList().getSelectedProjects();
            if (selectedProjects.size() > 0) {
              // Show one confirmation window for selected projects.
              if (deleteConfirmation(selectedProjects)) {
                for (Project project : selectedProjects) {
                  project.moveToTrash();
                }
              }
            } else {
              // The user can select a project to resolve the
              // error.
              ErrorReporter.reportInfo(MESSAGES.noProjectSelectedForDelete());
            }
          } else { //We are deleting a project in the designer view
            List<Project> selectedProjects = new ArrayList<Project>();
            Project currentProject = Ode.getInstance().getProjectManager().getProject(Ode.getInstance().getCurrentYoungAndroidProjectId());
            selectedProjects.add(currentProject);
            if (deleteConfirmation(selectedProjects)) {
              currentProject.moveToTrash();
              //Add the command to stop this current project from saving
            }
          }
          Ode.getInstance().switchToProjectsView();
        }
      });
    }


    private boolean deleteConfirmation(List<Project> projects) {
      String message;
      if (projects.size() == 1) {
        message = MESSAGES.confirmMoveToTrashSingleProject(projects.get(0).getProjectName());
      } else {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (Project project : projects) {
          sb.append(separator).append(project.getProjectName());
          separator = ", ";
        }
        String projectNames = sb.toString();
        message = MESSAGES.confirmMoveToTrash(projectNames);
      }
      return Window.confirm(message);
    }
  }

  private static class DownloadKeystoreAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().getUserInfoService().hasUserFile(StorageUtil.ANDROID_KEYSTORE_FILENAME,
          new OdeAsyncCallback<Boolean>(MESSAGES.downloadKeystoreError()) {
            @Override
            public void onSuccess(Boolean keystoreFileExists) {
              if (keystoreFileExists) {
                Tracking.trackEvent(Tracking.USER_EVENT, Tracking.USER_ACTION_DOWNLOAD_KEYSTORE);
                Downloader.getInstance().download(ServerLayout.DOWNLOAD_SERVLET_BASE +
                    ServerLayout.DOWNLOAD_USERFILE + "/" + StorageUtil.ANDROID_KEYSTORE_FILENAME);
              } else {
                Window.alert(MESSAGES.noKeystoreToDownload());
              }
            }
          });
    }
  }

  private class UploadKeystoreAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().getUserInfoService().hasUserFile(StorageUtil.ANDROID_KEYSTORE_FILENAME,
          new OdeAsyncCallback<Boolean>(MESSAGES.uploadKeystoreError()) {
            @Override
            public void onSuccess(Boolean keystoreFileExists) {
              if (!keystoreFileExists || Window.confirm(MESSAGES.confirmOverwriteKeystore())) {
                KeystoreUploadWizard wizard = new KeystoreUploadWizard(new Command() {
                  @Override
                  public void execute() {
                    Tracking.trackEvent(Tracking.USER_EVENT, Tracking.USER_ACTION_UPLOAD_KEYSTORE);
                    updateKeystoreFileMenuButtons();
                  }
                });
                wizard.center();
              }
            }
          });
    }
  }

  private class DeleteKeystoreAction implements Command {
    @Override
    public void execute() {
      final String errorMessage = MESSAGES.deleteKeystoreError();
      Ode.getInstance().getUserInfoService().hasUserFile(StorageUtil.ANDROID_KEYSTORE_FILENAME,
          new OdeAsyncCallback<Boolean>(errorMessage) {
            @Override
            public void onSuccess(Boolean keystoreFileExists) {
              if (keystoreFileExists && Window.confirm(MESSAGES.confirmDeleteKeystore())) {
                Tracking.trackEvent(Tracking.USER_EVENT, Tracking.USER_ACTION_DELETE_KEYSTORE);
                Ode.getInstance().getUserInfoService().deleteUserFile(
                    StorageUtil.ANDROID_KEYSTORE_FILENAME,
                    new OdeAsyncCallback<Void>(errorMessage) {
                      @Override
                      public void onSuccess(Void result) {
                        // The android.keystore shouldn't exist at this point, so reset cached values.
                        isKeystoreCached = true;
                        isKeystorePresent = false;
                        isKeystoreCheckPending = false;
                        fileDropDown.setItemEnabled(MESSAGES.deleteKeystoreMenuItem(), false);
                        fileDropDown.setItemEnabled(MESSAGES.downloadKeystoreMenuItem(), false);
                      }
                    });
              }
            }
          });
    }
  }

  /**
   *  Made changes to the now Projects menu made name changes to the menu items
   * Implements the action to generate the ".yail" file for each screen in the current project.
   * It does not build the entire project. The intention is that this will be helpful for
   * debugging during development, and will most likely be disabled in the production system.
   */
  private class GenerateYailAction implements Command {
    @Override
    public void execute() {
      ProjectRootNode projectRootNode = Ode.getInstance().getCurrentYoungAndroidProjectRootNode();
      if (projectRootNode != null) {
        String target = YoungAndroidProjectNode.YOUNG_ANDROID_TARGET_ANDROID;
        ChainableCommand cmd = new SaveAllEditorsCommand(new GenerateYailCommand(null));
        //updateBuildButton(true);
        cmd.startExecuteChain(Tracking.PROJECT_ACTION_BUILD_YAIL_YA, projectRootNode,
            new Command() {
              @Override
              public void execute() {
                //updateBuildButton(false);
              }
            });
      }
    }
  }

  private class EnableAutoloadAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().setUserAutoloadProject(true);
      createSettingsMenu();
    }
  }

  private class DisableAutoloadAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().setUserAutoloadProject(false);
      createSettingsMenu();
    }
  }

  private static class SetFontDyslexicAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().setUserDyslexicFont(true);
      // Window.Location.reload();
      // Note: We used to reload here, but this causes
      // a race condition with the saving of the user
      // settings. So we now reload in the callback to
      // saveSettings (in Ode.java)
    }
  }

  private static class SetFontRegularAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().setUserDyslexicFont(false);
      // Window.Location.reload();
      // Not: See above comment
    }
  }

  private static class AboutAction implements Command {
    @Override
    public void execute() {
      final DialogBox db = new DialogBox(false, true);
      db.setText("About MIT App Inventor");
      db.setStyleName("ode-DialogBox");
      db.setHeight("200px");
      db.setWidth("400px");
      db.setGlassEnabled(true);
      db.setAnimationEnabled(true);
      db.center();

      VerticalPanel DialogBoxContents = new VerticalPanel();
      String html = MESSAGES.gitBuildId(GitBuildId.getDate(), GitBuildId.getVersion()) +
          "<BR/>" + MESSAGES.useCompanion(YaVersion.PREFERRED_COMPANION, YaVersion.PREFERRED_COMPANION + "u") +
          "<BR/>" + MESSAGES.targetSdkVersion(YaVersion.TARGET_SDK_VERSION, YaVersion.TARGET_ANDROID_VERSION);
      Config config = Ode.getInstance().getSystemConfig();
      String releaseNotesUrl = config.getReleaseNotesUrl();
      if (!Strings.isNullOrEmpty(releaseNotesUrl)) {
        html += "<BR/><BR/>Please see <a href=\"" + releaseNotesUrl +
            "\" target=\"_blank\">release notes</a>";
      }
      String tosUrl = config.getTosUrl();
      if (!Strings.isNullOrEmpty(tosUrl)) {
        html += "<BR/><BR/><a href=\"" + tosUrl +
            "\" target=\"_blank\">" + MESSAGES.privacyTermsLink() + "</a>";
      }
      HTML message = new HTML(html);

      SimplePanel holder = new SimplePanel();
      Button ok = new Button("Close");
      ok.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          db.hide();
        }
      });
      holder.add(ok);
      DialogBoxContents.add(message);
      DialogBoxContents.add(holder);
      db.setWidget(DialogBoxContents);
      db.show();
    }
  }

  private static class AboutCompanionAction implements Command {
    @Override
    public void execute() {
      final DialogBox db = new DialogBox(false, true);
      db.setText("About The Companion");
      db.setStyleName("ode-DialogBox");
      db.setHeight("200px");
      db.setWidth("400px");
      db.setGlassEnabled(true);
      db.setAnimationEnabled(true);
      db.center();

      String downloadinfo = "";
      if (!YaVersion.COMPANION_UPDATE_URL1.equals("")) {
        String url = Window.Location.getProtocol() + "//" + Window.Location.getHost()
            + YaVersion.COMPANION_UPDATE_URL1;
        downloadinfo = "<br/>\n<a href=" + url + ">Download URL: " + url + "</a><br/>\n";
        downloadinfo += BlocklyPanel.getQRCode(url);
      }

      VerticalPanel DialogBoxContents = new VerticalPanel();
      HTML message = new HTML(
          "Companion Version " + BlocklyPanel.getCompVersion() + downloadinfo
      );

      SimplePanel holder = new SimplePanel();
      Button ok = new Button("Close");
      ok.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          db.hide();
        }
      });
      holder.add(ok);
      DialogBoxContents.add(message);
      DialogBoxContents.add(holder);
      db.setWidget(DialogBoxContents);
      db.show();
    }
  }

  private static class CompanionUpdateAction implements Command {
    @Override
    public void execute() {
      DesignToolbar.DesignProject currentProject = Ode.getInstance().getDesignToolbar().getCurrentProject();
      if (currentProject == null) {
        Window.alert(MESSAGES.companionUpdateMustHaveProject());
        return;
      }
      DesignToolbar.Screen screen = currentProject.screens.get(currentProject.currentScreen);
      screen.blocksEditor.updateCompanion();
    }
  }

  private static class ShowSplashAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().showWelcomeDialog();
    }
  }

  private static class WindowOpenAction implements Command {
    private final String url;

    WindowOpenAction(String url) {
      this.url = url;
    }

    @Override
    public void execute() {
      Window.open(url, WINDOW_OPEN_LOCATION, WINDOW_OPEN_FEATURES);
    }
  }

  private static class ImportComponentAction implements Command {
    @Override
    public void execute() {
      new ComponentImportWizard().center();
    }
  }

  private static class BuildComponentAction implements Command {
    @Override
    public void execute() {
      // to be added
    }
  }

  private static class UploadComponentAction implements Command {
    @Override
    public void execute() {
      new ComponentUploadWizard().show();
    }
  }

  private void updateConnectToDropDownButton(boolean isEmulatorRunning, boolean isCompanionRunning, boolean isUsbRunning){
    if (!isEmulatorRunning && !isCompanionRunning && !isUsbRunning) {
      connectDropDown.setItemEnabled(MESSAGES.AICompanionMenuItem(), true);
      if (iamChromebook) {
        connectDropDown.setItemEnabled(MESSAGES.chromebookMenuItem(), true);
      } else {
        connectDropDown.setItemEnabled(MESSAGES.emulatorMenuItem(), true);
        connectDropDown.setItemEnabled(MESSAGES.usbMenuItem(), true);
      }
      connectDropDown.setItemEnabled(MESSAGES.refreshCompanionMenuItem(), false);
    } else {
      connectDropDown.setItemEnabled(MESSAGES.AICompanionMenuItem(), false);
      if (iamChromebook) {
        connectDropDown.setItemEnabled(MESSAGES.chromebookMenuItem(), false);
      } else {
        connectDropDown.setItemEnabled(MESSAGES.emulatorMenuItem(), false);
        connectDropDown.setItemEnabled(MESSAGES.usbMenuItem(), false);
      }
      connectDropDown.setItemEnabled(MESSAGES.refreshCompanionMenuItem(), true);
    }
  }

  /**
   * Indicate that we are no longer connected to the Companion, adjust
   * buttons accordingly. Called from BlocklyPanel
   */
  public static void indicateDisconnect() {
    TopToolbar instance = Ode.getInstance().getTopToolbar();
    instance.updateConnectToDropDownButton(false, false, false);
  }

  /**
   * startRepl -- Start/Stop the connection to the companion.
   * If both forEmulator and forUsb are false, then we are connecting
   * via Wireless.
   *
   * @param start -- true to start the repl, false to stop it.
   * @param forChromebook -- true if we are connecting to a chromebook.
   * @param forEmulator -- true if we are connecting to the emulator.
   * @param forUsb -- true if this is a USB connection.
   */

  private void startRepl(boolean start, boolean forChromebook, boolean forEmulator, boolean forUsb) {
    DesignToolbar.DesignProject currentProject = Ode.getInstance().getDesignToolbar().getCurrentProject();
    if (currentProject == null) {
      OdeLog.wlog("DesignToolbar.currentProject is null. "
            + "Ignoring attempt to start the repl.");
      return;
    }
    DesignToolbar.Screen screen = currentProject.screens.get(currentProject.currentScreen);
    screen.blocksEditor.startRepl(!start, forChromebook, forEmulator, forUsb);
    if (start) {
      if (forEmulator) {        // We are starting the emulator...
        updateConnectToDropDownButton(true, false, false);
      } else if (forUsb) {      // We are starting the usb connection
        updateConnectToDropDownButton(false, false, true);
      } else {                  // We are connecting via wifi to a Companion
        updateConnectToDropDownButton(false, true, false);
      }
    } else {
      updateConnectToDropDownButton(false, false, false);
    }
  }

  private void replHardReset() {
    DesignToolbar.DesignProject currentProject = Ode.getInstance().getDesignToolbar().getCurrentProject();
    if (currentProject == null) {
      OdeLog.wlog("DesignToolbar.currentProject is null. "
            + "Ignoring attempt to do hard reset.");
      return;
    }
    DesignToolbar.Screen screen = currentProject.screens.get(currentProject.currentScreen);
    ((YaBlocksEditor)screen.blocksEditor).hardReset();
    updateConnectToDropDownButton(false, false, false);
  }

  private void replUpdate() {
    DesignToolbar.DesignProject currentProject = Ode.getInstance().getDesignToolbar().getCurrentProject();
    if (currentProject == null) {
      OdeLog.wlog("DesignToolbar.currentProject is null. "
              + "Ignoring attempt to refresh companion screen.");
      return;
    }
    DesignToolbar.Screen screen = currentProject.screens.get(currentProject.currentScreen);
    ((YaBlocksEditor)screen.blocksEditor).sendComponentData(true);
  }

  /**
   * Enables and/or disables buttons based on how many projects exist
   * (in the case of "Download All Projects") or are selected (in the case
   * of "Delete" and "Download Source").
   */
  public void updateFileMenuButtons(int view) {
    if (isReadOnly) {
      // This may be too simple
      return;
    }
    if (view == 0) {  // We are in the Projects view
      fileDropDown.setItemEnabled(MESSAGES.deleteProjectButton(), false);
      fileDropDown.setItemEnabled(MESSAGES.deleteFromTrashButton(), false);
      fileDropDown.setItemEnabled(MESSAGES.trashProjectMenuItem(),
          ProjectListBox.getProjectListBox().getProjectList().getMyProjectsCount() == 0);
      fileDropDown.setItemEnabled(MESSAGES.exportAllProjectsMenuItem(),
      ProjectListBox.getProjectListBox().getProjectList().getMyProjectsCount() > 0);
      fileDropDown.setItemEnabledById(WIDGET_NAME_EXPORTPROJECT, false);
      fileDropDown.setItemEnabled(MESSAGES.saveMenuItem(), false);
      fileDropDown.setItemEnabled(MESSAGES.saveAsMenuItem(), false);
      fileDropDown.setItemEnabled(MESSAGES.checkpointMenuItem(), false);
      buildDropDown.setItemEnabled(MESSAGES.showExportAndroidApk(), false);
      buildDropDown.setItemEnabled(MESSAGES.showExportAndroidAab(), false);
      if (Ode.getInstance().hasSecondBuildserver()) {
        buildDropDown.setItemEnabled(MESSAGES.showExportAndroidApk2(), false);
        buildDropDown.setItemEnabled(MESSAGES.showExportAndroidAab2(), false);
      }
    } else { // We have to be in the Designer/Blocks view
      fileDropDown.setItemEnabled(MESSAGES.deleteProjectButton(), true);
      fileDropDown.setItemEnabled(MESSAGES.trashProjectMenuItem(), true);
      fileDropDown.setItemEnabled(MESSAGES.exportAllProjectsMenuItem(),
      ProjectListBox.getProjectListBox().getProjectList().getMyProjectsCount() > 0);
      fileDropDown.setItemEnabledById(WIDGET_NAME_EXPORTPROJECT, true);
      fileDropDown.setItemEnabled(MESSAGES.saveMenuItem(), true);
      fileDropDown.setItemEnabled(MESSAGES.saveAsMenuItem(), true);
      fileDropDown.setItemEnabled(MESSAGES.checkpointMenuItem(), true);
      buildDropDown.setItemEnabled(MESSAGES.showExportAndroidApk(), true);
      buildDropDown.setItemEnabled(MESSAGES.showExportAndroidAab(), true);
      if (Ode.getInstance().hasSecondBuildserver()) {
        buildDropDown.setItemEnabled(MESSAGES.showExportAndroidApk2(), true);
        buildDropDown.setItemEnabled(MESSAGES.showExportAndroidAab2(), true);
      }
    }
    updateKeystoreFileMenuButtons(true);
  }

  /**
   * Enables or disables buttons based on whether the user has an android.keystore file.
   */
  public void updateKeystoreFileMenuButtons() {
    Ode.getInstance().getUserInfoService().hasUserFile(StorageUtil.ANDROID_KEYSTORE_FILENAME,
        new AsyncCallback<Boolean>() {
          @Override
          public void onSuccess(Boolean keystoreFileExists) {
            isKeystoreCached = true;
            isKeystorePresent = keystoreFileExists;
            fileDropDown.setItemEnabled(MESSAGES.deleteKeystoreMenuItem(), keystoreFileExists);
            fileDropDown.setItemEnabled(MESSAGES.downloadKeystoreMenuItem(), keystoreFileExists);
          }

          @Override
          public void onFailure(Throwable caught) {
            // Enable the MenuItems. If they are clicked, we'll check again if the keystore exists.
            fileDropDown.setItemEnabled(MESSAGES.deleteKeystoreMenuItem(), true);
            fileDropDown.setItemEnabled(MESSAGES.downloadKeystoreMenuItem(), true);
          }
        });
  }

  /**
   * Enables or disables buttons based on whether the user has an android.keystore file. If the
   * useCache parameter is true, then the last value returned from the UserInfoService is used.
   * Otherwise, the behavior is identical to {@link #updateKeystoreFileMenuButtons()}.
   *
   * @param useCache true if a cached value of a previous call is acceptable.
   */
  public void updateKeystoreFileMenuButtons(boolean useCache) {
    if (useCache && isKeystoreCheckPending) {
      return;
    }
    AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
      @Override
      public void onSuccess(Boolean keystoreFileExists) {
        isKeystoreCached = true;
        isKeystorePresent = keystoreFileExists;
        isKeystoreCheckPending = false;
        fileDropDown.setItemEnabled(MESSAGES.deleteKeystoreMenuItem(), keystoreFileExists);
        fileDropDown.setItemEnabled(MESSAGES.downloadKeystoreMenuItem(), keystoreFileExists);
      }

      @Override
      public void onFailure(Throwable caught) {
        // Enable the MenuItems. If they are clicked, we'll check again if the keystore exists.
        isKeystoreCached = false;
        isKeystorePresent = true;
        isKeystoreCheckPending = false;
        fileDropDown.setItemEnabled(MESSAGES.deleteKeystoreMenuItem(), true);
        fileDropDown.setItemEnabled(MESSAGES.downloadKeystoreMenuItem(), true);
      }
    };
    if (useCache && isKeystoreCached) {
      callback.onSuccess(isKeystorePresent);
    } else {
      isKeystoreCheckPending = true;
      Ode.getInstance().getUserInfoService().hasUserFile(StorageUtil.ANDROID_KEYSTORE_FILENAME,
          callback);
    }
  }

  //Admin commands
  private static class DownloadUserSourceAction implements Command {
    @Override
    public void execute() {
      new DownloadUserSourceWizard().center();
    }
  }

  private static class SwitchToDebugAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().switchToDebuggingView();
    }
  }

  private static class SwitchToUserAdminAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().switchToUserAdminPanel();
    }
  }

  public static class DeleteForeverProjectAction implements Command {
    @Override
    public void execute() {
      Ode.getInstance().getEditorManager().saveDirtyEditors(new Command() {
        @Override
        public void execute() {
          List<Project> deletedProjects = ProjectListBox.getProjectListBox().getProjectList().getSelectedProjects();
          if (deletedProjects.size() > 0) {
            // Show one confirmation window for selected projects.
            if (deleteConfirmation(deletedProjects)) {
              for (Project project : deletedProjects) {
                project.deleteFromTrash();
              }
            }
            Ode.getInstance().switchToTrash();
          } else {
            // The user can select a project to resolve the
            // error.
            ErrorReporter.reportInfo(MESSAGES.noProjectSelectedForDelete());
          }
        }
      });
    }

    private boolean deleteConfirmation(List<Project> projects) {
      String message;
      if (projects.size() == 1) {
        message = MESSAGES.confirmDeleteSingleProject(projects.get(0).getProjectName());
      } else {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (Project project : projects) {
          sb.append(separator).append(project.getProjectName());
          separator = ", ";
        }
        String projectNames = sb.toString();
        message = MESSAGES.confirmDeleteManyProjects(projectNames);
      }
      return Window.confirm(message);
    }
  }

  private static native boolean isChromeBook() /*-{
    if (/\bCrOS\b/.test(navigator.userAgent)) {
      return true;
    } else {
      return false;
    }
  }-*/;

}
