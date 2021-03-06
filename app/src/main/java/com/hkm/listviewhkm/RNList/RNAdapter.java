package com.hkm.listviewhkm.RNList;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkm.datamodel.RouteNode;
import com.hkm.listviewhkm.holder.Pattern;

import java.util.ArrayList;

import static com.hkm.U.Content.current_sketch_map;
import static com.hkm.U.Content.screensizeX;
import static com.hkm.listviewhkm.model.C.status;
import static com.hkm.listviewhkm.model.C.status.COMPLETE;

/**
 * Created by Hesk on 30/6/2014.
 */
public class RNAdapter extends EditPlainAdapter {
    public final String TAG = "RNAInspector";

    public RNAdapter(int layout, Context context, ArrayList<RouteNode> c) {
        super(layout, context, c);
    }

    public RNAdapter(int layout, Context context) {
        super(layout, context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Cursor c = getCursor();
        final int n = c.getPosition();
        Log.d(TAG, n + " - create view");
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(layout, null);

        return newPattern(n, context, v);
    }

    protected status getStatus() {
        //C.status.INITIAL
        return COMPLETE;
    }

    protected View newPattern(int n, Context context, View v) {
        final Pattern p = new Pattern(n, context, this);
        p.addConvertViewToElements(v);
        p.set_interactive_screen_width(screensizeX);
        p.setStatus(getStatus());
        p.startEngine();
        patternlist.put(n, p);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Cursor c = getCursor();
        final int n = c.getPosition();
        Log.d(TAG, n + " - bind view");

        //   LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //    View v = mInflater.inflate(layout, null);

        RouteNode rn = current_sketch_map.get_route_node_at(n);
        Pattern p = patternlist.get(n);
        if (p == null) {
            newPattern(n, context, view);
            p = patternlist.get(n);
        } else {
            p.getCacheView(view).startEngine();
            p.setStatusNoAnimation();
        }

        //p.addConvertViewToElements(view);

        p.bind_data2display(rn);

    }

}
