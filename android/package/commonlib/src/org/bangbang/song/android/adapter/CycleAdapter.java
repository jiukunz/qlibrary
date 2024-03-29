package org.bangbang.song.android.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * a cycle adapter, is a decorator pattern.
 * 
 * @see #getSuggestIniSelectPosition()
 * @author bangbang.song@gamil.com
 *
 */
public class CycleAdapter 
extends BaseAdapter
//implements ListAdapter, SpinnerAdapter
{
	private BaseAdapter mDecoratee;
	private int mCycleAccount;
	private int mCount;
	
	public CycleAdapter(BaseAdapter adapter){
		mDecoratee = adapter;
		mCount = adapter.getCount();
		mCycleAccount = Integer.MAX_VALUE;
	}
	
	public int getSuggestIniSelectPosition(){
		return mCycleAccount / mCount / 2 * mCount;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCycleAccount;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDecoratee.getItem(position % mCount);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mDecoratee.getItemId(position % mCount);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return mDecoratee.getView(position % mCount, convertView, parent);
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return mDecoratee.getItemViewType(position % mCount);
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return mDecoratee.getViewTypeCount();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return mDecoratee.hasStableIds();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return mDecoratee.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		mDecoratee.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		mDecoratee.unregisterDataSetObserver(observer);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return mDecoratee.getDropDownView(position % mCount, convertView, parent);
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return mDecoratee.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return mDecoratee.isEnabled(position % mCount);
	}

}
