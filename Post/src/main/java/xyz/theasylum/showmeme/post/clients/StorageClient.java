package xyz.theasylum.showmeme.post.clients;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xyz.theasylum.showmeme.post.domain.dto.ImageDTO;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StorageClient {


    RestTemplate template;
    private DiscoveryClient discoveryClient;
    ThreadLocalRandom random;

    public StorageClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        template = new RestTemplate();
    }

    public void test() {

        System.out.println("Test!");
    }

    public String uploadImage(String filename, byte[] file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource bar = new ByteArrayResource(file){
            @Override
            public String getFilename() {
                return filename;
            }
        };
        body.add("File", bar);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = template.postForEntity(getUrl(filename), requestEntity, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        return response.getBody();
    }

    public ImageDTO getImage(String hash) {
        ResponseEntity<ImageDTO> response = template.getForEntity(getUrl("hash", hash), ImageDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        return response.getBody();
    }


    private String getUrl(String operation, String... additional) {
        List<ServiceInstance> services = discoveryClient.getInstances("storage");
        int index = ThreadLocalRandom.current().nextInt(services.size());
        StringBuilder url = new StringBuilder(services.get(index).getUri().toString());
        url.append("/api/files/");
        url.append(operation);
        for (String s : additional) {
            url.append("/");
            url.append(s);
        }
        return url.toString();
    }

}
