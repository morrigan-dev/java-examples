package de.morrigan.dev.examples.mina.sshd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.Test;

public class SshTimeoutExceptionTest {

  @Test
  public void testConstruction() {
    SshTimeoutException sut = new SshTimeoutException("myCmd", "myHost", 42);

    assertThat(sut.getMessage(), containsString("myCmd"));
    assertThat(sut.getMessage(), containsString("myHost"));
    assertThat(sut.getMessage(), containsString("42"));
  }

}
