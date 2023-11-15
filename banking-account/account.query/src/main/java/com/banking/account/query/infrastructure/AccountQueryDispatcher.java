package com.banking.account.query.infrastructure;

import com.banking.cqrs.core.domain.BaseEntity;
import com.banking.cqrs.core.infrastructure.QueryDispatcher;
import com.banking.cqrs.core.queries.BaseQuery;
import com.banking.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();
    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> queryType, QueryHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(queryType, k -> new LinkedList<>());
        handlers.add(handlerMethod);
    }

    @Override
    public <U extends BaseEntity> List<U> sendQuery(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No handler registered for query " + query.getClass().getName());
        }

        if (handlers.size() > 1) {
            throw new RuntimeException("More than one handler registered for query " + query.getClass().getName());
        }

        return handlers.get(0).handle(query);
    }
}
