package com.gsugambit.partydjserver.utils;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UUIDGenerator implements IdentifierGenerator {

	private static final String ALPHA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private static final int ID_LENGTH = 12;
	
	public static String generate() {
		Random random = new Random();

		StringBuilder idBuilder = new StringBuilder();

		for (int i = 0; i < ID_LENGTH; i++) {
			idBuilder.append(ALPHA.charAt(random.nextInt(ALPHA.length())));
		}
		return idBuilder.toString();
	}
	
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return generate();
	}
}
