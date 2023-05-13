package Registry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegDelete {
	
	public static void main(String[] args) throws IOException {
		
		/*
		 명령 프롬프트(CMD)에 RegDelete 기능을 이용한 안티포렌식 방법
		 하지만 이 방법은 REGA를 비롯한 각종 포렌식-툴에 탐지됨.
		 가장 접근하기 쉬운 방법이자 가장 걸리기도 쉬운 방법.
		*/
		Runtime.getRuntime().exec("cmd /c reg del \"키\" /v \"값\"  /f");
		Runtime.getRuntime().exec("cmd /c reg del \"HKEY_CLASSES_ROOT\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache\" /v \"%UserProfile%\\Desktop\\U.exe\" /f");
		
		Runtime.getRuntime().exec("cmd /c reg del \"키\" /f");
		Runtime.getRuntime().exec("cmd /c reg del \"HKEY_CLASSES_ROOT\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache\" /f");
		
		/*
		 HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\Services\bam\State\UserSettings키에 경우 특별하게 권한이 없기 때문에
		 권한을 열어줘야한다. 만약 당신의 컴퓨터 계정이 하나라면 아래의 소스를 통해 SID를 추출하고 해당 SID에 대한 권한만 쉽게 열어 지울 수 있다.
		 그리고 특별하게 해당 키의 경우 당신의 다른 키와 다르게 경로가 나오기 때문에 Contains를 이용하여 삭제하는게 가장 효율적인 방법이다.
		 이 방법은 Reg query를 이용하여 한다.
		*/
		
		Process SIDProcess = Runtime.getRuntime().exec("cmd /c wmic useraccount where name='%username%' get sid");
		BufferedReader reader = new BufferedReader(new InputStreamReader(SIDProcess.getInputStream()));
		String result = "";
		String str;
		String SID;
		while ((str = reader.readLine()) != null) {
			result += str + "\n";
		}
		String[] SIDARR = result.split("SID");
		SID = SIDARR[1].replace("\n", "").trim();
		
		writeFile(System.getProperty("java.io.tmpdir") + "reginis.TMP", "\\Registry\\Machine\\SYSTEM\\ControlSet001\\Services\\bam\\State\\UserSettings\\" + SID + " [1 7 17]");
		Runtime.getRuntime().exec("cmd /c regini \"" + System.getProperty("java.io.tmpdir") + "reginis.TMP\"");
		
		Process regQ = Runtime.getRuntime().exec("cmd /c REG QUERY \"HKEY_LOCAL_MACHINE\\SYSTEM\\ControlSet001\\Services\\bam\\State\\UserSettings\\" + SID );
		BufferedReader br = new BufferedReader(new InputStreamReader(regQ.getInputStream()));
		String regQstr;
		br.readLine();
		br.readLine();
		br.readLine();
		while((regQstr = br.readLine()) != null) {
			String[] regQstrArr = regQstr.split("    ");
			if(regQstrArr.length > 1) {
				System.out.println(regQstrArr[1]);
				if(regQstrArr[1].contains("msedge")) {
					Runtime.getRuntime().exec("cmd /c reg del \"HKEY_LOCAL_MACHINE\\SYSTEM\\ControlSet001\\Services\\bam\\State\\UserSettings\\" + SID + "\" /v \"" + regQstrArr[1] + "\" /f");
				}
				
			}
			
		}
		
	
	}

	public static void writeFile(String str, String output) {
		try {
			BufferedWriter BufferedWriter = new BufferedWriter(new FileWriter(str));
			BufferedWriter.append(output);
			BufferedWriter.close();
		} catch (Exception e) {
			
		}
	}
	
	

}
