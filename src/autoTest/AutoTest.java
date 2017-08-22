package autoTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutoTest {

	public void autoTest(String name) throws IOException, InterruptedException{
		Runtime r = Runtime.getRuntime();
		File file =  new File("/var/lib/tomcat8/webapps/AutoESTest/Repositories/"+name);
		
		Process p1 = r.exec("/opt/apache-maven-3.5.0/bin/mvn compile"
				,null,file);
		p1.waitFor();
		System.out.println("compile success");
		
		Process p3 = r.exec("/opt/apache-maven-3.5.0/bin/mvn evosuite:generate"
				, null,file);
		p3.waitFor();
		System.out.println("generate test success");
		
		Process p4 = r.exec("/opt/apache-maven-3.5.0/bin/mvn evosuite:export"
				, null, file);
		p4.waitFor();
		System.out.println("export success");
		
		Process p5 = r.exec("/opt/apache-maven-3.5.0/bin/mvn test"
				,null, file);
		p5.waitFor();
		System.out.println("test success");
	}
	public static void main(String[] args) throws IOException, InterruptedException{
		AutoTest a = new AutoTest();
		a.autoTest("");
	}
}
