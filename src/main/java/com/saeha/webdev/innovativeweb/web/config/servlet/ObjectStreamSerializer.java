package com.saeha.webdev.innovativeweb.web.config.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

public class ObjectStreamSerializer implements StreamSerializer<Object> {
	public int getTypeId() {
		return 2;
	}

	public void write(ObjectDataOutput objectDataOutput, Object object)
			throws IOException {
		ObjectOutputStream out = new ObjectOutputStream((OutputStream) objectDataOutput);
		out.writeObject(object);
		out.flush();
	}

	public Object read(ObjectDataInput objectDataInput) throws IOException {
		ObjectInputStream in = new ObjectInputStream((InputStream) objectDataInput);
		try {
			return in.readObject();
		}
		catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

	public void destroy() {
	}

}