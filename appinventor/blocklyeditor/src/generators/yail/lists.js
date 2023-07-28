// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2012 Massachusetts Institute of Technology. All rights reserved.

/**
 * @license
 * @fileoverview Lists blocks yail generators for Blockly, modified for MIT App Inventor.
 * @author mckinney@mit.edu (Andrew F. McKinney)
 */

'use strict';

goog.provide('Blockly.Yail.lists');

Blockly.Yail.emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
Blockly.Yail.emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
Blockly.Yail.emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
Blockly.Yail.emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
Blockly.Yail.emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;


Blockly.Yail['lists_create_with'] = function() {

  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
  code += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  var itemsAdded = 0;
  for(var i=0;i<this.itemCount_;i++) {
    var argument = Blockly.Yail.valueToCode(this, 'ADD' + i, Blockly.Yail.ORDER_NONE) || null;
    if(argument != null){
      code += argument + Blockly.Yail.YAIL_SPACER;
      itemsAdded++;
    }
  }
  code += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  for(var i=0;i<itemsAdded;i++) {
    code += "any" + Blockly.Yail.YAIL_SPACER;
  }
  code += Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];

};

Blockly.Yail['lists_select_item'] = function() {
  // Select from list an item.

  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'NUM', Blockly.Yail.ORDER_NONE) || 1;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-get-item" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + argument1 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list number" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "select list item" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_replace_item'] = function() {
  // Replace Item in list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'NUM', Blockly.Yail.ORDER_NONE) || 1;
  var argument2 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-set-item!" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0 + Blockly.Yail.YAIL_SPACER + argument1
  code = code + Blockly.Yail.YAIL_SPACER + argument2 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list number any" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "replace list item" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return code;
};

Blockly.Yail['lists_remove_item'] = function() {
  // Remove Item in list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'INDEX', Blockly.Yail.ORDER_NONE) || 1;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-remove-item!" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + argument1 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list number" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "remove list item" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return code;
};

Blockly.Yail['lists_insert_item'] = function() {
  // Insert Item in list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'INDEX', Blockly.Yail.ORDER_NONE) || 1;
  var argument2 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-insert-item!" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0 + Blockly.Yail.YAIL_SPACER + argument1;
  code = code + Blockly.Yail.YAIL_SPACER + argument2 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list number any" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "insert list item" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return code;
};

Blockly.Yail['lists_length'] = function() {
  // Length of list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-length" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "length of list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_append_list'] = function() {
  // Append to list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST0', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'LIST1', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-append!" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER;
  code = code + argument1 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "append to list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return code;
};

