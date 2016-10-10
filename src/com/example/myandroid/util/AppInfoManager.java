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
	 * 单例模式
	 */
	private static AppInfoManager appInfoManager = null;

	public static AppInfoManager getAppInfoManager(Context context) {
		if (appInfoManager == null) {
			appInfoManager = new AppInfoManager(context);
		}
		return appInfoManager;
	}

	/**
	 * 清理空闲后台
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
		// 获取所有正在运行应用
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
			String packageName = appProcessInfo.processName; // 正在运行程序进程名
			int pid = appProcessInfo.pid; // 正在运行程序进程ID
			int importance = appProcessInfo.importance; // 正在运行程序进程级别
			// 服务进程（包括）级别以下进程
			if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				Drawable icon; // 所取数据：运行中程序图标
				String lableName; // 所取数据：运行中程序名称
				long size; // 所取数据：运行中程序所占内存
				// 获取每个进程ID(集合)占用的内存大小(集合), pid和MemoryInfo是一一对应的
				// 返回一个或多个进程的内存使用情况
				Debug.MemoryInfo[] memoryInfos = activityManager
						.getProcessMemoryInfo(new int[] { pid });
				size = (memoryInfos[0].getTotalPrivateDirty()) * 1024;// 获取个人
				try {
					icon = manager.getApplicationIcon(packageName);
					ApplicationInfo applicationInfo = manager
							.getApplicationInfo(
									packageName,
									PackageManager.GET_META_DATA
											| PackageManager.GET_SHARED_LIBRARY_FILES
											| PackageManager.GET_UNINSTALLED_PACKAGES);
					// 返回此应用程序的标签
					lableName = manager.getApplicationLabel(applicationInfo)
							.toString();
					RunningAppInfo runingAppInfo = new RunningAppInfo(
							packageName, icon, lableName, size);
					// 系统进程
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
						runingAppInfo.setSystem(true);
						runingAppInfo.setClear(false);
						sysapp.add(runingAppInfo);
					}
					// 用户进程(默认选中)
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
	 * 返回全部数据
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
	 * 通过该方法找到所有已经安装的软件
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
