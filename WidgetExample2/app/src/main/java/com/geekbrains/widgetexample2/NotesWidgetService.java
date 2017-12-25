package com.geekbrains.widgetexample2;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class NotesWidgetService extends RemoteViewsService {
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetNotesFactory(this.getApplicationContext(), intent);
    }
}