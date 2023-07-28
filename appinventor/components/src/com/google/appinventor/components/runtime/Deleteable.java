// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.components.runtime;

/**
 * Interface for components that need to do something when they are dynamically deleted (most
 * likely by the REPL)
 *
 * @author markf@google.com (Mark Friedman)
 */
public interface Deleteable {
  void onDelete();
}
