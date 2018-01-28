// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2012-2017 Massachusetts Institute of Technology. All rights reserved.

/**
 * @license
 * @fileoverview Text blocks yail generators for Blockly, modified for MIT App Inventor.
 * @author mckinney@mit.edu (Andrew F. McKinney)
 */

'use strict';

goog.provide('Blockly.Yail.text');

Blockly.Yail['text'] = function() {
  // Text value.
  var code = Blockly.Yail.quote_(this.getFieldValue('TEXT'));
  return [code, Blockly.Yail.ORDER_ATOMIC];
};

Blockly.Yail['text_join'] = function() {
  // Create a string made up of elements of any type..
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-append"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;

  for(var i=0;i<this.itemCount_;i++) {
    var argument = Blockly.Yail.valueToCode(this, 'ADD' + i, Blockly.Yail.ORDER_NONE) || "\"\"";
    code += argument + Blockly.Yail.YAIL_SPACER;
  }
  code += Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION;
  for(var i=0;i<this.itemCount_;i++) {
    code += "text" + Blockly.Yail.YAIL_SPACER;
  }
  code += Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "join"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_length'] = function() {
  // // String length
  var argument = Blockly.Yail.valueToCode(this, 'VALUE', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-length"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "length"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_isEmpty'] = function() {
  // Is the string null?
  var argument = Blockly.Yail.valueToCode(this, 'VALUE', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-empty?"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "is text empty?"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_compare'] = function() {
  // Basic compare operators
  var mode = this.getFieldValue('OP');
  var prim = Blockly.Yail.text_compare.OPERATORS[mode];
  var operator1 = prim[0];
  var operator2 = prim[1];
  var order = prim[2];
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT1', order) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'TEXT2', order) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + operator1
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument0 + Blockly.Yail.YAIL_SPACER + argument1
      + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + operator2
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_compare'].OPERATORS = {
  LT: ['string<?', 'text<', Blockly.Yail.ORDER_NONE],
  GT: ['string>?', 'text>', Blockly.Yail.ORDER_NONE],
  EQUAL: ['string=?', 'text=', Blockly.Yail.ORDER_NONE]
};

Blockly.Yail['text_trim'] = function() {
  // String trim
  var argument = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-trim"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "trim"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_changeCase'] = function() {
  // String change case.
  var mode = this.getFieldValue('OP');
  var tuple = Blockly.Yail.text_changeCase.OPERATORS[mode];
  var operator1 = tuple[0];
  var operator2 = tuple[1];
  var order = tuple[2];
  var argument = Blockly.Yail.valueToCode(this, 'TEXT', order) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + operator1
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + operator2
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_changeCase'].OPERATORS = {
  UPCASE: ['string-to-upper-case', 'upcase', Blockly.Yail.ORDER_NONE],
  DOWNCASE: ['string-to-lower-case', 'downcase', Blockly.Yail.ORDER_NONE]
};

Blockly.Yail.text_starts_at = function() {
  // String starts at
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'PIECE', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-starts-at"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument0 + Blockly.Yail.YAIL_SPACER + argument1
      + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "starts at"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_contains'] = function() {
  // String contains.
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'PIECE', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-contains"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument0 + Blockly.Yail.YAIL_SPACER + argument1
      + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "contains"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_split'] = function() {
  // String split operations.
  // Note that the type of arg2 might be text or list, depending on the dropdown selection
  var mode = this.getFieldValue('OP');
  var tuple = Blockly.Yail.text_split.OPERATORS[mode];
  var operator1 = tuple[0];
  var operator2 = tuple[1];
  var order = tuple[2];
  var arg2Type = tuple[3];
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'AT', Blockly.Yail.ORDER_NONE) || 1;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + operator1
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument0 + Blockly.Yail.YAIL_SPACER + argument1
      + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text" +  Blockly.Yail.YAIL_SPACER + arg2Type
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + operator2
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, order ];
};

