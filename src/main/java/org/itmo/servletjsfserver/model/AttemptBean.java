package org.itmo.servletjsfserver.model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.itmo.servletjsfserver.DbHelper;
import org.itmo.servletjsfserver.model.Attempt;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("tableBean")
@RequestScoped
public class AttemptBean implements Serializable {
    private LazyDataModel<Attempt> lazyModel;

    public AttemptBean() {
        lazyModel = new LazyDataModel<Attempt>() {
            @Override
            public int count(Map<String, FilterMeta> filters) {
                // Возвращаем общее количество записей в таблице
                return DbHelper.getAllAttempts().size();
            }

            @Override
            public List<Attempt> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
                List<Attempt> attempts = DbHelper.getAllAttempts();

                if (sortBy != null && !sortBy.isEmpty()) {
                    SortMeta sortMeta = sortBy.values().iterator().next();
                    attempts.sort((a1, a2) -> {
                        int result;
                        try {
                            if (sortMeta.getOrder().equals(SortOrder.ASCENDING)) {
                                result = a1.getClass().getDeclaredField(sortMeta.getField()).get(a1).toString().compareTo(a2.getClass().getDeclaredField(sortMeta.getField()).get(a2).toString());
                            } else {
                                result = a2.getClass().getDeclaredField(sortMeta.getField()).get(a2).toString().compareTo(a1.getClass().getDeclaredField(sortMeta.getField()).get(a1).toString());
                            }
                        } catch (Exception e) {
                            result = 0;
                        }
                        return result;
                    });
                }

                // Применяем фильтрацию (если необходимо)
                // if (filters != null && !filters.isEmpty()) {
                //     // Примените фильтрацию здесь
                // }

                // Возвращаем данные для текущей страницы
                return attempts.subList(first, Math.min(first + pageSize, attempts.size()));
            }
        };
    }

    public LazyDataModel<Attempt> getAttempts() {
        return lazyModel;
    }
}
