import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 
 * ����ϵͳ��Ϣ
 * 
 * @author longgangbai
 * 
 */
public final class SystemHelper {

	// ���ϵͳ���Լ�
	public static Properties props = System.getProperties();
	// ����ϵͳ����
	public static String OS_NAME = getPropertery("os.name");
	public static List<String> getSystemLocalIp(){
		List<String> rs = null;
		//String osname = getSystemOSName();
		rs=getLocalIp();
		return rs;
	}

	/**
	 * ��ȡFTP�����ò���ϵͳ
	 * 
	 * @return
	 */
	public static String getSystemOSName() {
		// ���ϵͳ���Լ�
		Properties props = System.getProperties();
		// ����ϵͳ����
		String osname = props.getProperty("os.name");
		return osname;
	}

	/**
	 * ��ȡ���Ե�ֵ
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getPropertery(String propertyName) {
		return props.getProperty(propertyName);
	}
	/**
	 * ��ȡ����ip��ַ
	 * 
	 * @return
	 * @throws 
	 */
	private static List<String> getLocalIp() {
		List<String> res = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				Enumeration<InetAddress> nii = ni.getInetAddresses();
				while (nii.hasMoreElements()) {
					ip =nii.nextElement();
					
					//System.out.println(ip.getHostName());
					if (ip.getHostAddress().indexOf(":") == -1
							&& !ip.getHostAddress().equalsIgnoreCase(
									"127.0.0.1")) {
						res.add(ni.getDisplayName());
						res.add(ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return res;
	}
}