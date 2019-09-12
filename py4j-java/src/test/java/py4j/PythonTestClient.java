/******************************************************************************
 * Copyright (c) 2009-2018, Barthelemy Dagenais and individual contributors.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - The name of the author may not be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/
package py4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PythonTestClient implements Runnable {

	Logger logger = LoggerFactory.getLogger(PythonTestClient.class);

	public volatile String lastProxyMessage;
	public volatile String lastReturnMessage;
	public volatile String nextProxyReturnMessage;

	private ServerSocket sSocket;

	private final int pythonPort;

	public PythonTestClient(int pythonPort) {
		this.pythonPort = pythonPort;
	}

	public void startProxy() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			/* do not use default ports for testing */
			try {
				sSocket = new ServerSocket(pythonPort);
			} catch (IOException e) {
				logger.error("[proxy] Could not start : "+e.getMessage());
				return;
			}
			Socket socket;
			try {
				socket = sSocket.accept();
			} catch(IOException e) {
				logger.error("[proxy] "+e.getMessage());
				return;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			lastProxyMessage = "";
			String temp = reader.readLine() + "\n";
			lastProxyMessage += temp;
			while (!temp.equals("e\n")) {
				temp = reader.readLine() + "\n";
				lastProxyMessage += temp;
			}
			logger.info("[proxy] Receiving : "+lastProxyMessage);
			logger.info("[proxy] Returning : "+nextProxyReturnMessage);
			writer.write(nextProxyReturnMessage);
			writer.flush();
			writer.close();
			reader.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopProxy() {
		NetworkUtil.quietlyClose(sSocket);
	}

	public void sendMesage(String message) {
		try {
			Socket socket = new Socket(InetAddress.getByName(GatewayServer.DEFAULT_ADDRESS), pythonPort);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			logger.info("[client] sending "+message);
			writer.write(message);
			writer.flush();
			lastReturnMessage = reader.readLine();
			logger.info("[client] received "+lastReturnMessage);

			try {
				Thread.sleep(250);
			} catch (Exception e) {
				e.printStackTrace();
			}
			writer.close();
			reader.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
