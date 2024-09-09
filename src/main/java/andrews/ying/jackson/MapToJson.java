package andrews.ying.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class MapToJson {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(generateJsonPayload());
    }

    private static String generateJsonPayload() throws JsonProcessingException {
        Map<String, Object> bodyData = new HashMap<>();
        String captureChannelUuid = "c943dd4d-9f82-4f25-a581-acb83a85e550";
        bodyData.put("uuid", captureChannelUuid);
        Map<String, Object> captureData = new HashMap<>();
        Map<String, Object> captureConfigData = new HashMap<>();
        captureConfigData.put("uri", "udp://239.0.0.1:12345?buffer_size=10000000&localaddr=0.0.0.0");
        captureConfigData.put("hlsOutputFolder", "C:\\TrustedApps\\GXP_Platform_Data\\data\\managed\\managed\\o\\e\\a\\202407241307729");
        captureConfigData.put("logLevel", "debug");
        captureConfigData.put("timeout", 40000);
        captureConfigData.put("hlsSegmentFilename", "csu_224.1.1.1_9001_2024-07-24T17_15Z_%06d.ts");
        captureConfigData.put("codec", "libx264");
        captureConfigData.put("segmentLengthSecs", 10);
        captureData.put("app", "ffmpeg-exe");
        captureData.put("config", captureConfigData);
        bodyData.put("capture", captureData);
        Map<String, Object> statusData = new HashMap<>();
        Map<String, Object> statusAppData = new HashMap<>();
        statusAppData.put("provider", "message-queue");
        Map<String, Object> statusAppConfigData = new HashMap<>();
        statusAppConfigData.put("protocol", "tcp");
        statusAppConfigData.put("host", "gxpd123.devlnk.net");
        statusAppConfigData.put("port", 61616);
        statusAppConfigData.put("topic", "fmvcapture");
        Map<String, Object> statusAppConfigCredentialsData = new HashMap<>();
        statusAppConfigCredentialsData.put("username", "bla");
        statusAppConfigCredentialsData.put("password", "bla");
        statusAppConfigData.put("credentials", statusAppConfigCredentialsData);
        statusAppData.put("config", statusAppConfigData);
        statusData.put("app", statusAppData);
        Map<String, Object> statusK8sData = new HashMap<>();
        statusK8sData.put("provider", "api");
        statusData.put("k8s", statusK8sData);
        bodyData.put("status", statusData);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bodyData);
    }
}
