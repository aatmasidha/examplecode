package com.ai.sample.isell.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ai.sample.common.dto.IntegrationFileFormatDTO;
import com.ai.sample.common.exception.ISellFileException;

public class ReadJSON {

	HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = new HashMap<String, IntegrationFileFormatDTO>();

	public HashMap<String, IntegrationFileFormatDTO> readJsonFile(String fileName)
	{
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader(fileName));

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray integrationList = (JSONArray) jsonObject.get("integrations");
			Iterator<JSONObject> iterator = integrationList.iterator();
			while (iterator.hasNext()) {
				JSONObject integrationDetails = iterator.next();
				String integrationName = integrationDetails.get("integrationname") + "";
				String fileType = integrationDetails.get("filetype") + "";
				String delimiter = integrationDetails.get("delimiter") + "";
				JSONObject headerDetails =   (JSONObject)integrationDetails.get("header");

				HashMap<String, String> coulmnMappings = new HashMap<String, String>();
				JSONObject headings =   (JSONObject)headerDetails.get("headings");
				Set<String> headerKeySet = headings.keySet();
				Iterator<String> iter = headerKeySet.iterator();
				while (iter.hasNext()) {
					String columnNameCode = iter.next();
					String columnNameValue =   (String)headings.get(columnNameCode);
					coulmnMappings.put(columnNameValue, columnNameCode);
				}
				String startLine = (String) headerDetails.get("startline");

				JSONObject contentDetails =   (JSONObject)integrationDetails.get("content");
				String contentStartLine = (String) contentDetails.get("startline");
				String ignoreEndLines = (String) contentDetails.get("ignoreendlines");
				IntegrationFileFormatDTO integrationExcelFormatDTO =
						new IntegrationFileFormatDTO(integrationName, fileType, delimiter, coulmnMappings, Integer.parseInt(startLine)
								,  Integer.parseInt(contentStartLine), Integer.parseInt(ignoreEndLines));

				integrationHeaderDetails.put(integrationName, integrationExcelFormatDTO);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return integrationHeaderDetails;
	}

	public IntegrationFileFormatDTO getExtractFileTypeDetails(String propertyName, String integrationType) throws ISellFileException
	{
		ReadJSON readJSON = new ReadJSON();
		//    	HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = readJSON.readJsonFile("D:/Development/isell/isell/isell-util/src/main/resources/integrationexcelconfiguration.json");


		/*	ClassLoader classLoader = getClass().getClassLoader();
//		File file = new File(classLoader.getResource("integrationexcelconfiguration.json").getFile());
//		String path = this.getClass().getClassLoader().getResource("integrationexcelconfiguration.json").toExternalForm();
		String path = ReadJSON.class.getClassLoader().getResource("integrationexcelconfiguration.json").toExternalForm();

		path = path.replace("file:/", "");

    	File file = new File(path);
    	path = file.getAbsolutePath();
    	System.out.println("*******************Path" + path);*/

		/*	ApplicationContext appContext =
		    	   new ClassPathXmlApplicationContext("config.xml");*/

		/*	Resource resource =
		          appContext.getResource("integrationexcelconfiguration.json");
		 */
		/*Resource resource = new ClassPathResource("integrationexcelconfiguration.json");
		File file = null;

		try {
			System.out.println("FileName is : " +  resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file = new File(resource.getFilename());*/
		
		File file = findFileOnClassPath("integrationexcelconfiguration.json");

		HashMap<String, IntegrationFileFormatDTO> integrationHeaderDetails = readJSON.readJsonFile(file.getAbsolutePath());
		IntegrationFileFormatDTO integrationExcelFormatDTO = integrationHeaderDetails.get(integrationType);

		if(integrationExcelFormatDTO == null)
		{
			throw new ISellFileException(1, "Integration file format is not specified in Configuration file.");
		}

		return integrationExcelFormatDTO;
	}
	public static void main(String args[])
	{
		ReadJSON readJSON = new ReadJSON();
		//		readJSON.readJsonFile("D:/Development/isell/isell/isell-util/src/main/resources/integrationexcelconfiguration.json");
	}


	public static File findFileOnClassPath(final String fileName) {

		final String classpath = System.getProperty("java.class.path");

		final String pathSeparator = System.getProperty("path.separator");

		final StringTokenizer tokenizer = new StringTokenizer(classpath, pathSeparator);

		while (tokenizer.hasMoreTokens()) {

			final String pathElement = tokenizer.nextToken();

			final File directoryOrJar = new File(pathElement);

			final File absoluteDirectoryOrJar = directoryOrJar.getAbsoluteFile();

			if (absoluteDirectoryOrJar.isFile()) {

				final File target = new File(absoluteDirectoryOrJar.getParent(), fileName);

				if (target.exists()) {

					return target;

				}

			} else {

				final File target = new File(directoryOrJar, fileName);

				if (target.exists()) {

					return target;

				}

			}

		}

		return null;

	}

}	
