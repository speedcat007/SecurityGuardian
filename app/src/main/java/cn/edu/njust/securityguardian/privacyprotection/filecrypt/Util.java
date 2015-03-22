package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;

import cn.edu.njust.securityguardian.R;

public class Util {
	public static Drawable getIcon(Context mContext, File file) {
		if (Util.isProtected(file)) {
			return mContext.getResources().getDrawable(
					R.drawable.filetype_sys_dir);

		} else if (!file.isFile()) // dir
		{
			if (Util.isSdCard(file)) {
				return mContext.getResources().getDrawable(
                    R.drawable.filetype_sdcard);
			} else {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_dir);
			}
		} else // file
		{
			String fileName = file.getName();
			if (Util.isProtected(file)) {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_sys_file);
			}
			if(fileName.endsWith(".fookey")){
				return mContext.getResources().getDrawable(R.drawable.filetype_locked);
			}
			if (fileName.endsWith(".apk")) {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_apk);
			}
			if (fileName.endsWith(".zip")) {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_zip);
			} else if (Util.isMusic(file)) {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_music);
			} else if (Util.isVideo(file)) {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_video);
			} else if (Util.isPicture(file)) {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_image);
			} else {
				return mContext.getResources().getDrawable(
						R.drawable.filetype_generic);
			}
		}

	}

	public static boolean isSdCard(File file) {

		try {
			return (file.getCanonicalPath().equals(Environment
					.getExternalStorageDirectory().getCanonicalPath()));
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean isProtected(File path) {
		return (!path.canRead() && !path.canWrite());
	}

	static boolean isMusic(File file) {
		Uri uri = Uri.fromFile(file);
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				MimeTypeMap.getFileExtensionFromUrl(uri.toString()));
		if (type == null)
			return false;
		else
			return (type.toLowerCase().startsWith("audio/"));
	}

	static boolean isVideo(File file) {
		Uri uri = Uri.fromFile(file);
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				MimeTypeMap.getFileExtensionFromUrl(uri.toString()));

		if (type == null)
			return false;
		else
			return (type.toLowerCase().startsWith("video/"));
	}

	public static boolean isPicture(File file) {
		Uri uri = Uri.fromFile(file);
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				MimeTypeMap.getFileExtensionFromUrl(uri.toString()));

		if (type == null)
			return false;
		else
			return (type.toLowerCase().startsWith("image/"));
	}
}
