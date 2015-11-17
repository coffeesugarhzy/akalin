package sms;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sms {

	public static String createCode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	public static void sms_api(String phone, String checkCode) { 
		Map<String, String> para = new HashMap<String, String>();
		para.put("mob", phone);
		para.put("uid", "Yem9f2np3AFi");
		para.put("pas", "rf8u4vwh");
		para.put("type", "json");
		para.put("cid", "SGOUEhULI84j");
		para.put("p1", checkCode);
		para.put("p2", "3");

		try {
			System.out.println(HttpClientHelper.convertStreamToString(
					HttpClientHelper.post(
							"http://api.weimi.cc/2/sms/send.html", para),
					"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public static void sendSms(String phone, String checkCode) { 
		sms_api(phone,checkCode);
	}

}
