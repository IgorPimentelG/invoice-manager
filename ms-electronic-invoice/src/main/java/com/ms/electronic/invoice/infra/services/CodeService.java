package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Random;

@Service
public class CodeService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Value("${security.secret-key}")
	private String secretKey;

	private final String algorithm = "AES";

	public String generateCode() {
		final int CODE_LENGTH = 6;
		StringBuilder code;
		var random = new Random();

		while (true) {
			code = new StringBuilder();

			for (int i = 0; i < CODE_LENGTH; i++) {
				code.append(random.nextInt(10));
			}

			var codeAlreadyExists = invoiceRepository.findByNumber(code.toString())
			  .orElse(null);

			if (codeAlreadyExists == null) {
				break;
			}
		}
		return code.toString();
	}

	public String encryptCode(String code)  {
		try {
			SecretKey key = new SecretKeySpec(secretKey.getBytes(), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] hash = cipher.doFinal(code.getBytes());

			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public boolean validateCode(String code, String hash) {
		var decryptedHash = decryptHash(hash);
		return code.equals(decryptedHash);
	}

	private String decryptHash(String hash) {
		try {
			SecretKey key = new SecretKeySpec(secretKey.getBytes(), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] decodedCode = Base64.getDecoder().decode(hash);
			byte[] decryptedCode = cipher.doFinal(decodedCode);

			return new String(decryptedCode);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}
