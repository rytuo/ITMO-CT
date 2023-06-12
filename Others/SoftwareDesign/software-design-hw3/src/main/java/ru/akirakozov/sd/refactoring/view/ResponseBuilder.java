package ru.akirakozov.sd.refactoring.view;

import java.util.List;

import ru.akirakozov.sd.refactoring.entity.Product;

public interface ResponseBuilder {
    String createGetTemplate(List<Product> products);

    String createMaxTemplate(Product product);

    String createMinTemplate(Product product);

    String createSumTemplate(Long sum);

    String createCountTemplate(Long count);

    String createUnknownCommandTemplate(String command);

    String createOKTemplate();

    String getContentType();
}
