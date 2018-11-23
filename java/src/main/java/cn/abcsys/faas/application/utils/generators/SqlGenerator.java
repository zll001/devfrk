/**
 * Copyrigt (2018, ) Institute of Software, Chinese Academy of Sciences
 */
package cn.abcsys.faas.application.utils.generators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2018年5月7日
 *
 */
public class SqlGenerator {

	public final static String GENERATOR_CONFIG = "src/main/resources/generatorConfig.xml";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<String>();
		String configFilePath = GENERATOR_CONFIG;
		boolean overwrite = true;
		File configFile = new File(configFilePath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		ProgressCallback progressCallback = new VerboseProgressCallback();
		myBatisGenerator.generate(progressCallback);
	}

}
