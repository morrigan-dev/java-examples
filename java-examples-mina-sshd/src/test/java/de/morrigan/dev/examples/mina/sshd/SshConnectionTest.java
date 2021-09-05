package de.morrigan.dev.examples.mina.sshd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SshConnectionTest {

  @Test
  public void testConstruction() {
    SshConnection sut = new SshConnection("username", "password", "hostname");

    assertEquals("username", sut.getUsername());
    assertEquals("password", sut.getPassword());
    assertEquals("hostname", sut.getHostname());
  }
}
