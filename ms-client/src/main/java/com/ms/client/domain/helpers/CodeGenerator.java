package com.ms.client.domain.helpers;

import java.util.Random;

public abstract class CodeGenerator {

	public static String getCode(int length) {
		StringBuilder code = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			code.append(random.nextInt(10));
		}

		return code.toString();
	}
}
