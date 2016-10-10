package com.example.myandroid.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.myandroid.bean.AppInfo;
import com.example.myandroid.bean.RunningAppInfo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.os.Debug.MemoryInfo;

public class AppInfoManager {

	private Context context;
	private PackageManager manager;
	private ActivityManager activityManager;

	private List<AppInfo> allPackageInfos = new ArrayList<AppInfo>();
	private List<AppInfo> sysPackageInfos = new ArrayList<AppInfo>();
	private List<AppInfo> usePackageInfos = new ArrayList<AppInfo>();

	@SuppressLint("ServiceCast")
	public AppInfoManager(Context context) {
		this.context = context;
		manager = context.getPackageManager();
		activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	/**
	 * ����ģʽ
	 */
	private static AppInfoManager appInfoManager = null;

	public static AppInfoManager getAppInfoManager(Context context) {
		if (appInfoManager == null) {
			appInfoManager = new AppInfoManager(context);
		}
		return appInfoManager;
	}

	/**
	 * ������к�̨
	 */

	public void KillAllProcesser() {
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo runappInfo : appProcessInfos) {
			if (runappInfo.importance > RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				String procesName = runappInfo.processName;
				try {
					ApplicationInfo appInfo = manager.getApplicationInfo(
							procesName, PackageManager.GET_META_DATA
									| PackageManager.GET_SHARED_LIBRARY_FILES
									| PackageManager.GET_SHARED_LIBRARY_FILES
									| PackageManager.GET_UNINSTALLED_PACKAGES);
					if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					} else {
						activityManager.killBackgroundProcesses(procesName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void KillProcesser(String PackageName) {
		activityManager.killBackgroundProcesses(PackageName);
	}

	public final static int SYSTEM_TYPE_FLAG = 1;
	public final static int USER_TYPE_FLAG = 0;

	public Map<Integer, List<RunningAppInfo>> getRuningAppInfos() {
		Map<Integer, List<RunningAppInfo>> runingAppInfos = new HashMap<Integer, List<RunningAppInfo>>();
		List<RunningAppInfo> sysapp = new ArrayList<RunningAppInfo>();
		List<RunningAppInfo> userapp = new ArrayList<RunningAppInfo>();
		// ��ȡ������������Ӧ��
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
			String packageName = appProcessInfo.processName; // �������г��������
			int pid = appProcessInfo.pid; // �������г������ID
			int importance = appProcessInfo.importance; // �������г�����̼���
			// ������̣��������������½���
			if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				Drawable icon; // ��ȡ���ݣ������г���ͼ��
				String lableName; // ��ȡ���ݣ������г�������
				long size; // ��ȡ���ݣ������г�����ռ�ڴ�
				// ��ȡÿ������ID(����)ռ�õ��ڴ��С(����), pid��MemoryInfo��һһ��Ӧ��
				// ����һ���������̵��ڴ�ʹ�����
				Debug.MemoryInfo[] memoryInfos = activityManager
						.getProcessMemoryInfo(new int[] { pid });
				size = (memoryInfos[0].getTotalPrivateDirty()) * 1024;// ��ȡ����
				try {
					icon = manager.getApplicationIcon(packageName);
					ApplicationInfo applicationInfo = manager
							.getApplicationInfo(
									packageName,
									PackageManager.GET_META_DATA
											| PackageManager.GET_SHARED_LIBRARY_FILES
											| PackageManager.GET_UNINSTALLED_PACKAGES);
					// ���ش�Ӧ�ó���ı�ǩ
					lableName = manager.getApplicationLabel(applicationInfo)
							.toString();
					RunningAppInfo runingAppInfo = new RunningAppInfo(
							packageName, icon, lableName, size);
					// ϵͳ����
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
						runingAppInfo.setSystem(true);
						runingAppInfo.setClear(false);
						sysapp.add(runingAppInfo);
					}
					// �û�����(Ĭ��ѡ��)
					else {
						runingAppInfo.setSystem(false);
						runingAppInfo.setClear(true);
						userapp.add(runingAppInfo);
					}
				} catch (NameNotFoundException ex) {
				}
			}
		}
		runingAppInfos.put(SYSTEM_TYPE_FLAG, sysapp);
		runingAppInfos.put(USER_TYPE_FLAG, userapp);
		return runingAppInfos;
	}

	/**
	 * ����ȫ������
	 * 
	 * @param isdel
	 * @return
	 */
	public List<AppInfo> getAllPackageInfos(boolean isdel) {
		if (isdel) {
			loadAllDataPackageInfo();
		}
		return allPackageInfos;

	}

	public List<AppInfo> getsysPackageInfos(boolean isdel) {
		if (isdel) {
			loadAllDataPackageInfo();
			sysPackageInfos.clear();
			for (AppInfo pi : allPackageInfos) {
				if ((pi.getPackageInfo().applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					sysPackageInfos.add(pi);
				}
			}
		}
		return sysPackageInfos;
	}

	public List<AppInfo> getusePackageInfos(boolean isdel) {
		if (isdel) {
			loadAllDataPackageInfo();
			usePackageInfos.clear();
			for (AppInfo pi : allPackageInfos) {
				if ((pi.getPackageInfo().applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				} else {
					usePackageInfos.add(pi);
				}
			}
		}
		return usePackageInfos;
	}

	/**
	 * ͨ���÷����ҵ������Ѿ���װ�����
	 */
	public void loadAllDataPackageInfo() {
		List<PackageInfo> allInfos = manager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_UNINSTALLED_PACKAGES);
		allPackageInfos.clear();
		for (PackageInfo packageInfo : allInfos) {
			allPackageInfos.add(new AppInfo(packageInfo));

		}

	}

}
