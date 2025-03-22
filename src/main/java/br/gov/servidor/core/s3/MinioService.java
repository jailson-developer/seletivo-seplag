package br.gov.servidor.core.s3;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.resource.spi.ConfigProperty;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MinioService {
    @Inject
    MinioClient minioClient;

    public void enviarArquivo(MinioSendFile file) {
        prepararBucket(file.getBucket());
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(file.getBucket()).object(file.getName()).stream(
                                    file.getFile(), -1, 10485760)
                            .contentType(file.getContentType())
                            .build());
        } catch (ErrorResponseException | ServerException | NoSuchAlgorithmException | XmlParserException |
                 IOException | InvalidResponseException | InvalidKeyException | InternalException |
                 InsufficientDataException e) {
            Log.error("Falha ao enviar o arquivo", e);
            throw new RuntimeException(e);
        }
    }

    public void prepararBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                Log.infof("Bucket %s criado com sucesso", bucketName);
            }
        } catch (ErrorResponseException | NoSuchAlgorithmException | InsufficientDataException | InternalException |
                 InvalidKeyException | InvalidResponseException | IOException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

//    @SneakyThrows
//    public FotoResponseDTO buscarArquivo(String bucket, String hash) {
//        String url = minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .method(Method.GET)
//                        .bucket(bucket)
//                        .object(hash)
//                        .expiry(5, TimeUnit.MINUTES)
//                        .build());
//        return FotoResponseDTO.builder()
//                .url(url)
//                .build();
//    }
}
