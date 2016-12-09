package com.scottcrossen42.familymap.ui.RecyclerView.PersonRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scottcrossen42.familymap.R;
import com.scottcrossen42.familymap.ui.activities.IRecyclerActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<ChildViewHolder> implements FamilyMapAdapter {

    private LayoutInflater mInflater;
    private IRecyclerActivity activity;
    private List<Element> items;

    public SearchAdapter(Context context, IRecyclerActivity activity, List<Element> objects) {
        this.activity = activity;
        mInflater = LayoutInflater.from(context);
        items = objects;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup viewGroup, int view_type) {
        View view = mInflater.inflate(R.layout.person_child_element, viewGroup,false);
        return new ChildViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder childViewHolder, int i) {
        Element child = items.get(i);
        childViewHolder.personChildImage.setImageDrawable(child.getIcon());
        childViewHolder.personChildTextUpper.setText(child.getTextUpper());
        childViewHolder.personChildTextLower.setText(child.getTextLower());
        childViewHolder.setCallBack(child.getParent(), child.getId());
    }

    public void swap(List<Element> new_data) {
        items = new_data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return items.size(); }
    public void elementClicked(int parent_index, String id) { activity.onElementClicked(parent_index, id); }

}
