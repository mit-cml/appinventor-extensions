package edu.mit.appinventor.ble;

import static org.junit.Assert.*;

import org.junit.Test;

public class BLEPacketReaderTest {
  @Test
  public void testReadInt8() {
    BLEPacketReader reader;
    reader = new BLEPacketReader(new byte[] { 0 });
    assertEquals(0, reader.readInt8());

    reader = new BLEPacketReader(new byte[] { -1 });
    assertEquals(-1, reader.readInt8());

    reader = new BLEPacketReader(new byte[] { 127 });
    assertEquals(127, reader.readInt8());

    reader = new BLEPacketReader(new byte[] { -128 });
    assertEquals(-128, reader.readInt8());
  }

  @Test
  public void testReadUint8() {
    BLEPacketReader reader;
    reader = new BLEPacketReader(new byte[] { 0 });
    assertEquals(0, reader.readUint8());

    reader = new BLEPacketReader(new byte[] { -1 });
    assertEquals(255, reader.readUint8());

    reader = new BLEPacketReader(new byte[] { 127 });
    assertEquals(127, reader.readUint8());

    reader = new BLEPacketReader(new byte[] { -128 });
    assertEquals(128, reader.readUint8());
  }

  @Test
  public void testReadInt16() {
    BLEPacketReader reader;
    reader = new BLEPacketReader(new byte[] { 0, 0 });
    assertEquals(0, reader.readInt16());

    reader = new BLEPacketReader(new byte[] { -1, 0 });
    assertEquals(255, reader.readInt16());

    reader = new BLEPacketReader(new byte[] { 0, -128 });
    assertEquals(-32768, reader.readInt16());

    reader = new BLEPacketReader(new byte[] { -1, -1 });
    assertEquals(-1, reader.readInt16());
  }

  @Test
  public void testReadUint16() {
    BLEPacketReader reader;
    reader = new BLEPacketReader(new byte[] { 0, 0 });
    assertEquals(0, reader.readUint16());

    reader = new BLEPacketReader(new byte[] { -1, 0 });
    assertEquals(255, reader.readUint16());

    reader = new BLEPacketReader(new byte[] { 0, -128 });
    assertEquals(32768, reader.readUint16());

    reader = new BLEPacketReader(new byte[] { -1, -1 });
    assertEquals(65535, reader.readUint16());
  }
}