Blockly.Yail['text_split'].OPERATORS = {
  SPLITATFIRST : [ 'string-split-at-first', 'split at first',
      Blockly.Yail.ORDER_ATOMIC, 'text' ],
  SPLITATFIRSTOFANY : [ 'string-split-at-first-of-any',
      'split at first of any', Blockly.Yail.ORDER_ATOMIC, 'list' ],
  SPLIT : [ 'string-split', 'split', Blockly.Yail.ORDER_ATOMIC, 'text' ],
  SPLITATANY : [ 'string-split-at-any', 'split at any', Blockly.Yail.ORDER_ATOMIC, 'list' ]
};

Blockly.Yail['text_split_at_spaces'] = function() {
  // split at spaces
  var argument = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-split-at-spaces"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "split at spaces"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_segment'] = function() {
  // Create string segment
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'START', Blockly.Yail.ORDER_NONE) || 1;
  var argument2 = Blockly.Yail.valueToCode(this, 'LENGTH', Blockly.Yail.ORDER_NONE) || 1;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-substring"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument0 + Blockly.Yail.YAIL_SPACER + argument1
      + Blockly.Yail.YAIL_SPACER + argument2
      + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text number number"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "segment"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_replace_all'] = function() {
  // String replace with segment
  var argument0 = Blockly.Yail.valueToCode(this, 'TEXT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument1 = Blockly.Yail.valueToCode(this, 'SEGMENT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var argument2 = Blockly.Yail.valueToCode(this, 'REPLACEMENT', Blockly.Yail.ORDER_NONE) || "\"\"";
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string-replace-all"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument0 + Blockly.Yail.YAIL_SPACER + argument1
      + Blockly.Yail.YAIL_SPACER + argument2
      + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text text text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "replace all"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['obfuscated_text'] = function() {
  // Deobfuscate the TEXT input argument
  var setupObfuscation = function(input, confounder) {
    // The algorithm below is also implemented in scheme in runtime.scm
    // If you change it here, you have to change it there!
    // Note: This algorithm is like xor, if applied to its output
    // it regenerates it input.
    var acc = [];
    // First make sure the confounder is long enough...
    while (confounder.length < input.length) {
      confounder += confounder;
    }
    for (var i = 0; i < input.length; i++) {
      var c = (input.charCodeAt(i) ^ confounder.charCodeAt(i)) & 0xFF;
      var b = (c ^ input.length - i) & 0xFF;
      var b2 = ((c >> 8) ^ i) & 0xFF;
      acc.push(String.fromCharCode((b2 << 8 | b) & 0xFF));
    }
    return acc.join('');
  }
  var input = this.getFieldValue('TEXT');
  var argument = Blockly.Yail.quote_(setupObfuscation(input, this.confounder));
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "text-deobfuscate"
      + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION
      + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER
      + argument + Blockly.Yail.YAIL_SPACER
      + Blockly.Yail.quote_(this.confounder) + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE
      + Blockly.Yail.YAIL_OPEN_COMBINATION + "text text"
      + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "deobfuscate text"
      + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};

Blockly.Yail['text_is_string'] = function() {
  // Check if the argument is a string
  var argument0 = Blockly.Yail.valueToCode(this, 'ITEM', Blockly.Yail.ORDER_NONE) || Blockly.Yail.YAIL_FALSE;
  var code = Blockly.Yail.YAIL_CALL_YAIL_PRIMITIVE + "string?" + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_OPEN_COMBINATION + Blockly.Yail.YAIL_LIST_CONSTRUCTOR + Blockly.Yail.YAIL_SPACER;
  code = code + argument0;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  code = code + Blockly.Yail.YAIL_SPACER + Blockly.Yail.YAIL_QUOTE + Blockly.Yail.YAIL_OPEN_COMBINATION;
  code = code + "any" + Blockly.Yail.YAIL_CLOSE_COMBINATION + Blockly.Yail.YAIL_SPACER;
  code = code + Blockly.Yail.YAIL_DOUBLE_QUOTE + "is a string?" + Blockly.Yail.YAIL_DOUBLE_QUOTE + Blockly.Yail.YAIL_CLOSE_COMBINATION;
  return [ code, Blockly.Yail.ORDER_ATOMIC ];
};
