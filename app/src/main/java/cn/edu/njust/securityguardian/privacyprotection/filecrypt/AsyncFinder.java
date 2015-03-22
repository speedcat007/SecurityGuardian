package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.njust.securityguardian.R;

public class AsyncFinder extends AsyncTask<File, Integer, FileList> {

	private final String TAG = AsyncFinder.class.getName();
	private FileExplorerActivity caller;
	private ProgressDialog waitDialog;
	private File currentDir;

	public AsyncFinder(FileExplorerActivity call) {
		this.caller = call;
	}

	@Override
	protected void onPostExecute(FileList result) {
		// TODO Auto-generated method stub
		FileList childFilesList = result;
		Log.v(TAG, "Children for " + currentDir.getAbsolutePath() + " received");

		if (waitDialog != null && waitDialog.isShowing()) {
			waitDialog.dismiss();
		}
		Log.v(TAG, "Children for " + currentDir.getAbsolutePath()
                + " passed to caller");
		caller.setCurrentDirAndChilren(currentDir, childFilesList);
	}

	@Override
	protected FileList doInBackground(File... params) {
		// TODO Auto-generated method stub
		Thread waitForASec = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				waitDialog = new ProgressDialog(caller);
				waitDialog.setTitle("");
				waitDialog.setMessage(caller
						.getString(R.string.querying_filesys));
				waitDialog.setIndeterminate(true);
				try {
					Thread.sleep(100);
					if (this.isInterrupted()) {
						return;
					} else {
						caller.runOnUiThread(new Runnable() {

							@Override
							public void run() {

								if (waitDialog != null)
									waitDialog.show();
							}
						});

					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG,
                            "Progressbar waiting thread encountered exception ",
                            e);
					e.printStackTrace();
				}
			}
		};
		caller.runOnUiThread(waitForASec);
		currentDir = params[0];
		Log.v(TAG,
                "Received directory to list paths - "
                        + currentDir.getAbsolutePath());

		String[] children = currentDir.list();
		FileList listing = new FileList(new ArrayList<FileListEntry>());
		List<FileListEntry> childFiles = listing.getChildren();
		FileListEntry child;

		// Map<String, Long> dirSizes = Util.getDirSizes(currentDir);
		int fileL = 0;
		for (String fileName : children) {
			File f = new File(currentDir.getAbsolutePath() + File.separator
					+ fileName);
			if (!f.exists()) {
				continue;
			}
			if (Util.isProtected(f)) {
				continue;
			}
			if (f.isHidden()) {
				continue;
			}

			child = new FileListEntry();
			child.setName(fileName);
			child.setPath(f);
			if (f.isDirectory()) {
				child.setSize(f.getTotalSpace());
			} else {
				child.setSize(f.length());
			}
			child.setLastModified(new Date(f.lastModified()));
			for (int i = 0; i <= childFiles.size(); ++i) {
				if (f.isDirectory()) {
					if ((i < fileL)
							&& (childFiles.get(i).getName()
									.compareToIgnoreCase(fileName) > 0)) {
						childFiles.add(i, child);
						break;
					}
					if (i == fileL) {
						childFiles.add(i, child);
						break;
					}
				} else {
					if (i == childFiles.size()) {
						childFiles.add(i, child);
						break;
					}
					if ((i >= fileL)
							&& (childFiles.get(i).getName()
									.compareToIgnoreCase(fileName) > 0)) {
						childFiles.add(i, child);
						break;
					}

				}

			}
			if (f.isDirectory())
				fileL++;
		}
		// FileListSorter sorter = new FileListSorter(caller);
		// Collections.sort(childFiles, sorter);
		if (currentDir.getParent() != null) {
			child = new FileListEntry();
			child.setName("..");
			child.setPath(currentDir.getParentFile());
			childFiles.add(0, child);
		}
		Log.v(TAG, "Will now interrupt thread waiting to show progress bar");
		if (waitForASec.isAlive()) {
			try {
				waitForASec.interrupt();
			} catch (Exception e) {
				Log.e(TAG, "Error while interrupting thread", e);
			}
		}
		return listing;
	}

}
