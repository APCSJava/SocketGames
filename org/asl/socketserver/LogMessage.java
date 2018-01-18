package org.asl.socketserver;

import java.util.Formattable;
import java.util.Formatter;

public class LogMessage implements Formattable {

	private String opCode;
	private String ipAddress;
	private String msg;

	public LogMessage(String opCode, String ipAddress, String msg) {
		this.opCode = opCode;
		this.ipAddress = ipAddress;
		this.msg = msg;
	}

	@Override
	public void formatTo(Formatter formatter, int flags, int width,
			int precision) {
		StringBuilder sb = new StringBuilder();

	}

}
