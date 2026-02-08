package com.shaker.ecommicro.aichat.rag;


import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class FilesReaderTika {


    private final VectorStore vectorStore;


    @Value("classpath:./shakerecommerce_documents/*.pdf")
    Resource[] shakerEcomFiles;



    public FilesReaderTika(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    @PostConstruct
    public void loadPfsToVectorStore() {
        List<Document> documents = new ArrayList<>();

        for (Resource pdf : shakerEcomFiles) {
            TikaDocumentReader reader = new TikaDocumentReader(pdf);
            TextSplitter textSplitter = TokenTextSplitter.builder()
                    .withChunkSize(60)
                    .withMaxNumChunks(200)
                    .build();

            documents.addAll(textSplitter.split(reader.get()));
        }
        vectorStore.add(documents);
    }
}
