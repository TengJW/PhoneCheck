package com.example.myandroid.Base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.myandroid.bean.ClassInfo;

import android.content.Context;
import android.renderscript.Element.DataType;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseBaseAdapter<DataType> extends BaseAdapter {
	protected Context context;
	private LayoutInflater layoutInflater;
	/** ��ǰ���������������� */
	private List<DataType> datalist = new ArrayList<DataType>();

	/**
	 * ���췽�� ���ز���ʱ��Ҫ�õ�layoutinflater ���ڹ��췽���г�ʼ��
	 * 
	 * @param context
	 */
	public BaseBaseAdapter(Context context) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/** ��ȡ�����������������б�(List����) */
	public List<DataType> getDataList() {
		return datalist;
	}

	/** �������������������б�(List����),���һ������ */
	public void addDataToAdapter(DataType data) {
		datalist.add(data);
	}

	/** ���������������������б�(List����)������,Ϊָ������(��������ԭ����) */
	public void setDataTypeToAdapter(DataType data) {
		datalist.clear();
		datalist.add(data);
	}

	/** ���������������������б�(List����)������,Ϊָ������(��������ԭ����) */
	public void setDataTypeListToAdapter(List<DataType> datas) {
		datalist.clear();
		datalist.addAll(datas);
	}

	@Override
	public int getCount() {
		return datalist == null ? 0 : datalist.size();
	}

	@Override
	public DataType getItem(int position) {
		return datalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView, parent);
	}

	/**
	 * @see
	 * @see ���б����һ����ʾ���������б����������Ļʱ�����б������ݱ仯������notifyDataSetChanged()����ʱ ������������
	 * @param position
	 *            ��ǰ�б������б��е�λ��
	 */
	public abstract View getItemView(int position, View convertView,
			ViewGroup parent);

}
