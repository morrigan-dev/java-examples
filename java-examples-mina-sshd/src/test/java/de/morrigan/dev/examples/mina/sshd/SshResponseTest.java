package de.morrigan.dev.examples.mina.sshd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SshResponseTest {

  @Test
  public void testConstruction() {
    SshResponse sut = new SshResponse("stdOut", "errOut", 42);

    assertEquals("stdOut", sut.getStdOutput());
    assertEquals("errOut", sut.getErrOutput());
    assertEquals(42, sut.getReturnCode());
  }
}
