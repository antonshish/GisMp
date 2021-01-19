package ru.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;

public class GisMpApi {
    private final static String API_URL = "https://ismp.crpt.ru";
    private final static String CREATE_DOCUMENT_RU = "/api/v3/lk/documents/commissioning/contract/create";


    public Content documentCreate(GisMpDocument document, String token) throws IOException {

        Content content = Request.Post(API_URL + CREATE_DOCUMENT_RU + "?pg=" + document.getPg())
                .addHeader("Authorization", "Bearer " + token)
                .bodyString(getJsonString(document), ContentType.APPLICATION_JSON)
                .execute().returnContent();
        return content;
    }

    public String getJsonString (GisMpDocument document) throws JsonProcessingException {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(document);
    }

    public enum DocumentFormat {
        MANUAL, XML, CSV
    }

    public static class GisMpDocument {

        private DocumentFormat document_Format;
        private String pg;
        private String product_document;
        private String product_group;
        private String signature;
        private String type;

        public GisMpDocument() {
        }

        public GisMpDocument(DocumentFormat document_Format,
                             String pg, String product_document,
                             String product_group,
                             String signature,
                             String type) {
            this.document_Format = document_Format;
            this.pg = pg;
            this.product_document = product_document;
            this.product_group = product_group;
            this.signature = signature;
            this.type = type;
        }

        public DocumentFormat getDocument_Format() {
            return document_Format;
        }

        public GisMpDocument(DocumentFormat document_Format, String pg, String product_document, String signature, String type) {
            this.document_Format = document_Format;
            this.pg = pg;
            this.product_document = product_document;
            this.signature = signature;
            this.type = type;
        }

        public void setDocument_Format(DocumentFormat document_Format) {
            this.document_Format = document_Format;
        }
        @JsonIgnore
        public String getPg() {
            return pg;
        }

        public void setPg(String pg) {
            this.pg = pg;
        }

        public String getProduct_document() {
            return product_document;
        }

        public void setProduct_document(String product_document) {
            this.product_document = product_document;
        }

        public String getProduct_group() {
            return product_group;
        }

        public void setProduct_group(String product_group) {
            this.product_group = product_group;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
