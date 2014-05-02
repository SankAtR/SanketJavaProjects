import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class T {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		List<String> deleteSQLList = new ArrayList<String>();
		List<String> appIds1 = new ArrayList<String>();
		List<String> appIds2 = new ArrayList<String>();
		File deletescriptsfile = new File("d:/Users/admin/Desktop/tobeuploaded/dropusers/App_Deletes_Tokenized.sql");
		String box1AppIds = "d:/Users/admin/Desktop/tobeuploaded/dropusers/box1sql.txt";
		String box2AppIds = "d:/Users/admin/Desktop/tobeuploaded/dropusers/box2sql.txt";
		File appidsRR1 = new File(box1AppIds);
		File appidsRR2 = new File(box2AppIds);
		String box1 = "d:/Users/admin/Desktop/tobeuploaded/dropusers/box1/";
		
		String box2 = "d:/Users/admin/Desktop/tobeuploaded/dropusers/box2/";
		readFileContents(deleteSQLList, deletescriptsfile);
		readFileContents(appIds1, appidsRR1);
		readFileContents(appIds2, appidsRR2);

		for (String s : deleteSQLList)
			System.out.println(s);
		for (String s : appIds1)
			System.out.println(s);
		for (String s : appIds2)
			System.out.println(s);

		writeFileContents(box1, appIds1, deleteSQLList);
		writeFileContents(box2, appIds2, deleteSQLList);

	}

	public static void readFileContents(List<String> contents, File deletescriptsfile) throws IOException {
		FileInputStream fis = null;
		BufferedReader br = null;
		String line = null;
		try {
			fis = new FileInputStream(deletescriptsfile);
			br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				contents.add(line);
			}
		}
		finally {
			try {
				if (br != null)
					br.close();

				if (fis != null)
					fis.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void writeFileContents(String path, List<String> appIds, List<String> dbscripts) throws IOException {
		BufferedWriter bw = null;

		String fileName = null;
		String content = null;
		File fileOp = null;

		for (String appId : appIds) {
			if (appId != null && appId.trim().length() > 0) {
				fileName = path + appId + ".txt";
				fileOp = new File(fileName);
				//fopStream = new FileOutputStream(fileOp);
				bw = new BufferedWriter(new FileWriter(fileOp, true));
				if (fileOp.exists()) {
					fileOp.delete();
					fileOp.createNewFile();
				}
				else
					fileOp.createNewFile();
				for (String script : dbscripts) {
					content = replaceScript("#(APP_ID)#", script, appId);

					bw.write(content);
					bw.newLine();
				}

				if (bw != null)
					bw.close();

			}// if end

		}// for end

	}

	public static String replaceScript(String pattern, String line, String replacement) {
		String s =line.replace(pattern, replacement);
		return s;
	}
}