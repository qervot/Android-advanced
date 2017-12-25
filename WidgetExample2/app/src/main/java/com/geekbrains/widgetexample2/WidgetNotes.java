package com.geekbrains.widgetexample2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetNotes extends AppWidgetProvider {

    public static final String UPDATE_WIDGET_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ITEM_ON_CLICK_ACTION = "android.appwidget.action.ITEM_ON_CLICK";
    public static final String NOTE_TEXT = "note_text";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds) {
            updateWidget(context, appWidgetManager, i);
        }
    }

    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equalsIgnoreCase(UPDATE_WIDGET_ACTION)) {
            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, WidgetNotes.class));
            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        }
        if (intent.getAction().equalsIgnoreCase(ITEM_ON_CLICK_ACTION)) {
            String itemText = intent.getStringExtra(NOTE_TEXT);
            if (!itemText.equalsIgnoreCase("")) {
                Toast.makeText(context, itemText, Toast.LENGTH_SHORT).show();
            }
        }
        super.onReceive(context, intent);
    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.layout_widget);
        setList(rv, context, appWidgetId);
        setListClick(rv, context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, NotesWidgetService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.list_view, adapter);
        rv.setEmptyView(R.id.list_view, R.id.empty_view);
    }

    void setListClick(RemoteViews rv, Context context) {
        Intent listClickIntent = new Intent(context, WidgetNotes.class);
        listClickIntent.setAction(ITEM_ON_CLICK_ACTION);
        PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0,
                listClickIntent, 0);
        rv.setPendingIntentTemplate(R.id.list_view, listClickPIntent);
    }
}

