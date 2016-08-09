package ru.yandex.yamblz.ui.activities;

import java.util.List;

import ru.yandex.yamblz.loaders.model.Skate;

/**
 * Created by vorona on 09.08.16.
 */

public interface ListProvider {
    List<Skate> getList();
}
