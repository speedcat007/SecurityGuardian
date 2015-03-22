package cn.edu.njust.securityguardian.privacyprotection.filecrypt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.njust.securityguardian.R;

public class FileListAdapter extends BaseAdapter {

	public static class ViewHolder {
		public TextView resName;
		public ImageView resIcon;
		public TextView resMeta;
	}

	private FileExplorerActivity mContex;
	private List<FileListEntry> files;
	private LayoutInflater myInflater;

	public FileListAdapter(FileExplorerActivity context, List<FileListEntry> files) {
		// TODO Auto-generated constructor stub
		super();
		mContex = context;
		this.files = files;
		myInflater = mContex.getLayoutInflater();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (files == null) {
			return 0;
		} else {
			return files.size();
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (files == null)
			return null;
		else
			return files.get(position);
	}

	public List<FileListEntry> getItems() {
		return files;

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = myInflater.inflate(R.layout.item_file_list, parent, false);
			holder = new ViewHolder();
			holder.resName = (TextView) convertView
					.findViewById(R.id.explorer_resName);
			holder.resMeta = (TextView) convertView
					.findViewById(R.id.explorer_resMeta);
			holder.resIcon = (ImageView) convertView
					.findViewById(R.id.explorer_resIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final FileListEntry currentFile = files.get(position);
		holder.resName.setText(currentFile.getName());
		holder.resIcon.setImageDrawable(Util.getIcon(mContex,
				currentFile.getPath()));
		if (currentFile.getName().compareTo("..") != 0) {
			java.text.DateFormat sdf = new SimpleDateFormat()
					.getDateTimeInstance();
			holder.resMeta.setText(sdf.format(currentFile.getLastModified())
					+ " " + currentFile.getSize() + "B");
		}else {
			holder.resMeta.setText("返回上级目录..");
		}
		return convertView;
	}

}
