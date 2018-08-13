package professor;

import java.util.UUID;

public class GenerateUUID {
	/**
	 * 
	 * @todo 生成UUID
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @return
	 * @return String
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
}
