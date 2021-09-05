package de.morrigan.dev.examples.mina.sshd.example1;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.common.file.nativefs.NativeFileSystemFactory;
import org.apache.sshd.common.file.util.MockFileSystem;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.scp.server.ScpCommandFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.examples.mina.sshd.SshConnection;
import de.morrigan.dev.examples.mina.sshd.SshResponse;
import de.morrigan.dev.examples.mina.sshd.SshTimeoutException;

public class Example1Test {

  private static final Logger LOG = LoggerFactory.getLogger(Example1.class);

  private static final String HOST = "localhost";
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final int PORT = 8001;

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  private SshServer sshd;

  @Before
  public void prepare() throws IOException {
    setupSSHServer();
  }

  @After
  public void cleanup() throws InterruptedException {
    try {
      sshd.stop(true);
    } catch (Exception e) {
      // do nothing
    }
  }

  private void setupSSHServer() throws IOException {

    sshd = SshServer.setUpDefaultServer();
    sshd.setFileSystemFactory(new NativeFileSystemFactory() {

      @Override
      public FileSystem createFileSystem(SessionContext session) throws IOException {
        return new MockFileSystem(testFolder.getRoot().getAbsolutePath()) {

          @Override
          public Iterable<Path> getRootDirectories() {
            try {
              return Files.walk(testFolder.getRoot().toPath(), 1)
                  .filter(Files::isDirectory)
                  .collect(Collectors.toList());
            } catch (IOException e) {
              LOG.error(e.getMessage(), e);
              return Collections.emptyList();
            }
          }

        };
      }

    });
    sshd.setPort(PORT);

    sshd.setSubsystemFactories(Arrays.asList(new SftpSubsystemFactory.Builder().build()));
    sshd.setCommandFactory(new ScpCommandFactory());
    sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(testFolder.newFile("hostkey.ser").toPath()));
    sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
      @Override
      public boolean authenticate(final String username, final String password, final ServerSession session) {
        return StringUtils.equals(username, USERNAME) && StringUtils.equals(password, PASSWORD);
      }
    });
    sshd.start();
    System.out.println("testFolder: " + testFolder.getRoot().getAbsolutePath());
  }

  //  @Test
  public void testRunSingleCommand() throws SshTimeoutException, IOException {
    SshConnection testConnection = new SshConnection(USERNAME, PASSWORD, HOST, PORT);
    SshResponse response = Example1.runSingleCommand(testConnection, "ls", 0);
    System.out.println(response);
  }

}
