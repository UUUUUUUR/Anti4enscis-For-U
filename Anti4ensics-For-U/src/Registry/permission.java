package Registry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class permission {
	
	public static void main(String args) throws IOException {
		/*
		 값을 삭제하는게 아닌, 값의 기록을 안남게하여 아예 생성자체를 억제시키는 방법
		 레지스트리를 생성이 안되기 때문에, 지울 일도 없고 내가 생각하는 가장 완벽한
		 레지스트리에 대한 안티포렌식 방법.
		 이 방법은 regini와 SubInACL을 이용한 방법 두가지가 존재한다.
		 regini 허용 권한에서 읽기만 주는 윈도우에 기본적으로 내장되어있는 기능이고
		 SubInACL은 거부 권한을 줄 수 있는 msdn에서 다운로드를 해야 사용할 수 있다.
		*/
		
		/*
		 Regini 권한 부여에 대한 정보
		 1  - Administrators Full Access
   		 2  - Administrators Read Access
   		 3  - Administrators Read and Write Access
   		 4  - Administrators Read, Write and Delete Access
   		 5  - Creator Full Access
   		 6  - Creator Read and Write Access
   		 7  - World Full Access
   		 8  - World Read Access
   		 9  - World Read and Write Access
   		 10 - World Read, Write and Delete Access
   		 11 - Power Users Full Access
   		 12 - Power Users Read and Write Access
   		 13 - Power Users Read, Write and Delete Access
   		 14 - System Operators Full Access
   		 15 - System Operators Read and Write Access
   		 16 - System Operators Read, Write and Delete Access
   		 17 - System Full Access
   		 18 - System Read and Write Access
   		 19 - System Read Access
   		 20 - Administrators Read, Write and Execute Access
   		 21 - Interactive User Full Access
   		 22 - Interactive User Read and Write Access
   		 23 - Interactive User Read, Write and Delete Access
		 */
		writeFile(System.getProperty("java.io.tmpdir") + "reginis.TMP", "\\Registry\\Machine\\SYSTEM\\ControlSet001\\Services\\bam\\State\\UserSettings [2 8 19]");
		Runtime.getRuntime().exec("cmd /c regini \"" + System.getProperty("java.io.tmpdir") + "reginis.TMP\"");
		
		/*
		subinacl에 대한 자세한 설명은 아래 참조
		https://ss64.com/nt/subinacl.html
		 */
		String regKey = "\"HKEY_CLASSES_ROOT\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache\"";
		Runtime.getRuntime().exec("cmd /c " + System.getProperty("java.io.tmpdir")+ "\\SubInACL.exe /subkeyreg " + regKey + " /deny="+System.getProperty("user.name")+"=r");
		Runtime.getRuntime().exec("cmd /c " + System.getProperty("java.io.tmpdir")+ "\\SubInACL.exe /subkeyreg " + regKey + " /deny=system=r");
		Runtime.getRuntime().exec("cmd /c " + System.getProperty("java.io.tmpdir")+ "\\SubInACL.exe /subkeyreg " + regKey + " /deny=administrators=r");
		
		
		
		
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
