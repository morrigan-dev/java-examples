package de.morrigan.dev.examples.mina.sshd.example1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.keyprovider.KeyIdentityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.examples.mina.sshd.SshConnection;
import de.morrigan.dev.examples.mina.sshd.SshResponse;
import de.morrigan.dev.examples.mina.sshd.SshTimeoutException;

public class Example1 {

	/** Logger for debugging */
	private static final Logger LOG = LoggerFactory.getLogger(Example1.class);

	public static void main(String[] args) throws SshTimeoutException, IOException {
		String username = args[0];
		String password = args[1];
		String host = args[2];
		SshConnection conn = new SshConnection(username, password, host);
		SshResponse response;

		response = runSingleCommand(conn, "cd Bilder", 5);
		LOG.info("returnCode: {}, errMsg: {}, stdOut: {}", response.getReturnCode(), response.getErrOutput(),
				response.getStdOutput());
		response = runSingleCommand(conn, "ls -la", 5);
		LOG.info("returnCode: {}, errMsg: {}, stdOut: {}", response.getReturnCode(), response.getErrOutput(),
				response.getStdOutput());

		String[] commands = { 
				"cd scripts", 
				"ls -la",
				"./setup.sh",
				"echo \"$ROOT_DIR\""
		};
		response = runMultipleCommandsInSameSession(conn, commands, 10);
		LOG.info("returnCode: {}, errMsg: {}, stdOut: {}", response.getReturnCode(), response.getErrOutput(),
				response.getStdOutput());
	}

	/**
	 * Runs a SSH command against a remote system.
	 * 
	 * @param conn    Defines the connection to the system.
	 * @param cmd     The command to run. Should generally be fully qualified (ex.
	 *                instead of 'ls -la', use '/bin/ls -la')
	 * @param timeout The amount of time to wait for the command to run before
	 *                timing out. This is in seconds. This is used as two separate
	 *                timeouts, one for login another for command execution.
	 * @return The {@link SshResponse} contains the output of a successful command.
	 * @throws SshTimeoutException Raised if the command times out.
	 * @throws IOException         Raised in the event of a general failure (wrong
	 *                             authentication or something of that nature).
	 */
	public static SshResponse runSingleCommand(SshConnection conn, String cmd, long timeout)
			throws SshTimeoutException, IOException {
		SshClient client = SshClient.setUpDefaultClient();
		client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
		client.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
		client.setKeyIdentityProvider(KeyIdentityProvider.EMPTY_KEYS_PROVIDER);

		// Open the client
		client.start();

		// Connect to the server
		try (ClientSession session = createClientSession(client, conn, timeout);
				ChannelExec ce = session.createExecChannel(cmd);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ByteArrayOutputStream err = new ByteArrayOutputStream();) {

			ce.setOut(out);
			ce.setErr(err);

			// Execute and wait
			ce.open();
			Set<ClientChannelEvent> events = ce.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
					TimeUnit.SECONDS.toMillis(timeout));
			session.close(false);

			// Check if timed out
			if (events.contains(ClientChannelEvent.TIMEOUT)) {
				throw new SshTimeoutException(cmd, conn.getHostname(), timeout);
			}

			// Respond
			return new SshResponse(out.toString(), err.toString(), ce.getExitStatus());
		} finally {
			client.stop();
		}
	}

	public static SshResponse runMultipleCommandsInSameSession(SshConnection conn, String[] cmds, long timeout)
			throws SshTimeoutException, IOException {
		SshClient client = SshClient.setUpDefaultClient();
		client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
		client.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
		client.setKeyIdentityProvider(KeyIdentityProvider.EMPTY_KEYS_PROVIDER);

		// Open the client
		client.start();

		try (ClientSession session = createClientSession(client, conn, timeout);
				ClientChannel channel = session.createShellChannel();
				PipedOutputStream pipedIn = new PipedOutputStream();
				PipedInputStream pipedOut = new PipedInputStream(pipedIn)) {

			channel.setIn(pipedOut);

			try (ByteArrayOutputStream out = new ByteArrayOutputStream();
					ByteArrayOutputStream err = new ByteArrayOutputStream()) {

				channel.setOut(out);
				channel.setErr(err);
				channel.open();

				StringBuilder allCmds = new StringBuilder();
				for (String cmd : cmds) {
					allCmds.append(cmd).append(",");
					if (!cmd.endsWith("\n")) {
						cmd = cmd + "\n";
					}
					pipedIn.write(cmd.getBytes(StandardCharsets.UTF_8));
					pipedIn.flush();
				}
				pipedIn.write("exit\n".getBytes(StandardCharsets.UTF_8));
				pipedIn.flush();

				Set<ClientChannelEvent> events = channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
						TimeUnit.SECONDS.toMillis(timeout));

				session.close(false);
				client.stop();

				// Check if timed out
				if (events.contains(ClientChannelEvent.TIMEOUT)) {
					throw new SshTimeoutException(allCmds.toString(), conn.getHostname(), timeout);
				}

				// Respond
				return new SshResponse(out.toString(), err.toString(), channel.getExitStatus());
			}
		} finally {
			client.stop();
		}
	}

	private static ClientSession createClientSession(SshClient client, SshConnection conn, long timeout)
			throws IOException {
		ClientSession session = client.connect(conn.getUsername(), conn.getHostname(), 22)
				.verify(TimeUnit.SECONDS.toMillis(timeout)).getSession();
		try {
			session.addPasswordIdentity(conn.getPassword());
			session.auth().verify(TimeUnit.SECONDS.toMillis(timeout));

			ClientSession returnValue = session;
			session = null; // avoid 'finally' close
			return returnValue;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
