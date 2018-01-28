// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2013-2017 Massachusetts Institute of Technology, All rights reserved.
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.widgets;

import com.google.appinventor.client.utils.PZAwarePositionCallback;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;

import com.google.gwt.user.client.ui.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a drop-down button with its associated menu. Note
 * that all items in the menu should have unique captions for removeItem
 * and setItemEnabled to work properly.
 */
public class DropDownButton extends TextButton {

  private final ContextMenu menu;
  private final List<MenuItem> items;
  private final boolean rightAlign;

  public static class DropDownItem {
    private final String widgetName;
    private String caption;
    private final Command command;

    public DropDownItem(String widgetName, String caption, Command command) {
      this.widgetName = widgetName;
      this.caption = caption;
      this.command = command;
    }
  }

  /**
   * A subclass of PZAwarePositionCallback designed to position the ContextMenu
   * of a DropDownButton.
   */
  private class DropDownPositionCallback extends PZAwarePositionCallback {
    public DropDownPositionCallback(Element elem) {
      super(elem);
    }

    /**
     * @param offsetWidth width of the ContextMenu being positioned on the parent element
     * @param offsetHeight height of the ContextMenu being positioned on the parent element
     * Sets the position of the ContextMenu on the screen given it's dimensions
     */
    @Override
    public void setPosition(int offsetWidth, int offsetHeight) {

      // getAbsoluteLeft/Right() gives the top coordinate of the parent element
      // getOffsetWidth/Height() gives the width/height of the parent element
      int left = Window.Navigator.getUserAgent().contains("Chrome") && isPinchZoomed()
              ? getTrueAbsoluteLeft() : getAbsoluteLeft();

      if (rightAlign) {
        left += getOffsetWidth() - offsetWidth;
      }

      int top = Window.Navigator.getUserAgent().contains("Chrome") && isPinchZoomed()
              ? getTrueAbsoluteTop() + getOffsetHeight()
              : getAbsoluteTop() + getOffsetHeight();

      // Values to determine how to display the ContextMenu - above or below

      int dropDownBottom = top + offsetHeight;
      int screenBottom  = Window.getScrollTop()+Window.getClientHeight();

      // if the bottom will go off the current browser screen, display
      // the dropdown as a 'dropup' where the ContextMenu appears
      // above instead

      if(dropDownBottom > screenBottom) {
          int newTop = Window.Navigator.getUserAgent().contains("Chrome") && isPinchZoomed()
            ? getTrueAbsoluteTop() -offsetHeight
            : getAbsoluteTop() - offsetHeight;

          // account for the extreeeemely unlikely case where newTop
          // also goes off the screen in this case, it makes more
          // sense to just go off the bottom of the screen (the screen
          // won't grow up, and so the menu would get completely cut
          // off at the top

          if(newTop >= 0) {
              top = newTop;
          }
      }

      menu.setPopupPosition(left, top);

    }
  }

  // Create a new drop-down menu button (with text), initially populated with items. Null
  // items in the list cause a separator to be added at that position.
  public DropDownButton(String widgetName, String caption, List<DropDownItem> toolbarItems,
                        boolean rightAlign) {
    super(caption + " \u25BE ");  // drop down triangle

    this.menu = new ContextMenu();
    this.items = new ArrayList<MenuItem>();
    this.rightAlign = rightAlign;

    for (DropDownItem item : toolbarItems) {
      if (item != null) {
        this.items.add(menu.addItem(item.caption, true, item.command));
      } else {
        menu.addSeparator();
      }
    }

    addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        menu.setPopupPositionAndShow(new DropDownPositionCallback(getElement()));
      }
    });
  }

  public DropDownButton(String widgetName, String caption, List<DropDownItem> toolbarItems,
                        boolean rightAlign, boolean hasTriangle) {
    this(widgetName, caption, toolbarItems, rightAlign);

    if (!hasTriangle) {
      setText(caption);
    }
  }

  public DropDownButton(String widgetName, String caption, List<DropDownItem> toolbarItems,
                        boolean rightAlign, boolean hasTriangle, boolean hasHtmlCaption) {
    this(widgetName, caption, toolbarItems, rightAlign);

    if (hasHtmlCaption) {

      // Set the button's caption as an HTML String with or without a dropdown triangle
      if (hasTriangle)
        setCaption(caption);
      else
        setHTML(caption);
    }
  }

  // Create a new drop-down menu button (with image), initially populated with items. Null
  // items in the list cause a separator to be added at that position.
  public DropDownButton(String widgetName, Image icon, List<DropDownItem> toolbarItems,
                        boolean rightAlign) {
    super(icon);  // icon for button

    this.menu = new ContextMenu();
    this.items = new ArrayList<MenuItem>();
    this.rightAlign = rightAlign;

    for (DropDownItem item : toolbarItems) {
      if (item != null) {
        addItem(item);
      } else {
        menu.addSeparator();
      }
    }

    addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        menu.setPopupPositionAndShow(new DropDownPositionCallback(getElement()));
      }
    });
  }

  public void clearAllItems() {
    for (MenuItem item : items) {
      menu.removeItem(item);
    }
    items.clear();
  }

  public void addItem(DropDownItem item) {
    items.add(menu.addItem(item.caption, true, item.command));
  }

  public void removeItem(String itemName) {
    for (MenuItem item : items) {
      if (item.getText().equals(itemName)) {
        menu.removeItem(item);
        items.remove(item);
        break;
      }
    }
  }

  public void setItemEnabled(String itemName, boolean enabled) {
    for (MenuItem item : items) {
      if (item.getText().equals(itemName)) {
        item.setEnabled(enabled);
        break;
      }
    }
  }

  public void replaceLastItem(DropDownItem item) {
    menu.removeItem(items.get(items.size()-1));
    items.remove(items.size()-1);
    items.add(menu.addItem(item.caption, true, item.command));
  }

  public void setCaption(String caption) {
    this.setText(caption + " \u25BE ");
  }

  public ContextMenu getContextMenu() {
    return menu;
  }
}
