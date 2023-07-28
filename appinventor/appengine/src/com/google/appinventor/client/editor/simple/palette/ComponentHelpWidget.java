// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2018 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.editor.simple.palette;

import com.google.appinventor.client.ComponentsTranslation;
import com.google.appinventor.client.Ode;
import com.google.appinventor.client.utils.PZAwarePositionCallback;
import com.google.common.base.Strings;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import static com.google.appinventor.client.Ode.MESSAGES;

/**
 * Defines a widget that has the appearance of a question mark and
 * creates a popup with information about a component when it is clicked on.
 *
 */
public final class ComponentHelpWidget extends AbstractPaletteItemWidget {
  private static final ImageResource imageResource = Ode.getImageBundle().help();

  // Keep track of the last time (in milliseconds) of the last closure
  // so we don't reopen a popup too soon after closing it.  Specifically,
  // if a user clicks on the question-mark icon to close a popup, we
  // don't want the question-mark click to reopen it.
  private long lastClosureTime = 0;

  private class ComponentHelpPopup extends PopupPanel {

    private ComponentHelpPopup() {
      // Create popup panel.
      super(true);
      setStyleName("ode-ComponentHelpPopup");
      setTitle(scd.getName());

      // Create title from component name.
      Label titleBar = new Label(ComponentsTranslation.getComponentName(scd.getName()));
      setTitle(scd.getName());
      titleBar.setStyleName("ode-ComponentHelpPopup-TitleBar");

      // Create content from help string.
      String helpTextKey = scd.getExternal() ? scd.getHelpString() : scd.getName();
      HTML helpText = new HTML(ComponentsTranslation.getComponentHelpString(helpTextKey));
      helpText.setStyleName("ode-ComponentHelpPopup-Body");

      // Create panel to hold the above three widgets and act as the
      // popup's widget.
      VerticalPanel inner = new VerticalPanel();
      inner.add(titleBar);
      inner.add(helpText);

      // Create link to more information.  This would be cleaner if
      // GWT supported String.format.
      String referenceComponentsUrl = Ode.getSystemConfig().getReferenceComponentsUrl();
      String url = null;
      int version = -1;
      if (scd.getExternal()) {  // extensions will not have documentation hosted in ai2
        url = scd.getHelpUrl().isEmpty() ? null : scd.getHelpUrl();
        if (url != null) {
          if (!url.startsWith("http:") && !url.startsWith("https:")) {
            url = null;
          } else {
            // prevent embedded HTML tags, e.g. <script> in the URL
            url = url.replaceAll("<", "%3C")
                .replaceAll(">", "%3E")
                .replaceAll("\"", "%22");
          }
        }
        version = scd.getVersion();
      } else if (!Strings.isNullOrEmpty(referenceComponentsUrl)) {
        if (!referenceComponentsUrl.endsWith("/")) {
          referenceComponentsUrl += "/";
        }
        String categoryDocUrlString = scd.getCategoryDocUrlString();
        url = (categoryDocUrlString == null)
            ? referenceComponentsUrl + "index.html"
            : referenceComponentsUrl + categoryDocUrlString + ".html#" + scd.getName();
      }
      if (!scd.getVersionName().equals("")) {
        HTML html = new HTML("<b>" + MESSAGES.externalComponentVersion() + "</b> " +
            scd.getVersionName());
        html.setStyleName("ode-ComponentHelpPopup-Body");
        inner.add(html);
      } else if (version > 0) {
        HTML html = new HTML("<b>" + MESSAGES.externalComponentVersion() + "</b> " + version);
        html.setStyleName("ode-ComponentHelpPopup-Body");
        inner.add(html);
      }
      if (scd.getExternal() && scd.getDateBuilt() != null && !scd.getDateBuilt().equals("")) {
        String date = scd.getDateBuilt().split("T")[0];
        HTML dateCreatedHtml = new HTML("<b>" + MESSAGES.dateBuilt() + "</b> <time datetime=\"" + scd.getDateBuilt() + "\">" + date + "</time>");
        dateCreatedHtml.setStyleName("ode-ComponentHelpPopup-Body");
        inner.add(dateCreatedHtml);
      }
      if (url != null) {  // only show if there is a relevant URL
        HTML link = new HTML("<a href=\"" + url + "\" target=\"_blank\">" +
            MESSAGES.moreInformation() + "</a>");
        link.setStyleName("ode-ComponentHelpPopup-Link");
        inner.add(link);
      }
      if (scd.getExternal() && !"".equals(scd.getLicense())) {
        String license = scd.getLicense();
        HTML viewLicenseHTML = new HTML("<a href=\"" + license + "\" target=\"_blank\">" +
            MESSAGES.viewLicense() + "</a>");
        viewLicenseHTML.setStyleName("ode-ComponentHelpPopup-Link");
        inner.add(viewLicenseHTML);
      }

      setWidget(inner);

      // When the panel is closed, save the time in milliseconds.
      // This will help us avoid immediately reopening it if the user
      // closed it by clicking on the question-mark icon.
      addCloseHandler(new CloseHandler<PopupPanel>() {
          @Override
          public void onClose(CloseEvent<PopupPanel> event) {
            lastClosureTime = System.currentTimeMillis();
          }
        });

      // Use a Pinch Zoom aware PopupPanel.PositionCallback to handle positioning to
      // avoid the Google Chrome Pinch Zoom bug.
      setPopupPositionAndShow(new PZAwarePositionCallback(ComponentHelpWidget.this.getElement()) {
        @Override
        public void setPosition(int offsetWidth, int offsetHeight) {
          // Position the upper-left of the panel just to the right of the
          // question-mark icon, unless that would make it too low.
          final int X_OFFSET = 20;
          final int Y_OFFSET = -5;
          if(Window.Navigator.getUserAgent().contains("Chrome") && isPinchZoomed()) {
            setPopupPosition(getTrueAbsoluteLeft() + 1 + X_OFFSET,
                Math.min(getTrueAbsoluteTop() + 1 + Y_OFFSET,
                    Math.max(0, Window.getClientHeight()
                        - offsetHeight + Y_OFFSET)));
          } else {
            setPopupPosition(ComponentHelpWidget.this.getAbsoluteLeft() + X_OFFSET,
                Math.min(ComponentHelpWidget.this.getAbsoluteTop() + Y_OFFSET,
                    Math.max(0, Window.getClientHeight()
                        - offsetHeight + Y_OFFSET)));
          }
        }
      });
    }
  }

  public ComponentHelpWidget(final SimpleComponentDescriptor scd) {
    super(scd, imageResource);
  }

  @Override
  protected void handleClick() {
    final long MINIMUM_MS_BETWEEN_SHOWS = 250;  // .25 seconds

    if (System.currentTimeMillis() - lastClosureTime >=
        MINIMUM_MS_BETWEEN_SHOWS) {
      new ComponentHelpPopup();
    }
  }
}
