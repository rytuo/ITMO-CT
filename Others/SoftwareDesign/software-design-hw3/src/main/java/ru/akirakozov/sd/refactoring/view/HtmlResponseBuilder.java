package ru.akirakozov.sd.refactoring.view;

import java.util.List;
import java.util.stream.Collectors;

import ru.akirakozov.sd.refactoring.entity.Product;

public class HtmlResponseBuilder implements ResponseBuilder {

    private final HtmlTagBuilder tb = new HtmlTagBuilder();

    public String createGetTemplate(List<Product> products) {
        return tb.document(
                products.stream()
                        .map(product -> createProductTemplate(product) + tb.br())
                        .collect(Collectors.joining())
        );
    }

    public String createMaxTemplate(Product product) {
        return tb.document(
                tb.h1("Product with max price: "),
                createProductTemplate(product) + tb.br()
        );
    }

    public String createMinTemplate(Product product) {
        return tb.document(
                tb.h1("Product with min price: "),
                createProductTemplate(product) + tb.br()
        );
    }

    public String createUnknownCommandTemplate(String command) {
        return "Unknown command: " + command;
    }

    public String createOKTemplate() {
        return "OK";
    }

    public String createSumTemplate(Long sum) {
        return tb.document("Summary price: " + toStringSafe(sum));
    }

    public String createCountTemplate(Long count) {
        return tb.document("Number of products: " + toStringSafe(count));
    }

    public String getContentType() {
        return "text/html";
    }

    private String toStringSafe(Object o) {
        return o == null ? "" : o.toString();
    }

    private String createProductTemplate(Product product) {
        return product.getName() + "\t" + product.getPrice();
    }
}
