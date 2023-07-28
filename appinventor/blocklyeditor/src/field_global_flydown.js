// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2013-2016 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0
/**
 * @license
 * @fileoverview Clickable field with flydown menu of global getter and setter blocks.
 * @author fturbak@wellesley.edu (Lyn Turbak)
 */

'use strict';

goog.provide('AI.Blockly.FieldGlobalFlydown');

goog.require('AI.Blockly.FieldFlydown');

/**
 * Class for a clickable global variable declaration field.
 * @param {string} text The initial parameter name in the field.
 * @extends {Blockly.Field}
 * @constructor
 */
Blockly.FieldGlobalFlydown = function(name, displayLocation) {
  Blockly.FieldGlobalFlydown.superClass_.constructor.call(this, name, true, displayLocation,
      // rename all references to this global variable
      Blockly.LexicalVariable.renameGlobal)
};
goog.inherits(Blockly.FieldGlobalFlydown, Blockly.FieldFlydown);

Blockly.FieldGlobalFlydown.prototype.fieldCSSClassName = 'blocklyFieldParameter'

Blockly.FieldGlobalFlydown.prototype.flyoutCSSClassName = 'blocklyFieldParameterFlydown'

/**
 * Block creation menu for global variables
 * Returns a list of two XML elements: a getter block for name and a setter block for this parameter field.
 *  @return {!Array.<string>} List of two XML elements.
 **/
Blockly.FieldGlobalFlydown.prototype.flydownBlocksXML_ = function() {
  var name = Blockly.Msg.LANG_VARIABLES_GLOBAL_PREFIX + " " + this.getText(); // global name for this parameter field.
  var getterSetterXML =
      '<xml>' +
        '<block type="lexical_variable_get">' +
          '<title name="VAR">' +
            name +
          '</title>' +
        '</block>' +
        '<block type="lexical_variable_set">' +
          '<title name="VAR">' +
            name +
          '</title>' +
        '</block>' +
      '</xml>';
  return getterSetterXML;
}


