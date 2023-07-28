// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2020 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * BLEPacketReader encapsulates a packet from a Bluetooth low energy device and provides an API
 * for interpreting the contents of the packet.
 *
 * The types for this class are taken from the Bluetooth Specification on Format Types.
 * https://www.bluetooth.com/specifications/assigned-numbers/format-types/
 *
 * @author ewpatton@mit.edu (Evan W. Patton)
 */
public class BLEPacketReader {
  private static final int INFINITY_S32 = 0x007FFFFE;
  private static final int NAN_S32 = 0x007FFFFF;
  private static final int NRES_S32 = 0x00800000;
  private static final int NEG_INFINITY_S32 = 0x00800002;
  private static final int INFINITY_S16 = 0x07FE;
  private static final int NEG_INFINITY_S16 = 0x801;
  private static final int NAN_S16 = 0x07FF;
  private static final int NRES_S16 = 0x0800;
  private final byte[] buffer;
  private int index;

  /**
   * Creates a new BLEPacketReader to interpret the given array of bytes.
   *
   * @param packet byte array content of the packet
   */
  public BLEPacketReader(byte[] packet) {
    buffer = Arrays.copyOf(packet, packet.length);
    index = 0;
  }

  /**
   * Reads a boolean value from the packet.
   */
  public boolean readBoolean() {
    return buffer[index++] != 0;
  }

  /**
   * Reads a 2 bit value from from the packet.
   */
  public int read2Bit() {
    return buffer[index++];
  }

  /**
   * Reads a nibble (4 bits) from the packet.
   */
  public int readNibble() {
    return buffer[index++];
  }

  /**
   * Reads a signed 8 bit value from the packet.
   */
  public int readInt8() {
    return buffer[index++];
  }

  /**
   * Reads an unsigned 8 bit value from the packet.
   */
  public int readUint8() {
    return (int) buffer[index++] & 0xFF;
  }

  /**
   * Reads a signed 16 bit value from the packet.
   */
  public int readInt16() {
    return readInt16(false);
  }

  public int readInt16(boolean bigEndian) {
    int lower = readInt8();
    int upper = readInt8();
    if (bigEndian) {
      return (lower << 8) | (upper & 0xFF);
    } else {
      return (upper << 8) | (lower & 0xFF);
    }
  }

  /**
   * Reads an unsigned 16 bit value from the packet.
   */
  public int readUint16() {
    return readUint16(false);
  }

  public int readUint16(boolean bigEndian) {
    int lower = readUint8();
    int upper = readUint8();
    if (bigEndian) {
      return lower << 8 | upper;
    } else {
      return upper << 8 | lower;
    }
  }

  /**
   * Reads a signed 12 bit value from the packet.
   */
  public int readInt12() {
    return readInt16();
  }

  /**
   * Reads an unsigned 12 bit value from the packet.
   */
  public int readUint12() {
    return readUint16();
  }

  /**
   * Reads an unsigned 24 bit value from the packet.
   */
  public int readUint24() {
    return readInt32();
  }

  /**
   * Reads a signed 24 bit value from the packet.
   */
  public int readInt24() {
    return (int) readUint32() & 0xFFFFFF;
  }

  /**
   * Reads a signed 32 bit value from the packet.
   */
  public int readInt32() {
    int lower16 = readUint16();
    int upper16 = readInt16();
    return upper16 << 16 | lower16;
  }

  /**
   * Reads an unsigned 32 bit value from the packet.
   */
  public long readUint32() {
    long lower16 = readUint16();
    long upper16 = readUint16();
    return upper16 << 16 | lower16;
  }

  /**
   * Reads a signed 48 bit value from the packet.
   */
  public long readInt48() {
    long lower16 = readUint16();
    long upper32 = readInt32();
    return (upper32 << 16) | lower16;
  }

  /**
   * Reads an unsigned 48 bit value from the packet.
   */
  public long readUint48() {
    long lower16 = readUint16();
    long upper32 = readUint32();
    return (upper32 << 16) | lower16;
  }

