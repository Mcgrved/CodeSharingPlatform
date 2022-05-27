package platform;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CodeService {

    @Autowired
    private Code currentCode;

    @Autowired
    private CodeRepository codeRepository;

    public Code getCodeSnippet(UUID id) {
        if (codeRepository.existsById(id)) {
            Code foundSnippet = codeRepository.findByCodeId(id);
            subtractTime(foundSnippet);
            subtractViews(foundSnippet);
            deleteIfDepleted(foundSnippet);
            return foundSnippet;
        }
        return null;
    }

    public Map<String, UUID> postCodeSnippet(Code code) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        code.setDate(LocalDateTime.now().format(formatter));
        Logger.getGlobal().info("" + code.getTime());
        checkRestrictions(code);
        codeRepository.save(code);
        return Map.of("id", code.getCodeId());
    }

    public List<Code> getLatestList() {
        ArrayList<Code> codeArrayList = new ArrayList<>(codeRepository.findByIsRestrictedFalse());

        Collections.reverse(codeArrayList);
        return codeArrayList.stream().limit(10).collect(Collectors.toUnmodifiableList());
    }

    private void subtractTime(Code code) {
        if (code.isTimeRestricted()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
            if (Objects.equals(code.getOriginalTime(), null)) {
                Logger.getGlobal().info("Once");
                code.setOriginalTime(code.getTime());
            }
            code.setTime(code.getOriginalTime()  - Duration.between(
                    LocalDateTime.parse(code.getDate(), formatter),
                    LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter)).getSeconds());
            codeRepository.save(code);
        }
    }
    private void subtractViews(Code code) {
        if (code.isViewRestricted()) {
            code.setViews(code.getViews() - 1);
            codeRepository.save(code);
        }
    }

    private void deleteIfDepleted(Code code) {
        if (code.isTimeRestricted() && code.getTime() <= 0 ||
                code.isViewRestricted() && code.getViews() <= 0) {
            codeRepository.deleteById(code.getCodeId());
            codeRepository.save(code);
        }
    }

    private void checkRestrictions(Code code) {
        if (code.getTime() > 0) {
            code.setTimeRestricted(true);
            code.setRestricted(true);
        }
        if (code.getViews() > 0) {
            code.setViewRestricted(true);
            code.setRestricted(true);
        }
        if (!code.isTimeRestricted() && !code.isViewRestricted()) {
            code.setRestricted(false);
        }
    }

    public ResponseEntity<?> getNewWebCode() {
        String response = "<html>\n" +
                "<head>\n" +
                "<title>" + "Create" + "</title>\n" +
                "<script>\n" +
                "function send() {\n" +
                "    let object = {\n" +
                "        \"code\": document.getElementById(\"code_snippet\").value\n" +
                "    };\n" +
                "    \n" +
                "    let json = JSON.stringify(object);\n" +
                "    \n" +
                "    let xhr = new XMLHttpRequest();\n" +
                "    xhr.open(\"POST\", '/api/code/new', false)\n" +
                "    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');\n" +
                "    xhr.send(json);\n" +
                "    \n" +
                "    if (xhr.status == 200) {\n" +
                "      alert(\"Success!\");\n" +
                "    }\n" +
                "}" +
                "</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form>\n" +
                "<textarea id='code_snippet'>" +
                currentCode.getCode() +
                " </textarea>\n" +
                "<button id='send_snippet' type='submit' onclick='send()'>Submit</button>" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
        return ResponseEntity.ok(response);
    }
}