Blockly.Yail['lists_add_items'] = function() {
  // Add items to list.
  // TODO: (Andrew) Make this handle multiple items.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || 1;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-add-to-list!" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0 + Blockly.Yail.YAIL_SPACER;

  for(var i=0;i<this.itemCount_;i++) {
    var argument = Blockly.Yail.valueToCode(this, 'ITEM' + i, Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
    code += argument + Blockly.Yail.YAIL_SPACER;
  }

  code += Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list ";
  for(var i=0;i<this.itemCount_;i++) {
    code += "any" + Blockly.Yail.YAIL_SPACER;
  }
  code += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "add items to list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return code;
};

Blockly.Yail['lists_is_in'] = function() {
  // Is in list?.
  var argument0 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
  var argument1 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-member?" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + argument1 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "any list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "is in list?" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_position_in'] = function() {
  // Postion of item in list.
  // This block is now called 'index in list"
  // It used to be called "position in list"
  var argument0 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || 1;
  var argument1 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-index" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + argument1 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "any list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "index in list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_pick_random_item'] = function() {
  // Pick random item
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-pick-random" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "pick random item" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_is_empty'] = function() {
  // Is the list empty?.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-empty?" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "is list empty?" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_copy'] = function() {
  // Make a copy of list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-copy" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "copy list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_is_list'] = function() {
  // Create an empty list.
  // TODO:(Andrew) test whether thing is var or text or number etc...
  var argument0 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list?" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "any" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "is a list?" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_reverse'] = function() {
  // Reverse the list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-reverse" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "reverse list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_to_csv_row'] = function() {
  // Make a csv row from list.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-to-csv-row" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "list to csv row" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_to_csv_table'] = function() {
  // Make a csv table from list
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-to-csv-table" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "list to csv table" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_from_csv_row'] = function() {
  // Make list from csv row.
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-from-csv-row" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "text" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "list from csv row" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_from_csv_table'] = function() {
  // Make list from csv table.
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-from-csv-table" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "text" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "list from csv table" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_lookup_in_pairs'] = function() {
  // Lookup in pairs in list of lists (key, value).
  var argument0 = Blockly.Yail.valueToCode(this, 'KEY', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
  var argument1 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument2 = Blockly.Yail.valueToCode(this, 'NOTFOUND', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_NULL;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-alist-lookup" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0 + Blockly.Yail.YAIL_SPACER + argument1 + Blockly.Yail.YAIL_SPACER + argument2 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "any list any" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "lookup in pairs" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_join_with_separator'] = function() {
  // Joins list items into a string separated by specified separator
  var argument0 = Blockly.Yail.valueToCode(this, 'SEPARATOR', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-join-with-separator" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument1 + Blockly.Yail.YAIL_SPACER;
  code = code + argument0 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + "list" + Blockly.Yail.YAIL_SPACER + "text" + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "join with separator" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_map'] = function() {
  // Map the list with given expression
  var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
  emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
  emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  var loopIndexName = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR');
  var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
  var bodyCode = Blockly.Yail.valueToCode(this, 'TO', Blockly.Yail.ORDER_NONE) ||  Blockly.Yail.YAIL_FALSE;
  var code = Blockly.Yail.YAIL_MAP + loopIndexName + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
      + listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_filter'] = function() {
  // Filter the list
	var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
	var loopIndexName = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR');
	var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
	var bodyCode = Blockly.Yail.valueToCode(this, 'TEST', Blockly.Yail.ORDER_NONE) ||  Blockly.Yail.YAIL_FALSE;
	var code = Blockly.Yail.YAIL_FILTER + loopIndexName + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
    	+ listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
    return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_reduce'] = function() {
    // Reduce the list
	var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
	var loopIndexName1 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR1');
	var loopIndexName2 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR2');
	var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
	var initAnswerCode = Blockly.Yail.valueToCode(this, 'INITANSWER', Blockly.Yail.ORDER_NONE);
	var bodyCode = Blockly.Yail.valueToCode(this, 'COMBINE', Blockly.Yail.ORDER_NONE) ||  Blockly.Yail.YAIL_FALSE;
	var code = Blockly.Yail.YAIL_REDUCE + initAnswerCode + Blockly.Yail.YAIL_SPACER + loopIndexName2 + Blockly.Yail.YAIL_SPACER
				+ loopIndexName1 + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
				+ listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
    return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_sort'] = function() {
  // Sort the list in ascending order
	var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
	var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-sort";
	code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
	code = code + argument0;
	code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
	code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
	code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
	code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "sort " + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
    return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_sort_comparator'] = function() {
  // Sort the list with specified comparator
	var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
	var loopIndexName1 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR1');
	var loopIndexName2 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR2');
	var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
	var bodyCode = Blockly.Yail.valueToCode(this, 'COMPARE', Blockly.Yail.ORDER_NONE) ||  Blockly.Yail.YAIL_FALSE;
	var code = Blockly.Yail.YAIL_SORT_COMPARATOR_NONDEST + loopIndexName1 + Blockly.Yail.YAIL_SPACER + loopIndexName2 + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
    	+ listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
    return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_sort_key'] = function() {
  // Sorting the list using the key, a proxy value user creates with expressions.
	var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
	emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "make a list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
	var loopIndexName = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR');
	var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
	var bodyCode = Blockly.Yail.valueToCode(this, 'KEY', Blockly.Yail.ORDER_NONE) ||  Blockly.Yail.YAIL_FALSE;
	var code = Blockly.Yail.YAIL_SORT_KEY_NONDEST + loopIndexName + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
    	+ listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
    return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_minimum_value'] = function() {
  // Minimum number in the list
  var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
  emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
  emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "minimum value of list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  var loopIndexName1 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR1');
  var loopIndexName2 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR2');
  var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
  var bodyCode = Blockly.Yail.valueToCode(this, 'COMPARE', Blockly.Yail.ORDER_NONE) ||
    ('(call-yail-primitive < (*list-for-runtime* (lexical-value ' + loopIndexName1 + ') (lexical-value ' + loopIndexName2 + ')) \'(number number) "<")');
  var code = "(mincomparator-nondest " + loopIndexName1 + Blockly.Yail.YAIL_SPACER + loopIndexName2 + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
      + listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_maximum_value'] = function() {
  // Maximum number in the list
  var emptyListCode = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "make-yail-list" + Blockly.Yail.YAIL_SPACER;
  emptyListCode += Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  emptyListCode += Blockly.Yail.YAIL_CLOSE_COMBINATION;
  emptyListCode += Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_DOUBLE_QUOTE + "maximum value of list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  var loopIndexName1 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR1');
  var loopIndexName2 = Blockly.Yail.YAIL_LOCAL_VAR_TAG + this.getFieldValue('VAR2');
  var listCode = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || emptyListCode;
  var bodyCode = Blockly.Yail.valueToCode(this, 'COMPARE', Blockly.Yail.ORDER_NONE) ||
    ('(call-yail-primitive < (*list-for-runtime* (lexical-value ' + loopIndexName1 + ') (lexical-value ' + loopIndexName2 + ')) \'(number number) "<")');
  var code = "(maxcomparator-nondest " + loopIndexName1 + Blockly.Yail.YAIL_SPACER + loopIndexName2 + Blockly.Yail.YAIL_SPACER + bodyCode + Blockly.Yail.YAIL_SPACER
      + listCode + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_but_first'] = function() {
  // Return the list without the first element
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-but-first";
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "butFirst of list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_but_last'] = function() {
  // Return the list without the last element
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-but-last";
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "butLast of list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['lists_slice'] = function() {
  // Slices list at the two given index.
  var argument0 = Blockly.Yail.valueToCode(this, 'LIST', Blockly.Yail.ORDER_NONE) || Blockly.Yail.emptyListCode;
  var argument1 = Blockly.Yail.valueToCode(this, 'INDEX1', Blockly.Yail.ORDER_NONE) || 1;
  var argument2 = Blockly.Yail.valueToCode(this, 'INDEX2', Blockly.Yail.ORDER_NONE) || 1;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "yail-list-slice";
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0 + Blockly.Yail.YAIL_SPACER + argument1;
  code = code + Blockly.Yail.YAIL_SPACER + argument2 + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "list number number" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "slice of list" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};
