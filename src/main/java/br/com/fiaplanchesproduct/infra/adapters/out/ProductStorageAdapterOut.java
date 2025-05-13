package br.com.fiaplanchesproduct.infra.adapters.out;

import br.com.fiaplanchesproduct.application.ports.out.ProductStoragePortOut;
import br.com.fiaplanchesproduct.infra.exception.handler.StorageException;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
public class ProductStorageAdapterOut implements ProductStoragePortOut {

    private final MinioClient minio;

    @Value("${minio.bucket}")
    private String bucket;

    public ProductStorageAdapterOut(MinioClient minio) {
        this.minio = minio;
    }

    @PostConstruct
    public void initBucket() {
        try {
            boolean exists = minio.bucketExists(
                BucketExistsArgs.builder().bucket(bucket).build()
            );
            if (!exists) {
                minio.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new StorageException("Erro ao criar bucket", e);
        }
    }

    public String store(MultipartFile file) {
        log.info("Start metodo store");
        String original = file.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(original);
        String key = UUID.randomUUID() + "." + ext;
        try {
            minio.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException("Falha no upload", e);
        }
        log.info("Imagem '{}' armazenada no bucket '{}'", key, bucket);
        log.info("End metodo store");
        return key;
    }

    public String generatePresignedUrlImage(String key) {
        try {
            log.info("Start metodo generatePresignedUrlImage");
            return minio.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucket)
                    .object(key)
                    .build()
            );
                            
        } catch (Exception e) {
            throw new StorageException("Falha ao baixar objeto", e);
        }
    }

    /**
     * Remove um objeto do bucket informado.
     *
     * @param key  nome (chave) do objeto no bucket
     */
    public void delete(String key) {
        try {
            log.info("Start metodo delete");
            // Remove o objeto
            minio.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(key)
                            .build());

            log.info("Imagem '{}' removida do bucket '{}'", key, bucket);
            log.info("End metodo delete");
        } catch (Exception ex) {
            log.error("Falha ao remover '{}' em '{}'", key, bucket, ex);
            throw new RuntimeException("Erro ao deletar imagem", ex);
        }
    }
}