  /**
   * Reads a signed 64 bit value from the packet.
   */
  public long readInt64() {
    long lower32 = readUint32();
    long upper32 = readInt32();
    return (upper32 << 32) | lower32;
  }

  /**
   * Reads an unsigned 64 bit value from the packet.
   */
  public BigInteger readUint64() {
    byte[] data = Arrays.copyOfRange(buffer, index, index + 8);
    index += 8;
    return new BigInteger(data);
  }

  /**
   * Reads a 32-bit IEEE 754 floating point value from the packet.
   */
  public float readFloat32() {
    return Float.intBitsToFloat(readInt32());
  }

  /**
   * Reads a 64-bit IEEE 754 floating point value from the packet.
   */
  public double readFloat64() {
    return Double.longBitsToDouble(readInt64());
  }

  /**
   * Reads a 16-bit IEEE 11073 floating point value from the packet.
   */
  public double readSFloat() {
    int data = readInt16();
    if (data == INFINITY_S16) {
      return Double.POSITIVE_INFINITY;
    } else if (data == NEG_INFINITY_S16) {
      return Double.NEGATIVE_INFINITY;
    } else if (data == NAN_S16) {
      return Double.NaN;
    } else if (data == NRES_S16) {
      // Decide whether we want to use a different value
      return Double.NaN;
    } else {
      int exponent = (data >> 12);
      int mantissa = data & 0xFFF;
      if ((mantissa & 0x800) != 0) {
        mantissa |= 0xFFFFF000;
      }
      return mantissa * Math.pow(10.0, exponent);
    }
  }

  /**
   * Reads a 32-bit IEEE 11703 floating point value from the packet.
   */
  public double readFloat10() {
    int data = readInt32();
    if (data == INFINITY_S32) {
      return Double.POSITIVE_INFINITY;
    } else if (data == NEG_INFINITY_S32) {
      return Double.NEGATIVE_INFINITY;
    } else if (data == NAN_S32) {
      return Double.NaN;
    } else if (data == NRES_S32) {
      // Decide whether we want to use a different value
      return Double.NaN;
    } else {
      int exponent = (data >> 24);
      int mantissa = data & 0xFFFFFF;
      if ((mantissa & 0x800000) != 0) {
        mantissa |= 0xFF000000;
      }
      return mantissa * Math.pow(10.0, exponent);
    }
  }

  /**
   * Reads a UTF-8 encoded string (optionally null terminated) from the
   * packet.
   */
  @SuppressWarnings("CharsetObjectCanBeUsed")
  public String readUtf8() {
    int end = index;
    for (; end < buffer.length; end++) {
      if (buffer[end] == 0) {
        break;
      }
    }
    try {
      return new String(buffer, index, end - index, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    } finally {
      index = end + 1;
    }
  }

  /**
   * Reads a UTF-16 encoded string (optionally null terminated) from the
   * packet.
   */
  @SuppressWarnings("CharsetObjectCanBeUsed")
  public String readUtf16() {
    int end = index;
    for (; end < buffer.length; end++) {
      if (buffer[end] == 0) {
        break;
      }
    }
    try {
      return new String(buffer, index, end - index, "UTF-16");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    } finally {
      index = end + 2;
    }
  }

  /**
   * Reads an unsigned 128 bit value from the packet.
   */
  public BigInteger readUint128() {
    BigInteger lower64 = readUint64();
    BigInteger upper64 = readUint64();
    return upper64.shiftLeft(64).or(lower64);
  }

  /**
   * Reads a signed 128 bit value from the packet.
   */
  public BigInteger readInt128() {
    BigInteger lower64 = readUint64();
    BigInteger upper64 = BigInteger.valueOf(readInt64());
    return upper64.shiftLeft(64).or(lower64);
  }

  /**
   * Reads a pair of unsigned 16 bit values from the packet.
   */
  public int[] readDuint16() {
    return new int[] {
        readUint16(),
        readUint16()
    };
  }
}
