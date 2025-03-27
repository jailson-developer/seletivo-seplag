package br.gov.servidor.modules.servidor.services;


import br.gov.servidor.core.exceptions.RegraNegocioException;
import br.gov.servidor.core.s3.MinioSendFile;
import br.gov.servidor.core.s3.MinioService;
import br.gov.servidor.modules.servidor.dtos.FotoResponseDto;
import br.gov.servidor.modules.servidor.models.FotoPessoa;
import br.gov.servidor.modules.servidor.models.Pessoa;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServidorService {

    @Inject
    MinioService minioService;

    @ConfigProperty(name = "minio.bucket-foto-servidor")
    String bucketFotoServidor;

    List<String> EXTENSOES_PERMITIDAS = List.of(
            ".jpg",
            ".jpeg",
            ".png",
            ".gif",
            ".bmp",
            ".tiff",
            ".avif",
            ".webp",
            ".svg");


    @Transactional
    public void uploadFotos(Long pessoaId, List<FileUpload> files) throws IOException {
        Log.infof("Uploading fotos pessoa %s", pessoaId);
        Pessoa pessoa = Pessoa.<Pessoa>findByIdOptional(pessoaId).orElseThrow(() -> new RuntimeException("Servidor não encontrado"));
        LocalDate hoje = LocalDate.now();
        for (FileUpload file : files) {
            try (FileInputStream stream = new FileInputStream(file.uploadedFile().toFile())) {
                byte[] byteArray = IOUtils.toByteArray(stream);
                String hash = DigestUtils.sha256Hex(byteArray);
                String extensao = Optional.of(file.fileName())
                        .filter(f -> f.contains("."))
                        .map(f -> f.substring(f.lastIndexOf(".")))
                        .orElse("");
                hash = String.format("%s%s", hash, extensao);
                if (!EXTENSOES_PERMITIDAS.contains(extensao.toLowerCase())) {
                    throw new RegraNegocioException("Ops! O arquivo que você selecionou não é uma imagem. Tente novamente com um arquivo de imagem.");
                }

                MinioSendFile minioSendFile = new MinioSendFile();
                minioSendFile.setBucket(bucketFotoServidor);
                minioSendFile.setName(String.format("%s/%s/%s", hoje.getYear(), hoje.getMonthValue(), hash));
                minioSendFile.setContentType(file.contentType());
                minioSendFile.setFile(new ByteArrayInputStream(byteArray));

                FotoPessoa fotoPessoa = new FotoPessoa();
                fotoPessoa.setPessoa(pessoa);
                fotoPessoa.setBucket(minioSendFile.getBucket());
                fotoPessoa.setHash(minioSendFile.getName());

                minioService.enviarArquivo(minioSendFile);
                pessoa.getFotos().add(fotoPessoa);
            }
        }
        pessoa.persist();
    }

    public FotoResponseDto buscarFoto(FotoPessoa foto) {
        return FotoResponseDto.builder()
                .url(minioService.retornarUrl(foto.getBucket(), foto.getHash()))
                .nome(StringUtils.substringAfterLast(foto.getHash(), "/"))
                .build();
    }
}
