package com.noone.paymentms.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class GenerateModelAndRepository {

	private String tagertDir = "com.noone.paymentms.domain";
	private String baseDir = System.getProperty("user.dir");

	public static void main(String[] args) throws Exception {

		new GenerateModelAndRepository().generate("NooneOrder", true);

	}

	public void generate(String gclassName, boolean overWrite) throws Exception {

		boolean result = checkIfExist(gclassName, overWrite);

		if (!result) {
			System.out.println(tagertDir + gclassName + " >> Is already existed. Generate fail.");
			return;
		}

		autoGenerateModel(gclassName);

		autoGenerateModelRepository(gclassName);

		System.out.println("generate Done. >> " + gclassName);
	}

	private boolean checkIfExist(String className, boolean overWrite) {

		String packagePath = tagertDir.replaceAll("\\.", "/");

		File directory = new File(baseDir + File.separator + "src/main/java" + File.separator + packagePath);
		if (!directory.exists()) {
			directory.mkdir();
		}

		File[] files = directory.listFiles();
		if (null != files && files.length > 0) {

			for (File file : files) {
				if (file.isFile()) {
					String fileName = file.getName();
					String javaName = fileName.substring(0, fileName.indexOf("."));
					if (className.equals(javaName) && !overWrite) {
						return false; // 如果已经存在,并且参数是不覆盖的话 返回false.
					}
				}
			}
		}

		return true;
	}

	private void autoGenerateModel(String className) throws Exception {

		String packagePath = tagertDir.replaceAll("\\.", "/");

		File file = new File(
				baseDir + File.separator + "src/main/java" + File.separator + packagePath + File.separator + className + ".java");

		PrintStream ps = new PrintStream(new FileOutputStream(file));

		StringBuffer content = new StringBuffer();
		content.append("package " + tagertDir + ";");
		content.append("\n");
		content.append("\n");

		content.append("import javax.persistence.Entity;");
		content.append("\n");

		content.append("import javax.persistence.GeneratedValue;");
		content.append("\n");

		content.append("import javax.persistence.GenerationType;");
		content.append("\n");

		content.append("import javax.persistence.Id;");
		content.append("\n");

		content.append("import lombok.Data;");
		content.append("\n");
		content.append("import java.util.Date;");
		content.append("\n");

		content.append("\n");

		content.append("@Data");
		content.append("\n");
		content.append("@Entity");
		content.append("\n");
		content.append("public class " + className);
		content.append(" {\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");

		content.append("	// -------------Auto generated start (" + new Date() + ")-------------//");
		content.append("\n");
		content.append("\n");
		content.append("	@Id");
		content.append("\n");
		content.append("	@GeneratedValue(strategy = GenerationType.AUTO)");
		content.append("\n");
		content.append("	private Long id;");
		content.append("\n");
		content.append("\n");
		content.append("	private Date createdDate;");
		content.append("\n");
		content.append("\n");
		content.append("	private Long createdBy;");
		content.append("\n");
		content.append("\n");
		content.append("	private Date modifiedDate;");
		content.append("\n");
		content.append("\n");
		content.append("	private Long modifiedBy;");
		content.append("\n");
		content.append("\n");

		content.append("	public void setBaseField(Long operatorId) {");
		content.append("\n");
		content.append("		Date now = new Date();");
		content.append("\n");
		content.append("		if (this.getId() == null) {");
		content.append("\n");
		content.append("			setCreatedBy(operatorId);");
		content.append("\n");
		content.append("			setCreatedDate(now);");
		content.append("\n");
		content.append("		}");
		content.append("\n");
		content.append("		setModifiedBy(operatorId);");
		content.append("\n");
		content.append("		setModifiedDate(now);");
		content.append("\n");
		content.append("	}");
		content.append("\n");
		content.append("\n");
		content.append("	// -------------Auto generated end-------------//");
		content.append("\n");
		content.append("\n}");
		ps.print(content);
		ps.close();
	}

	private void autoGenerateModelRepository(String modelName) throws Exception {
		String packagePath = tagertDir.replaceAll("\\.", "/");

		String repositoryName = modelName + "Repository";

		File file = new File(baseDir + File.separator + "src/main/java" + File.separator + packagePath + File.separator
				+ repositoryName + ".java");

		PrintStream ps = new PrintStream(new FileOutputStream(file));

		StringBuffer content = new StringBuffer();
		content.append("package " + tagertDir + ";");
		content.append("\n");
		content.append("\n");

		content.append("import org.springframework.data.querydsl.QueryDslPredicateExecutor;");
		content.append("\n");

		content.append("import org.springframework.data.repository.CrudRepository;");
		content.append("\n");
		content.append("\n");

		content.append("public interface " + repositoryName + " extends CrudRepository<" + modelName
				+ ", Long> , QueryDslPredicateExecutor<" + modelName + ">");
		content.append(" {\n");
		content.append("\n");
		content.append("\n");
		content.append("\n");
		content.append("\n}");
		ps.print(content);
		ps.close();
	}
}
