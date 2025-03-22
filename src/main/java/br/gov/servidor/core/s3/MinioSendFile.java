package br.gov.servidor.core.s3;

import lombok.*;

import java.io.InputStream;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MinioSendFile {
    private String bucket;
    private InputStream file;
    private String name;
    private String contentType;
}
