import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 
 * 本机系统信息
 * 
 * @author longgangbai
 * 
 */
public final class SystemHelper {

	// 获得系统属性集
	public static Properties props = System.getProperties();
	// 操作系统名称
	public static String OS_NAME = getPropertery("os.name");
	public static List<String> getSystemLocalIp(){
		List<String> rs = null;
		//String osname = getSystemOSName();
		rs=getLocalIp();
		return rs;
	}

	/**
	 * 获取FTP的配置操作系统
	 * 
	 * @return
	 */
	public static String getSystemOSName() {
		// 获得系统属性集
		Properties props = System.getProperties();
		// 操作系统名称
		String osname = props.getProperty("os.name");
		return osname;
	}

	/**
	 * 获取属性的值
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getPropertery(String propertyName) {
		return props.getProperty(propertyName);
	}
	/**
	 * 获取本地ip地址
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