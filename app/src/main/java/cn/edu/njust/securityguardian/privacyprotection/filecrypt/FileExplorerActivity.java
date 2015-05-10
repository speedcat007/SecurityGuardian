package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.njust.securityguardian.R;

public class FileExplorerActivity extends ListActivity {
	private File current_Dir;
	private List<FileListEntry> files;
	private FileListAdapter adapter;
	private ListView filesListView;
	private File previousOpenDirChild;
	private FileExplorerActivity app = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_crypt);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		initRootDir(savedInstanceState);
		files = new ArrayList<FileListEntry>();
		initFileListView();
		listContents(current_Dir);
	}

	private void initFileListView() {
		filesListView = getListView();
		
		adapter = new FileListAdapter(this, files);
		filesListView.setAdapter(adapter);
		filesListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (filesListView.isClickable()) {
					FileListEntry file = (FileListEntry) filesListView
							.getAdapter().getItem(position);
					select(file.getPath());
				}
			}

		});
		filesListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0,
										   final View view, int posiotion, long id) {

				return true;// ..........................................
			}

		});
		registerForContextMenu(filesListView);
	}

	private void select(File file) {
		if (Util.isProtected(file)) {
			new Builder(this)
					.setTitle(getString(R.string.access_denied))
					.setMessage(
							getString(R.string.cant_open_dir, file.getName()))
					.show();
		} else if (file.isDirectory()) {
			listContents(file);

		} else {
			showAction(file);
		}
	}

	private void showAction(final File file) {
		String fileName = file.getName();
		Dialog alertDialog;
		if (fileName.endsWith(".fookey")) {
			alertDialog = new AlertDialog.Builder(this)
					.setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					}).setPositiveButton("解密", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							new AsyncCrypt(app).execute(file);
						}
					}).create();
		} else {
			alertDialog = new AlertDialog.Builder(this)
					.setPositiveButton("加密", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						
							new AsyncCrypt(app).execute(file);
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					}).create();
		}
		alertDialog.show();
	}

	
	public void listContents(File dir) {
		listContents(dir, null);
	}

	private void listContents(File dir, File previousOpenDirChild) {
		if (!dir.isDirectory() || Util.isProtected(dir)) {
			return;
		}
		if (previousOpenDirChild != null) {
			this.previousOpenDirChild = new File(
					previousOpenDirChild.getAbsolutePath());
		} else {
			this.previousOpenDirChild = null;
		}
		new AsyncFinder(this).execute(dir);

	}

	private void initRootDir(Bundle savedInstanceState) {
		File rootDir = new File(Environment.getExternalStorageDirectory()
				.getPath());
		if (rootDir.exists() && rootDir.isDirectory()) {
			current_Dir = rootDir;
		} else {
			current_Dir = new File("/");
		}
	}

	public synchronized void setCurrentDirAndChilren(File dir,
			FileList folderListing) {
		current_Dir = dir;

		List<FileListEntry> children = folderListing.getChildren();

		TextView emptyText = (TextView) findViewById(android.R.id.empty);
		if (emptyText != null) {
			emptyText.setText(R.string.empty_folder);
		}
		files.clear();
		files.addAll(children);
		adapter.notifyDataSetChanged();
		// getActionBar().setSelectedNavigationItem(0);

		if (previousOpenDirChild != null) {
			int position = files.indexOf(new FileListEntry(previousOpenDirChild
					.getAbsolutePath()));
			if (position >= 0)
				filesListView.setSelection(position);
		} else {
			filesListView.setSelection(0);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
