/**
 * Copyright 2019 Drew Thorstensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.morrigan.dev.examples.mina.sshd;

/**
 * Thrown when a SSH Timeout occurs.
 */
public final class SshTimeoutException extends Exception {

  private static final long serialVersionUID = 1747556318497978741L;

  public SshTimeoutException(String cmd, String host, long timeout) {
    super(new StringBuilder()
        .append("Command '")
        .append(cmd)
        .append("' on host '")
        .append(host)
        .append("' timed out after ")
        .append(timeout)
        .append(" seconds")
        .toString());
  }
}