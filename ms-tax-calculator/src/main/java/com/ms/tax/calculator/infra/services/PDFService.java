package com.ms.tax.calculator.infra.services;

import com.ms.tax.calculator.domain.entities.NationalSimpleTax;
import com.ms.tax.calculator.domain.entities.PresumedProfitTax;
import com.ms.tax.calculator.domain.entities.TaxResume;
import com.ms.tax.calculator.infra.errors.BadRequestException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class PDFService {

	private final Logger logger = LoggerFactory.getLogger(PDFService.class);

	public void printSummary(TaxResume taxResume) {
		try {
			var content = parseThymeleafTemplate(taxResume);
			var outputDir = System.getProperty("user.dir") + File.separator  + "ms-tax-calculator"
			  + File.separator + "files" + File.separator + "taxes";

			createDirectory(outputDir);

			OutputStream outputStream = new FileOutputStream(
			  outputDir + File.separator + taxResume.getId() + ".pdf"
			);

			var renderer = new ITextRenderer();
			renderer.setDocumentFromString(content);
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.close();
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	private String parseThymeleafTemplate(TaxResume taxResume) {
		var templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setPrefix("templates/");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		var templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		var nationalSimpleTaxes = taxResume.getTaxes().stream()
		  .filter(tax -> tax instanceof NationalSimpleTax)
		  .map(tax -> (NationalSimpleTax) tax)
		  .toList();

		var presumedProfitTaxes = taxResume.getTaxes().stream()
		  .filter(tax -> tax instanceof PresumedProfitTax)
		  .map(tax -> (PresumedProfitTax) tax)
		  .toList();

		Path path = Paths.get("ms-tax-calculator/src/main/resources/static/images/logo.png");
		String base64ImageLogo = parseImage(path);

		var context = new Context();
		context.setVariable("logo", "data:image/png;base64," + base64ImageLogo);
		context.setVariable("reference", taxResume.getReference());
		context.setVariable("owner", taxResume.getOwner());
		context.setVariable("total", taxResume.getAmount());
		context.setVariable("nationalSimpleTaxes", nationalSimpleTaxes);
		context.setVariable("presumedProfitTaxes", presumedProfitTaxes);

		return templateEngine.process("tax_resume_template", context);
	}

	private String parseImage(Path path) {
		byte[] imagesAsBytes = new byte[0];

		try {
			var resource = new UrlResource(path.toUri());
			var inputStream = resource.getInputStream();
			imagesAsBytes = IOUtils.toByteArray(inputStream);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

		return Base64.getEncoder().encodeToString(imagesAsBytes);
	}

	private void createDirectory(String path) throws URISyntaxException {
		File file = new File(path);

		if (!file.exists()) {
			if (file.mkdirs()) {
				logger.info("Creating directory.");
			} else {
				logger.warn("An error occurred while creating directory");
			}
		} else {
			logger.info("Directory already exists.");
		}
	}
}
