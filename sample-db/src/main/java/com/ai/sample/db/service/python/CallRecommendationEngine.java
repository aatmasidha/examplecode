package com.ai.sample.db.service.python;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CallRecommendationEngine {

	public static void main(String a[]) {
		try {

			/*String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
			BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
			out.write(prg);
			out.close();
			int number1 = 10;
			int number2 = 32;

			ProcessBuilder pb = new ProcessBuilder("python",
					"D:\\sonal\\Assignment\\test1.py", "" + number1, ""
							+ number2);
			Process p = pb.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			int ret = new Integer(in.readLine()).intValue();
			System.out.println("value is : " + ret);*/
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void runRecommendationScripts()
	{
		System.out.println("Begin runRecommendationScripts()");
		try {

			/*String pythonScriptPath = "D:\\development\\temp\\test1.py";
			String[] cmd = new String[2];
			cmd[0] = "python"; // check version of installed python: python -V
			cmd[1] = pythonScriptPath;

			// create runtime to execute external command
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd);

			// retrieve output from python script
			BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while((line = bfr.readLine()) != null) {
				// display each output line form python script
				System.out.println(line);

			} */
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
