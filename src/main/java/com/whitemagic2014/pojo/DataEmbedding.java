package com.whitemagic2014.pojo;

import java.util.List;

/**
 * gpt用 数据向量模型
 */
public class DataEmbedding {

    String context;

    List<Double> contextEmbedding;

    Double embeddingWithQuery;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public List<Double> getContextEmbedding() {
        return contextEmbedding;
    }

    public void setContextEmbedding(List<Double> contextEmbedding) {
        this.contextEmbedding = contextEmbedding;
    }

    public Double getEmbeddingWithQuery() {
        return embeddingWithQuery;
    }

    public void setEmbeddingWithQuery(Double embeddingWithQuery) {
        this.embeddingWithQuery = embeddingWithQuery;
    }

    @Override
    public String toString() {
        return "DataEmbedding{" +
                "context='" + context + '\'' +
                ", embeddingWithQuery=" + embeddingWithQuery +
                '}';
    }
}
