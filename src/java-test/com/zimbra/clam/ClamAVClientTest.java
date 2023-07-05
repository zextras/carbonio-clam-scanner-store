package com.zimbra.clam;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.zimbra.clam.client.ClamAVClient;
import org.junit.Test;

public class ClamAVClientTest {

  @Test
  public void testIsCleanReply_WhenCleanReply_ReturnsTrue() {
    byte[] reply = "OK".getBytes();
    boolean result = ClamAVClient.replyOk(reply);
    assertTrue(result);
  }

  @Test
  public void testIsCleanReply_WhenInfectedReply_ReturnsFalse() {
    byte[] reply = "FOUND".getBytes();
    boolean result = ClamAVClient.replyOk(reply);
    assertFalse(result);
  }
}