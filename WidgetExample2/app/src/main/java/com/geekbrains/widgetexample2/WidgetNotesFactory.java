package com.geekbrains.widgetexample2;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class WidgetNotesFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    private List<Note> records;

    public WidgetNotesFactory(Context context, Intent intent) {
        mContext = context;
    }

    public void onCreate() {
        records = new ArrayList<Note>();
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(mContext.getPackageName(),
                R.layout.item_widget);
        rView.setTextViewText(R.id.text, records.get(position).toString());
        Intent clickIntent = new Intent();
        clickIntent.putExtra(WidgetNotes.NOTE_TEXT, records.get(position).toString());
        rView.setOnClickFillInIntent(R.id.text, clickIntent);
        return rView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        records.clear();
        NoteDataSource notesDataSource = new NoteDataSource(mContext);
        notesDataSource.open();
        records = notesDataSource.getAllNotes();
        notesDataSource.close();
    }

    @Override
    public void onDestroy() {

    }

}
